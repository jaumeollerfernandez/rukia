# Pistas y pistas falsas (caso RUK-93429049)

Qué lleva al Mas de la Rosalia, qué despista y dónde se consigue cada cosa. No va en el juego.

Leyenda: 🔎 pista · 🎭 pista falsa · ⚠️ trampa (empeora el caso) · 💬 solo si el jugador pregunta (charla o contacto)

## Cómo se juega ahora

- **Charlas entre escenas.** Núria, Pol, Dani, Èric, Marta, Papá, Arnau y los grupos `amigas`, `esplai` y `veins` tienen huecos en los que el jugador escribe primero. Las preguntas cambian según el día y lo que ya sabe; caducan justo antes de la escena siguiente.
- **Contactos.** Los 36 contactos que antes abrían un chat vacío tienen ahora su propia charla (`story/<id>.ink`). Cada día, a medianoche, salen preguntas nuevas. Contestan cuando están en línea.
- **Consultas a Laia.** Después de cada parte nocturno, Laia ofrece **una** comprobación para el día siguiente. Las opciones dependen de lo averiguado: residencias, Can Pericot, la coartada de Dani, el prepago, el coche gris. Hay que elegir.
- **Riesgo.** Escribir a los adultos cotillas (Ramon, Roser, Conxita) sube `sospecha_familia`; a mamá por privado o en el grupo de vecinos (Montse está en él), más. Contarle a Laura dónde está Alicia se lo cuenta a Berta.

## La verdad: el Mas de la Rosalia

| Pista | Dónde | Cuándo |
|---|---|---|
| 🔎 El bus era un señuelo (billete cancelado, cámaras) | Galería, Laia | D1-D2 |
| 🔎 Pan para alguien | Èric, Pilar, Pol, Arnau | D1-D3 |
| 🔎💬 Pienso para perro grande en la lista de Alicia | Ona (la bolsa en su maletero) | D2+ |
| 🔎💬 «Pienso para perro grande» también en la panadería | Èric | D3+ |
| 🔎💬 Subía lotes a «la señora de la perra», en la subida a Bracons | Toni | D2+ |
| 🔎💬 «R., 78 años, viuda desde 2009, sin luz, con una perra; no quiere residencias» | Sergi (el borrador de la exposición) | D2+ |
| 🔎💬 Libro «Masies de la Vall de Bianya», doblado en Sant Salvador | Teresa | D2+ |
| 🔎💬 La chica pasado el desvío de Sant Salvador; un perro ladrando más arriba | Oriol | D2-D3+ |
| 🔎💬 El dibujo de la Laieta: puerta azul, **perro**, estrellas, «la casa de la abuela de la Ali» | Grupo esplai, Clara | D4+ |
| 🔎💬 La foto donada: Alicia de pequeña delante de una puerta azul **con pozo** | Toni | D2+ |
| 🔎 La prueba de Núria → «donde vimos las estrellas» | Núria | D3 |
| 🔎 Llamada de Rosalia, fijo de Sant Salvador | Rosalia, Laia | D4 |
| 🔎 Alicia desde el prepago: antena de Bracons, puerta azul | Desconocido, Laia | D4-D5 |
| 🔎 Puerta azul y pozo; la foto «mas de la rosalia» | Núria | D5-D6 |

También en Gonpi (perfiles de los contactos, ver `GONPI.md`): el lomo de «Masies de la Vall de Bianya» (@teresa.llibres), la foto de la coral de 1979 con la yaya Mercè, la Rosalia y la Pepita (@roser.canta) y el dibujo de la Laieta con la perra (@clara.moni, D4). Pista falsa: la «noche épica» de @biel.gg en Can Pericot (D2), que también la descarta si se lee el comentario de Pau.

## Lo que despista

| Pista falsa | Dónde | Cómo se descarta |
|---|---|---|
| 🎭 **Can Pericot, en Capsec.** El otro mas de una amiga de la yaya (la Pepita, ya muerta), también con la **puerta azul** (la yaya Mercè pintó las dos). Ahora es el refugio del esplai; Alicia tenía llaves, dejó allí una mochila hace un mes (Èric la subió en moto), faltan unas llaves (D3) y Marc ve luz de noche (D4). Teresa y Pol: Alicia miraba autobuses a Camprodon | Gonpi (D-45), esplai, Èric, Marc, Marta, Ramon, Roser, Pol, Teresa, Núria («dos casas») | No tiene **pozo ni perro**. Biel confiesa que la luz eran él y Noa (`descarta_capsec`). Laia: es municipal. La patrulla encuentra «B + N» y la mochila con polvo |
| 🎭 **La residencia.** Montse dice que la Rosalia está en una residencia de Olot; Ramon, Roser y Marta lo repiten. Alicia buscó la residencia en la biblioteca | Familia (D3), Marta, Ramon, Roser, Teresa | Consulta a Laia o patrulla: nunca ha estado. Sergi: «de aquí me sacarán con los pies por delante» |
| 🎭 **Ramon cambia los nombres**: la Rosalia en Capsec y la Pepita en Bracons | Ramon | Marta y Laia (Can Pericot era de Josepa Pericot) |
| 🎭 **Dani.** Sabe que se ha ido, ronda su calle en moto, tiene un mas «sin cobertura» en la Vall d'en Bas y los rumores dicen que se fue con él a Barcelona | Dani, Núria, Pol, Carla, Judit, amigas | Su post del concierto (D3) o la consulta a Laia (`dani_descartado`) |
| 🎭 **Girona.** El piso vacío del tío de Jan, con la llave bajo el felpudo | Jan | Núria: «a girona no, es lo primero que mirarían» |
| 🎭 Barcelona, la estación, Pol | Lo que ya había | Laia (cámaras), Pol |

## Trampas

| Trampa | Efecto |
|---|---|
| ⚠️ Escribir en `familia`, en `veins` (Montse está en el grupo) o a mamá por privado | `sospecha_familia` +2 |
| ⚠️ Escribir a Ramon, Roser o Conxita | `sospecha_familia` +1 la primera vez: se lo cuentan a Montse |
| ⚠️ Preguntarle a mamá por la Rosalia | `secta_sabe_rosalia` |
| ⚠️ Decirle a **Laura** que Alicia está por Bracons | `secta_sabe_rosalia`: Laura se lo cuenta a Berta (D3) y suben al valle |
| 🎭 Decirle a Laura que está en Capsec | `secta_a_capsec`: la secta revienta la puerta de Can Pericot (lo cuenta Marc). No cambia el final |
| ⚠️ Hablar a Núria de Alicia en tercera persona sin haberle confesado nada | `confianza_nuria` −2 |

Señales de que Laura es del Casal: su comentario «cuánto cuesta» en el post del Casal (D4), su post de mindfulness con el «bienvenida a casa, hermana» de Berta (D3), Carla la vio subir al coche de Berta.

## Policía (D6)

Dos operaciones nuevas en `police/actions.json`: **Can Pericot** (`envio_capsec`) y **las residencias de Olot** (`envio_residencia`). Las dos son pistas falsas: gastan una de las dos salidas.
