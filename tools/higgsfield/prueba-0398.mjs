// Prueba única: IMG_0398 con Soul Standard. Soul solo admite UNA imagen de referencia (image_reference_url).
// Uso: node tools/higgsfield/prueba-0398.mjs
import { readFileSync, writeFileSync } from "node:fs";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";

const here = dirname(fileURLToPath(import.meta.url));
const ref = join(here, "..", "refs", "ruk-93429049", "alicia.jpg");
const API = "https://api.higgsfield.ai";
const auth = { Authorization: `Key ${process.env.HF_API_KEY_ID}:${process.env.HF_API_KEY_SECRET}`, "Content-Type": "application/json" };
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
const j = async (r) => { if (!r.ok) throw new Error(`${r.status} ${await r.text()}`); return r.json(); };

// El prompt de prompt.mjs sin las frases que hablan de "reference image N" (Soul no las entiende).
const prompt = `Front-camera smartphone selfie inside a small hatchback on a sunny country road in the Garrotxa, taken by the driver with one hand: an 18-year-old Catalan girl, tall (short glossy black bob, dark eyes, light olive skin, gentle smile) at the wheel, singing with her mouth wide open and her eyes half-closed, and in the passenger seat an 18-year-old Catalan girl of Mediterranean look, slim athletic build of a volleyball setter (oval face, light olive skin with faint freckles across the nose and cheeks, thick dark brown hair just past the shoulders, in a loose low ponytail with loose strands framing the face, straight dark eyebrows, warm brown almond-shaped eyes, small straight nose, full lips, no makeup, small silver stud earrings) laughing with her head tipped back, her ponytail loose; both wearing blue and orange volleyball club tracksuit tops. On the back seat, clearly visible between them, a big blue sports bag with the knee pads sticking out of the open zip. Green hills through the windows, sun strap of light across the dashboard, slightly blurry from the movement. No watermark, no logos of real brands, no text, no distorted hands.`;

const up = await j(await fetch(`${API}/files/generate-upload-url`, { method: "POST", headers: auth, body: JSON.stringify({ content_type: "image/jpeg" }) }));
const put = await fetch(up.upload_url, { method: "PUT", headers: up.upload_headers, body: readFileSync(ref) });
if (!put.ok) throw new Error(`subida ${put.status} ${await put.text()}`);

const body = { prompt, aspect_ratio: "3:4", resolution: "720p", batch_size: 1, image_reference_url: up.public_url };
const task = await j(await fetch(`${API}/higgsfield-ai/soul/standard`, { method: "POST", headers: auth, body: JSON.stringify(body) }));
console.log("enviado", task.request_id);

for (;;) {
  await sleep(3000);
  const r = await j(await fetch(task.status_url, { headers: auth }));
  console.log(r.status);
  if (r.status === "completed") {
    const res = await fetch(r.images[0].url);
    const ext = (res.headers.get("content-type") ?? "").includes("png") ? "png" : "jpg";
    const out = join(here, `IMG_0398.${ext}`);
    writeFileSync(out, Buffer.from(await res.arrayBuffer()));
    console.log("guardada", out);
    break;
  }
  if (["failed", "nsfw", "canceled"].includes(r.status)) throw new Error(JSON.stringify(r));
}
