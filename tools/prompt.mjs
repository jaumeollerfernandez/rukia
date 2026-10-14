// Arma el prompt final de una imagen de MEDIA_PENDIENTE.md: sustituye {ficha} por su descripción y dice qué fotos
// de referencia adjuntar, y en qué orden («reference image 1» = el primer adjunto).
//
//   node tools/prompt.mjs IMG_0393.jpg            prompt + adjuntos (también lo copia al portapapeles)
//   node tools/prompt.mjs gonpi/guia_a.jpg --corto --max 3
//   node tools/prompt.mjs --ref alicia            prompt del retrato neutro de una ficha (la foto de referencia)
//   node tools/prompt.mjs --refs                  qué fichas se usan más y cuáles todavía no tienen foto
//
// Las fotos de referencia van en tools/refs/<caso>/<ficha>.jpg (o .png/.webp).
// --corto: con foto, la ficha se reduce a «una chica de 18 años (como en reference image 1)».
// --max N: adjunta como mucho N referencias (las primeras que aparecen); el resto va solo con texto.
import { existsSync, readFileSync } from "node:fs";
import { spawnSync } from "node:child_process";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";

const caso = "ruk-93429049";
const root = join(dirname(fileURLToPath(import.meta.url)), "..");
const lines = readFileSync(join(root, "app/src/main/assets/cases", caso, "MEDIA_PENDIENTE.md"), "utf8").split(/\r?\n/);
// --refs-fotos: la referencia de una persona es su foto de perfil oficial (media/photos), si existe; los lugares y animales no.
const NO_PERSONA = new Set(["forn", "casal", "hostalnou", "voley", "excursions", "pabellon", "cocina", "cuarto_alicia", "calle_del_pont", "crater", "mas_rosalia", "can_pericot", "trufa", "trufa_cachorra", "rocky"]);
const FOTO = { montse: "mama", ignasi: "guia" };
const fotoOficial = (t) => process.argv.includes("--refs-fotos") && !NO_PERSONA.has(t)
  ? ["jpg", "png", "webp"].map((e) => join(root, "app/src/main/assets/cases", caso, "media/photos", `${FOTO[t] ?? t}.${e}`)).find(existsSync) : undefined;
const refFile = (t) => process.argv.includes("--sin-ref") ? undefined : fotoOficial(t) ?? ["jpg", "jpeg", "png", "webp"].map((e) => join(root, "tools/refs", caso, `${t}.${e}`)).find(existsSync);
const ESTILO = new Set(["movil", "movil_texto"]); // fichas de estilo: sin foto de referencia
const FOOTER = "No watermark, no logos of real brands, no text other than the text quoted in this prompt, no distorted hands.";

