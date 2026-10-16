// Galería de todo el multimedia del caso: ves cada imagen, la dejas como está («saltada») o arrastras una foto
// nueva encima para sustituirla («cambiada»).
//   node tools/galeria.mjs [--port 4174]
// La foto arrastrada se recorta al formato de la original (si dejas «ajustar» marcado), se convierte al mismo
// tipo de archivo y sustituye a la del juego al instante. La original se guarda en tools/backup_ia/<ruta>
// (la versión del último commit de git, si ya se había tocado) y «Deshacer» la devuelve.
// «Filtro móvil»: genera una previa con aspecto de foto de móvil (suave/normal/fuerte) SIN tocar el juego; solo
// «Quedarme con la filtrada» la aplica (con copia de la original). Estado: tools/out/galeria/estado.json. No gasta nada: no llama a ninguna API.
import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { spawn, execFileSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';
import { descripciones } from './galeria_desc.mjs';

const RAIZ = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const CASO = path.join(RAIZ, 'app/src/main/assets/cases/ruk-93429049');
const BACKUP = path.join(RAIZ, 'tools/backup_ia');
const SAL = path.join(RAIZ, 'tools/out/galeria');
const ESTADO = path.join(SAL, 'estado.json');
const i = process.argv.indexOf('--port');
const PUERTO = i >= 0 ? Number(process.argv[i + 1]) : 4174;
fs.mkdirSync(path.join(SAL, 'tmp'), { recursive: true });

const CARPETAS = ['media/gonpi', 'multimedia', 'media/photos', 'media/chat', 'media/places', 'profile_pics', 'real_ubi', 'police'];
const EXT = /\.(jpe?g|png|webp)$/i;
function escanear() {
  const out = [];
  const rec = (rel) => {
    const abs = path.join(CASO, rel);
    if (!fs.existsSync(abs)) return;
    for (const e of fs.readdirSync(abs, { withFileTypes: true })) {
      const r = rel + '/' + e.name;
      if (e.isDirectory()) rec(r); else if (EXT.test(e.name)) out.push(r);
    }
  };
  CARPETAS.forEach(rec);
  return out.sort((a, b) => a.localeCompare(b, 'es', { numeric: true }));
}
let rutas = escanear();
const estado = fs.existsSync(ESTADO) ? JSON.parse(fs.readFileSync(ESTADO, 'utf8')) : {};
const guardar = () => fs.writeFileSync(ESTADO, JSON.stringify(estado, null, 1));
const info = r => (estado[r] ??= { estado: 'sin revisar', autor: '' });

const abs = r => path.join(CASO, r);
const resp = r => path.join(BACKUP, r);
const previa = r => path.join(SAL, 'previa', r.replace(/[\\/]/g, '__'));
const NIVELES = {   // grados de inclinación, grano, desenfoque, saturación, contraste
  suave: { rot: 0.4, ruido: 7, blur: 0.5, sat: 0.93, con: 0.97 },
  normal: { rot: 0.8, ruido: 11, blur: 0.7, sat: 0.90, con: 0.95 },
  fuerte: { rot: 1.2, ruido: 18, blur: 1.0, sat: 0.85, con: 0.92 },
};
const hash = t => [...t].reduce((a, c) => (a * 31 + c.charCodeAt(0)) >>> 0, 7);
// el «aspecto de móvil»: ligera inclinación con zoom para tapar las esquinas, grano, algo de desenfoque y color apagado, viñeta
function filtroMovil(r, nivel) {
  const n = NIVELES[nivel] || NIVELES.normal, d = ffprobeDims(abs(r));
  if (!d) throw new Error('no se pudo leer el tamaño de la imagen');
  const rad = ((hash(r) % 2 ? 1 : -1) * n.rot * Math.PI / 180).toFixed(5);
  return new Promise((ok, ko) => {
    fs.mkdirSync(path.dirname(previa(r)), { recursive: true });
    const vf = [`scale=${Math.round(d.w * 1.1)}:${Math.round(d.h * 1.1)}`, `rotate=${rad}:c=black`, `crop=${d.w}:${d.h}`,
      `eq=saturation=${n.sat}:contrast=${n.con}:gamma=1.03`, `gblur=sigma=${n.blur}`, `noise=alls=${n.ruido}:allf=t+u`, 'vignette=angle=0.3'].join(',');
    const p = spawn('ffmpeg', ['-y', '-loglevel', 'error', '-i', abs(r), '-vf', vf, '-q:v', '4', '-frames:v', '1', previa(r)]);
    let log = ''; p.stderr.on('data', x => log += x);
    p.on('close', c => c === 0 ? ok() : ko(new Error(log.trim() || 'ffmpeg falló'))); p.on('error', ko);
  });
}
function ffprobeDims(f) {
  try {
    const donde = execFileSync('where.exe', ['ffmpeg']).toString().split(/\r?\n/)[0].trim();
    const [w, h] = execFileSync(path.join(path.dirname(donde), 'ffprobe.exe'), ['-v', 'error', '-show_entries', 'stream=width,height', '-of', 'csv=p=0', f]).toString().trim().split(',').map(Number);
    return w && h ? { w, h } : null;
  } catch { return null; }
}
function asegurarRespaldo(r) {
  if (fs.existsSync(resp(r))) return;
  fs.mkdirSync(path.dirname(resp(r)), { recursive: true });
  try {   // el original del último commit, por si ya se había sustituido
    fs.writeFileSync(resp(r), execFileSync('git', ['show', 'HEAD:app/src/main/assets/cases/ruk-93429049/' + r], { cwd: RAIZ, maxBuffer: 1 << 27, stdio: ['ignore', 'pipe', 'ignore'] }));
  } catch { fs.copyFileSync(abs(r), resp(r)); }
}

function sustituir(r, datos, ajustar, nombreOrigen) {
  return new Promise((ok, ko) => {
    asegurarRespaldo(r);
    const tmp = path.join(SAL, 'tmp', Date.now() + path.extname(nombreOrigen || '.bin'));
    fs.writeFileSync(tmp, datos);
    const dims = ajustar ? ffprobeDims(resp(r)) : null;
    const vf = dims ? `crop='min(iw,ih*${(dims.w / dims.h).toFixed(5)})':'min(ih,iw/${(dims.w / dims.h).toFixed(5)})',scale=${dims.w}:${dims.h}` : null;
    const args = ['-y', '-loglevel', 'error', '-i', tmp, ...(vf ? ['-vf', vf] : []), '-q:v', '2', '-frames:v', '1', abs(r)];
    const p = spawn('ffmpeg', args); let log = '';
    p.stderr.on('data', d => log += d);
    p.on('close', c => { fs.rmSync(tmp, { force: true }); c === 0 ? ok(dims) : ko(new Error(log.trim() || 'ffmpeg falló')); });
    p.on('error', ko);
  });
}

const pagina = () => fs.readFileSync(path.join(RAIZ, 'tools/galeria.html'), 'utf8');   // se lee en cada visita: basta recargar
const json = (res, o, c = 200) => { res.writeHead(c, { 'Content-Type': 'application/json' }); res.end(JSON.stringify(o)); };
const lee = req => new Promise(ok => { const b = []; req.on('data', d => b.push(d)).on('end', () => ok(Buffer.concat(b))); });

http.createServer(async (req, res) => {
  const u = new URL(req.url, 'http://x');
  const ruta = u.searchParams.get('ruta');
  const valida = ruta && rutas.includes(ruta);
  try {
    if (u.pathname === '/') { res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' }); return res.end(pagina()); }
    if (u.pathname === '/api/lista') { rutas = escanear(); const desc = descripciones(CASO); return json(res, rutas.map(r => ({ ruta: r, info: desc[r] ?? null, grupo: path.dirname(r), ...info(r), hayAntes: fs.existsSync(resp(r)) && info(r).estado === 'cambiada', hayPrevia: fs.existsSync(previa(r)) }))); }
    if (u.pathname === '/img' && valida) {
      const f = u.searchParams.get('antes') ? resp(ruta) : u.searchParams.get('previa') ? previa(ruta) : abs(ruta);
      if (!fs.existsSync(f)) { res.writeHead(404); return res.end(); }
      res.writeHead(200, { 'Content-Type': /\.png$/i.test(f) ? 'image/png' : /\.webp$/i.test(f) ? 'image/webp' : 'image/jpeg', 'Cache-Control': 'no-store' });
      return fs.createReadStream(f).pipe(res);
    }
    if (u.pathname === '/api/subir' && req.method === 'POST' && valida) {
      const datos = await lee(req);
      const dims = await sustituir(ruta, datos, u.searchParams.get('ajustar') !== '0', u.searchParams.get('nombre'));
      Object.assign(info(ruta), { estado: 'cambiada', origen: u.searchParams.get('nombre') || '' }); guardar();
      return json(res, { ok: true, ajustada: dims });
    }
    if (u.pathname === '/api/estado' && req.method === 'POST' && valida) {
      const { estado: e, autor } = JSON.parse((await lee(req)).toString() || '{}');
      if (e && ['sin revisar', 'saltada'].includes(e) && info(ruta).estado !== 'cambiada') info(ruta).estado = e;
      if (typeof autor === 'string') info(ruta).autor = autor;
      guardar(); return json(res, { ok: true });
    }
    if (u.pathname === '/api/filtro' && req.method === 'POST' && valida) { await filtroMovil(ruta, u.searchParams.get('nivel')); return json(res, { ok: true }); }
    if (u.pathname === '/api/filtro-aplicar' && req.method === 'POST' && valida && fs.existsSync(previa(ruta))) {
      asegurarRespaldo(ruta); fs.copyFileSync(previa(ruta), abs(ruta)); fs.rmSync(previa(ruta), { force: true });
      Object.assign(info(ruta), { estado: 'cambiada', origen: 'filtro móvil' }); guardar(); return json(res, { ok: true });
    }
    if (u.pathname === '/api/filtro-descartar' && req.method === 'POST' && valida) { fs.rmSync(previa(ruta), { force: true }); return json(res, { ok: true }); }
    if (u.pathname === '/api/deshacer' && req.method === 'POST' && valida) {
      if (fs.existsSync(resp(ruta))) fs.copyFileSync(resp(ruta), abs(ruta));
      info(ruta).estado = 'sin revisar'; guardar(); return json(res, { ok: true });
    }
    res.writeHead(404); res.end();
  } catch (e) { json(res, { ok: false, error: String(e.message || e) }, 500); }
}).listen(PUERTO, '127.0.0.1', () => console.log(`Galería en http://localhost:${PUERTO}  (${rutas.length} imágenes)`));
