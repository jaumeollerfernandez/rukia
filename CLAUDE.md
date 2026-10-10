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
- **Audio provisional de una llamada:** es lo que hace `tools/placeholders.ps1`, pero solo para ese archivo, porque el script entero crea todo lo que falta.
  - La voz «Microsoft Helena Desktop» (System.Speech) lee las líneas `> ` de su sección en LLAMADAS.md, sin acotaciones y empezando con «Audio provisional.».
  - Después, ffmpeg la convierte: `-ac 1 -c:a aac -b:a 64k`.
- **Herramientas de esta máquina:** no hay Python; usa Node para scripts. Algunos `.ink` tienen saltos de línea de Windows, así que los reemplazos de varias líneas con sed o Node fallan en ellos: usa el editor.
