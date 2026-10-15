# Pistas y pistas falsas (caso RUK-93429049)

Qué lleva al Mas de la Rosalia, qué despista y dónde se consigue cada cosa. No va en el juego.

Leyenda: 🔎 pista · 🎭 pista falsa · ⚠️ trampa (empeora el caso) · 💬 solo si el jugador pregunta (charla o contacto)

## Cómo se juega ahora

- **Charlas entre escenas.** Núria, Pol, Dani, Èric, Marta, Papá, Arnau y los grupos `amigas`, `esplai` y `veins` tienen huecos en los que el jugador escribe primero. Las preguntas cambian según el día y lo que ya sabe; caducan justo antes de la escena siguiente.
- **Contactos.** Los 36 contactos que antes abrían un chat vacío tienen ahora su propia charla (`story/<id>.ink`). Cada día, a medianoche, salen preguntas nuevas. Contestan cuando están en línea.
  - Cada tema admite réplicas (seguir preguntando, reaccionar, cortar), y lo que ya no tiene sentido decir desaparece (saludos, una mentira y su verdad).
  - Muchos escriben por su cuenta según su vida (Aina, Oriol, Teresa, Enric, Imma...). Así, una pista puede llegar aunque el jugador no pregunte: Oriol el D4, si nadie le ha preguntado por la chica de Bracons; Enric el D4, por el coche gris.
  - Recuerdan lo que se les dice: una promesa incumplida (el turno del Banc, el cumple de Mireia, la clase de Leo) sale después.
- **La galería abre preguntas.** Abrir una foto de pista pone `foto_img_<número>` y la charla de quien sabe de eso ofrece al momento una pregunta nueva (Roser, Carla, Papá, Marta, Pol, Toni; tabla en MULTIMEDIA.md). Contarle a Laia lo del reembolso (D2) exige haber abierto `IMG_0411.jpg` antes.
  - 🔎💬 Roser (IMG_0391): la Trufa es de la Rosalia, y su casa tiene pozo; la de la Pepita (Capsec), no.
  - 🔎💬 Pol (IMG_0409): por el retrovisor la vio cruzar hacia la rotonda, no hacia los autobuses.
  - 🔎💬 Toni (IMG_0413): las masías de arriba de Joanetes no tienen luz; si confía, la de la señora de la perra va con velas.
  - 🔎💬 Papá (IMG_0401): el piso de la yaya se vendió por 120.000 € y Montse le dijo que no le quedó nada. Marta (IMG_0402): el folleto se lo dejó el de la barba blanca. Carla (IMG_0397): Alicia fue a una reunión «del alba» y no quiso volver.
- **Gonpi archiva.** Si Alicia se lo pide, Arnau archiva el vídeo de la rotonda (`arnau_calla`) y Èric su post de las barras (`eric_archiva`): desaparecen del feed.
- **Llamadas.** Suenan sobre cualquier app y quedan en la app Llamadas: contestada, se oye en el momento; si no, salta el buzón de voz con el mismo audio. La historia espera a saber si el jugador la cogió. Pilar (D3): si la coge, solo oye ruido y pregunta; si no, le deja el mensaje en el buzón y le escribe. Papá (D2): igual. Alicia elige la excusa (afónica, gente delante, sin cobertura, la ducha).
- **Consultas a Laia.** Después de cada parte nocturno, Laia ofrece **una** comprobación para el día siguiente. Las opciones dependen de lo averiguado: residencias, Can Pericot, la coartada de Dani, el prepago, el coche gris. Hay que elegir.
- **Riesgo.** Escribir a los adultos cotillas (Ramon, Roser, Conxita) sube `sospecha_familia`; a mamá por privado o en el grupo de vecinos (Montse está en él), más. Contarle a Laura dónde está Alicia se lo cuenta a Berta.

## Confianza: si te pillan, cambia la partida

Los contactos con pistas verdaderas tienen confianza propia (`fia_<id>` en `main.ink`): **1** confía (cuenta una pista extra), **0** normal, **-1** recela (no cuenta nada hasta que lo convences), **-2** bloqueado (no vuelve a hablar).

