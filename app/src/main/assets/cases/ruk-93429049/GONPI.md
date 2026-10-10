# Gonpi: publicaciones por día (caso RUK-93429049)

Gonpi es el Instagram del móvil. La idea es que el jugador tenga que **cotillear**: perfiles, comentarios de desconocidos, quién da «me gusta» a qué. Varias pistas solo están aquí. Cuando esté todo, se pasará a `cases/ruk-93429049/gonpi/gonpi.json`.

Las publicaciones nuevas aparecen día a día. El `gonpi.json` actual es estático, así que hace falta un campo nuevo `"at": "D3 21:00"` en publicaciones y comentarios. Lo que sea anterior al D1 está desde el principio.

Leyenda: 🔎 pista · 🎭 pista falsa · ⚪ ruido

## Cuentas

| id | Usuario | Quién | Bio | Seguidores |
|---|---|---|---|---|
| `alicia` | @ali.serra | Alicia | «18 · Bianya · 🏐 · integración social» | 412 |
| `nuria` | @nuriii.g | Núria | «girona ✈️ bianya · 💛 @ali.serra» | 688 |
| `carla` | @carla.bcn_ | Carla | «🦋» | 903 |
| `mireia` | @mire.ia | Mireia | «a dieta de dramas» | 521 |
| `pol` | @pol.rider | Pol | «🚗 Garrotxa» | 377 |
| `dani` | @dani.moto | Dani | «22 · 🏍️ · Olot» | 1.204 |
| `arnau` | @arnau.gg | Arnau | «main support · 16» | 289 |
| `eric` | @eric.forner | Èric | «pan, masa madre y malas decisiones 🥖» | 344 |
| `berta` | @berta.luz | Berta | «Cada amanecer es una puerta ☀️ · @rosadabril.casal» | 1.017 |
| `mama` | @montse.vidal | Montse | «Gratitud. Familia. Luz.» | 156 |
| `guia` | @ignasi.coll.acompanya | Ignasi Coll, el líder. Se presenta como coach | «Acompaño procesos de transformación personal · Meditación · Constelaciones familiares · Encuentros en el Casal 🌹» | 3.420 |
| `casal` | @rosadabril.casal | La asociación (la secta, aunque nunca lo dice) | «Associació Comunitat Rosa d'Abril (CRA) · Grupos de duelo, mindfulness y acompañamiento 🌿 · Sant Joan les Fonts». «CRA» es el beneficiario del banco | 1.890 |
| `iris` | @iris.ambllum | Iris | «buscando mi luz» | 233 |
| `oriol` | @oriol.pedals | Oriol, de clase | «🚴 subo puertos de noche porque de día hace calor» | 640 |
| `voley` | @voleiolot | Club de vóley | «Vòlei Olot · Juvenil y Sénior» | 1.530 |
| `excursions` | @excursions.bianya | Desconocido: club excursionista del valle | «Rutas por la Vall de Bianya y la Alta Garrotxa 🥾» | 2.310 |
| `hostalnou` | @hostalnou.debianya | Cuenta del pueblo | «L'Hostalnou de Bianya · avisos y fiestas» | 1.120 |
| `marc` | @marc.hostalnou | Colega del pueblo (19) | «tractor > coche» | 410 |
| `laura` | @lauravila_ | Colega del pueblo (18), excompañera de instituto | «🌻» | 760 |
| `forn` | @forncanbatlle | La panadería | «Forn Can Batlle · Olot · desde 1964» | 2.050 |
| `joan` | @joan.pericot | Hermano de Carme, otra miembro | «Olot» | 98 |

## Antes del D1 (visible desde el principio)

