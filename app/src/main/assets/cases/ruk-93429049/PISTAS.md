# Pistas y pistas falsas (caso RUK-93429049)

Qué lleva al Mas de la Rosalia, qué despista y dónde se consigue cada cosa. No va en el juego.

Leyenda: 🔎 pista · 🎭 pista falsa · ⚠️ trampa (empeora el caso) · 💬 solo si el jugador pregunta (charla o contacto)

## Cómo se juega ahora

- **Charlas entre escenas.** Núria, Pol, Dani, Èric, Marta, Papá, Arnau y los grupos `amigas`, `esplai` y `veins` tienen huecos en los que el jugador escribe primero. Las preguntas cambian según el día y lo que ya sabe; caducan justo antes de la escena siguiente.
- **Contactos.** Los 36 contactos que antes abrían un chat vacío tienen ahora su propia charla (`story/<id>.ink`). Cada día, a medianoche, salen preguntas nuevas. Contestan cuando están en línea.
- **Consultas a Laia.** Después de cada parte nocturno, Laia ofrece **una** comprobación para el día siguiente. Las opciones dependen de lo averiguado: residencias, Can Pericot, la coartada de Dani, el prepago, el coche gris. Hay que elegir.
- **Riesgo.** Escribir a los adultos cotillas (Ramon, Roser, Conxita) sube `sospecha_familia`; a mamá por privado o en el grupo de vecinos (Montse está en él), más. Contarle a Laura dónde está Alicia se lo cuenta a Berta.

## Confianza: si te pillan, cambia la partida

Los contactos con pistas verdaderas tienen confianza propia (`fia_<id>` en `main.ink`): **1** confía (cuenta una pista extra), **0** normal, **-1** recela (no cuenta nada hasta que lo convences), **-2** bloqueado (no vuelve a hablar).

| Contacto | El desliz o la prueba | Respuesta buena (dónde está) | Si recela: mentir | Si recela: la verdad («Trabajo con los Mossos») | Premio si confía |
|---|---|---|---|---|---|
| Toni | Preguntarle la ruta de los lotes (Alicia la sabe): «cómo se llama la perra?» | «trufa» (IMG_0391). Trampa: «rocky» (el perro perdido de Gonpi) | Cuela sin rumores y da la ruta | Colabora (adulto sensato) | La Rosalia le dijo que «la nena» iba a quedarse con ella (`sabe_rosalia`) |
| Teresa | Preguntarle qué libros sacó: «cuál me debes?» | «Nada» (grupo de lectura). Trampa: «Bernarda Alba» (teatro) | Cuela sin rumores y da los libros | Colabora y es discreta | La fotocopia del mas de 1782 con pozo y puerta azul (`sabe_puerta_azul`) |
| Èric | Preguntarle qué dijo Pilar (Alicia estaba delante): «qué me debes por la masa madre?» | «un café» o «se murió sola» (historial D-3). Trampa: «una birra» (es por el turno) | Cuela sin rumores y da lo del pan | Colabora, pero es un bocazas: lo cuenta en el Forn (`delata`) | «Para la Rosalia, que si no le sube nadie, no baja» (`sabe_rosalia`) |
| Ona | Solo si ya hay rumores: «qué dorsal llevas?» | «el 7» (IMG_0394) | Cuela sin más rumores y da la lista | Se asusta, bloquea y lo cuenta en el vóley (`delata`) | Si le dices que el pienso es para una señora mayor: detrás de la lista, «sábado: lote + pienso → R.» |
| Oriol | Solo si ya hay rumores: «qué te pedí hace unas semanas?» | «los apuntes de dinámicas» (su historial) | Cuela sin más rumores y da lo que vio | Colabora asustado | La masía tras una curva con un banco de piedra, con pozo |
| Núria | Su sistema de siempre (`confianza_nuria`) | | Nueva: «nuri perdona, estoy fatal» la recupera una vez si no hay rumores; si los hay, empeora | | |

