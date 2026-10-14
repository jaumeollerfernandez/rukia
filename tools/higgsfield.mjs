// Genera con la API de Higgsfield (Grok Image 2.0) las imágenes de MEDIA_PENDIENTE.md.
// Necesita HF_API_KEY_ID y HF_API_KEY_SECRET en el entorno. Por defecto NO gasta nada: solo lista qué haría.
//
//   node tools/higgsfield.mjs --ref alicia,nuria,montse     genera las fotos de referencia en tools/refs/<caso>/ (con --go)
//   node tools/higgsfield.mjs IMG_0393.jpg --go             una imagen
//   node tools/higgsfield.mjs --only gonpi/berta_ --go      todas las que contienen ese texto en su destino
//   node tools/higgsfield.mjs --all --budget 5 --cost 0.05 --go    todas, parando al llegar a 5 USD estimados
//
// Las imágenes se guardan en tools/out/higgsfield/<destino>, NUNCA sobre el proyecto (hay placeholders y fotos tuyas
// con el mismo nombre). Cuando te gusten, cópialas a su sitio: la ruta dentro de tools/out/higgsfield es la del caso.
// Opciones: --model <ruta> (por defecto xai/grok-imagine-image-2.0), --resolution 1k|2k, --quality low|medium,
//           --clues (las marcadas 🔎), --group <regex> (por el encabezado de MEDIA_PENDIENTE: "^(Antes del D1|D[1-7])$"); se suman
//           --ref-oficial a,b,c (fotos de media/photos que usan como referencia su foto oficial ya existente en vez de la ficha),
//           --refs-dir <carpeta> (si hay un archivo con el mismo nombre que una referencia, usa ese; p. ej. versiones degradadas),
//           --refs-fotos (cada referencia se sustituye por su foto oficial media/photos/<ficha>.jpg si existe),
//           --sin-ref (genera sin fotos de referencia: caras nuevas solo con la descripción de la ficha),
//           --force (regenera aunque ya exista o esté marcada [x] en MEDIA_PENDIENTE.md), --concurrency N (por defecto 2).
// Grok admite hasta 10 imágenes de referencia (image_urls). Las fichas {movil}/{movil_texto} van dentro del prompt.
// Campos y rutas: https://docs.higgsfield.ai/docs/models/grok-image-2/generate-and-edit.md (compruébalos si responde 4xx).
import { spawnSync } from "node:child_process";
import { existsSync, mkdirSync, readFileSync, unlinkSync, writeFileSync } from "node:fs";
import { basename, dirname, extname, join } from "node:path";
import { fileURLToPath } from "node:url";

const caso = "ruk-93429049";
const root = join(dirname(fileURLToPath(import.meta.url)), "..");
const API = "https://api.higgsfield.ai";
const { HF_API_KEY_ID: keyId, HF_API_KEY_SECRET: keySecret } = process.env;
const args = process.argv.slice(2);
const flag = (n) => args.includes(n);
const opt = (n, d) => (flag(n) ? args[args.indexOf(n) + 1] : d);
const go = flag("--go");
const cost = Number(opt("--cost", 0));
const budget = Number(opt("--budget", Infinity));
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));

if (go && !(keyId && keySecret)) throw new Error('Faltan HF_API_KEY_ID / HF_API_KEY_SECRET en el entorno (setx HF_API_KEY_ID "…" y reabre la terminal)');

// Grok no tiene 4:5: el más cercano es 3:4.
const RATIOS = new Set(["auto", "1:1", "1:2", "2:1", "3:2", "2:3", "4:3", "3:4", "16:9", "9:16"]);
const ratioOf = (prompt) => {
  const r = prompt.match(/Aspect ratio (\d+:\d+)/)?.[1] ?? "1:1";
  return r === "4:5" ? "3:4" : RATIOS.has(r) ? r : "1:1";
};
const MIME = { ".jpg": "image/jpeg", ".jpeg": "image/jpeg", ".png": "image/png", ".webp": "image/webp" };

async function call(url, init = {}, tries = 5) {
  for (let i = 0; i < tries; i++) {
    const r = await fetch(url, { ...init, headers: { Authorization: `Key ${keyId}:${keySecret}`, "content-type": "application/json", ...init.headers } });
    if (r.status === 429 || r.status >= 500) { await sleep(2000 * 2 ** i); continue; }
    if (!r.ok) throw new Error(`${r.status} ${await r.text()}`);
    return r.json();
  }
  throw new Error("Demasiados reintentos (429/5xx)");
}

// Sube cada referencia una sola vez por ejecución y devuelve su URL pública.
const uploads = new Map();
function upload(file) {
  if (!uploads.has(file)) {
    uploads.set(file, (async () => {
      const type = MIME[extname(file).toLowerCase()] ?? "image/jpeg";
      const up = await call(`${API}/files/generate-upload-url`, { method: "POST", body: JSON.stringify({ content_type: type }) });
      // el PUT va a almacenamiento externo: sin las credenciales de Higgsfield
      const put = await fetch(up.upload_url, { method: "PUT", headers: up.upload_headers, body: readFileSync(file) });
      if (!put.ok) throw new Error(`subida de ${file}: ${put.status}`);
      return up.public_url;
    })());
  }
  return uploads.get(file);
}

