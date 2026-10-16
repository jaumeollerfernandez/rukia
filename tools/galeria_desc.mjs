// Descripción de lo que debería verse en cada imagen, sacada de MEDIA_PENDIENTE.md y MULTIMEDIA.md.
import fs from 'node:fs';
import path from 'node:path';

const relDe = n => /^(gonpi|photos|chat|places)\//.test(n) ? 'media/' + n : /^IMG_/.test(n) ? 'multimedia/' + n : n;

export function descripciones(caso) {
  const out = {};   // ruta -> { desc, prompt, tipo, cruza }
  const pon = (r, d) => out[r] = { ...(out[r] ?? {}), ...Object.fromEntries(Object.entries(d).filter(([, v]) => v)) };

  const pend = path.join(caso, 'MEDIA_PENDIENTE.md');
  if (fs.existsSync(pend)) {
    const l = fs.readFileSync(pend, 'utf8').split(/\r?\n/);
    l.forEach((linea, i) => {
      let m = linea.match(/^\s*- \[.\] ((?:`[^`]+\.(?:jpg|png|webp)`(?:,\s*)?)+)\s*[:·.]?\s*(.*)$/);
      if (m) {
        const sig = (l[i + 1] || '').match(/^\s*- Prompt: `(.*)`\s*$/);
        for (const n of m[1].matchAll(/`([^`]+)`/g)) pon(relDe(n[1]), { desc: m[2].replace(/\*\*/g, ''), prompt: sig?.[1] });
        return;
      }
      m = linea.match(/^\s*- \[.\] `(\w+)` (.+)$/);   // cuentas de Gonpi de la sección 4: «`nitsdagost` Logo del grupo…»
      if (m && !out['media/photos/' + m[1] + '.jpg']) { pon('media/photos/' + m[1] + '.jpg', { desc: m[2] }); return; }
      m = linea.match(/^\s*- `([^`]+\.(?:jpg|png|webp))`:\s*`(.*)`\s*$/);   // fotos de perfil: solo hay prompt
      if (m) pon(relDe(m[1]), { desc: m[2].replace(/\s*\{movil\}.*$/, '').replace(/\s*Aspect ratio.*$/, ''), prompt: m[2] });
    });
  }

  const mult = path.join(caso, 'MULTIMEDIA.md');
  if (fs.existsSync(mult)) {
    for (const linea of fs.readFileSync(mult, 'utf8').split(/\r?\n/)) {
      let m = linea.match(/^\| `(IMG_[^`]+\.jpg)` \| (.+?) \| ([^|]+?) \| (.*?) \|?\s*$/);
      if (m && !/relleno/.test(m[3])) { pon('multimedia/' + m[1], { desc: m[2].replace(/\*\*/g, ''), tipo: m[3], cruza: m[4].replace(/\*\*/g, '') }); continue; }
      m = linea.match(/^\| `(IMG_[^`]+\.jpg)` \| `([^`]+)` \| ⚪ relleno/);
      if (m) pon('multimedia/' + m[1], { desc: 'Relleno del carrete: foto real sin pista, para que la galería parezca de verdad (original ' + m[2] + ').', tipo: '⚪ relleno' });
    }
  }
  return out;
}
