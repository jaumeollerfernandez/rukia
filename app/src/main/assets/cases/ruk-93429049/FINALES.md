# Guía de pruebas: cómo llegar a cada final (caso RUK-93429049)

Qué elegir para ver cada final. No va en el juego. Las pistas y las pistas falsas están en [PISTAS.md](PISTAS.md).

## Cómo probar

- Usa el caso **DEBUG:RUK-93429049**. Tiene su propia partida y una barra amarilla arriba para adelantar el tiempo: «siguiente evento», +10m, +1h, +6h y «08:00 →».
- Al adelantar, las elecciones que caducan por el camino **se eligen solas como «(sin responder)»**. Contesta lo que necesites **antes** de pulsar.
- El caso debug no manda notificaciones. Para empezar de cero, usa «Reset chats».
- Las salidas de la Policía (D6) se mandan desde la app **Policía**:
  - **Mañana:** de D6 08:00 a 12:00.
  - **Noche:** de D6 22:05 a D7 02:00.
- **Resolver el caso** en la app Policía (marcar el Mas de la Rosalia en el mapa antes de D7 06:30) pone `caso_resuelto`. ⚠️ Si fallas, es game over y se borra la partida.
- **El caso se cierra** cuando llega la última línea del final de Laia (`#effect: case_closed`): sale el sello «EXPEDIENTE CERRADO» y el informe de cierre con el final (`final_caso`), los logros y los finales descubiertos. Resolverlo en el mapa no lo cierra antes. «Repetir» borra la partida pero conserva logros y finales.

## Qué decide el final

Todo se decide a las **D7 06:30** con tres funciones de `main.ink`:

| Función | Es verdad si… |
|---|---|
| `rescatada()` | `patrulla_en_mas` **o** `alicia_a_salvo` **o** `caso_resuelto` |
| `capturada()` | `secta_sabe_rosalia` **y no** `rescatada()` |
| `familia_salvada()` | `vigilancia_crater` **o** `iris_ayuda` |

| Final (chat de Laia) | Condición | Primera línea |
|---|---|---|
| 1. Bueno: Alicia a salvo | `familia_salvada()` y `rescatada()` | «06:31. Entramos.» … «Alicia está en el coche patrulla de Bianya» |
| 2. Bueno: rescate en el cráter | `familia_salvada()` y `capturada()` | «06:31. Entramos.» … «entre ellos estaba Alicia» |
| 3. Alicia sola | `rescatada()` sin `familia_salvada()` | «Alicia está a salvo. Está conmigo.» |
| 4. Malo | `capturada()` sin `familia_salvada()` | «Hemos llegado tarde.» (efecto `hacked`, llamada del Guía) |
| 5. Escondida, familia a salvo | ni `rescatada()` ni `capturada()`, con `familia_salvada()` | «06:31. Entramos. Ignasi Coll, detenido.» |
| 6. Escondida y sola | ninguna de las tres | «Hay ambulancias en el cráter.» |

## Las piezas

Cada final se monta con estas piezas. Las horas son de la partida.

### Salvar a Alicia: `rescatada()` (basta una)
- **A. Patrulla al mas:** en D6, manda la salida de la mañana o la de la noche a «Mas de la Rosalia, Sant Salvador de Bianya» → `patrulla_en_mas`.
- **B. Resolver el caso:** marca el Mas de la Rosalia en el mapa antes de D7 06:30 → `caso_resuelto`.
- **C. Alicia baja con la patrulla:** solo añade texto, porque ya hace falta A. En `desconocido`, D6 23:50, elige «Sí. Baja con ellos» → `alicia_a_salvo`. Antes tienes que haberte ganado su confianza (pieza F).

### Parar la ceremonia: `familia_salvada()` (basta una)
- **D. Vigilar el cráter:** en D6, manda la salida de la **noche** a «Vigilar el cráter de Santa Margarida al amanecer» → `vigilancia_crater`.
- **E. Iris declara:** en `iris`, en la llamada de D6 00:45, elige «Iris, ve a los Mossos de Olot…» antes de las D6 02:00 → `iris_ayuda`.

