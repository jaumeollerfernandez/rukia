# Rukia

Juego Android (Kotlin, Compose): el jugador se mete en el móvil de una desaparecida (chats, Gonpi, Multimedia, Policía) y resuelve el caso en una semana de tiempo real. Arquitectura hexagonal en todos los módulos (README, «Architecture»).

## Antes de tocar la historia

**Empieza por `app/src/main/assets/cases/<caso>/INDICE.md`.** Es generado y tiene:
- **Variables:** cada variable de la historia, quién la pone (también la app) y quién la lee.
- **Cronología:** todo lo que pasa cada día, con hora.
- **Personajes:** dónde se nombra a cada uno y desde qué día.

Ve directo a las líneas `archivo:línea` que indica, en vez de buscar por los 90 `.ink`.

Contenido del caso RUK-93429049 (`app/src/main/assets/cases/ruk-93429049/`):

| Qué | Dónde |
|---|---|
| Historia, un `.ink` por chat; `main.ink` declara las variables y las funciones de los finales | `story/*.ink` |
| Quién es cada personaje (datos, biografía, cómo escribe) | `story/perfiles/<id>.md` |
| Pistas, despistes, trampas y la tapadera | `PISTAS.md` |
| Cómo llegar a cada final y a cada logro | `FINALES.md` (los logros, en `game/infrastructure/Cases.kt`) |
| Contactos y chats ocultos | `characters.json` |
| Gonpi | `gonpi/gonpi.json`, `GONPI.md` |
| Guiones de las llamadas | `story/LLAMADAS.md` |
| Fichas policiales | `police/records.json` |
| Imágenes y audios que faltan, con prompts | `MEDIA_PENDIENTE.md`, `MULTIMEDIA.md` |

## Convenciones de los .ink

- **Etiquetas:** `#at: D3 22:15` (hora del caso; pausa la historia hasta entonces), `#delay: s`, `#caduca: D3 23:00` (en la línea antes de unas elecciones), `#from: id`, `#call: audio/x.m4a`, `#effect: nombre`, `#image: ruta`. Detalle en el README.
  - **Las condiciones se evalúan al escribir la línea con `#at`, es decir, cuando llega la línea anterior.** Si un `{condición:` tiene que ver lo que pase hasta su hora, pon antes `(espera) #at: D3 22:30`: no se muestra, pero el chat espera hasta esa hora. Una línea `#at:` sola no pausa.
- **Charla de contacto** (lo que el jugador escribe primero), como en `story/pep.ink`:
  ```
  = charla
  - (opciones)
  #caduca: D{dia + 1} 00:00
  * {dia >= 3} [pregunta en minúscula, como escribe Alicia]
      respuesta #delay: 600
      -> opciones
  + [(sin responder)]
      -> charla
  ```
  - Las preguntas `*` se gastan al usarlas. Condiciónalas con `dia` o con lo que el jugador ya sabe.
  - Retrasos de las respuestas: los adultos `#delay: 600` y los jóvenes 120-300.
- **Respuestas anidadas (`**`):** siempre con su propio `#caduca: D{dia + 1} 00:00` antes y un `** [(sin responder)]`. Si no, el chat se queda esperando para siempre (lo comprueba `StoryRulesTest`).
- **Realismo de los chats:** al escribir diálogo nuevo, sigue el prompt maestro «Sistema NEXO» de `docs/NEXO.md` (Partes III, V y VI sobre todo). Lo carga el agente `historia`; no hace falta leerlo para tocar Kotlin, medios o docs.
- **Conversación viva** (ejemplos: `pep.ink`, `teresa.ink`, `ona.ink`; el perfil de cada uno explica de dónde sale):
  - Cada tema es un hilo: tras la respuesta, 2-3 réplicas `**` para seguir preguntando, reaccionar o dejarlo. No todas dan información.
  - El contacto escribe por su cuenta según su vida: función `<id>_escribe()` (la hora de hoy, o 0), `#caduca: {caduca(<id>_escribe())}` en la charla y, al principio de `charla`, el salto a su stitch con `#at` cuando `hora >= <id>_escribe()`. Así la charla no se bloquea hasta esa hora.
  - Memoria con etiquetas de opción, `* (aniversario) [...]`, que se leen después como `{pep.charla.aniversario: ...}`. La etiqueta no puede llamarse como un knot (`roser`, `mama`...).
  - **Opciones excluyentes:** lo que ya no se puede decir tras otra opción desaparece. Saludos, dos versiones de la misma pregunta, una mentira y su verdad: `* (hola) {not buenas} [hola]` / `* (buenas) {not hola} [buenos días]`. Lo mismo entre temas: si Alicia ya dijo algo, no le queda la opción de decir lo contrario como si nada.
  - Opciones concretas, distintas en intención (directa, evasiva, broma, pregunta de vuelta, cortar). Alguna puede salir mal: el personaje lo recuerda.
  - Si un personaje hace una pregunta, el jugador tiene que poder contestarla (o cortar de forma lógica), no solo cambiar de tema.
