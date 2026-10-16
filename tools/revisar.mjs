// Página local para revisar las imágenes de Gonpi generadas con Higgsfield: ves la actual y la nueva y decides.
//   node tools/revisar.mjs [--adelante 10] [--max-auto 10] [--port 4173]
// Necesita HF_API_KEY_ID y HF_API_KEY_SECRET en el entorno (solo para generar).
//   --adelante N : mantiene hasta N imágenes generadas esperando tu revisión.
// Una imagen en «saltada» nunca se genera: se queda como está en el juego y no se llama a la API.
//   --max-auto N : tope de generaciones automáticas en esta ejecución (las de «Rehacer» no cuentan). Es el freno de gasto.
// Cada botón: «Usar nueva» copia la imagen a media/gonpi/ (la anterior está en tools/backup_ia/), «Rehacer» genera otra
// (calidad low), «Saltar» la deja como está. Todo se guarda en tools/out/revision/estado.json.
import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { spawn } from 'node:child_process';
import { fileURLToPath } from 'node:url';
import { execFileSync } from 'node:child_process';

const RAIZ = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const CASO = path.join(RAIZ, 'app/src/main/assets/cases/ruk-93429049');
const BACKUP = path.join(RAIZ, 'tools/backup_ia');   // la anterior (IA) se guarda aquí al usar la nueva
const rutaDestino = d => path.join(CASO, 'media/gonpi', d.split('/')[1] + '.jpg');
function leerItems() {
  return fs.readFileSync(path.join(RAIZ, 'tools/revisar_items.txt'), 'utf8').split(/\r?\n/).filter(l => l.trim() && !l.startsWith('#'))
    .map(l => { const [dest, contexto] = l.split('|').map(s => s.trim()); return { dest, id: dest.replace('/', '__'), contexto }; });
}

const args = process.argv.slice(2);
const opt = (n, d) => { const i = args.indexOf(n); return i >= 0 ? Number(args[i + 1]) : d; };
const ADELANTE = opt('--adelante', 10), MAX_AUTO = opt('--max-auto', 10), PUERTO = opt('--port', 4173);
const REV = path.join(RAIZ, 'tools/out/revision');
const ESTADO = path.join(REV, 'estado.json');
fs.mkdirSync(REV, { recursive: true });

const items = leerItems().filter(i => i.dest.startsWith('gonpi/'));
const porId = Object.fromEntries(items.map(i => [i.id, i]));
const estado = fs.existsSync(ESTADO) ? JSON.parse(fs.readFileSync(ESTADO, 'utf8')) : {};
for (const it of items) estado[it.dest] ??= { estado: 'pendiente', versiones: 0 };
const guardar = () => fs.writeFileSync(ESTADO, JSON.stringify(estado, null, 1));
guardar();

// prompt tal como está en MEDIA_PENDIENTE.md, para enseñarlo
const md = fs.readFileSync(path.join(CASO, 'MEDIA_PENDIENTE.md'), 'utf8').split(/\r?\n/);
function promptDe(dest) {
  const i = md.findIndex(l => /^- \[.\] /.test(l) && l.includes('`' + dest + '.jpg`'));
  const p = i >= 0 ? md.slice(i + 1, i + 3).find(l => l.includes('Prompt:')) : null;
  return p ? p.replace(/^\s*- Prompt: `?/, '').replace(/`$/, '') : '';
}

// ---- generación ----
const cola = [], generando = new Set(), errores = {};
let auto = 0, activos = 0, sinSaldo = false;
const hfSalida = d => path.join(RAIZ, 'tools/out/higgsfield/media', d.replace('gonpi/', 'gonpi/') + '.jpg');
const ultima = it => path.join(REV, it.id, `${estado[it.dest].versiones}.jpg`);