**El rumor (`delatado`).** Cada contacto que te bloquea (o que lo cuenta, como Èric y Ona con la verdad) suma uno.
- **1**: Laia avisa en la consulta de la noche, y Ona y Oriol empiezan a ponerte a prueba.
- **2**: las mentiras ya no cuelan y el rumor llega a Montse (`sospecha_familia` +1).

**El suelo: lo que no se pierde nunca.** La galería, Gonpi (el post de Oriol, el comentario de @excursions.bianya con la puerta azul y el pozo, el dibujo de la Laieta), Laia (cámaras, padrón), la llamada de Rosalia (D4) y el pan de Pilar no dependen de la confianza. Con eso se puede llegar a la subida a Bracons y a la puerta azul, pero sin nombres ni detalles. Con confianza, en cambio, se consiguen el nombre de la Rosalia, la puerta azul (Teresa) y la ruta exacta mucho antes, y el D6 es más fácil acertar.

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
| 🎭 **Dani.** Chico malote de poca monta: sabe que se ha ido, ronda su calle en moto, tiene un mas «sin cobertura» en la Vall d'en Bas y los rumores dicen que se fue con él a Barcelona. Menudea (costo, hierba, pastillas en el bar), tuvo un tonteo con Alicia (un beso que ella llamó «un error») y la controlaba. D3 noche: unos del bar le reclaman dinero (el «audi negro de Andorra»). D4: confiesa que vende (`dani_droga`) | Dani, Núria, Pol, Carla, Judit, amigas | Su post del concierto (D3) o la consulta a Laia (`dani_descartado`: multa + denuncia archivada por hachís, sin conexión con la desaparición). **Operación `dani_mas`** (D6): el mas del tío de Dani; solo hay hachís y una báscula, y gasta una de las dos salidas (`dani_registrado`: Dani lo comenta) |
| 🎭 **La red de trata de Barcelona.** Dani le ofreció «curro» en un club del Port Olímpic con su amigo Iker: 1.500 €, piso pagado, «no lo digas en casa», le pagaba el bus (Dani D-15, D-9). Iker le escribe (historial D-14, D2: «trae el dni», «te recojo en nord»; D3: la policía en el club). Laia (D3): Barcelona desarticula una red que capta chicas así y las recoge en Nord. La coartada de Dani lo pone en ese club la noche del billete | Dani, Iker, Laia, Carla («que te has ido a barcelona con el dani») | La cámara de la estación (no subió al bus). **Operación `dani_detencion`** (D6): Dani confiesa que la quería para él, no para la red. El jugador elige: creerle (lo sueltan y da una pista verdadera: la siguió hasta un mas con perra en Sant Salvador) o no (Barcelona se lo lleva y **el coche de la noche hace el traslado**: si queda la última salida, se pierde) |
| 🎭 **Girona y la clínica (embarazo).** Carla: la prima de Judit, en una farmacia de Girona, la vio comprar una prueba de embarazo con una chica con capucha. El banco: farmacia de Girona (D-9). Mireia: «si alguien pregunta por girona, no fuimos» (D1). Sergi: «lo de Girona no era asunto tuyo». Núria: fue a Girona hace nueve días. Laia (consulta): la cámara del centro de salud sexual. Jan: el piso vacío de su tío, llaves bajo el felpudo | Carla, Mireia, Sergi, Núria, Jan, banco, Laia | La embarazada era **Mireia** (de Sergi): Alicia la acompañó y le pagó la prueba. El detalle: el **Bizum de 13 € de Mireia** al día siguiente (banco D-8) y que Mireia sea «la de la capucha». **Mireia lo confiesa** solo si el jugador la trata con cuidado (`girona_descartado`); si la presiona, se cierra (`mireia_cerrada`). **Operación `piso_jan`**: una manta, dos tazas y el ticket de la farmacia, de hace nueve días. Nadie ha dormido allí desde entonces. Núria: «a girona no, es lo primero que mirarían» |
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
Una tercera operación, **el mas del tío de Dani** (`envio_dani`), también es pista falsa: aparece costo y una báscula, ni rastro de Alicia.
La cuarta, **detener a Dani** (`envio_detencion_dani`), gasta una salida pero puede devolver una pista verdadera si el jugador le cree. Si no le cree, la operación de la noche (mas, masías o cráter) se queda sin coche.