- **Lo que se descubre fuera del chat abre preguntas:** abrir una foto de la galería pone `foto_img_0391` (por `IMG_0391.jpg`) y leer una ficha, `ficha_<id>`; decláralas en `main.ink`. Cada charla tiene un `+ [(actualizar)] -> opciones` invisible que la app elige en ese momento, para que la pregunta nueva salga al instante. Las fotos con pregunta están en MULTIMEDIA.md.
- **Llamadas:** suenan sobre cualquier app y quedan todas en la app Llamadas. Si no se coge (rechazada o tras 30 s), salta el buzón de voz con el mismo audio: no hay llamadas perdidas. La historia espera a que la llamada acabe; lo que sigue puede leer `llamada_<chat>_contestada` (declárala en `main.ink`). Kimo no puede hablar con la voz de Alicia: si la coge, el que llama solo oye silencio, y Alicia necesita una excusa creíble; si no, el que llama sabe que ha dejado un mensaje de voz. Cada guion de LLAMADAS.md tiene que servir contestado y como mensaje.
- **El jugador finge ser Alicia:** los contactos le hablan a ella, cada uno con la voz de su perfil (mayúsculas o no, emojis).
- **Fechas del caso:** en relativo («ayer», «anteanoche», «dentro de dos días»). Los días de la semana solo para lo que se repite («los sábados», «el jueves hay ensayo»).

## Reglas de la trama

- **La tapadera:** Rosa d'Abril parece un grupo de duelo. Nadie dice «secta», y la verdad solo sale cruzando detalles (PISTAS.md, «La tapadera»).
- **Ignasi Coll, el sospechoso principal:**
  - Su nombre solo aparece investigando la asociación: `sabe_ignasi` o `ficha_ignasi`, o en los finales.
  - Hasta entonces es «el que acompaña» o «el de la barba blanca».
  - Lo comprueba `StoryRulesTest`, también en Gonpi, `characters.json` y las fichas.
- **Vehículos:** salen en las fichas solo al leer la ficha de su conductor o al dar la historia la matrícula (`unlockedBy`). La matrícula del chat y la de la ficha deben coincidir (test).
- **Pistas nuevas:** que sean pequeñas y salgan del trasfondo del personaje. Apúntalas en PISTAS.md.

## Al cambiar la historia, sincroniza

1. **PISTAS.md / FINALES.md:** si cambia una pista, un final, un logro o la variable que lo da.
2. **story/perfiles/<id>.md:** si cambia lo que es o sabe un personaje.
3. **GONPI.md, LLAMADAS.md y police/records.json:** si el cambio les afecta.
   - Si cambia un guion de LLAMADAS.md, regenera su audio provisional (abajo).
4. **MEDIA_PENDIENTE.md:** si hace falta una imagen o un audio nuevo.
5. **Avisa al usuario:** cualquier cambio en los `.ink` hace que las partidas en curso de ese caso empiecen de cero.

## Comandos

- **Tests:** `./gradlew.bat :app:testDebugUnitTest`.
  - Si `StoryIndexTest` falla diciendo «INDICE.md regenerado», es que la historia cambió: pásalos otra vez.
  - `CaseWeekTest` juega la semana entera y compila la historia. `StoryRulesTest` comprueba las reglas en segundos.
- **Prompt de una imagen:** `node tools/prompt.mjs <ruta>` (p. ej. `IMG_0393.jpg`) arma el prompt final de MEDIA_PENDIENTE.md y dice qué fotos de `tools/refs/ruk-93429049/` adjuntar y en qué orden. Los personajes, animales y lugares que se repiten son fichas `{token}` de la sección 0: una imagen nueva usa la ficha, no copia sus rasgos. `--refs` lista las fichas sin foto.
- **Audio provisional de una llamada:** es lo que hace `tools/placeholders.ps1`, pero solo para ese archivo, porque el script entero crea todo lo que falta.
  - La voz «Microsoft Helena Desktop» (System.Speech) lee las líneas `> ` de su sección en LLAMADAS.md, sin acotaciones y empezando con «Audio provisional.».
  - Después, ffmpeg la convierte: `-ac 1 -c:a aac -b:a 64k`.
- **Herramientas de esta máquina:** no hay Python; usa Node para scripts. Algunos `.ink` tienen saltos de línea de Windows, así que los reemplazos de varias líneas con sed o Node fallan en ellos: usa el editor.

## Agentes (`.claude/agents/`)

La sesión principal orquesta; los agentes hacen el trabajo con contexto acotado y devuelven un resumen corto.

| Agente | Para qué | Modelo |
|---|---|---|
| `historia` | Escribir o cambiar `.ink` y perfiles | opus |
| `sincronia` | Llevar un cambio de historia a PISTAS, FINALES, GONPI, LLAMADAS, records.json, MULTIMEDIA y MEDIA_PENDIENTE | sonnet |
| `medios` | Prompts de imagen y audios provisionales | haiku |
| `kotlin` | Código de la app (hexagonal) | sonnet |
| `tests` | Pasar los tests y resumir los fallos | haiku |

- **Orden:** `historia` → `sincronia` → `tests`. En paralelo solo lo que no se pisa: chats distintos, `kotlin` y `medios`.
- **`main.ink`:** solo lo edita la sesión principal. Los agentes devuelven las variables y funciones que necesitan.
- **Cambios pequeños** (unas pocas líneas): sin agentes, en la sesión principal.
- El aviso de que las partidas se reinician lo da la sesión principal, una vez, al final.
- **Flujo completo:** `/capitulo <qué cambiar>`.