const fichas = {};
const prompts = []; // { paths, label, text }
let sec = "";
let item = [];
let done = false;
let clue = false;
let group = "";
for (const l of lines) {
  const h = l.match(/^## (\d+)\./);
  if (h) { sec = h[1]; group = ""; }
  const f = sec === "0" && l.match(/^- \[[ x]\] `(\w+)`: (.+)$/);
  if (f) { fichas[f[1]] = f[2]; continue; }
  const g = l.match(/^\*{1,2}([^*]+?)\*{1,2}(?: \(|$)/); // encabezados «**D1**», «*Familia*»
  if (g) group = g[1].trim();
  if (/^- /.test(l)) { clue = l.includes("🔎"); done = /^- \[x\]/.test(l); item = [...l.matchAll(/`([^`]+)`/g)].map((m) => m[1]); continue; }
  let m = l.match(/^\s+- `([^`]+)`: `(.+)`$/);
  if (m) { prompts.push({ sec, done, clue, group, paths: [m[1]], label: "", text: m[2] }); continue; }
  m = l.match(/^\s+- Prompt(?: \(([^)]+)\))?: `(.+)`$/);
  if (m) prompts.push({ sec, done, clue, group, paths: item, label: m[1] ?? "", text: m[2] });
}

const tokensOf = (text) => [...new Set([...text.matchAll(/\{(\w+)\}/g)].map((m) => m[1]))];
const noun = (t) => fichas[t].split(":")[0];
const full = (t) => { const [n, ...r] = fichas[t].split(": "); return r.length ? `${n} (${r.join(": ")})` : n; };

function build(text, max, corto) {
  const used = tokensOf(text);
  for (const t of used) if (!fichas[t]) throw new Error(`Ficha desconocida: {${t}} (defínela en la sección 0)`);
  const attach = used.filter(refFile).slice(0, max);
  const seen = new Set();
  const body = text.replace(/\{(\w+)\}/g, (_, t) => {
    const idx = attach.indexOf(t) + 1;
    const first = !seen.has(t);
    seen.add(t);
    if (!first) return idx ? `the same one from reference image ${idx}` : noun(t).replace(/^(an?|the)\s+/i, "the same ");
    if (!idx) return full(t);
    return corto ? `${noun(t)} (exactly as in reference image ${idx})` : `${full(t)} (must look exactly like reference image ${idx})`;
  });
  const pre = attach.length
    ? `Reference images are attached in this order: ${attach.map((t, i) => `image ${i + 1} = ${t}`).join(", ")}. ` +
      "Take only the identity from each reference (face, hair, body, markings, architecture); never copy its background, pose, clothes or lighting.\n\n"
    : "";
  return { attach, missing: used.filter((t) => !ESTILO.has(t) && !attach.includes(t) && !refFile(t)), prompt: `${pre}${body}\n\n${FOOTER}` };
}

function copy(text) {
  if (process.argv.includes("--no-clip")) return;
  spawnSync("powershell", ["-NoProfile", "-Command", "$env:P | Set-Clipboard"], { env: { ...process.env, P: text } });
}

const args = process.argv.slice(2);
const flag = (n) => args.includes(n);
const max = flag("--max") ? Number(args[args.indexOf("--max") + 1]) : 99;
const norm = (p) => p.replace(/^photos\//, "").replace(/\.\w+$/, "");

if (flag("--refs")) {
  const n = {};
  for (const p of prompts) for (const t of tokensOf(p.text).filter((x) => !ESTILO.has(x))) n[t] = (n[t] ?? 0) + 1;
  for (const [t, c] of Object.entries(n).sort((a, b) => b[1] - a[1])) console.log(`${String(c).padStart(3)}  ${refFile(t) ? "✔" : "·"}  ${t}`);
} else if (flag("--all")) {
  // JSON para tools/higgsfield.mjs: una entrada por archivo, con su destino dentro del caso y los adjuntos en orden.
  const dest = (sec, p) => ({ 2: `multimedia/${p}`, 3: `media/${p}`, 4: `media/photos/${norm(p)}.jpg`, 7: `media/${p}`, 9: `police/resenas/${p}` })[sec];
  const out = [];
  for (const p of prompts) {
    if (!dest(p.sec, "x")) continue;
    for (const path of p.paths) {
      if (p.sec !== "4" && !/.(jpg|png|webp)$/.test(path)) continue;
      // rutas citadas de pasada en una descripción («copia de `dani_a.jpg`»): solo valen las que llevan su carpeta
      if ((p.sec === "3" && !path.startsWith("gonpi/")) || (p.sec === "7" && !path.startsWith("chat/")) || (p.sec === "2" && !path.startsWith("IMG_"))) continue;
      if (p.label && p.paths.length > 1 && !path.includes(/frente/i.test(p.label) ? "frontal" : "perfil")) continue;
      const b = build(p.text, max, flag("--corto"));
      out.push({ path, done: p.done, clue: p.clue, group: p.group, dest: dest(p.sec, path), label: p.label, prompt: b.prompt, attach: b.attach.map(refFile), missing: b.missing });
    }
  }
  console.log(JSON.stringify(out));
} else if (flag("--ref")) {
  const t = args[args.indexOf("--ref") + 1];
  if (!fichas[t]) throw new Error(`No hay ficha «${t}»`);
  const text =
    `Photorealistic neutral reference portrait for a character sheet: ${fichas[t]}. Head and shoulders, facing the camera straight on, ` +
    "relaxed neutral expression with only a hint of a natural smile, eyes looking into the lens, plain light-grey seamless background, soft even daylight " +
    "from a large window, no hard shadows on the face, 85 mm lens, sharp focus on the eyes, natural skin texture with pores and small imperfections, " +
    "plain neutral clothing (simple white t-shirt), hair and ears visible, nothing covering the face. Aspect ratio 1:1.";
  console.log(text);
  copy(text);
} else {
  const target = args.find((a, i) => !a.startsWith("--") && args[i - 1] !== "--max");
  if (!target) throw new Error("Uso: node tools/prompt.mjs <ruta de la imagen> | --ref <ficha> | --refs");
  const found = prompts.filter((p) => p.paths.some((x) => norm(x) === norm(target)));
  if (!found.length) throw new Error(`Sin prompt para «${target}»`);
  const out = found.map((p) => ({ ...p, ...build(p.text, max, flag("--corto")) }));
  for (const o of out) {
    console.log(`\n=== ${target}${o.label ? ` (${o.label})` : ""} ===`);
    console.log(o.attach.length ? "ADJUNTA, en este orden:\n" + o.attach.map((t, i) => `  ${i + 1}. ${refFile(t)}`).join("\n") : "ADJUNTA: nada");
    if (o.missing.length) console.log(`Sin foto de referencia todavía (van solo con texto): ${o.missing.join(", ")}`);
    console.log(`\n${o.prompt}`);
  }
  copy(out.map((o) => o.prompt).join("\n\n"));
}