## Fichas policiales (app Policía)

Contenido en `police/records.json`: una ficha por cada contacto de la agenda, más Alicia, Ignasi, Iker y tres vehículos. Los vehículos no salen hasta que se sabe de ellos: el Audi con la ficha de Dani o su mensaje del D3 noche (`sabe_audi`, «acaba en 7731»); la furgoneta con la ficha de Ignasi o Conxita (`sabe_furgoneta`, «HFT»); el Seat con la ficha de Ignasi, Enric (`sabe_seat`, «KDP») o la consulta a Laia sobre el coche gris. En la lista no se ve el papel de nadie (antecedentes, denunciante...) hasta leer su ficha; las leídas llevan el sello «CONFIDENCIAL» y se pueden abrir siempre. Se piden en Policía › Fichas policiales y llegan horas después por la burocracia (12 h el D1, 1 h el D7); una cada vez. Al leer una, la app pone `ficha_<id>` en la historia.

| Ficha | Lo que solo está aquí | Qué abre |
|---|---|---|
| 🔎 **Ignasi Coll** | Detenido hace once años: murió una mujer de 34 años en un «retiro del alba» de su primer grupo, en la ermita del cráter de Santa Margarida, tras nueve días de ayuno y un «agua preparada». Sobreseído. Un año después fundó Rosa d'Abril | Consulta a Laia (D4+): pone agentes en el cráter (`vigilancia_crater`) sin gastar salida. Pregunta a Iris (D5 00:30): «pasado mañana, al alba». Logro «El precedente» |
| 🔎 **Dani** | Multa de radar de su moto en la carretera de Sant Salvador, un sábado de hace un mes, bajando | Pregunta en su charla: confiesa que la siguió hasta un mas con una perra enorme (`sabe_ruta_lotes`) |
| 🔎 **Seat gris** | Lector de matrículas: en el aparcamiento del cráter de Santa Margarida a las seis de la mañana, D-3, D-2 y D-1 (los ensayos) | Nada: el jugador lo cruza con el folleto (IMG_0402) |
| 🔎 **Furgoneta blanca** | Control en Olot (D-5): la conduce Iris Ferrer con garrafas de agua sin etiquetar | Nada: da el apellido de Iris antes de que escriba |
| 🔎 **Montse** | Vendió el piso de la yaya por 120.000 €, que no están en sus cuentas | Nada: se cruza con la libreta (IMG_0401) |
| 🔎 **Berta** | Tesorera de la asociación, firma las cuentas de CRA Serveis | Nada |
| **Alicia** | La pulsera roja, regalo de Núria (respuesta de la prueba del D3); llamadas de un fijo de Sant Salvador, siempre en sábado | Nada |
| ⚠️🔎 **Laura** | Firmó con Berta un permiso municipal para una «jornada de meditación al alba» de Rosa d'Abril | Nada: avisa de la trampa de Laura antes de caer |
| 🔎 **Toni** | Reparte los lotes del Banc d'Aliments los sábados por las masías del valle | Nada |
| 🔎 **Conxita** | Ha denunciado «una furgoneta que llega de madrugada» a la calle del Pont | Nada |
| 🎭 **Biel**, **Marc** | Biel tiene llaves de Can Pericot; Marc fue detenido en una tractorada (con reseña en Multimedia) | Ruido: parecen sospechosos |
| 🎭 **Iker**, **Audi andorrano** | Iker no está imputado y ninguna chica de la Garrotxa sale en la investigación de Barcelona; el Audi va por las deudas de Dani | Descartan la trata |

