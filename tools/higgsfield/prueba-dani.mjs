// Prueba: photos/dani.jpg con tres estilos de Soul Standard, solo texto. Uso: node tools/higgsfield/prueba-dani.mjs
import { writeFileSync } from "node:fs";
import { dirname, join } from "node:path";
import { fileURLToPath } from "node:url";

const here = dirname(fileURLToPath(import.meta.url));
const API = "https://api.higgsfield.ai";
const auth = { Authorization: `Key ${process.env.HF_API_KEY_ID}:${process.env.HF_API_KEY_SECRET}`, "Content-Type": "application/json" };
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
const j = async (r) => { if (!r.ok) throw new Error(`${r.status} ${await r.text()}`); return r.json(); };

const prompt = `Profile photo of a 22-year-old Spanish young man with a gym build (muscular, dark buzz cut, thick eyebrows, olive skin, a small stud earring in the left ear, a confident smirk) with a smirk, wearing a black leather jacket, sitting on a black motorcycle at night in Olot under a street lamp, a bit of flash on his face. No watermark, no logos of real brands, no text, no distorted hands.`;
const estilos = {
  iphone: "1b798b54-03da-446a-93bf-12fcba1050d7",
  selfie: "8dd89de9-1cff-402e-88a8-580c29d91473",
  digitalcam: "ca4e6ad3-3e93-4e03-81a0-d1722d2c128b",
};

async function run([nombre, style_id]) {
  const body = { prompt, aspect_ratio: "1:1", resolution: "720p", batch_size: 1, style_id };
  const t = await j(await fetch(`${API}/higgsfield-ai/soul/standard`, { method: "POST", headers: auth, body: JSON.stringify(body) }));
  for (;;) {
    await sleep(3000);
    const r = await j(await fetch(t.status_url, { headers: auth }));
    if (r.status === "completed") {
      const res = await fetch(r.images[0].url);
      const ext = (res.headers.get("content-type") ?? "").includes("png") ? "png" : "jpg";
      const out = join(here, `dani_${nombre}.${ext}`);
      writeFileSync(out, Buffer.from(await res.arrayBuffer()));
      return console.log("guardada", out);
    }
    if (["failed", "nsfw", "canceled"].includes(r.status)) return console.error(nombre, r.status, JSON.stringify(r));
  }
}
await Promise.all(Object.entries(estilos).map(run));