**@ali.serra**, el perfil que más se va a mirar:
1. **D-1 · 20:47**: foto de un amanecer sobre el valle. *«a veces hay que irse para salvarse»* 🔎. Es su último post. Comentarios: @nuriii.g «??? 🥺» (D-1 23:59); @berta.luz «🌅» (D1 07:10, ver D1).
2. **D-12**: la foto del **cielo estrellado** desde la ventana (la misma que `IMG_0405`). *«el sitio de siempre ✨»* 🔎. Comentarios: @nuriii.g «nuestro sitio 🥹»; **@excursions.bianya «¡Esta vista es desde la subida a Bracons! ¿En qué mas estabais? 😍»**, sin respuesta 🔎. Desde ahí el jugador puede abrir el perfil del club excursionista.
3. **D-40**: cumpleaños, con la pulsera roja en primer plano. *«18 🎂 gracias a mi persona favorita por esto»*. Comentario de @nuriii.g: «es para siempre eh 💛» 🔎 (respuesta de la prueba de Núria).
4. **D-90**: playa de El Port de la Selva con Núria. *«viaje de fin de curso 🌊»* 🔎 (respuesta de la prueba de Núria).
5. **D-120**: vóley, foto de equipo. ⚪

**@excursions.bianya** (desconocido: el jugador solo llega aquí si cotillea los comentarios):
- **D-60**: una masía con la **puerta azul** y el dintel de 1782 🔎🔎. *«Ruta Sant Salvador de Bianya → Collada de Bracons. A media subida, un mas de 1782 con la porta blava y un pozo que todavía da agua. La señora de la casa (78 años, ¡como una rosa!) nos invitó a un vaso y nos presentó a su perra 🐕. Sin luz eléctrica ni cobertura: así se vive allí arriba.»* Ya no dice el nombre del mas ni el de la perra: «como una rosa» es el único guiño. Une `IMG_0391` (puerta azul, pozo, perra), `IMG_0405` (la vista de Bracons) y la llamada de Rosalia.
- **D-45**: 🎭 **Can Pericot**, en Capsec: otra masía con la **puerta azul** (descolorida), vacía, sin pozo ni perro. *«Capsec. Can Pericot, vacío desde hace años, con su porta blava ya descolorida. Ahora es el refugio del esplai: si pasáis, ¡dejadlo limpio! 🙏»*. Comentan .esplai («Las llaves, en el local del esplai 😉») y **.serra** («el mejor refugi del mundo 💙»). El señuelo de la segunda puerta azul: la yaya Mercè pintó las dos (ver `PISTAS.md`).
- **D-30**, **D-15**: rutas por la Alta Garrotxa, el Santuari del Mont, una cascada. ⚪

**@ignasi.coll.acompanya**: parece la cuenta de un facilitador de mindfulness. Hace tres días cuenta que «una joven» le preguntó en el círculo por qué cuesta soltar: es Alicia, la noche que el chat Familia llama «lo que has compartido» 🔎. Frases sobre soltar lo material («Lo que posees acaba poseyéndote») 🔎 y sobre alejarse de la familia («Hay vínculos que sanan y vínculos que pesan… aunque lleven tu apellido»), que Montse agradece: «Gracias por devolverme a mi familia» 🔎.

**@rosadabril.casal**: la tapadera es una asociación de mindfulness y retiros espirituales «para sanar el alma»: todo transmite calma (jardines, velas, cojines, testimonios como «Llegué rota. Hoy tengo una familia.», de «M., 52 años», que es Montse 🔎). El retiro «Sanar el alma» lo comenta Iris: «este retiro me cambió la vida». Fotos de meditaciones en grupo, siempre de espaldas y sin caras. En una se ve a Montse y a Berta, y debajo comenta @joan.pericot: su hermana Carme lleva dos años sin hablar con la familia; la cuenta le contesta «Te enviamos luz… Carme está en su proceso» 🔎 (aislamiento). Un retiro de silencio «sin móviles, sin relojes» 🔎 (control) y encuentros con «aportación voluntaria» 🔎 (dinero). Nadie usa la palabra secta: el jugador la deduce.

**@montse.vidal**: flores, el valle, frases. Hace unos meses dejó de publicar fotos con Alicia. ⚪/🔎

**@dani.moto**: motos y gimnasio. ⚪

