# Guía de pruebas: finales y logros (caso RUK-93429049)

Qué elegir para ver cada final y cómo conseguir cada logro (sección [Logros](#logros)). No va en el juego. Las pistas y las pistas falsas están en [PISTAS.md](PISTAS.md).

## Cómo probar

- Usa el caso **DEBUG:RUK-93429049**. Tiene su propia partida y una barra amarilla arriba para adelantar el tiempo: «siguiente evento», +10m, +1h, +6h y «08:00 →».
- Al adelantar, las elecciones que caducan por el camino **se eligen solas como «(sin responder)»**. Contesta lo que necesites **antes** de pulsar.
- El caso debug no manda notificaciones. Para empezar de cero, usa «Reset chats».
- Las salidas de la Policía (D6) se mandan desde la app **Policía**:
  - **Mañana:** de D6 08:00 a 12:00.
  - **Noche:** de D6 22:05 a D7 02:00.
- **Resolver el caso** en la app Policía (marcar el Mas de la Rosalia en el mapa antes de D7 06:30) pone `caso_resuelto`. Un intento al día: si fallas, no pasa nada; esa zona queda descartada y puedes volver a intentarlo al día siguiente. Una zona de 500 m o menos da el 100%. El mapa marca los lugares del caso que ya conoces (`police/case.json`, `places`): la casa y la estación desde el principio; Masías de Joanetes con `sabe_ruta_lotes`, `sabe_rosalia`, `sabe_puerta_azul` o `ficha_dani`; Can Pericot con `sabe_capsec`; el cráter con `ficha_ignasi` o `vigilancia_crater`; el mas del tío de Dani con `dani_sospechoso` o `dani_droga`; Girona con `sabe_girona`. El Mas de la Rosalia no sale nunca: Joanetes queda a unos 4 km y desde ahí se afina con el porcentaje.
- **El caso se cierra** cuando llega la última línea del final de Laia (`#effect: case_closed`): sale el sello «EXPEDIENTE CERRADO» y el informe de cierre con el final (`final_caso`), los logros y los finales descubiertos. Resolverlo en el mapa no lo cierra antes. «Repetir» borra la partida pero conserva logros y finales.

## Qué decide el final

El final se decide a las **D7 06:30** con estas funciones de `main.ink`. Antes, a la hora de la **entrega** (pieza H), Laia comprueba si Dani y la red se llevan a Alicia (`alicia_entregada`); si se la llevan, ya no hay rescate posible.

| Función | Es verdad si… |
|---|---|
| `rescatada()` | (`patrulla_en_mas` **o** `alicia_a_salvo` **o** `caso_resuelto`) **y no** `alicia_entregada` |
| `capturada()` | `secta_sabe_rosalia` **y no** `rescatada()` **y no** `alicia_entregada` |
| `a_merced_de_dani()` | ni `rescatada()` ni `secta_sabe_rosalia` (la secta gana a Dani) ni `alicia_entregada` |
| `familia_salvada()` | `vigilancia_crater` **o** `iris_ayuda` |

| Final (chat de Laia) | Condición | Primera línea |
|---|---|---|
| 1. Bueno: Alicia a salvo | `familia_salvada()` y `rescatada()` | «06:31. Entramos.» … «Alicia está en el coche patrulla de Bianya» |
| 2. Bueno: rescate en el cráter | `familia_salvada()` y `capturada()` | «06:31. Entramos.» … «entre ellos estaba Alicia» |
| 3. Alicia sola | `rescatada()` sin `familia_salvada()` | «Alicia está a salvo. Está conmigo.» |
| 4. Malo | `capturada()` sin `familia_salvada()` | «Hemos llegado tarde.» (efecto `hacked`, llamada del Guía) |
| 5. Entregada, familia a salvo | `alicia_entregada` con `familia_salvada()` | «06:31. Entramos. Ignasi Coll, detenido.» … «De Alicia, nada.» |
| 6. Entregada y sola | `alicia_entregada` sin `familia_salvada()` | «Hay ambulancias en el cráter.» … «De Alicia, nada.» |

Ya no existe «nadie la encuentra»: si a la hora de la entrega Alicia no está rescatada y la familia no sabe dónde está, Dani y la red se la llevan.

En el informe de cierre, cada final trae la **declaración de Laia a la prensa** (en `game/infrastructure/Cases.kt`, `rukEndings`). Solo cuenta lo que el final de `laia.ink` da por hecho y lo de las fichas. En público usa iniciales (A. S. V., I. C. F.) y nunca menciona el móvil. Si cambias un final en `laia.ink`, revisa su declaración.

## Las piezas

Cada final se monta con estas piezas. Las horas son de la partida.

### Salvar a Alicia: `rescatada()` (basta una)
- **A. Patrulla al mas:** en D6, manda la salida de la mañana o la de la noche a «Mas de la Rosalia, Joanetes» → `patrulla_en_mas`. La de la noche solo llega a tiempo si frenaste a Dani (pieza H); si no, encuentra el mas vacío.
- **B. Resolver el caso:** marca el Mas de la Rosalia en el mapa antes de la entrega (D6 18:00, o D7 04:00 con Dani frenado) → `caso_resuelto`.
- **C. Alicia baja con la patrulla:** solo añade texto, porque ya hace falta A. En `desconocido`, D6 23:50, elige «Sí. Baja con ellos» → `alicia_a_salvo`. Antes tienes que haberte ganado su confianza (pieza F).

### Parar la ceremonia: `familia_salvada()` (basta una)
- **D. Vigilar el cráter:** en D6, manda la salida de la **noche** a «Vigilar el cráter de Santa Margarida al amanecer» → `vigilancia_crater`.
- **E. Iris declara:** en `iris`, en la llamada de D6 00:45, elige «Iris, ve a los Mossos de Olot…» antes de las D6 02:00 → `iris_ayuda`.
- **D'. Laia vigila el cráter (fichas policiales):** pide y lee la ficha de **Ignasi Coll** en Policía › Fichas policiales (`ficha_ignasi`). Su ficha no aparece hasta saber su nombre (`sabe_ignasi`): Laia lo da al investigar la asociación (CRA en el parte del D2, «¿Qué es Rosa d'Abril?», las voluntades o el coche gris), o lo da la ficha del Seat. A partir del D4, en la consulta nocturna de Laia, elige «Mira el atestado de Ignasi Coll de hace once años…» → `vigilancia_crater` **sin gastar ninguna salida**: las dos quedan libres (por ejemplo, para el mas y las masías).

### Que la secta encuentre a Alicia: `secta_sabe_rosalia` (basta una)
- **S1. Error en el grupo familiar:** en `familia`, D3 22:00, elige «la rosalia no está en ninguna residencia» (antes de D4 08:00).
- **S2. No callar a Pol ni a Arnau:** pasa **sin hacer nada**. Si **ni** Pol **ni** Arnau prometen callar, Berta lo une todo en D5 10:40.
  - `pol_calla` se consigue con «no les digas nada de la estación porfa» (D1), «sí. no les digas nada de mí porfa» (D1, tras «qué tío?») o «no se lo digas a nadie pol» (D2).
  - `arnau_calla` se consigue con «arnau, archiva el vídeo porfa…» (D2 18:45).
- **S3. Laura:** en `laura`, D2 19:40, elige «ahora mismo me iría bien una manta» y luego «subiendo hacia bracons…». Esa opción solo sale si ya tienes `sabe_rosalia`, `sabe_estrellas` o `sabe_puerta_azul`, así que en la práctica la tienes con la prueba de Núria aprobada en D3 de 17:00 a 18:00, que es cuando caduca.
- **S4. Mamá por privado:** en el contacto Mamá, «mamá, dónde está la rosalia?». Solo sale con `sabe_rosalia`: hablarle de la Rosalia a Laia en el parte de D3 o D4, o la foto de Núria de D6.

### H. La deuda de Dani: la entrega (finales 5 y 6)
Dani debe 6.000 € a la red de Barcelona (los del Audi andorrano) y paga con Alicia. Desde el D4 pregunta por ella por todo el valle. La entrega solo pasa si `a_merced_de_dani()`: ni rescatada ni la familia sabe dónde está (la secta gana a Dani).
- **Sin frenar a Dani:** D6 19:20, durante el apagón. Laia lo cuenta a las 22:05 (`laia.entrega_tarde`). Solo la salvan la salida de la **mañana** al mas o el mapa resuelto antes de las 18:00.
- **Con Dani frenado** (`dani_frenado`): D7 04:20 (`laia.entrega_madrugada`). La salida de la **noche** al mas aún llega a tiempo. Se frena con una de estas:
  - **H1. Laia lo detiene:** en la consulta (D4+), «Dani vende para los del Audi negro de Andorra…». Sale con `dani_droga` (Dani lo confiesa el D4 21:00, antes del parte de Laia) y `sabe_audi` (la matrícula, D3 23:40). **No gasta salida.**
  - **H2. La operación del D6:** «Detener a Dani Rius e interrogarlo» (gasta la salida; si no le crees, también la de la noche).
  - **H3. Avisar a Alicia:** en `desconocido`, D5 23:30, «Dani te está buscando…». Necesita `sabe_dani_busca` (Toni en `aliments` D5 16:10, Iker D5 01:10, Dani D5 23:15 o D6 13:00, Pilar D6 07:40, Èric D6 09:45) y `confianza_alicia` ≥ 1. Ocupa la pregunta de la puerta azul.
- **Para ver el 5 o el 6:** haz que Pol **o** Arnau prometan callar (S2), no caigas en S1, S3 ni S4 y no la rescates.

### F. Confianza de Alicia (opcional, cambia textos)
En `desconocido`, D4 23:40:
1. «Trabajo con una sargento de los Mossos…»
2. «Porque si fuera de ellos, ya sabrían dónde estás.»
3. «Cuídate, Alicia…»

Con eso `confianza_alicia` llega a 3. En D5 23:30, «Dime dónde estás…» da la puerta azul y la llamada de la pieza I.

### I. La llamada de Alicia (con confianza 3)
En `desconocido`, D5 23:30, con `confianza_alicia` 3 (pieza F), elige «Dime dónde estás. Mandaremos una patrulla…». Alicia no lo escribe: **te llama** (`audio/alicia_ubicacion.m4a`). Si la coges, o escuchas su mensaje en el buzón de voz (app Llamadas o Grabaciones) antes de las D6 01:30, Laia la oye contigo (`alicia_llamo`). Si no la escuchas a tiempo, esa noche no pasa nada y Alicia sigue sola en el mas:
- A las D6 01:30, Laia escucha la grabación y manda el coche de guardia al mas, **sin gastar salida**. Alicia, en comisaría (`patrulla_en_mas`, `alicia_a_salvo`): a salvo de la secta y de Dani.
- Alicia se lo cuenta todo: Rosa d'Abril, los ayunos, el agua, los papeles, Ignasi Coll (`sabe_secta`, `sabe_ignasi`) y la Trobada de l'Alba. Con su declaración, Laia pone gente en el cráter (`vigilancia_crater`).
- Te lleva directo al **final 1**. Las dos salidas del D6 quedan libres (para logros: el mas del tío de Dani, la detención…).
- Esta opción ocupa el hueco del aviso de Dani (H3) esa noche.

### G. Fichas policiales (opcional, dan pistas)
En Policía › Fichas policiales se pide una ficha y llega horas después (12 h el D1, 10 el D2, 8 el D3, 6 el D4, 4 el D5, 2 el D6, 1 el D7). Solo se tramita una a la vez. Leerla pone `ficha_<id>`:
- **Dani** (`ficha_dani`): la multa del radar de Joanetes abre en su charla «tu moto salta un radar…» → `sabe_ruta_lotes`, lo mismo que si lo detienes y le crees, sin gastar una salida.
- **Ignasi** (`ficha_ignasi`): la pieza D' y, en `iris` (D5 00:30), «¿Sabes lo que pasó hace once años…?», donde Iris adelanta el cuándo y el dónde. Da el logro «El precedente».
- Las demás solo informan. El Seat gris sube al cráter al amanecer los tres días antes del caso; la furgoneta, con Iris al volante y garrafas de agua; Montse vendió el piso de la yaya por 120.000 € (como en la libreta, IMG_0401).
- Quien tiene reseña (Dani, Ignasi, Marc) deja en Multimedia sus fotos policiales de frente y de perfil.

## Recetas

| Final | Haz esto | Y no hagas esto |
|---|---|---|
| **1. Bueno: Alicia a salvo** | A (salida de la mañana al mas) + D (salida de la noche al cráter). Opcional: C, E | — |
| **1b. Igual, sin Policía en el mas** | B (el mapa, resuelto antes del apagón del D6 a las 18:00) + E (Iris) | — |
| **1d. Igual, la vía de Alicia** | F (confianza 3) + I (coge su llamada el D5 por la noche). Nada más | — |
| **1c. Igual, con la salida de la noche al mas** | H1 (Laia detiene a Dani) + D' (Laia vigila el cráter) + A con la salida de la noche | — |
| **2. Bueno: rescate en el cráter** | S1 (lo más rápido) + D o E | A, B |
| **3. Alicia sola** | A por la mañana, o H + A por la noche, o B | D, E (deja caducar la llamada de Iris) |
| **4. Malo** | No contestes nada en toda la semana ni mandes a nadie. S2 activa la secta sola | A, B, D, E |
| **5. Entregada, familia a salvo** | `arnau_calla` (D2) + D o E | S1, S3, S4, A, B |
| **6. Entregada y sola** | `arnau_calla` (D2) | S1, S3, S4, A, B, D, E |

`CaseWeekTest` cubre el 1 (contestando siempre la primera opción), el 4 (sin contestar nada) y la trama de Dani: el 6 (nadie lo frena), el 3 (frenado y salida de la noche al mas) y el 5 (frenado, sin rescate y con vigilancia del cráter). Y la llamada de Alicia (pieza I): final 1.

## Logros

Salen en el informe de cierre (al llegar el final de Laia, D7). Se calculan con las variables de la historia en ese momento: da igual cuándo se consiguieran, cuentan si siguen siendo verdad al final. Están definidos en `game/infrastructure/Cases.kt`.

- **Se conservan entre partidas.** «Repetir» borra la semana, pero los logros y los finales descubiertos se quedan.
- **% completado** = (logros conseguidos + finales descubiertos) / (15 + 6).
- Las «pistas falsas» del informe (x/4) cuentan solo las de **esta** partida.

### Pistas clave

| Logro | Variable | Cómo conseguirlo |
|---|---|---|
| **El billete señuelo** | `descarta_bus` | En el parte de Laia del D2 (08:30, antes de las 14:00) elige «En su galería hay un correo: canceló el billete…» o «Pol, su ex, la llevó…»: las dos piden las cámaras, que el D2 16:10 confirman que no subió al bus. Si eliges «Barcelona» o no contestas, se pierde |
| **CRA Serveis** | `sabe_cra` | En el parte del D2 (21:30) elige «…en el banco el beneficiario es «CRA Serveis»». También con las consultas a Laia «¿Qué es «Rosa d'Abril»…?» o, desde el D4, «¿De quién es el coche gris…?» |
| **Donde vimos las estrellas** | `sabe_estrellas` | Aprueba la prueba de Núria del D3 (17:00-18:00): «el port de la selva» y «una pulsera roja» (IMG_0393, IMG_0395 o la ficha policial de Alicia) |
| **La porta blava** | `sabe_puerta_azul` | Cualquiera: Núria lo recuerda el D5 10:30 si aprobaste su prueba; Alicia por el prepago (D5 23:30, con `confianza_alicia` 3); Núria el D6 con `confianza_nuria` ≥ 2 |
| **Un fijo en Joanetes** | `sabe_rosalia` | Cualquiera: contarle a Laia lo del pan para la Rosalia (parte D3) o la llamada de la anciana (parte D4); Toni si confía (D3+); Núria el D6 con `confianza_nuria` ≥ 2 |
| **Confianza** | `confianza_alicia` ≥ 3 | Pieza F: en `desconocido` (D4 23:40) elige «Trabajo con una sargento…», «Porque si fuera de ellos…» y «Cuídate, Alicia…» |
| **El precedente** | `ficha_ignasi` | Primero hay que saber su nombre (ver pieza D'). Luego pide y lee su ficha policial |

### Pistas falsas descartadas

| Logro | Variable | Cómo conseguirlo |
|---|---|---|
| **Barcelona** | `descarta_bus` | Igual que «El billete señuelo» (salen juntos) |
| **Girona** | `girona_descartado` | En `mireia` (D2+), «mire, lo de girona…» y luego «no se lo he contado a nadie. ni a nuri. tranquila 💛». No la presiones (`mireia_cerrada`) ni dejes que Núria vaya a los Mossos |
| **Can Pericot** | `descarta_capsec` | Primero oye hablar de Can Pericot (`sabe_capsec`: Marc D4 «has visto luz por algún mas…?», el esplai…). Luego, en `biel`, «biel, las llaves del refugi las tienes tú, no?» |
| **El mas del tío de Dani** | `dani_registrado` | En D6, manda una salida de Policía a «El mas del tío de Dani, en la Vall d'en Bas». Gasta la salida |

### Decisiones

| Logro | Variable | Cómo conseguirlo |
|---|---|---|
| **Labios sellados** | `pol_calla` o `arnau_calla` | Pol: «no les digas nada de la estación porfa» (D1), «sí. no les digas nada de mí porfa» (D1) o «no se lo digas a nadie pol» (D2). Arnau: «arnau, archiva el vídeo porfa…» (D2 18:45) |
| **Fantasma** | `sospecha_familia` = 0 | No escribas en `familia` ni en `veins`, ni a Mamá por privado, ni a Ramon, Roser o Conxita. Y que no se corra el rumor: dos contactos que te bloquean también suben la sospecha. Ver PISTAS.md, «Trampas» y «El rumor». Ojo: Conxita da la matrícula de la furgoneta, pero escribirle rompe este logro |
| **Iris declara** | `iris_ayuda` | Pieza E: en `iris`, llamada del D6 00:45, «Iris, ve a los Mossos de Olot…» antes de las 02:00 |
| **Vigilancia al alba** | `vigilancia_crater` | Pieza D (salida de la noche del D6 al cráter) o D' (consulta a Laia del D4+ con la ficha de Ignasi) |

### Recetas de logros

- **Los 15 en una partida** son posibles según las reglas (no lo cubre ningún test): en el parte del D2, el correo del billete y luego CRA; Arnau archiva el vídeo (D2); Mireia con cuidado (D2+); aprueba a Núria (D3); con CRA en el parte del D2 sale su nombre: pide su ficha el D3 (llega en 8 h); pregúntale a Marc por la luz (D4) y luego a Biel por las llaves; F con Alicia (D4); en la consulta del D4, D' (el cráter, sin gastar salida); Iris el D6 00:45; en el D6, la salida de la mañana al mas de la Rosalia y la de la noche al tío de Dani. Durante toda la semana, nada de escribir a la familia, al grupo de vecinos ni a Ramon, Roser o Conxita.
- **El conflicto de siempre:** sin D', «El mas del tío de Dani» y «Vigilancia al alba» compiten por las dos salidas con el mas de la Rosalia. Se puede repartir entre partidas: los logros se conservan.

## Variaciones que no cambian el final

| Qué | Cómo | Dónde se nota |
|---|---|---|
| Berta sabe que alguien usa el móvil | `sospecha_familia` ≥ 3: escribe en `familia` o `veins`, a mamá, o a Ramon, Roser o Conxita (+1 cada uno la primera vez) | Berta (D3-D6: pasa de «Tienes 24 horas para devolverme a mi hermana» a suplicar «dime dónde está»), «Seas quien seas» en `familia` |
| Núria va a los Mossos | Falla una pregunta de la prueba de D3 | Laia D4, `amigas` D3-D4, Núria D5 |
| La foto del Mas de la Rosalia | `confianza_nuria` ≥ 2 en D6 11:30: aprueba la prueba o escríbele «nuri te echo de menos 💛» | Núria D6 |
| Laura delata | S3 o «por capsec…» | Laura D3-D7; Marc D5+ (la furgoneta en Capsec) |
| Barcelona | En Laia, D2, «Puede que esté en Barcelona» o no contestar | Laia D3, Papá D3, `familia` D3 |
| Pistas falsas descartadas | Biel (Can Pericot), consultas a Laia (residencia, Dani) | Laia, Biel |
| Mensajes finales de Alicia | Pieza F | `desconocido` D6 23:50 y D7 (calla si se la han llevado) |
| Dani desaparece o sigue en el calabozo | Finales 5 y 6: detenido sin creerle (H1 o H2) o no | Laia D7, Dani D7 01:30 («perdóname») |