| Contacto | El desliz o la prueba | Respuesta buena (dónde está) | Si recela: mentir | Si recela: la verdad («Trabajo con los Mossos») | Premio si confía |
|---|---|---|---|---|---|
| Toni | Preguntarle la ruta de los lotes (Alicia la sabe): «cómo se llama la perra?» | «trufa» (IMG_0391). Trampa: «rocky» (el perro perdido de Gonpi) | Cuela sin rumores y da la ruta | Colabora (adulto sensato) | La Rosalia le dijo que «la nena» iba a quedarse con ella (`sabe_rosalia`) |
| Teresa | Preguntarle qué libros sacó: «cuál me debes?» | «Nada» (grupo de lectura). Trampa: «Bernarda Alba» (teatro) | Cuela sin rumores y da los libros | Colabora y es discreta | La fotocopia de un mas de 1782 con pozo y banco de piedra, «a mitja pujada de Bracons» (`teresa.charla.fotocopia`; ya no pone `sabe_puerta_azul`) |
| Èric | Preguntarle qué dijo Pilar (Alicia estaba delante): «qué me debes por la masa madre?» | «un café» o «se murió sola» (historial D-3). Trampa: «una birra» (es por el turno) | Cuela sin rumores y da lo del pan | Colabora, pero es un bocazas: lo cuenta en el Forn (`delata`) | Nada nuevo sobre la Rosalia: solo lo de la mochila en Capsec (`sabe_capsec`) |
| Ona | Solo si ya hay rumores: «qué dorsal llevas?» | «el 7» (IMG_0394) | Cuela sin más rumores y da la lista | Se asusta, bloquea y lo cuenta en el vóley (`delata`) | Si le dices que el pienso es para una señora mayor: detrás de la lista, «sábado: lote + pienso → R.» |
| Oriol | Solo si ya hay rumores: «qué te pedí hace unas semanas?» | «los apuntes de dinámicas» (su historial) | Cuela sin más rumores y da lo que vio | Colabora asustado | La masía tras una curva con un banco de piedra, con pozo |
| Núria | Su sistema de siempre (`confianza_nuria`) | | Nueva: «nuri perdona, estoy fatal» la recupera una vez si no hay rumores; si los hay, empeora | | |

**El rumor (`delatado`).** Cada contacto que te bloquea (o que lo cuenta, como Èric y Ona con la verdad) suma uno.
- **1**: Laia avisa en la consulta de la noche, y Ona y Oriol empiezan a ponerte a prueba.
- **2**: las mentiras ya no cuelan y el rumor llega a Montse (`sospecha_familia` +1).

**El suelo: lo que no se pierde nunca.** La galería, Gonpi (el post de Oriol, el comentario de @excursions.bianya con la puerta azul y el pozo, el dibujo de la Laieta), Laia (cámaras, padrón), la llamada de Rosalia (D4) y el pan de Pilar no dependen de la confianza. Con eso se puede llegar a la subida a Bracons y a la puerta azul, pero sin nombres ni detalles. Con confianza, en cambio, se consiguen el nombre de la Rosalia (Toni), la fotocopia del mas (Teresa) y la ruta exacta mucho antes, y el D6 es más fácil acertar.

## La tapadera: que Rosa d'Abril no parezca una secta

Al empezar, la familia tiene que parecer una familia en duelo y con problemas de dinero, no una secta. Cada cosa rara tiene una excusa creíble, y la verdad solo sale **cruzando detalles o preguntando**. Nadie la cuenta de golpe antes del D4.