La tapadera se mantiene: ninguna ficha dice «secta». Rosa d'Abril sale como una asociación y lo grave está en el pasado de Ignasi; el jugador tiene que cruzarlo.

## Charlas de contactos: pistas menores y despistes

Todas las charlas de la agenda tienen varias preguntas por día (ruido, trasfondo y alguna respuesta a elegir). Lo que aportan al caso:

| Contacto | Qué suelta | Cuándo |
|---|---|---|
| 🔎 Pep (cartero 40 años) | La Rosalia vive «arriba del todo», sin luz; le dejaba el correo en el **pozo** porque la perra no le dejaba llegar al buzón; «siempre pedía pan» | Con `sabe_rosalia` |
| 🔎 Xavier | La Rosalia fundó la coral con la yaya; en el entierro se fue andando valle arriba con una perra enorme | D2-D3+ |
| 🔎 Lluïsa | El lote de «la masía de arriba» solo lo quiere si lo sube Alicia («la nena»). Montse ha donado las cosas de Alicia | D3+ |
| 🔎 Roser | La Rosalia siempre tuvo perras «con nombre de dulce» (Trufa) | D3+ |
| 🔎 Marc | Hay dos puertas azules: Can Pericot y otra **con pozo** subiendo a Bracons, con una perra que ataca al tractor | D3+ con `sabe_puerta_azul` |
| 🔎 Quim | En la subida a Bracons hay una masía que se dio de baja de la luz cuando murió el marido. Una asociación preguntó por «un acto al alba» en la ermita de Santa Margarida | D3+ / D4+ |
| 🔎 Rafa | Alicia le preguntó si el puerto de Bracons se cierra de noche; «no pensaba subir en coche» | D2+ |
| 🔎 Anna | Martina: Alicia le prometió presentarle a «una perra gigante que se llama Trufa» | D3+ |
| 🔎 Hugo | Alicia le pidió una batería externa porque iba a estar «sin enchufe unos días» | D2+ |
| 🔎 Imma | El folleto: «Trobada de l'Alba · per a famílies», un volcán con una ermita y «la séptima alba» | D3+ |
| 🔎 Fàtima | Montse llegó a urgencias hace un mes mareada, sin comer: «haciendo un proceso» | D2+ |
| 🔎 Sílvia | El piso de la yaya (120.000 €) lo cobró «una asociación de unas siglas», no Montse; un señor de barba preguntó cuánto vale la casa | D3+ / D4+ |
| 🔎 Clàudia | La que repartía folletos del Casal: chica joven, pálida, rubia, de blanco (Iris) | D3+ |
| 🔎 Sonia | Alicia se mareaba en los entrenos tres semanas seguidas (los ayunos) | D2+ |
| ⚠️ Gemma | Berta y **Laura** pidieron juntas la plaza para «una meditación al alba»: Laura es de la Casa | D3+ |
| ⚠️ Enric | «A la Conxita, ni la hora»: todo llega a tu madre | D2+ |
| 🔎 Ramon | Su nuera trabaja en la residencia Sant Jaume: allí no hay ninguna Rosalia | D3+ con `sabe_residencia` (y Ramon se lo cuenta a Montse) |
| 🎭 Pau | Falta el juego de llaves de la taquilla de Alicia: empuja a Can Pericot (`sabe_capsec`). Un señor mayor preguntó por «un refugio en el valle» | D3+ / D4+ |
| 🎭 Biel | Tras confesar: una furgoneta blanca parada en el cruce de Capsec a las tres | Con `descarta_capsec` |
| 🎭 Judit, Paula | El rumor de Barcelona con Dani, el de Girona y «una furgo blanca» | D2+ |
| 🎭 Jan | También le habló del piso a Mireia: dos tazas en el fregadero (Girona era de Mireia) | D2+ |
| Aina | Ella sí cree a Oriol (la vio en la carretera de Bracons) | D3+ |