// Un trabajo: { prompt, attach: [rutas], out: ruta de salida }
async function generate({ prompt, attach, out }) {
  const body = { prompt, aspect_ratio: ratioOf(prompt), resolution: opt("--resolution", "1k"), quality: opt("--quality", "medium") };
  if (attach.length) body.image_urls = await Promise.all(attach.slice(0, 10).map(upload));

  const task = await call(`${API}/${opt("--model", "xai/grok-imagine-image-2.0")}`, {
    method: "POST",
    headers: { "Idempotency-Key": crypto.randomUUID() },
    body: JSON.stringify(body),
  });
  for (;;) {
    await sleep(3000);
    const r = await call(task.status_url);
    if (r.status === "completed") {
      const img = Buffer.from(await (await fetch(r.images[0].url)).arrayBuffer());
      mkdirSync(dirname(out), { recursive: true });
      // si el destino es .jpg y llega PNG, se convierte con ffmpeg (ya hace falta para placeholders.ps1)
      if (img.subarray(0, 4).toString("hex") === "89504e47" && out.endsWith(".jpg")) {
        const png = out.replace(/.jpg$/, ".tmp.png");
        writeFileSync(png, img);
        const c = spawnSync("ffmpeg", ["-loglevel", "error", "-y", "-i", png, "-q:v", "2", out]);
        if (c.status !== 0) throw new Error("ffmpeg no pudo convertir a jpg: " + c.stderr);
        unlinkSync(png);
      } else writeFileSync(out, img);
      return;
    }
    if (["failed", "nsfw", "canceled"].includes(r.status)) throw new Error(`${r.status}: ${JSON.stringify(r.error ?? r)}`);
  }
}

const node = (a) => spawnSync("node", [join(root, "tools/prompt.mjs"), ...a, "--no-clip", "--max", "10"], { encoding: "utf8", maxBuffer: 1 << 28 });

// --ref-oficial alicia,berta,mama: para media/photos/<x>.jpg la referencia es la foto oficial que ya existe (mantiene la cara de la familia)
const oficiales = (opt("--ref-oficial", "") || "").split(",").filter(Boolean);
const refsDir = opt("--refs-dir");

const swap = (f) => {
  if (flag("--refs-fotos")) {
    const o = join(root, "app/src/main/assets/cases", caso, "media/photos", `${basename(f, extname(f))}.jpg`);
    if (existsSync(o)) return o;
  }
  const g = refsDir && join(root, refsDir, basename(f));
  return g && existsSync(g) ? g : f;
};
const oficial = (e) => {
  e = { ...e, attach: e.attach.map(swap) };
  const n = e.dest.match(/^media\/photos\/(\w+)\.jpg$/)?.[1];
  const f = n && oficiales.includes(n) ? join(root, "app/src/main/assets/cases", caso, e.dest) : null;
  return f && existsSync(f) ? [f, ...e.attach.slice(1)] : e.attach;
};

let jobs = [];
if (flag("--ref")) {
  for (const t of opt("--ref").split(",")) {
    const r = node(["--ref", t]);
    if (r.status !== 0) throw new Error(r.stderr);
    // Un retrato nuevo no lleva referencia: es la referencia. Las edades distintas (alicia_nina…) conviene
    // generarlas adjuntando a mano la principal; aquí van solo con texto.
    jobs.push({ name: `ref ${t}`, prompt: r.stdout.trim(), attach: [], out: join(root, "tools/refs", caso, `${t}.jpg`) });
  }
} else {
  const r = node(["--all", ...(flag("--sin-ref") ? ["--sin-ref"] : [])]);
  if (r.status !== 0) throw new Error(r.stderr);
  const valued = ["--refs-dir", "--ref-oficial", "--only", "--group", "--resolution", "--quality", "--model", "--budget", "--cost", "--concurrency", "--ref"];
  const targets = args.filter((a, i) => !a.startsWith("--") && !valued.includes(args[i - 1]));
  const only = opt("--only");
  jobs = JSON.parse(r.stdout)
    .filter((e) => !e.done || flag("--force"))
    .filter((e) => flag("--all") || (flag("--clues") && e.clue) || (flag("--group") && new RegExp(opt("--group"), "i").test(e.group)) || (only && e.dest.includes(only)) || targets.some((t) => e.path === t || e.path.endsWith(`/${t}`) || e.dest.endsWith(t)))
    .map((e) => ({ name: e.dest, prompt: e.prompt, attach: oficial(e), out: join(root, "tools/out/higgsfield", e.dest), missing: e.missing }));
  if (!jobs.length) throw new Error("Nada que generar: pasa rutas, --only <texto> o --all");
}

jobs = jobs.filter((j) => flag("--force") || !existsSync(j.out));
const limit = cost ? Math.floor(budget / cost) : Infinity;
const todo = jobs.slice(0, limit);
console.log(`${jobs.length} pendientes${todo.length < jobs.length ? `, ${todo.length} dentro del presupuesto` : ""}` + (cost ? `, ≈ ${(todo.length * cost).toFixed(2)} USD` : ""));
for (const j of todo.slice(0, 15)) console.log(`  ${j.name}${j.attach.length ? `  [${j.attach.length} ref: ${j.attach.map((p) => p.split(/[\/]/).slice(-2).join("/")).join(", ")}]` : ""}${j.missing?.length ? `  (sin foto de: ${j.missing.join(", ")})` : ""}`);
if (todo.length > 15) console.log(`  … y ${todo.length - 15} más`);
if (!go) { console.log("\nSimulación: añade --go para generar de verdad."); process.exit(0); }

let done = 0;
const queue = [...todo];
await Promise.all(
  Array.from({ length: Number(opt("--concurrency", 2)) }, async () => {
    while (queue.length) {
      const j = queue.shift();
      try {
        await generate(j);
        console.log(`[${++done}/${todo.length}] ${j.name}`);
      } catch (e) {
        console.error(`FALLO ${j.name}: ${String(e.message).slice(0, 300)}`);
      }
    }
  }),
);