| Lo que se ve | La excusa | El detalle que no cuadra | Dónde se confirma |
|---|---|---|---|
| «La asociación», «el grupo» | El grupo de duelo al que van desde que murió la yaya Mercè (hace tres años el D7) | Un señor mayor «que las acompaña», sin nombre: «barba blanca» en el banco (Marta D2), en casa de Pol, delante de casa (Enric); Gemma y Laura hablan de «el que acompaña». **Su nombre (Ignasi Coll) solo sale investigando la asociación**: Laia (CRA en el parte D2, «¿Qué es Rosa d'Abril?», las voluntades o el coche gris; `sabe_ignasi`) o la ficha del Seat. Hasta entonces su ficha policial no aparece | 💬 Pol, Gemma, Jordi, Marta |
| Los ayunos | Ayuno solidario: lo ahorrado va a familias con escasez (familia D-6, canal D-10) | Alicia se marea en los entrenos | |
| 2.840 € de la cuenta de Alicia (D2) | «Lo de Hacienda» por la venta del piso de la yaya; se lo devolverán con el seguro | 🔎 El banco dice **CRA SERVEIS**, no Hacienda. El canal del Casal (D-10) pide las aportaciones a «CRA Serveis». La sala nueva «ya tiene tejado» media hora después (canal y Gonpi D2). 💬 Jordi: la plusvalía ya se pagó hace dos años. 💬 Marta: «Hacienda no te espera en la oficina con un señor de barba» | Laia: parte D2 («el beneficiario es CRA Serveis») → D3 le dice quién es CRA. O consulta «¿Qué es Rosa d'Abril?» si se sabe el nombre (`sabe_cra`) |
| «Las voluntades» (D4) | Voluntades anticipadas, un papel médico «después de lo de la yaya en la UCI» | 🔎 Firmadas «en el despacho de siempre»; ese día el canal del Casal anuncia que su despacho las tramita. 💬 Iris: el mismo día te hacen firmar otro papel | Consulta a Laia (D4+, con `sabe_cra`): testigos Ignasi y Berta, «que no la reanimen» y un testamento a favor de la asociación |
| Ropa blanca, sin móvil, al alba en el cráter (D5-D7) | El aniversario de la yaya: de blanco como su coral, y sin móviles porque «es un momento para ella» | 🔎 El canal: en ayunas, «con el agua de la Casa» (las botellas de Iris). El número sin nombre que escribe de noche (D5): «las familias, completas» | Alicia (prepago) solo da pistas: «pregunta en qué despacho los firmaron» |

Laia ya no reconoce el nombre «Rosa d'Abril» el D1: un grupo de duelo no es delito. Hasta que el jugador le da algo concreto (CRA, el coche gris o las voluntades) no lo trata como un caso.

## La verdad: el Mas de la Rosalia

| Pista | Dónde | Cuándo |
|---|---|---|
| 🔎 El bus era un señuelo (billete cancelado, cámaras) | Galería, Laia | D1-D2 |
| 🔎 Pan para alguien | Èric, Pilar, Pol, Arnau | D1-D3 |
| 🔎💬 Pienso para perro grande en la lista de Alicia | Ona (la bolsa en su maletero) | D2+ |
| 🔎💬 «Pienso para perro grande» también en la panadería | Èric | D3+ |
| 🔎💬 Subía lotes a «la señora de la perra», en la subida a Bracons | Toni | D2+ |
| 🔎💬 «R., 78 años, viuda desde 2009, sin luz, con una perra; no quiere residencias» | Sergi (el borrador de la exposición) | D2+ |
| 🔎💬 Libro «Masies de la Vall de Bianya», doblado arriba de Joanetes | Teresa | D2+ |
| 🔎💬 La chica pasado el desvío de Joanetes; un perro ladrando más arriba | Oriol | D2-D3+ |
| 🔎💬 El dibujo de la Laieta: casita en el bosque, **perro** enorme, estrellas, «la casa de la abuela de la Ali» (Pau: «la historia de la casa del cuento») | Grupo esplai, Clara | D4+ |
| 🔎💬 La foto donada: Alicia de pequeña delante de una puerta azul **con pozo** | Toni | D2+ |
| 🔎 La prueba de Núria → «donde vimos las estrellas» | Núria | D3 |
| 🔎 Llamada de Rosalia, fijo de Joanetes | Rosalia, Laia | D4 |
| 🔎 Alicia desde el prepago: antena de Bracons, puerta azul | Desconocido, Laia | D4-D5 |
| 🔎 Puerta azul y pozo; la foto «mas de la rosalia» | Núria | D5-D6 |
| 🔎 Con confianza 3, Alicia llama y da la ubicación exacta. Si el jugador la coge o escucha el mensaje del buzón antes de las D6 01:30, Laia la saca esa madrugada y ella le cuenta la secta y la ceremonia (`alicia_llamo`, FINALES.md pieza I) | Alicia (prepago), Laia | D5 noche |

También en Gonpi (perfiles de los contactos, ver `GONPI.md`): el lomo de «Masies de la Vall de Bianya» (@teresa.llibres), la foto de la coral de 1979 con la yaya Mercè, la Rosalia y la Pepita (@roser.canta) y el dibujo de la Laieta con la perra (@clara.moni, D4). Pista falsa: la «noche épica» de @biel.gg en Can Pericot (D2), que también la descarta si se lee el comentario de Pau.

## Lo que despista

| Pista falsa | Dónde | Cómo se descarta |
|---|---|---|
| 🎭 **Can Pericot, en Capsec.** El otro mas de una amiga de la yaya (la Pepita, ya muerta), también con la **puerta azul** (la yaya Mercè pintó las dos; solo lo dicen Gonpi D-45 y Central). Ahora es el refugio del esplai; Alicia tenía llaves, dejó allí una mochila hace un mes (Èric la subió en moto), faltan unas llaves (D3) y Marc ve luz de noche (D4). Pau relaciona el dibujo de la Laieta con Can Pericot. Teresa y Pol: Alicia miraba autobuses a Camprodon | Gonpi (D-45), esplai, Èric, Marta, Ramon, Roser, Pol, Teresa, Núria («dos casas») | No tiene **pozo ni perro**. Biel confiesa que la luz eran él y Noa (`descarta_capsec`). Laia: es municipal. La patrulla encuentra «B + N» y la mochila con polvo |
| 🎭 **La residencia.** Montse dice que la Rosalia está en una residencia de Olot; Ramon, Roser y Marta lo repiten. Alicia buscó la residencia en la biblioteca | Familia (D3), Marta, Ramon, Roser, Teresa | Consulta a Laia o patrulla: nunca ha estado. Sergi: «de aquí me sacarán con los pies por delante» |
| 🎭 **Ramon cambia los nombres**, sin pronunciar «Rosalia»: «a la otra en Capsec» y «la Pepita en Bracons» | Ramon | Marta y Laia (Can Pericot era de Josepa Pericot) |
| 🎭 **Dani.** Chico malote de poca monta: sabe que se ha ido, ronda su calle en moto, tiene un mas «sin cobertura» en la Vall d'en Bas y los rumores dicen que se fue con él a Barcelona. Menudea (costo, hierba, pastillas en el bar), tuvo un tonteo con Alicia (un beso que ella llamó «un error») y la controlaba. D3 noche: unos del bar le reclaman dinero (el «audi negro de Andorra»). D4: confiesa que vende (`dani_droga`). Que se fuera no tiene que ver con él; lo que hace después, sí (ver «La deuda de Dani») | Dani, Núria, Pol, Carla, Judit, amigas | Su post del concierto (D3) o la consulta a Laia (`dani_descartado`: multa + denuncia archivada por hachís, sin conexión con la desaparición). **Operación `dani_mas`** (D6): el mas del tío de Dani; solo hay hachís y una báscula, y gasta una de las dos salidas (`dani_registrado`: Dani lo comenta) |
| 🎭 **La red de trata de Barcelona.** Dani le ofreció «curro» en un club del Port Olímpic con su amigo Iker: 1.500 €, piso pagado, «no lo digas en casa», le pagaba el bus (Dani D-15, D-9). Iker le escribe (historial D-14, D2: «trae el dni», «te recojo en nord»; D3: la policía en el club). Laia (D3): Barcelona desarticula una red que capta chicas así y las recoge en Nord. La coartada de Dani lo pone en ese club la noche del billete | Dani, Iker, Laia, Carla («que te has ido a barcelona con el dani») | La cámara de la estación (no subió al bus). Hasta el D3 es despiste: Alicia no se fue con ellos. Desde el D4 se vuelve real (ver «La deuda de Dani») |
| 🎭 **Girona y la clínica (embarazo).** Carla: la prima de Judit, en una farmacia de Girona, la vio comprar una prueba de embarazo con una chica con capucha. El banco: farmacia de Girona (D-9). Mireia: «si alguien pregunta por girona, no fuimos» (D1). Sergi: «lo de Girona no era asunto tuyo». Núria: fue a Girona hace nueve días. Laia (consulta): la cámara del centro de salud sexual. Jan: el piso vacío de su tío, llaves bajo el felpudo | Carla, Mireia, Sergi, Núria, Jan, banco, Laia | La embarazada era **Mireia** (de Sergi): Alicia la acompañó y le pagó la prueba. El detalle: el **Bizum de 13 € de Mireia** al día siguiente (banco D-8) y que Mireia sea «la de la capucha» (en la galería, IMG_0406: el selfi en el bus a Girona, con su rizo negro y sus gafas redondas bajo la capucha). **Mireia lo confiesa** solo si el jugador la trata con cuidado (`girona_descartado`); si la presiona, se cierra (`mireia_cerrada`). **Operación `piso_jan`**: una manta, dos tazas y el ticket de la farmacia, de hace nueve días. Nadie ha dormido allí desde entonces. Núria: «a girona no, es lo primero que mirarían» |
| 🎭 Barcelona, la estación, Pol | Lo que ya había | Laia (cámaras), Pol |

## La deuda de Dani: la trata de verdad (D4-D7)

Hasta el D3, la trata es una pista falsa: Alicia no se fue con Dani ni con Iker. Desde el D4 es la amenaza real de los finales 5 y 6.

| Qué | Dónde | Cuándo |
|---|---|---|
| Dani debe 6.000 € a los del Audi andorrano (le pillaron un kilo fiado); son de la parte de la red de Barcelona que no cayó. «me han dado dos días» | Dani | D3 23:40, D4 21:00 |
| Acorralado, ofrece a Alicia para saldar la deuda. Iker le presenta a la red y les pasa sus fotos | Laia (detención), operación `dani_detencion`: el chat con Iker | D4+ |
| Pregunta por ella por todo el valle (`sabe_dani_busca`) | Toni en `aliments` (D5 16:10), Pilar (D6 07:40), Èric (D6 09:45) | D5-D6 |
| Le escribe desesperado: «dime dónde estás», «es mejor que te encuentre yo que otros», «sé que estás por joanetes» | Dani | D5 23:15, D6 13:00 |
| Iker, con buenas palabras: «yo te mando a alguien», «esta tarde paso por la zona» | Iker | D5 01:10, D6 10:30 |
| **La entrega**: Dani y dos hombres en una furgoneta blanca se la llevan del mas (`alicia_entregada`). Rosalia herida, la pulsera roja en el barro | Laia, Central (`mas_vacio`), Dani («perdóname») | D6 19:20, o D7 04:20 con Dani frenado |

- **La secta gana a Dani:** si la familia sabe dónde está (`secta_sabe_rosalia`), no hay entrega.
- **Frenar a Dani solo retrasa** (`dani_frenado`): Laia lo detiene (consulta D4+, con `dani_droga` y `sabe_audi`, sin gastar salida), la operación del D6, o avisar a Alicia por el prepago (D5 23:30; se esconde en el pozo cuando sube la moto). La entrega pasa a D7 04:20 y la salida de la noche al mas aún la salva.
- **Dani, en los finales 5 y 6:** desaparece («esa gente no deja cabos sueltos»), salvo si está en el calabozo.

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
Una tercera operación, **el mas del tío de Dani** (`envio_dani`), también es pista falsa: aparece costo y una báscula, ni rastro de Alicia.
La cuarta, **detener a Dani** (`envio_detencion_dani`), gasta una salida pero frena su búsqueda (`dani_frenado`) y puede devolver una pista verdadera si el jugador le cree. Si no le cree, la operación de la noche (mas, masías o cráter) se queda sin coche. Si Laia ya lo detuvo desde la consulta, la salida se gasta para nada.

Como los lugares del mapa, un destino de Operaciones solo sale cuando el jugador ya sabe de él (`unlockedBy` en `actions.json`), para que la lista no dé pistas:

| Destino | Sale con |
|---|---|
| Estación de Olot, casa de Pol, casa del padre | desde el principio |
| Mas de la Rosalia | `sabe_rosalia` o `sabe_puerta_azul` |
| Masías de Bracons | `sabe_ruta_lotes`, `sabe_rosalia`, `sabe_puerta_azul` o `ficha_dani` |
| Can Pericot | `sabe_capsec` |
| Residencias | `sabe_residencia` |
| Mas del tío de Dani, detener a Dani | `dani_sospechoso` o `dani_droga` |
| Piso de Núria, piso del tío de Jan | `sabe_girona` |
| Cráter, vigilar el cráter | `ficha_ignasi` o `vigilancia_crater` |

## Fichas policiales (app Policía)

Contenido en `police/records.json`: una ficha por cada contacto de la agenda, más Alicia, Ignasi, Iker y tres vehículos. Los vehículos no salen hasta que se sabe de ellos: el Audi con la ficha de Dani o su mensaje del D3 noche (`sabe_audi`, «acaba en 7731»); la furgoneta con la ficha de Ignasi o Conxita (`sabe_furgoneta`, «HFT»); el Seat con la ficha de Ignasi, Enric (`sabe_seat`, «KDP») o la consulta a Laia sobre el coche gris. En la lista no se ve el papel de nadie (antecedentes, denunciante...) hasta leer su ficha; las leídas llevan el sello «CONFIDENCIAL» y se pueden abrir siempre. Se piden en Policía › Fichas policiales y llegan horas después por la burocracia (12 h el D1, 1 h el D7); una cada vez. Al leer una, la app pone `ficha_<id>` en la historia.

| Ficha | Lo que solo está aquí | Qué abre |
|---|---|---|
| 🔎 **Ignasi Coll** | Detenido hace once años: murió una mujer de 34 años en un «retiro del alba» de su primer grupo, en la ermita del cráter de Santa Margarida, tras nueve días de ayuno y un «agua preparada». Sobreseído. Un año después fundó Rosa d'Abril | Consulta a Laia (D4+): pone agentes en el cráter (`vigilancia_crater`) sin gastar salida. Pregunta a Iris (D5 00:30): «pasado mañana, al alba». Logro «El precedente» |
| 🔎 **Dani** | Multa de radar de su moto en la carretera de Joanetes, un sábado de hace un mes, bajando | Pregunta en su charla: confiesa que la siguió hasta un mas con una perra enorme (`sabe_ruta_lotes`) |
| 🔎 **Seat gris** | Lector de matrículas: en el aparcamiento del cráter de Santa Margarida a las seis de la mañana, D-3, D-2 y D-1 (los ensayos) | Nada: el jugador lo cruza con el folleto (IMG_0402) |
| 🔎 **Furgoneta blanca** | Control en Olot (D-5): la conduce Iris Ferrer con garrafas de agua sin etiquetar | Nada: da el apellido de Iris antes de que escriba. Se cruza con IMG_0400 (la furgoneta descargando garrafas delante de casa) |
| 🔎 **Montse** | Vendió el piso de la yaya por 120.000 €, que no están en sus cuentas | Nada: se cruza con la libreta (IMG_0401) |
| 🔎 **Berta** | Tesorera de la asociación, firma las cuentas de CRA Serveis | Nada |
| **Alicia** | La pulsera roja, regalo de Núria (respuesta de la prueba del D3); llamadas de un fijo de Joanetes, siempre en sábado | Nada |
| ⚠️🔎 **Laura** | Firmó con Berta un permiso municipal para una «jornada de meditación al alba» de Rosa d'Abril | Nada: avisa de la trampa de Laura antes de caer |
| 🔎 **Toni** | Reparte los lotes del Banc d'Aliments los sábados por las masías del valle | Nada |
| 🔎 **Conxita** | Ha denunciado «una furgoneta que llega de madrugada» a la calle del Pont | Nada |
| 🎭 **Biel**, **Marc** | Biel tiene llaves de Can Pericot; Marc fue detenido en una tractorada (con reseña en Multimedia) | Ruido: parecen sospechosos |
| 🔎 **Iker**, **Audi andorrano** | Iker no está imputado, pero llama a menudo a un número de Andorra; la sociedad del Audi sale en las diligencias de Barcelona | Unen la deuda de Dani con la red |

La tapadera se mantiene: ninguna ficha dice «secta». Rosa d'Abril sale como una asociación y lo grave está en el pasado de Ignasi; el jugador tiene que cruzarlo.

## Charlas de contactos: pistas menores y despistes

Todas las charlas de la agenda tienen varias preguntas por día (ruido, trasfondo y alguna respuesta a elegir). Lo que aportan al caso:

| Contacto | Qué suelta | Cuándo |
|---|---|---|
| 🔎 Lluïsa | El lote de «la masía de arriba» solo lo quiere si lo sube Alicia («la nena»). Montse ha donado las cosas de Alicia | D3+ |
| 🔎 Roser | La Rosalia siempre tuvo perras «con nombre de dulce» (Trufa) | D3+ |
| 🔎 Marc | Subiendo a Bracons hay un mas **con pozo** delante, con una perra que ataca al tractor (sin nombrar Can Pericot) | D3+ con `sabe_puerta_azul` o `teresa.charla.fotocopia` |
| 🔎 Quim | En la subida a Bracons hay una masía que se dio de baja de la luz cuando murió el marido. Una asociación preguntó por «un acto al alba» en la ermita de Santa Margarida | D3+ / D4+ |
| 🔎 Rafa | Alicia le preguntó si el puerto de Bracons se cierra de noche; «no pensaba subir en coche» | D2+ |
| 🔎 Anna | Martina: Alicia le prometió presentarle a «una perra gigante que se llama Trufa» | D3+ |
| 🔎 Hugo | Alicia le pidió una batería externa porque iba a estar «sin enchufe unos días» | D2+ |
| 🔎 Imma | El folleto: «Trobada de l'Alba · per a famílies», un volcán con una ermita y «la séptima alba» | D3+ |
| 🔎 Fàtima | Montse llegó a urgencias hace un mes mareada, sin comer: «haciendo un proceso» | D2+ |
| 🔎 Sílvia | El piso de la yaya (120.000 €) lo cobró «una asociación de unas siglas», no Montse; un señor de barba preguntó cuánto vale la casa | D3+ / D4+ |
| 🔎 Clàudia | La que repartía folletos del Casal: chica joven, pálida, rubia, de blanco (Iris) | D3+ |
| 🔎 Sonia | Alicia se mareaba en los entrenos tres semanas seguidas (los ayunos). Montse fue a darla de baja: ahora irá más «a lo de su asociación. Rosa d'Abril, o algo así» (`sabe_secta`) | D2+ |
| 🔎 Oriol | Si nadie le ha preguntado por la chica de Bracons, escribe él el D4 a las 22:10: «no serías tú no?» | D4 |
| 🔎 Enric | El D4 a las 11:15 escribe él: no se queda tranquilo con el coche gris; si Alicia pregunta, da la matrícula (`sabe_seat`) | D4 |
| ⚠️ Gemma | Berta y **Laura** pidieron juntas la plaza para «una meditación al alba»: Laura es de la Casa | D3+ |
| ⚠️ Enric | «A la Conxita, ni la hora»: todo llega a tu madre | D2+ |
| 🔎 Ramon | Su nuera trabaja en la residencia Sant Jaume: «allí no hay nadie del valle de la edad de tu yaya» | D3+ con `sabe_residencia` (y Ramon se lo cuenta a Montse) |
| 🎭 Pau | Falta el juego de llaves de la taquilla de Alicia: empuja a Can Pericot (`sabe_capsec`). Un señor mayor preguntó por «un refugio en el valle» | D3+ / D4+ |
| 🎭 Biel | Tras confesar: una furgoneta blanca parada en el cruce de Capsec a las tres | Con `descarta_capsec` |
| 🎭 Judit, Paula | El rumor de Barcelona con Dani, el de Girona y «una furgo blanca» | D2+ |
| 🎭 Jan | También le habló del piso a Mireia: dos tazas en el fregadero (Girona era de Mireia) | D2+ |
| Aina | Ella sí cree a Oriol (la vio en la carretera de Bracons) | D3+ |