### Que la secta encuentre a Alicia: `secta_sabe_rosalia` (basta una)
- **S1. Error en el grupo familiar:** en `familia`, D3 22:00, elige «la rosalia no está en ninguna residencia» (antes de D4 08:00).
- **S2. No callar a Pol ni a Arnau:** pasa **sin hacer nada**. Si **ni** Pol **ni** Arnau prometen callar, Berta lo une todo en D5 10:40.
  - `pol_calla` se consigue con «no les digas nada de la estación porfa» (D1), «sí. no les digas nada de mí porfa» (D1, tras «qué tío?») o «no se lo digas a nadie pol» (D2).
  - `arnau_calla` se consigue con «arnau, archiva el vídeo porfa…» (D2 18:45).
- **S3. Laura:** en `laura`, D2 19:40, elige «ahora mismo me iría bien una manta» y luego «subiendo hacia bracons…». Esa opción solo sale si ya tienes `sabe_rosalia`, `sabe_estrellas` o `sabe_puerta_azul`, así que en la práctica la tienes con la prueba de Núria aprobada en D3 de 17:00 a 18:00, que es cuando caduca.
- **S4. Mamá por privado:** en el contacto Mamá, «mamá, dónde está la rosalia?». Solo sale con `sabe_rosalia`: hablarle de la Rosalia a Laia en el parte de D3 o D4, o la foto de Núria de D6.

### Evitar que la encuentren (para los finales 5 y 6)
- Haz que Pol **o** Arnau prometan callar (ver S2) y **no** caigas en S1, S3 ni S4.

### F. Confianza de Alicia (opcional, cambia textos)
En `desconocido`, D4 23:40:
1. «Trabajo con una sargento de los Mossos…»
2. «Porque si fuera de ellos, ya sabrían dónde estás.»
3. «Cuídate, Alicia…»

Con eso `confianza_alicia` llega a 3. En D5 23:30, «Dime dónde estás…» da la puerta azul.

## Recetas

| Final | Haz esto | Y no hagas esto |
|---|---|---|
| **1. Bueno: Alicia a salvo** | A (salida de la mañana al mas) + D (salida de la noche al cráter). Opcional: C, E | — |
| **1b. Igual, sin Policía en el mas** | B (resolver en el mapa) + E (Iris) | — |
| **2. Bueno: rescate en el cráter** | S1 (lo más rápido) + D o E | A, B |
| **3. Alicia sola** | A o B | D, E (deja caducar la llamada de Iris) |
| **4. Malo** | No contestes nada en toda la semana ni mandes a nadie. S2 activa la secta sola | A, B, D, E |
| **5. Escondida, familia a salvo** | `arnau_calla` (D2) + D o E. Opcional: F, y en D7 07:30 en `desconocido` «Es verdad… Ya puedes bajar.» | S1, S3, S4, A, B |
| **6. Escondida y sola** | `arnau_calla` (D2) | S1, S3, S4, A, B, D, E |

Los tests automáticos cubren el 1 (contestando siempre la primera opción) y el 4 (sin contestar nada): `CaseWeekTest`.

## Variaciones que no cambian el final

| Qué | Cómo | Dónde se nota |
|---|---|---|
| Berta sabe que alguien usa el móvil | `sospecha_familia` ≥ 3: escribe en `familia` o `veins`, a mamá, o a Ramon, Roser o Conxita (+1 cada uno la primera vez) | Berta (D3-D6, efecto `glitch`), «Seas quien seas» en `familia` |
| Núria va a los Mossos | Falla una pregunta de la prueba de D3 | Laia D4, `amigas` D3-D4, Núria D5 |
| La foto del Mas de la Rosalia | `confianza_nuria` ≥ 2 en D6 11:30: aprueba la prueba o escríbele «nuri te echo de menos 💛» | Núria D6 |
| Laura delata | S3 o «por capsec…» | Laura D3-D7; Marc D5+ (la furgoneta en Capsec) |
| Barcelona | En Laia, D2, «Puede que esté en Barcelona» o no contestar | Laia D3, Papá D3, `familia` D3 |
| Pistas falsas descartadas | Biel (Can Pericot), consultas a Laia (residencia, Dani) | Laia, Biel |
| Mensajes finales de Alicia | Pieza F | `desconocido` D7 |
