// Prueba: photos/iris.jpg con Grok Image 2.0 + filtro "iPhone + DigitalCam" y una referencia (iris.png).
// Uso: node tools/higgsfield/prueba-iris-grok.mjs
import { readFileSync, writeFileSync } from "node:fs";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";

const here = dirname(fileURLToPath(import.meta.url));
const ref = join(here, "..", "refs", "ruk-93429049", "iris.png");
const API = "https://api.higgsfield.ai";
const auth = { Authorization: `Key ${process.env.HF_API_KEY_ID}:${process.env.HF_API_KEY_SECRET}`, "Content-Type": "application/json" };
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
const j = async (r) => { if (!r.ok) throw new Error(`${r.status} ${await r.text()}`); return r.json(); };

// Filtro común: móvil casero (iPhone: luz natural, encuadre casual, HDR suave) + compacta digital (DigitalCam:
// flash directo, grano, colores algo lavados). Si la escena dice "no flash", se respeta.
const FILTRO = `Look: an unedited casual photo straight from a phone camera mixed with an old compact digital camera. Natural available light with slightly clipped highlights and soft HDR, harsh direct on-camera flash only if the scene allows it (flat, hard shadows right behind the subject), visible digital noise and grain, mild JPEG compression, slightly washed-out colors with a faint warm cast, imperfect casual framing, real skin with pores and blemishes, no retouching, no studio lighting, no cinematic color grading, not a professional photograph.`;

const escena = `Profile photo, head and shoulders, of the 20-year-old woman in the reference image (keep exactly her face, hair, skin and body, but not its background, pose, clothes or lighting), wearing white loose clothes with a faint sad smile, slightly blurry and dim as if taken in secret in a bathroom or a small room, no flash, grain.`;
const prompt = `${escena} ${FILTRO} No watermark, no logos of real brands, no text, no distorted hands.`;

const up = await j(await fetch(`${API}/files/generate-upload-url`, { method: "POST", headers: auth, body: JSON.stringify({ content_type: "image/png" }) }));
const put = await fetch(up.upload_url, { method: "PUT", headers: up.upload_headers, body: readFileSync(ref) });
if (!put.ok) throw new Error(`subida ${put.status} ${await put.text()}`);

const body = { prompt, image_urls: [up.public_url], aspect_ratio: "1:1", resolution: "1k", quality: "medium" };
const t = await j(await fetch(`${API}/xai/grok-imagine-image-2.0`, { method: "POST", headers: { ...auth, "Idempotency-Key": crypto.randomUUID() }, body: JSON.stringify(body) }));
console.log("enviado", t.request_id);
for (;;) {
  await sleep(3000);
  const r = await j(await fetch(t.status_url, { headers: auth }));
  if (r.status === "completed") {
    const res = await fetch(r.images[0].url);
    const ext = (res.headers.get("content-type") ?? "").includes("png") ? "png" : "jpg";
    const out = join(here, `iris_grok.${ext}`);
    writeFileSync(out, Buffer.from(await res.arrayBuffer()));
    console.log("guardada", out);
    break;
  }
  if (["failed", "nsfw", "canceled"].includes(r.status)) throw new Error(JSON.stringify(r));
}