function encolar(it) {
  if (generando.has(it.dest)) return;
  generando.add(it.dest); delete errores[it.dest]; cola.push(it); bombear();
}
function bombear() {
  while (activos < 2 && cola.length) {
    const it = cola.shift(); activos++;
    const inicio = Date.now();
    const p = spawn(process.execPath, [path.join(RAIZ, 'tools/higgsfield.mjs'), it.dest + '.jpg', '--quality', 'low', '--force', '--go'],
      { cwd: RAIZ, env: process.env });
    let log = '';
    p.stdout.on('data', d => log += d); p.stderr.on('data', d => log += d);
    p.on('close', () => {
      activos--; generando.delete(it.dest);
      const f = hfSalida(it.dest);
      if (fs.existsSync(f) && fs.statSync(f).mtimeMs >= inicio - 1000) {
        const e = estado[it.dest]; e.versiones++;
        fs.mkdirSync(path.join(REV, it.id), { recursive: true });
        fs.copyFileSync(f, path.join(REV, it.id, `${e.versiones}.jpg`));
        if (e.estado === 'saltada') e.estado = 'pendiente';
        guardar();
      } else if (/credit balance/i.test(log)) { sinSaldo = true; cola.length = 0; errores[it.dest] = 'sin saldo en Higgsfield'; }
      else errores[it.dest] = log.trim().split('\n').slice(-3).join(' | ').slice(0, 300) || 'no se generó';
      rellenar(); bombear();
    });
  }
}
function rellenar() {
  if (sinSaldo) return;   // sin saldo no se lanza nada más
  const esperando = items.filter(i => estado[i.dest].estado === 'pendiente' && (estado[i.dest].versiones > 0 || generando.has(i.dest))).length;
  let falta = ADELANTE - esperando;
  for (const it of items) {
    if (falta <= 0 || auto >= MAX_AUTO) break;
    const e = estado[it.dest];
    if (e.estado === 'pendiente' && e.versiones === 0 && !generando.has(it.dest) && !errores[it.dest]) { auto++; falta--; encolar(it); }
  }
}

// ---- acciones ----
function asegurarRespaldo(dest, respaldo) {
  if (fs.existsSync(respaldo)) return;
  fs.mkdirSync(path.dirname(respaldo), { recursive: true });
  try {   // la versión original (IA) del último commit, por si el archivo ya se sustituyó antes
    const rel = path.relative(RAIZ, rutaDestino(dest)).split(path.sep).join('/');
    fs.writeFileSync(respaldo, execFileSync('git', ['show', 'HEAD:' + rel], { cwd: RAIZ, maxBuffer: 1 << 26 }));
  } catch { if (fs.existsSync(rutaDestino(dest))) fs.copyFileSync(rutaDestino(dest), respaldo); }
}
function accion(dest, a) {
  const it = items.find(i => i.dest === dest); if (!it) return 'desconocida';
  const e = estado[dest];
  const respaldo = path.join(BACKUP, dest.replace('/', path.sep) + '.jpg');
  if (a === 'usar') {
    if (!e.versiones) return 'sin imagen nueva';
    asegurarRespaldo(dest, respaldo);
    fs.copyFileSync(ultima(it), rutaDestino(dest)); e.estado = 'usada';
  } else if (a === 'saltar') { if (!e.versiones) return 'aún sin imagen'; e.estado = 'saltada'; }
  else if (a === 'rehacer') { e.estado = 'pendiente'; encolar(it); }
  else if (a === 'deshacer') { if (fs.existsSync(respaldo)) fs.copyFileSync(respaldo, rutaDestino(dest)); e.estado = 'pendiente'; }
  guardar(); rellenar(); return 'ok';
}

// ---- servidor ----
const PAGINA = fs.readFileSync(path.join(RAIZ, 'tools/revisar.html'), 'utf8');
http.createServer((req, res) => {
  const u = new URL(req.url, 'http://x');
  const sirve = (f, tipo) => fs.existsSync(f) ? (res.writeHead(200, { 'Content-Type': tipo, 'Cache-Control': 'no-store' }), fs.createReadStream(f).pipe(res)) : (res.writeHead(404), res.end());
  if (u.pathname === '/') { res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' }); return res.end(PAGINA); }
  if (u.pathname === '/api/estado') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    return res.end(JSON.stringify({
      items: items.map(i => ({ dest: i.dest, id: i.id, contexto: i.contexto, prompt: promptDe(i.dest), ...estado[i.dest], generando: generando.has(i.dest), error: errores[i.dest] ?? null })),
      auto, maxAuto: MAX_AUTO,
    }));
  }
  let m;
  if ((m = u.pathname.match(/^\/img\/actual\/([\w]+)$/)) && porId[m[1]]) return sirve(rutaDestino(porId[m[1]].dest), 'image/jpeg');
  if ((m = u.pathname.match(/^\/img\/nueva\/([\w]+)\/(\d+)$/)) && porId[m[1]]) return sirve(path.join(REV, m[1], `${m[2]}.jpg`), 'image/jpeg');
  if (u.pathname === '/api/accion' && req.method === 'POST') {
    let body = ''; req.on('data', d => body += d).on('end', () => {
      const { dest, accion: a } = JSON.parse(body || '{}');
      res.writeHead(200, { 'Content-Type': 'application/json' }); res.end(JSON.stringify({ resultado: accion(dest, a) }));
    });
    return;
  }
  res.writeHead(404); res.end();
}).listen(PUERTO, '127.0.0.1', () => { console.log(`Revisión en http://localhost:${PUERTO}  (adelante ${ADELANTE}, tope automático ${MAX_AUTO})`); rellenar(); });
