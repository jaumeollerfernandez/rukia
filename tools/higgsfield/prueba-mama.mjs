// Prueba: photos/mama.jpg en dos variantes con Grok. Uso: node tools/higgsfield/prueba-mama.mjs
//   mama_pose.jpg      con la referencia montse.jpg pero de tres cuartos, mirando fuera de cámara
//   mama_sin_ref.jpg   solo con texto (sin referencia)
import { readFileSync, writeFileSync } from "node:fs";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";

const here = dirname(fileURLToPath(import.meta.url));
const root = join(here, "..", "..");
const caso = "ruk-93429049";
const md = readFileSync(join(root, "app/src/main/assets/cases", caso, "MEDIA_PENDIENTE.md"), "utf8");
const ficha = (t) => md.match(new RegExp("^- \\[.\\] `" + t + "`: (.+)$", "m"))[1].trim();
const API = "https://api.higgsfield.ai";
const auth = { Authorization: `Key ${process.env.HF_API_KEY_ID}:${process.env.HF_API_KEY_SECRET}`, "Content-Type": "application/json" };
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
const j = async (r) => { if (!r.ok) throw new Error(`${r.status} ${await r.text()}`); return r.json(); };

const montse = ficha("montse").replace("Alicia's mother: ", "").replace(" (she has Alicia's nose and mouth)", "");
const escena = `wearing a beige linen blouse, standing in a flower garden in front of a stone wall in L'Hostalnou de Bianya, taken by someone else at dusk with a cheap old phone, very dim fading light and no flash, the focus caught on the flowers so that her face is soft and out of focus, motion blur from a shaky hand, heavy digital noise and grain, muddy dark shadows, a blurry low-quality low-light snapshot.`;
const filtro = ficha("movil");

const variantes = {
  pose: {
    ref: join(root, "tools/refs", caso, "montse.jpg"),
    prompt: `Use the woman in the reference image: keep exactly her face, hair, skin and body, but not its background, pose, clothes or lighting. Photo, head and shoulders, of that woman in three-quarter view, her head turned away from the camera and looking off to the side at something out of frame, a faint serene smile, ${escena} ${filtro} No watermark, no logos of real brands, no text, no distorted hands.`,
  },
  degradada: {
    ref: join(here, "montse_degradada.jpg"), // montse.jpg con ffmpeg: desenfoque, grano y baja resolución
    prompt: `Use the woman in the reference image: keep exactly her face, hair, skin and body, but not its background, pose, clothes or lighting. Photo, head and shoulders, of that woman in three-quarter view, her head turned away from the camera and looking off to the side at something out of frame, a faint serene smile, ${escena} ${filtro} No watermark, no logos of real brands, no text, no distorted hands.`,
  },
  sin_ref: {
    ref: null,
    prompt: `Photo, head and shoulders, of ${montse}, in three-quarter view looking off to the side with a faint serene smile, ${escena} ${filtro} No watermark, no logos of real brands, no text, no distorted hands.`,
  },
};

async function run([nombre, v]) {
  const body = { prompt: v.prompt, aspect_ratio: "1:1", resolution: "1k", quality: "medium" };
  if (v.ref) {
    const up = await j(await fetch(`${API}/files/generate-upload-url`, { method: "POST", headers: auth, body: JSON.stringify({ content_type: "image/jpeg" }) }));
    const put = await fetch(up.upload_url, { method: "PUT", headers: up.upload_headers, body: readFileSync(v.ref) });
    if (!put.ok) throw new Error(`subida ${put.status}`);
    body.image_urls = [up.public_url];
  }
  const t = await j(await fetch(`${API}/xai/grok-imagine-image-2.0`, { method: "POST", headers: { ...auth, "Idempotency-Key": crypto.randomUUID() }, body: JSON.stringify(body) }));
  for (;;) {
    await sleep(3000);
    const r = await j(await fetch(t.status_url, { headers: auth }));
    if (r.status === "completed") {
      const out = join(here, `mama_${nombre}.jpg`);
      writeFileSync(out, Buffer.from(await (await fetch(r.images[0].url)).arrayBuffer()));
      return console.log("guardada", out);
    }
    if (["failed", "nsfw", "canceled"].includes(r.status)) return console.error(nombre, r.status);
  }
}
// sin argumentos corre pose y sin_ref; con argumentos, solo esas (p. ej. node ... degradada)
const only = process.argv.slice(2);
await Promise.all(Object.entries(variantes).filter(([n]) => (only.length ? only.includes(n) : n !== "degradada")).map(run));