## D1

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 07:10 | @berta.luz | Amanecer en el valle. Empieza su serie diaria *«☀️ Frase del alba: «Suelta lo que pesa. Lo que es tuyo, siempre vuelve.»» Cada mañana, una frase para acompañaros en el día 🌹*. Le da «me gusta» @ignasi.coll.acompanya | 🔎 Autoayuda inofensiva a primera vista; leídas en orden, las frases hablan de Alicia y de lo que preparan |
| 07:10 | @berta.luz | Comenta el último post de Alicia: «🌅» | 🔎 |
| 12:00 | @hostalnou.debianya | «Se busca perro perdido, mestizo negro, responde a Rocky» | ⚪ |
| 19:30 | @marc.hostalnou | Foto del tractor en la fiesta del pueblo. Comentario de @lauravila_: «tú y ese tractor 🙄» | ⚪ |
| 21:15 | @carla.bcn_ | Story-post: «falta alguien 💔» con una foto antigua de las cuatro | ⚪ |

## D2

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 07:05 | @berta.luz | *«Frase del alba: Dar es la forma más pura de recibir.»* | 🔎 El día de la transferencia |
| 10:30 | @montse.vidal | Unas manos abiertas al sol. *«Cerrar etapas también es cuidar de los tuyos 🌸»*. Coincide con la transferencia de 2.840 € («lo de Hacienda») | 🔎 Ambiguo |
| 11:00 | @rosadabril.casal | El tejado nuevo de la sala. *«Gracias a la generosidad de nuestras familias, la sala nueva ya tiene tejado 🌹»*. Montse comenta «🙏» | 🔎 Media hora después de la transferencia a «CRA Serveis» (la bio dice «Comunitat Rosa d'Abril (CRA)») |
| 13:00 | @eric.forner | Cesta vacía en el Forn. *«Caso abierto: seis barras de payés desaparecidas 🕵️🥖»*. Comentario de @forncanbatlle: «Èric, a trabajar 😤» | 🔎 Pista del pan, en tono de broma |
| 18:40 | @arnau.gg | Foto de un «fail» con la bici, hecha **anteanoche a las 22:50 en la rotonda del Hostalnou**. Al fondo, borrosa, una chica con mochila y una **bolsa con barras que asoman**. *«la rotonda maldita 💀»* | 🔎🔎 Es Alicia esperando a Pol con el pan. Encaja con el chat de Arnau del D2 |
| 22:00 | @pol.rider | Foto nocturna del salpicadero, con el reloj a las **23:41**; por la ventana se ve la estación de autobuses de Olot. *«taxi nocturno 🚕»*. La publicó anteanoche y la vuelve a subir hoy, o aparece archivada | 🎭 Confirma que la dejó en la estación |
| 23:10 | @dani.moto | Foto nocturna de una terraza de bar con una mochila negra sobre la mesa. *«noche de curro 🌙»*. Comenta @carla.bcn_: «tú y tu curro 🙄» | 🎭 Refuerza que "algo" hace de noche (en realidad menudea) |

## D3

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 07:00 | @berta.luz | *«Frase del alba: Quien de verdad te quiere, te espera sin hacer preguntas.»* | 🔎 |
| 11:00 | @dani.moto | Carrusel de un concierto en Barcelona. *«Barna anteanoche 🔥»*, con la entrada fechada | 🎭→⚪ Es la coartada de Dani: estaba en Barcelona |
| 14:20 | @oriol.pedals | Captura de la ruta en bici: *«Subida nocturna a Bracons 🌙 · 1:12 h»*, hecha anteanoche entre la 01:00 y las 02:30. Comentario de @oriol.pedals: «por cierto a mitad de subida vi a una chica sola andando por el arcén con una mochila. a esas horas! casi me paro» | 🔎🔎 Alicia subiendo andando hacia Bracons. Se cruza con `IMG_0409` (ruta a pie «…de Bianya») |
| 18:30 | @lauravila_ | Cojines y velas en un jardín. *«primera sesión de mindfulness 🌿 qué paz»*. Comenta @berta.luz: «Bienvenida a casa, hermana 🌹» | 🔎 Laura entra en el Casal: lo que le cuentes llega a Berta (chat `laura`) |
| 20:00 | @mire.ia | Fiesta de cumpleaños (la que planeaban en el grupo). *«los 18 con mis personas (falta una 💔)»* | ⚪ |
| 23:10 | @ignasi.coll.acompanya | Comenta el último post de Alicia: **«Pensamos mucho en ti, Alicia. Tu madre y tu hermana te esperan 🌹»**. A la misma hora le escribe por privado | 🔎 Un hombre de 61 años que comenta a una chica de 18: raro, no alarmante |

## D4

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 07:00 | @berta.luz | *«Frase del alba: No todo el que se aleja se pierde. Y quien se pierde, vuelve a casa.»* | 🔎 |
| 09:00 | @hostalnou.debianya | «Aviso: la carretera de Bracons tendrá obras en el tramo de Sant Salvador de 9 a 14 h» | ⚪ (pero sitúa el lugar) |
| 16:00 | @iris.ambllum | Amanecer, foto movida. *«no todos los amaneceres son iguales»*. Comentario de @berta.luz: «¿Qué quieres decir, hermana? 🙏». Iris no contesta | 🔎 Las dudas de Iris; ese mismo día empieza a escribir |
| 18:00 | @rosadabril.casal | *«Tarde de mindfulness abierta en el jardín. Ven tal como eres 🌿»*. @lauravila_ pregunta cuánto cuesta; la cuenta contesta «La primera vez, nada 🌹 Escríbenos por privado» | ⚪/🔎 Captación: el precio nunca se dice en público |
| 21:00 | @nuriii.g | Foto antigua con Alicia. *«vuelve pronto. sé que estás bien. lo sé»* (si `confianza_nuria` es alta, añade «✨🏡»: guiño a las estrellas) | 🔎 |

## D5

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 07:00 | @berta.luz | *«Frase del alba: Viste el alma de blanco y el mundo se vuelve ligero.»* | 🔎 La ropa blanca |
| 12:00 | @voleiolot | *«Derrota 1-3 sin nuestra colocadora titular. ¡Te esperamos, Ali! 🏐»* | ⚪ |
| 22:00 | @rosadabril.casal | **Anuncio:** foto del **cráter de Santa Margarida** con su ermita. *«Trobada de l'Alba. Subimos de madrugada al cráter de Santa Margarida a ver salir el sol y desayunamos juntas arriba. Venid en familia 🌹»* | 🔎🔎 El lugar. Parece una excursión; «familias completas» solo lo dice Ignasi por privado |
| 22:30 | @lauravila_ | Atardecer en el Hostalnou. Comentario de @marc.hostalnou: «oye esta tarde ha pasado una furgo blanca del Casal subiendo hacia Bracons, iban parando en cada mas 🤨» | 🔎 La secta busca por la zona correcta: presión |

## D6

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 07:00 | @berta.luz | *«Frase del alba: Lo mejor está a punto de amanecer.»* | 🔎 Es el día antes |
| 10:00 | @hostalnou.debianya | **«Corte de suministro eléctrico programado en L'Hostalnou de 18:00 a 22:00 por mantenimiento.»** | 🔎 Anticipa el apagón del D6: el jugador puede prepararse |
| 13:00 | @montse.vidal | Mesa puesta para tres. *«Mañana volvemos a estar todas.»* | 🔎 Inquietante |
| 18:30 | @excursions.bianya | Nuevo post: «Hoy no hemos podido llegar al Mas de la Rosalia: unos señores de una furgoneta blanca nos han dicho que el camino estaba cortado. ¿Alguien sabe algo?» | 🔎🔎 La secta está al lado: la confirmación definitiva |

## D7

| Hora | Quién | Publicación | Tipo |
|---|---|---|---|
| 05:00 | @ignasi.coll.acompanya | Negro total. *«Hoy amanece para todos.»* | 🔎 |
| 06:30 | Final bueno: @hostalnou.debianya | «Gracias a los Mossos por su trabajo esta madrugada en el valle 💙» | |
| 06:30 | Final malo: @berta.luz | Amanecer. *«Ya estamos todas.»* | |

## Canal «Rosa d'Abril 🌹» (chat de solo lectura, `story/casal.ink`)

La lista de difusión de la asociación en el móvil de Alicia. Leída de corrido parece una asociación más del valle: grupo de duelo, mindfulness, ayuno solidario, una obra en la sala. Lo que importa son detalles que solo cuadran cruzados: las aportaciones van a «CRA Serveis» (D-10, el beneficiario del banco), la sala «ya tiene tejado» media hora después del pago (D2), el despacho de la Casa tramita las voluntades anticipadas (D4, «el despacho de siempre» de Montse), la Trobada de l'Alba sin bolsos, móviles ni llaves (D5) y en ayunas, «con el agua de la Casa» (D6).

## Grupos del pueblo (camuflaje)

Para que Rosa d'Abril sea «una actividad más» de Alicia, su móvil tiene diez grupos del pueblo con su propia vida (chats `lectura`, `coral`, `aliments`, `ioga`, `esplai`, `festa`, `veins`, `autoescola`, `teatre`, `repas`). Casi todo es ruido ⚪, con alguna señal suelta 🔎:

| Grupo | Gente | Señal |
|---|---|---|
| Club de lectura 📚 | Teresa (biblioteca), Ramon, Gemma | 🔎 D5: Montse devuelve el libro y dice que Alicia deja el club porque tiene «otras lecturas» |
| Coral l'Hostalnou 🎶 | Xavier (director), Roser, Pep | 🔎 Montse dejó la coral hace un año; D2 Roser la ve en el mercado con una bolsa llena de velas |
| Voluntaris Banc d'Aliments 🥫 | Lluïsa, Toni, Fàtima | 🔎 D2: Montse dona cuatro cajas, «se están desprendiendo de lo material» (también en Gonpi) |
| Ioga al Poli 🧘‍♀️ | Clàudia (profe), Imma, Gemma | 🔎 Contraste: cuota clara, sin permanencia. D2 Imma pregunta por un folleto de Rosa d'Abril; Clàudia: «desconfiad de quien os prometa cambiaros la vida en un fin de semana» |
| Monis Esplai Bianya ⛺ | Pau, Clara, Biel | 🎭 D3: falta un juego de llaves del refugio de Can Pericot (Capsec). Las tiene Biel |
| Comissió Festa Major 🎉 | Quim (Ajuntament), Marc, Laura | ⚪ |
| Veïns carrer del Pont 🏘️ | Conxita, Enric y Montse | 🔎 Montse, muy serena: «estamos de recogimiento»; un coche gris con una rosa en el cristal (D4); el corte de luz del D6 |
| Autoescola · Teòrica 🚗 | Rafa (profe), Hugo | 🔎 D4: Alicia no va al examen; su madre dice que ha «cambiado de prioridades» |
| Teatre l'Hostalnou 🎭 | Dolors (directora), Queralt, Jan | ⚪ Ensayan «La casa de Bernarda Alba» (una madre que encierra a sus hijas) |
| Repàs Martina i Leo 📐 | Anna y Sílvia (madres) | 🔎 D2: persianas bajadas, no hay nadie en casa |

Cuentas nuevas en Gonpi: `lectura`, `coral`, `aliments`, `claudia`, `esplai`, `teatre`, `autoescola`, `pau`, `queralt`, `gemma`, `toni`. Publicaciones antiguas con Alicia en sus actividades (tertulia, gincana, ensayo, campamento) y algunas durante el caso: la donación de cajas (D2 12:00), el aprobado del teórico (D4), «buscamos una Adela» (D4) y el récord de lotes (D5).

## Perfiles de los contactos

Todos los contactos del móvil tienen cuenta en Gonpi, y el buscador **solo encuentra a los contactos** (por usuario o nombre, sin importar acentos ni mayúsculas). Las cuentas que no son contactos (Ignasi, el Casal, Iris, excursions.bianya, hostalnou, los grupos del pueblo…) solo se alcanzan tocando un nombre en el feed o en un comentario: hay que cotillear.

Cada cuenta tiene entre dos y cinco publicaciones escritas con la voz de su ficha en [story/perfiles/](story/perfiles/). Casi todas son ruido ⚪ que da realismo; estas llevan algo:

| Publicación | Tipo | Qué aporta |
|---|---|---|
| @teresa.llibres, D-35: novedades de la sección local | 🔎 | En el lomo de un libro se lee «Masies de la Vall de Bianya» (el que se llevó Alicia) |
| @roser.canta, D-70: la coral en 1979 | 🔎 | La yaya Mercè, la Rosalia y la Pepita juntas: las dos amigas existen |
| @imma.respira, D2 21:00: el folleto del mercado | ⚪/🔎 | Captación de Rosa d'Abril en el mercado |
| @clara.moni, D4 15:00: el dibujo de la Laieta | 🔎 | Puerta azul **con perro** y estrellas: no es Can Pericot |
| @biel.gg, D2 23:30: «noche épica 🕯️🌲» | 🎭 | Velas y saco de dormir en Can Pericot. Pau comenta «eso es el refugi???»: la luz de Capsec era Biel |
| @jordi.serra.bcn, D-45: la habitación de Ali | ⚪ | El padre que espera |
| @marta (fruites.canserra), D4 13:00 | ⚪ | Cierra la parada después de que Montse no le abra |
| @pilar.batlle, D7 05:40: «Encendiendo el horno.» | ⚪ | La mañana del final |

**Fechas.** Las publicaciones ya no llevan `"time": "hace 12 días"`: todas tienen `"at"`, también las del pasado (`"at": "D-12 19:30"`). El feed las ordena por fecha, la más nueva primero, y escribe «hace 2 horas», «hace 3 semanas»… según la hora del caso. Así se pueden añadir publicaciones en cualquier lugar del archivo.

## Cuentas que sigue Alicia

Para que el feed no sea solo gente del pueblo: grupos, influencers, divulgadores y locales de Olot. **Todas inventadas** (ningún artista ni marca real) y elegidas según los gustos de Alicia (ver «Gustos» en [story/perfiles/alicia.md](story/perfiles/alicia.md)). No son contactos, así que el buscador no las encuentra: salen en el feed y se abren tocando su nombre. Los anuncios llevan `"sponsored": true` y se ven con «Publicidad».

| Cuenta | Qué es | Detalle |
|---|---|---|
| @lesnitsdagost | Grupo indie-folk, su favorito | Single «Estels» («para los que habéis mirado el cel des d'una finestra que no era casa vostra»); Ali: «la yaya habría llorado con esta». Concierto en Girona con Núria. D3: fecha en el Fanals |
| @julia.vents | Cantautora | «Casa és on et deixen ser»; Ali: «esta canción me ha salvado la semana». D5: «No cal que ningú entengui per què te'n vas» |
| @nova.oficial | Estrella del pop | Anuncio del disco (D1). Ali no comenta: le da vergüenza |
| @festival.fanals | Festival de luz de Olot | Anuncio (D2) |
| @cel.fosc | Astrofotografía de la Garrotxa | 🔎 suave: los mejores cielos, entre ellos «las masías de la subida a Bracons»; Ali: «conozco uno mejor 🤫». D5: Vía Láctea esta noche |
| @nord.enlla | Viajes al norte, auroras | Su sueño: Ali comenta «algún día» en una cabaña sin luz |
| @tinta.lenta | Ilustradora | Masías, ventanas, chicas mirando el cielo; Ali le pide que le enseñe |
| @psicologia.amable | Psicóloga divulgadora | ⚪/🔎 temático: límites con la familia (Ali: «necesitaba leer esto»), ansiedad, D3 «señales de que un grupo te está aislando» |
| @fil.social | Asociación contra la soledad rural | Su vocación (Ali: «quiero hacer esto toda mi vida»); taller con su clase y Elena |
| @protectora.garrotxa | Protectora de animales | D2: Nit, perro grande y viejo; Ona: «ali este es para ti» |
| @marroig.7 | Colocadora de Vòlei Girona | Clínic en Olot: «me firmó la rodillera 😭😭» |
| @memes.de.poble | Memes rurales en catalán | Madres («ja en parlarem»), tractores, Girona-Nueva York |
| @llibreria.lacova | Librería de Olot | «Nada», «Mujercitas» |
| @granja.delfiral | Granja (chocolate) de Olot | Anuncio; Núria: «ali nuestro sitio» |
| @vintage.olot | Tienda de segunda mano | La chaqueta verde de Ali salió de aquí; anuncio (D2) |
| @pizzeria.lavolcanica | Pizzería | Anuncio de 2x1 para el vóley (D4) |
| @cinema.garrotxa | Cine de Olot | Anuncio: «La casa de los veranos» (una nieta y su abuela) |
