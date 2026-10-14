// Prueba: photos/dani.jpg con Grok Image 2.0 y una referencia (dani.jpg). Uso: node tools/higgsfield/prueba-dani-grok.mjs
import { readFileSync, writeFileSync } from "node:fs";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";

const here = dirname(fileURLToPath(import.meta.url));
const ref = join(here, "..", "refs", "ruk-93429049", "dani.jpg");
const API = "https://api.higgsfield.ai";
const auth = { Authorization: `Key ${process.env.HF_API_KEY_ID}:${process.env.HF_API_KEY_SECRET}`, "Content-Type": "application/json" };
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
const j = async (r) => { if (!r.ok) throw new Error(`${r.status} ${await r.text()}`); return r.json(); };

const prompt = `Use the person in the reference image: keep exactly his face, hair, body and markings, but not its background, pose, clothes or lighting. Profile photo of that 22-year-old Spanish young man with a confident smirk, wearing a black leather jacket, sitting on a black motorcycle at night in Olot under a street lamp, a bit of flash on his face, shot with a phone. No watermark, no logos of real brands, no text, no distorted hands.`;

const up = await j(await fetch(`${API}/files/generate-upload-url`, { method: "POST", headers: auth, body: JSON.stringify({ content_type: "image/jpeg" }) }));
const put = await fetch(up.upload_url, { method: "PUT", headers: up.upload_headers, body: readFileSync(ref) });
if (!put.ok) throw new Error(`subida ${put.status} ${await put.text()}`);

const body = { prompt, image_urls: [up.public_url], aspect_ratio: "1:1", resolution: "1k", quality: "medium" };
const t = await j(await fetch(`${API}/xai/grok-imagine-image-2.0`, { method: "POST", headers: { ...auth, "Idempotency-Key": crypto.randomUUID() }, body: JSON.stringify(body) }));
console.log("enviado", t.request_id);
for (;;) {
  await sleep(3000);
  const r = await j(await fetch(t.status_url, { headers: auth }));
  console.log(r.status);
  if (r.status === "completed") {
    const res = await fetch(r.images[0].url);
    const ext = (res.headers.get("content-type") ?? "").includes("png") ? "png" : "jpg";
    const out = join(here, `dani_grok.${ext}`);
    writeFileSync(out, Buffer.from(await res.arrayBuffer()));
    console.log("guardada", out);
    break;
  }
  if (["failed", "nsfw", "canceled"].includes(r.status)) throw new Error(JSON.stringify(r));
}
