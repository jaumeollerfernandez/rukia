# Índice de la historia (generado)

Lo genera `StoryIndexTest` a partir de los `.ink`, `characters.json` y `police/records.json`. **No lo edites a mano**: si cambias la historia, pasa los tests y se regenera.
Para buscar algo, empieza aquí y ve a las líneas `archivo:línea` que indica.

## Variables

| Variable | Qué es | La pone | La lee |
|---|---|---|---|
| `dia` |  | *(app: el reloj del caso, antes de cada paso)* | `aina.ink:11` `aina.ink:12` `aina.ink:21` `aina.ink:25` `aina.ink:27` `aina.ink:39` `aina.ink:47` `aina.ink:52` `aina.ink:56` `aina.ink:66` `aina.ink:72` `aina.ink:79` `aina.ink:94` `aina.ink:98` … (+375) |
| `hora` |  | *(app: el reloj del caso, antes de cada paso)* | `aina.ink:10` `anna.ink:10` `arnau.ink:58` `biel.ink:11` `casal.ink:52` `dani.ink:190` `desconocido.ink:57` `dolors.ink:12` `enric.ink:10` `enric.ink:34` `eric.ink:128` `gemma.ink:11` `hugo.ink:9` `hugo.ink:22` … (+28) |
| `ultimo_intento` | último día del caso en que el jugador mandó una búsqueda desde la app de Policía (lo pone la app) | *(app: Policía, al mandar una búsqueda)* | `laia.ink:65` |
| `sospecha_familia` |  | `berta.ink:28` `berta.ink:33` `berta.ink:38` `conxita.ink:68` `familia.ink:101` `main.ink:219` `mama.ink:20` `mama.ink:28` `mama.ink:36` `mama.ink:47` `ramon.ink:72` `roser.ink:88` `veins.ink:66` | `berta.ink:14` `berta.ink:46` `berta.ink:53` `berta.ink:59` `berta.ink:69` `familia.ink:75` `familia.ink:102` `veins.ink:67` |
| `confianza_nuria` |  | `nuria.ink:31` `nuria.ink:36` `nuria.ink:42` `nuria.ink:48` `nuria.ink:57` `nuria.ink:62` `nuria.ink:101` `nuria.ink:104` `nuria.ink:152` `nuria.ink:186` `nuria.ink:196` `nuria.ink:267` `nuria.ink:273` `nuria.ink:325` `nuria.ink:338` `nuria.ink:346` `nuria.ink:350` `nuria.ink:372` | `nuria.ink:90` `nuria.ink:120` `nuria.ink:280` `nuria.ink:330` `nuria.ink:344` `nuria.ink:384` |
| `nuria_sabe` | el jugador le ha confesado a Núria que no es Alicia | `nuria.ink:65` `nuria.ink:266` | `amigas.ink:161` `nuria.ink:82` `nuria.ink:116` `nuria.ink:154` `nuria.ink:223` `nuria.ink:248` `nuria.ink:254` `nuria.ink:281` `nuria.ink:297` `nuria.ink:312` `nuria.ink:324` `nuria.ink:329` `nuria.ink:344` `nuria.ink:355` … (+7) |
| `confianza_alicia` |  | `desconocido.ink:12` `desconocido.ink:23` `desconocido.ink:32` `desconocido.ink:37` `desconocido.ink:59` | `desconocido.ink:89` `desconocido.ink:99` `desconocido.ink:135` |
| `pol_calla` | Pol ha prometido no contar lo de la estación | `pol.ink:30` `pol.ink:37` `pol.ink:65` | `familia.ink:167` `pol.ink:85` `pol.ink:97` `pol.ink:100` `pol.ink:103` `pol.ink:114` `pol.ink:125` |
| `arnau_calla` | Arnau ha archivado el vídeo de la rotonda y no se lo dirá a nadie (desaparece de Gonpi) | `arnau.ink:43` | `arnau.ink:53` `eric.ink:79` `familia.ink:151` `familia.ink:163` `pilar.ink:103` |
| `eric_archiva` | Èric ha archivado su post de las barras (desaparece de Gonpi) | `eric.ink:130` | — |
| `foto_img_0391` | la yaya, la puerta azul, el pozo y la Trufa → Roser | — | `roser.ink:52` |
| `foto_img_0397` | el meme del gato meditando → Carla | — | `carla.ink:88` |
| `foto_img_0401` | la libreta de mamá: «Piso yaya: 120.000 → C.R.A.» → Papá | — | `jordi.ink:128` |
| `foto_img_0402` | el folleto de la Trobada de l'Alba → Marta | — | `marta.ink:114` |
| `foto_img_0409` | la ruta a pie desde la estación hasta «…de Bianya» → Pol | — | `pol.ink:166` |
| `foto_img_0411` | el reembolso del billete de bus → Laia (D2) | — | `laia.ink:104` |
| `foto_img_0413` | la mochila con velas, cerillas y un cargador sin móvil → Toni | — | `toni.ink:91` |
| `aciertos_nuria` |  | `nuria.ink:130` `nuria.ink:137` `nuria.ink:144` | `nuria.ink:149` `nuria.ink:188` |
| `nuria_denuncia` | Núria suspendió la prueba y va a ir a los Mossos | `nuria.ink:187` | `amigas.ink:73` `amigas.ink:92` `amigas.ink:135` `amigas.ink:142` `amigas.ink:151` `amigas.ink:158` `carla.ink:18` `carla.ink:26` `carla.ink:46` `carla.ink:62` `carla.ink:75` `carla.ink:88` `carla.ink:99` `carla.ink:104` … (+12) |
| `secta_sabe_rosalia` | la familia ha descubierto que Alicia está con Rosalia | `familia.ink:132` `familia.ink:168` `laura.ink:34` `mama.ink:29` | `central.ink:145` `desconocido.ink:145` `familia.ink:146` `familia.ink:180` `familia.ink:199` `main.ink:207` `main.ink:212` |
| `sabe_secta` | conoce el nombre «Rosa d'Abril» (el grupo de duelo de la madre) | `claudia.ink:15` `jordi.ink:25` `jordi.ink:123` `laia.ink:273` `laia.ink:433` `laia.ink:531` `laia.ink:541` `laia.ink:550` `marta.ink:20` `pol.ink:38` `sonia.ink:26` | `laia.ink:523` |
| `sabe_cra` | Laia sabe que «CRA Serveis», el beneficiario del pago «a Hacienda», es Rosa d'Abril | `laia.ink:137` `laia.ink:524` `laia.ink:542` | `laia.ink:523` `laia.ink:530` |
| `sabe_pan` | faltan barras en la panadería | `eric.ink:24` `eric.ink:30` `eric.ink:168` `eric.ink:182` `pol.ink:59` | `laia.ink:189` |
| `descarta_bus` |  | `laia.ink:105` `laia.ink:124` | `laia.ink:154` |
| `sabe_estrellas` |  | `nuria.ink:153` | `laia.ink:185` `laura.ink:32` `nuria.ink:212` `nuria.ink:236` |
| `sabe_rosalia` |  | `eric.ink:194` `laia.ink:193` `laia.ink:244` `nuria.ink:289` `toni.ink:77` | `laia.ink:226` `laura.ink:32` `mama.ink:27` `pep.ink:76` |
| `sabe_puerta_azul` | sabe que la casa tiene la puerta azul | `desconocido.ink:100` `nuria.ink:242` `nuria.ink:290` `teresa.ink:93` | `laia.ink:291` `laura.ink:32` `marc.ink:65` |
| `sabe_pienso` | Ona: en la lista de Alicia había pienso para perro | `ona.ink:42` `ona.ink:71` | — |
| `sabe_ruta_lotes` | Toni: Alicia subía lotes a una señora con perra de Sant Salvador | `dani.ink:259` `dani.ink:337` `ona.ink:84` `toni.ink:42` `toni.ink:110` | — |
| `sabe_capsec` | conoce Can Pericot, el mas abandonado de Capsec (también con puerta azul) | `eric.ink:137` `esplai.ink:42` `marc.ink:25` `marta.ink:89` `pau.ink:44` `ramon.ink:17` `roser.ink:17` | `biel.ink:20` `laia.ink:489` `pau.ink:25` `quim.ink:18` `toni.ink:52` |
| `descarta_capsec` | Biel ha confesado que las llaves y la luz de Can Pericot eran suyas | `biel.ink:21` | `biel.ink:20` `biel.ink:48` `biel.ink:62` `biel.ink:68` `biel.ink:76` `biel.ink:110` `esplai.ink:67` `esplai.ink:73` `esplai.ink:79` `pau.ink:25` |
| `sabe_residencia` | ha oído que la Rosalia «está en una residencia» | `marta.ink:90` `roser.ink:18` `teresa.ink:39` | `laia.ink:484` `ramon.ink:36` |
| `residencia_falsa` | Laia ha comprobado que la Rosalia nunca ha estado en ninguna residencia | `laia.ink:485` | — |
| `dani_sospechoso` |  | `amigas.ink:152` `carla.ink:27` `dani.ink:60` `dani.ink:140` `dani.ink:283` `dani.ink:292` `judit.ink:22` `laia.ink:202` `nuria.ink:365` `paula.ink:49` `pol.ink:182` | `dani.ink:276` `laia.ink:493` |
| `dani_descartado` |  | `laia.ink:494` | — |
| `dani_droga` | sabe que Dani menudea (lo confiesa él en el D4 o se lo saca el jugador) | `dani.ink:158` | `dani.ink:185` `dani.ink:288` `dani.ink:297` `dani.ink:302` `dani.ink:306` `laia.ink:497` `laia.ink:504` |
| `dani_registrado` | el jugador mandó una patrulla al mas del tío de Dani y los Mossos le requisaron el costo | `central.ink:156` | `dani.ink:210` `dani.ink:244` |
| `dani_avisado` | Dani ya ha contado lo del registro (para no repetirlo) | `dani.ink:215` | `dani.ink:244` |
| `dani_detenido` | el jugador mandó detener e interrogar a Dani (envio_detencion_dani) | `central.ink:98` `laia.ink:505` | `central.ink:97` `dani.ink:181` `dani.ink:208` `dani.ink:237` `dani.ink:244` `iker.ink:65` `iker.ink:89` `laia.ink:413` `laia.ink:463` `laia.ink:504` |
| `dani_creido` | tras la confesión, el jugador le cree: lo sueltan y cuenta lo que vio | `central.ink:114` | `dani.ink:208` `dani.ink:209` `dani.ink:237` `dani.ink:243` `iker.ink:65` `laia.ink:413` `laia.ink:463` |
| `dani_no_creido` | no le cree: Barcelona se lo lleva y el coche de la noche hace el traslado | `central.ink:117` | `central.ink:50` `central.ink:58` `central.ink:71` `dani.ink:208` `iker.ink:85` |
| `sabe_dani_busca` | alguien le ha dicho a Alicia que Dani va preguntando por ella | `aliments.ink:57` `dani.ink:192` `dani.ink:218` `eric.ink:104` `iker.ink:53` `pilar.ink:123` | `desconocido.ink:111` |
| `dani_frenado` | detenido (Laia o la operación del D6) o Alicia avisada: la entrega pasa de D6 19:00 a D7 04:00 | `central.ink:100` `desconocido.ink:113` `laia.ink:506` | `laia.ink:321` `laia.ink:334` |
| `alicia_avisada` | el jugador le ha dicho a Alicia, por el prepago, que Dani la busca | `desconocido.ink:112` | `desconocido.ink:153` |
| `alicia_entregada` | Dani y la red se la han llevado: finales 5 y 6, pase lo que pase después | `laia.ink:448` `laia.ink:459` | `central.ink:59` `dani.ink:237` `desconocido.ink:138` `iker.ink:83` `laia.ink:334` `main.ink:203` `main.ink:207` `main.ink:212` |
| `llamada_rosalia_contestada` | lo pone la app cuando el jugador atiende o rechaza una llamada de ese chat | *(app: el teléfono, al atender o rechazar una llamada de ese chat)* | `rosalia.ink:8` |
| `llamada_pilar_contestada` | ídem, la llamada de Pilar del D3 | *(app: el teléfono, al atender o rechazar una llamada de ese chat)* | `pilar.ink:55` |
| `sabe_prepago` | Mireia: Alicia compró un móvil barato en un estanco de Olot | `mireia.ink:45` | `laia.ink:520` |
| `fia_toni` |  | `toni.ink:23` `toni.ink:34` `toni.ink:39` `toni.ink:47` `toni.ink:116` | `toni.ink:14` `toni.ink:18` `toni.ink:32` `toni.ink:46` `toni.ink:52` `toni.ink:59` `toni.ink:76` `toni.ink:91` `toni.ink:93` `toni.ink:99` |
| `fia_teresa` |  | `teresa.ink:27` `teresa.ink:60` `teresa.ink:71` `teresa.ink:76` `teresa.ink:83` `teresa.ink:141` | `teresa.ink:26` `teresa.ink:38` `teresa.ink:55` `teresa.ink:69` `teresa.ink:82` `teresa.ink:92` `teresa.ink:106` `teresa.ink:118` `teresa.ink:162` |
| `fia_eric` |  | `eric.ink:156` `eric.ink:159` `eric.ink:162` `eric.ink:175` `eric.ink:180` `eric.ink:187` | `eric.ink:136` `eric.ink:151` `eric.ink:167` `eric.ink:173` `eric.ink:186` `eric.ink:193` `eric.ink:198` `eric.ink:202` |
| `fia_ona` |  | `ona.ink:49` `ona.ink:62` `ona.ink:67` `ona.ink:76` `ona.ink:124` `ona.ink:129` | `ona.ink:23` `ona.ink:35` `ona.ink:37` `ona.ink:60` `ona.ink:75` `ona.ink:83` `ona.ink:96` `ona.ink:166` |
| `fia_oriol` |  | `oriol.ink:68` `oriol.ink:73` `oriol.ink:79` `oriol.ink:106` `oriol.ink:113` `oriol.ink:118` | `oriol.ink:24` `oriol.ink:26` `oriol.ink:36` `oriol.ink:50` `oriol.ink:52` `oriol.ink:56` `oriol.ink:61` `oriol.ink:66` `oriol.ink:78` `oriol.ink:83` `oriol.ink:144` |
| `delatado` |  | `main.ink:217` | `eric.ink:174` `laia.ink:474` `main.ink:218` `nuria.ink:345` `ona.ink:36` `ona.ink:61` `oriol.ink:25` `oriol.ink:51` `oriol.ink:67` `teresa.ink:70` `toni.ink:33` |
| `aviso_delatado` |  | `laia.ink:475` | `laia.ink:474` |
| `sabe_girona` | ha oído lo de Girona (la prueba de embarazo, la clínica, «no fuimos») | `carla.ink:47` `jan.ink:22` `judit.ink:44` `mireia.ink:27` `sergi.ink:37` | `laia.ink:515` |
| `girona_descartado` | Mireia ha confesado que la prueba y la clínica eran suyas | `mireia.ink:92` | `laia.ink:515` `mireia.ink:68` `mireia.ink:128` |
| `mireia_cerrada` | el jugador la presionó y Mireia ya no cuenta nada | `mireia.ink:110` | `mireia.ink:68` `mireia.ink:128` |
| `laura_sabe` | le has dicho a Laura por dónde está Alicia | `laura.ink:29` `laura.ink:33` | `laura.ink:59` `laura.ink:80` `laura.ink:84` `laura.ink:94` `laura.ink:98` `laura.ink:101` `laura.ink:108` |
| `secta_a_capsec` | le has mandado a Laura (y a la secta) a Capsec | `laura.ink:30` | `laura.ink:51` `laura.ink:80` `marc.ink:38` |
| `patrulla_en_mas` | Laia ha mandado agentes al mas de la puerta azul | `central.ink:60` `laia.ink:425` | `desconocido.ink:139` `familia.ink:201` `laia.ink:323` `main.ink:203` |
| `vigilancia_crater` | habrá agentes en el cráter de Santa Margarida al amanecer | `central.ink:72` `laia.ink:441` `laia.ink:555` | `laia.ink:342` `laia.ink:549` `main.ink:223` |
| `alicia_a_salvo` | Alicia ha bajado con la patrulla | `desconocido.ink:164` `laia.ink:426` | `main.ink:203` |
| `llamada_desconocido_contestada` | lo pone la app cuando el jugador atiende o rechaza la llamada del prepago | *(app: el teléfono, al atender o rechazar una llamada de ese chat)* | `desconocido.ink:72` |
| `alicia_llamo` | con confianza 3, Alicia dio la ubicación exacta por teléfono (D5 noche): Laia la saca esa noche | `desconocido.ink:73` | `desconocido.ink:138` `laia.ink:306` `laia.ink:308` `laia.ink:323` `laia.ink:370` |
| `iris_ayuda` | Iris va a declarar a los Mossos | `iris.ink:49` | `iris.ink:64` `laia.ink:347` `main.ink:223` |
| `caso_resuelto` | lo activa la app de Policía si el jugador acierta la pregunta antes del límite | *(app: Policía, al acertar la zona en el mapa)* | `main.ink:203` |
| `ficha_dani` | la multa de su moto en la carretera de Sant Salvador: abre una pregunta en su charla | *(app: Policía, al leer la ficha «dani»)* | `dani.ink:333` |
| `sabe_ignasi` | el jugador ya sabe cómo se llama el que «acompaña»: solo investigando la asociación (Laia). Desbloquea su ficha | `laia.ink:163` `laia.ink:437` `laia.ink:525` `laia.ink:532` `laia.ink:544` | *(desbloquea la ficha «ignasi»)* |
| `ficha_ignasi` | el retiro del alba de hace once años en Santa Margarida: abre una consulta a Laia y una pregunta a Iris | *(app: Policía, al leer la ficha «ignasi»)* | `iris.ink:22` `laia.ink:549` |
| `sabe_audi` | Dani (D3 noche): la matrícula andorrana del Audi | `dani.ink:137` | `laia.ink:504` *(desbloquea la ficha «audi»)* |
| `sabe_furgoneta` | Conxita: la matrícula de la furgoneta que llegó de madrugada | `conxita.ink:20` | *(desbloquea la ficha «furgoneta»)* |
| `sabe_seat` | Enric, o la consulta a Laia sobre el coche gris | `enric.ink:60` `laia.ink:543` | *(desbloquea la ficha «seat»)* |
| `final_caso` | el final al que se ha llegado (1–6, ver FINALES.md); lo lee el juego para el informe de cierre | `laia.ink:361` `laia.ink:367` `laia.ink:382` `laia.ink:394` `laia.ink:404` `laia.ink:408` | — |

## Cronología

Cada línea con hora propia (`#at`). Las respuestas con `#delay` llegan después de la suya y no salen aquí.

### D-40 (historial del móvil)

- 08:02 · **marta** · Feliz cumple, mi niña!!! 18 ya 🎂🎉 ¡Qué mayor! Un besazo de la tía · `marta.ink:8`
- 11:15 · **me** · graciaas tía 💛💛 · `marta.ink:9`

### D-34 (historial del móvil)

- 23:50 · **dani** · ey fue top lo de anoche 😏 · `dani.ink:16`
- 23:58 · **me** · dani fue un error. estaba nerviosa, no pasó nada · `dani.ink:17`

### D-33 (historial del móvil)

- 00:02 · **dani** · ya claro. un error. por eso me mirabas así · `dani.ink:18`
- 00:03 · **dani** · no me tomes el pelo ali · `dani.ink:19`
- 08:10 · **me** · no te tomo el pelo. somos amigos y punto · `dani.ink:20`
- 08:11 · **dani** · amigos 😂 vale guay · `dani.ink:21`

### D-30 (historial del móvil)

- 20:14 · **mireia** · para qué querías el móvil ese del estanco tía? 😂 · `mireia.ink:13`
- 20:40 · **me** · para emergencias. no se lo digas a nadie porfa · `mireia.ink:14`
- 20:41 · **mireia** · 🤐 · `mireia.ink:15`

### D-25 (historial del móvil)

- 10:00 · **teresa** · Recordatorio: este mes leemos «Nada», de Carmen Laforet. Nos vemos el último martes 📚 · `lectura.ink:7`
- 21:40 · **me** · yo ya voy por la mitad, qué agobio la casa de la calle aribau · `lectura.ink:8`
- 22:05 · **teresa** · Esa casa es un personaje más, Alicia. Bien visto. · `lectura.ink:9`

### D-24 (historial del móvil)

- 09:15 · **gemma** · Yo aún no lo he empezado. Como siempre 🙈 · `lectura.ink:10`
- 11:30 · **ramon** · A mi edad lo leí tres veces y aún no entiendo a la tía Angustias · `lectura.ink:11`

### D-20 (historial del móvil)

- 09:00 · **casal** · 🌹 Bienvenida al canal de Rosa d'Abril, Alicia. Aquí compartimos horarios, lecturas y momentos de calma. · `casal.ink:10`
- 09:00 · **casal** · Respira. Ya estás en casa. · `casal.ink:11`
- 09:00 · **claudia** · Recordad: este trimestre la cuota son 45 €, por Bizum o en efectivo. Si un mes no podéis venir, no pasa nada  · `ioga.ink:8`
- 09:01 · **casal** · Mindfulness al amanecer: cada día a las 5:30 en el jardín del Casal. Grupo de duelo: jueves a las 20:00. Retir · `casal.ink:12`
- 13:10 · **me** · yo pago el jueves · `ioga.ink:9`
- 13:15 · **gemma** · Ali vino a la primera clase y se durmió en la relajación 😂 · `ioga.ink:10`
- 13:16 · **me** · gemma te odio · `ioga.ink:11`
- 13:30 · **claudia** · Dormirse en savasana es de lo más sano que hay. Aprobada 😌 · `ioga.ink:12`
- 21:10 · **oriol** · ali me pasas los apuntes de dinámicas · `oriol.ink:12`
- 21:15 · **dani** · con quién estabas ayer en la plaza? te vi con un chico · `dani.ink:22`
- 21:30 · **me** · con pol. es mi amigo, dani · `dani.ink:23`
- 21:31 · **dani** · pol el de la furgo? no me gusta ese tío · `dani.ink:24`
- 21:31 · **dani** · deja de seguirle en gonpi, hazme caso · `dani.ink:25`
- 21:45 · **me** · te los mando luego · `oriol.ink:13`
- 21:45 · **oriol** · eres un sol · `oriol.ink:14`
- 21:50 · **me** · no eres quién para decirme eso · `dani.ink:26`
- 21:51 · **dani** · perdona. me preocupo por ti · `dani.ink:27`

### D-18 (historial del móvil)

- 21:00 · **dolors** · Reparto de «La casa de Bernarda Alba» para la Fiesta Mayor: Queralt es Bernarda, Alicia es Adela y Jan hace de · `teatre.ink:7`
- 21:10 · **jan** · jajaja el mejor papel del mundo, no salir · `teatre.ink:8`
- 21:30 · **me** · adela 🖤 me encanta · `teatre.ink:9`

### D-15 (historial del móvil)

- 01:20 · **dani** · oye una cosa seria · `dani.ink:28`
- 01:20 · **dani** · un colega mío de barna, el iker, lleva las relaciones públicas de un club en el port olímpic · `dani.ink:29`
- 01:21 · **dani** · busca chicas para la puerta y los reservados. 1.500 al mes y piso pagado · `dani.ink:30`
- 01:21 · **dani** · tú vales para eso. y te quitas de encima a tu familia 😏 · `dani.ink:31`
- 01:22 · **dani** · le he pasado tu número · `dani.ink:32`
- 09:30 · **me** · por qué le pasas mi número sin preguntarme · `dani.ink:33`
- 09:31 · **dani** · para ayudarte joder · `dani.ink:34`
- 18:00 · **xavier** · Ensayo el jueves a las 20:30 en la parroquia. Repasad el «Cant dels ocells» 🎶 · `coral.ink:7`
- 19:10 · **me** · yo no llego, tengo entreno. el siguiente sí · `coral.ink:8`
- 19:12 · **pep** · Contraltos sin Alicia... que Dios nos pille confesados 😅 · `coral.ink:9`

### D-14 (historial del móvil)

- 08:00 · **casal** · Lectura del día: «El alma no se rompe. Se olvida de sí misma. Y volver a ella es un camino que no se hace sola · `casal.ink:13`
- 12:00 · **quim** · Reunión de comisión el miércoles a las 21 en el local. Hay que cerrar orquesta y castillo hinchable 🎉 · `festa.ink:7`
- 15:00 · **me** · yo me pido el puesto de las crepes otra vez · `festa.ink:8`
- 15:02 · **marc** · el tractor desfila sí o sí eh · `festa.ink:9`
- 15:30 · **quim** · Marc, el tractor no es una carroza. · `festa.ink:10`
- 15:31 · **marc** · lo será · `festa.ink:11`
- 19:20 · **iker** · hola alicia! soy iker, el amigo de dani 😊 · `iker.ink:11`
- 19:21 · **iker** · me ha dicho que te interesa lo del club. cuando quieras te lo explico · `iker.ink:12`
- 22:40 · **me** · quién te ha dado mi número · `iker.ink:13`
- 22:41 · **iker** · dani jaja. tranqui, es todo legal. 1.500 al mes y la habitación del piso pagada · `iker.ink:14`
- 22:41 · **iker** · de momento mejor no lo comentes en casa. a los padres esto de la noche les asusta 🙈 · `iker.ink:15`

### D-13 (historial del móvil)

- 09:15 · **me** · me lo pienso · `iker.ink:16`

### D-12 (historial del móvil)

- 02:10 · **dani** · estás despierta? · `dani.ink:35`
- 02:11 · **dani** · tengo algo que te va a gustar 🌿 · `dani.ink:36`
- 09:00 · **me** · ni de coña dani. ya te dije que no quiero saber nada de eso · `dani.ink:37`
- 09:05 · **dani** · es solo un poco de hierba, no te hagas la santa · `dani.ink:38`
- 09:06 · **me** · es que no. y deja de escribirme de madrugada · `dani.ink:39`
- 09:07 · **dani** · vale vale. no te enfades · `dani.ink:40`
- 19:00 · **casal** · Para el retiro de silencio de este fin de semana: los móviles y los relojes se quedan en la cesta de la entrad · `casal.ink:14`
- 20:00 · **pau** · Monis, el sábado gincana en el parque. Cada uno trae una prueba ⛺ · `esplai.ink:7`
- 20:30 · **me** · yo hago la de los globos de agua · `esplai.ink:8`
- 20:31 · **biel** · Ali eres la mejor y la peor a la vez · `esplai.ink:9`

### D-11 (historial del móvil)

- 12:00 · **anna** · Ali, ¿puedes este jueves a las 18? Martina tiene control de fracciones 📐 · `repas.ink:7`
- 14:20 · **me** · sí, perfecto · `repas.ink:8`
- 14:30 · **silvia** · Leo también, ¿os juntáis? Así os lo pagamos a medias 😊 · `repas.ink:9`
- 15:00 · **me** · vale, los dos juntos · `repas.ink:10`
- 22:00 · **queralt** · Ali, lo de «¡aquí se acabaron las voces de presidio!» dilo con rabia en el ensayo, eh · `teatre.ink:10`
- 22:15 · **me** · la rabia la tengo, tranqui · `teatre.ink:11`

### D-10 (historial del móvil)

- 09:00 · **banco** · BancRural: Se ha abonado tu nómina de 312,40 € en tu cuenta ****4417. · `banco.ink:4`
- 10:00 · **casal** · Gracias a todas las familias que habéis colaborado este mes 🙏 El ayuno solidario ha llenado treinta cestas pa · `casal.ink:15`
- 10:01 · **casal** · Las aportaciones para la obra de la sala nueva, como siempre, a la cuenta de CRA Serveis. · `casal.ink:16`
- 17:00 · **rafa** · Simulacro de examen el viernes. El que saque menos de 27 no se presenta 😈 · `autoescola.ink:7`
- 17:20 · **me** · yo saqué 28 la última vez · `autoescola.ink:8`
- 17:21 · **hugo** · yo 19 jajajaja · `autoescola.ink:9`
- 17:25 · **rafa** · Hugo, tú preséntate en 2030. · `autoescola.ink:10`
- 21:00 · **roser** · Ali, dile a tu madre que la echamos de menos en las sopranos. ¡Un año ya! · `coral.ink:10`
- 22:10 · **mireia** · ali mañana a las 8 en la parada del bus? 🥺 · `mireia.ink:16`
- 22:15 · **me** · sí. y tranqui, todo va a ir bien 💛 · `mireia.ink:17`
- 22:15 · **mireia** · ni a nuri eh · `mireia.ink:18`
- 22:16 · **me** · 🤐 · `mireia.ink:19`
- 23:40 · **me** · se lo digo · `coral.ink:11`
- 23:41 · **roser** · Pues dicho queda 🙂 · `coral.ink:12`

### D-9 (historial del móvil)

- 00:40 · **dani** · iker dice que si vas, mejor sin decir nada en casa. ya sabes cómo son · `dani.ink:41`
- 00:41 · **dani** · te pago yo el bus. y en un mes me mudo yo también. estaríamos juntos 😏 · `dani.ink:42`
- 08:20 · **me** · no estaríamos juntos dani. y no he dicho que sí · `dani.ink:43`
- 08:21 · **dani** · vale. pero piénsatelo. allí nadie te controla · `dani.ink:44`
- 09:48 · **banco** · BancRural: Tu tarjeta ****8821 se ha usado en FARMACIA PL. DEL VI GIRONA por 12,95 €. · `banco.ink:5`
- 10:00 · **clara** · Gracias por el sábado, monis. Los peques no paran de hablar de Ali y los globos 😂 · `esplai.ink:10`
- 11:20 · **me** · 💛💛 · `esplai.ink:11`
- 18:00 · **lluisa** · Turnos del sábado: Alicia y Toni de 10 a 13, Fàtima de 13 a 16. ¡Gracias! 🥫 · `aliments.ink:7`
- 18:40 · **me** · ok · `aliments.ink:8`
- 18:42 · **toni** · Ali trae otra vez esas galletas de la panadería porfa 🙏 · `aliments.ink:9`
- 19:00 · **me** · las del forn? si me quedan, sí · `aliments.ink:10`
- 19:30 · **jordi** · Hola cariño. ¿Te vienes unos días a Barcelona cuando acabes los exámenes? Papá · `jordi.ink:8`
- 21:10 · **me** · no sé papa, ya veré · `jordi.ink:9`
- 21:12 · **jordi** · Vale. La habitación siempre está lista. Papá · `jordi.ink:10`

### D-8 (historial del móvil)

- 17:30 · **carla** · ali me dejas tu top negro para el finde · `carla.ink:9`
- 18:02 · **me** · vale pero me lo devuelves · `carla.ink:10`
- 18:02 · **carla** · obvio 😇 · `carla.ink:11`
- 19:00 · **conxita** · Recordatorio: el martes pasan a recoger trastos viejos. Dejadlos delante de casa antes de las 8. · `veins.ink:7`
- 19:30 · **enric** · Gracias, Conxita. · `veins.ink:8`
- 21:28 · **mireia** · cómo estás? 🥺 · `mireia.ink:20`
- 21:30 · **banco** · BancRural: Has recibido un Bizum de MIREIA P. de 13,00 €. Concepto: «🤍». · `banco.ink:6`
- 21:40 · **me** · bien. cansada. y tú? · `mireia.ink:21`
- 21:41 · **mireia** · rara. pero bien. gracias por todo 🤍 · `mireia.ink:22`

### D-7 (historial del móvil)

- 10:10 · **teresa** · Gracias por la tertulia de ayer. Alicia, lo que dijiste de Andrea y su familia dio para mucho 👏 · `lectura.ink:12`
- 12:02 · **me** · es que a veces la familia es lo que más te ahoga, no? · `lectura.ink:13`
- 12:30 · **teresa** · Para el mes que viene: «El infinito en un junco», de Irene Vallejo. · `lectura.ink:14`
- 15:00 · **lluisa** · Gracias a todos por el sábado. Hemos repartido 64 lotes. · `aliments.ink:11`
- 21:30 · **casal** · Os recordamos que lo que se comparte en el grupo es confidencial, como en cualquier terapia. Cuidémonos entre  · `casal.ink:17`

### D-6 (historial del móvil)

- 07:45 · **mama** · Buenos días bonitas, hoy ya veréis como el ayuno es mas llevadero. Hay que ser consciente de lo que sufren alg · `familia.ink:20`
- 07:46 · **mama** · Tú también, cariño. Te irá bien para lo de la ansiedad, confia en mamá · `familia.ink:21`
- 08:30 · **me** · mama no puedo hacer eso, que tengo entreno, sabes que acabo desmayada. no puedo ir sin comer nada de nada · `familia.ink:22`
- 08:31 · **mama** · Ya lo hablamos, Alicia. Es un día al mes y es por una buena causa. Además ya lo has hecho otras veces y no ha  · `familia.ink:23`
- 08:40 · **berta** · Ali, recuerda lo que hablamos. No puedes ser siempre la oveja negra · `familia.ink:24`
- 12:10 · **mama** · Nena, ¿has comido? Hoy toca ayuno pero tú come algo, que tienes entreno 🌸 · `mama.ink:9`
- 13:40 · **me** · sí mamá · `mama.ink:10`
- 14:02 · **pilar** · Alicia mañana entras a las 6 que Èric no puede. Gracias guapa · `pilar.ink:10`
- 14:30 · **me** · vale pilar · `pilar.ink:11`
- 19:00 · **laura** · Ali haces tú el cartel? dibujas mejor que todos juntos · `festa.ink:12`
- 19:05 · **laura** · ali el cartel para cuándo?? 🎨 · `laura.ink:11`
- 20:14 · **pol** · ya no me hablas ni para lo de los apuntes eh · `pol.ink:8`
- 22:03 · **me** · jajaja perdón, semana horrible · `pol.ink:9`
- 22:10 · **me** · vale, la semana que viene os lo paso · `festa.ink:13`
- 22:12 · **me** · la semana que viene, te lo juro · `laura.ink:12`
- 22:13 · **laura** · te tomo la palabra 🌻 · `laura.ink:13`

### D-5 (historial del móvil)

- 08:10 · **berta** · ali me coges el cargador del coche? · `berta.ink:8`
- 08:40 · **me** · cógelo tú · `berta.ink:9`
- 08:41 · **berta** · Siempre igual 🙄 · `berta.ink:10`
- 12:00 · **casal** · Si alguien de vuestro entorno tiene dudas sobre la Casa, invitadlo al taller abierto del jueves. Las puertas e · `casal.ink:18`
- 19:00 · **anna** · Martina ha sacado un 7,5 🎉 ¡Gracias, Ali! Te hago el Bizum. · `repas.ink:11`
- 20:10 · **me** · 🥳🥳 · `repas.ink:12`
- 20:30 · **marc** · ali el cartel lleva tractor o qué · `marc.ink:9`
- 21:00 · **me** · ni de broma · `marc.ink:10`
- 21:00 · **marc** · lo veremos · `marc.ink:11`
- 23:40 · **dani** · q haces · `dani.ink:45`
- 23:58 · **me** · nada, estudiando · `dani.ink:46`
- 23:58 · **dani** · pásate por olot esta semana · `dani.ink:47`
- 23:59 · **dani** · te invito a algo · `dani.ink:48`

### D-4 (historial del móvil)

- 10:12 · **me** · no puedo, cosas de familia · `dani.ink:49`
- 10:12 · **dani** · siempre igual · `dani.ink:50`
- 13:00 · **me** · mañana me llevas al entreno? · `ona.ink:12`
- 13:05 · **ona** · sí!! paso a las 7 · `ona.ink:13`
- 13:12 · **banco** · BancRural: Tu tarjeta ****8821 se ha usado en FORN CAN BATLLE OLOT por 3,20 €. · `banco.ink:7`
- 16:20 · **carla** · CHICAS el cumple de mireia lo hacemos en el bosc o en mi casa · `amigas.ink:7`
- 16:22 · **mireia** · en tu casa que en el bosc hace un frío que te mueres · `amigas.ink:8`
- 16:40 · **nuria** · yo voto bosc 🌲🔥 · `amigas.ink:9`
- 17:05 · **me** · yo lo que digáis · `amigas.ink:10`
- 17:05 · **carla** · ali tú siempre «lo que digáis» 🙄 · `amigas.ink:11`
- 17:06 · **me** · es que me da igual de verdad jajaja · `amigas.ink:12`
- 18:12 · **arnau** · prima · `arnau.ink:7`
- 18:12 · **arnau** · PRIMA · `arnau.ink:8`
- 18:12 · **arnau** · me pasas tu cuenta de netflix · `arnau.ink:9`
- 19:00 · **sergi** · Alicia, ¿has empezado tu parte de la exposición? · `sergi.ink:9`
- 19:40 · **me** · ni de broma · `arnau.ink:10`
- 19:40 · **arnau** · 😭😭 · `arnau.ink:11`
- 19:40 · **mama** · Nena, esta noche hay evento en la asociación. Vienes? Lo pasaremos bien, ya verás. · `familia.ink:25`
- 19:52 · **me** · mamá mañana tengo examen · `familia.ink:26`
- 20:05 · **berta** · Alicia, no le hagas esto a mamá. · `familia.ink:27`
- 20:31 · **me** · vale · `familia.ink:28`
- 21:30 · **me** · sí, ya casi. es sobre la soledad de la gente mayor en el campo · `sergi.ink:10`
- 21:31 · **sergi** · Perfecto. · `sergi.ink:11`

### D-3 (historial del móvil)

- 00:40 · **mama** · Gracias por lo de hoy, estoy super contenta, siento que volvemos a conectar como antes. · `familia.ink:29`
- 00:41 · **berta** · Ali, lo que has compartido hoy ha sido muy valiente, mil gracias <3 · `familia.ink:30`
- 08:10 · **me** · bueno · `familia.ink:31`
- 08:12 · **berta** · Es normal que te sientas asi, pero date tiempo. Confia en mi, de verdad · `familia.ink:32`
- 08:15 · **mama** · Mañana a las 5:30 hay meditación. Vamos todas verdad, como siempre?. · `familia.ink:33`
- 13:40 · **eric** · mañana me traes un café y te perdono lo de la masa madre jajaja · `eric.ink:9`
- 15:02 · **me** · la masa madre se murió sola!! · `eric.ink:10`
- 15:02 · **eric** · asesina · `eric.ink:11`
- 17:02 · **nuria** · tía mañana vienes a girona o qué · `nuria.ink:8`
- 17:30 · **me** · no puedo, reunión de las de mi madre · `nuria.ink:9`
- 17:31 · **nuria** · otra vez??? · `nuria.ink:10`
- 17:31 · **nuria** · cada vez vas más a eso ali · `nuria.ink:11`
- 17:45 · **me** · lo sé · `nuria.ink:12`
- 17:46 · **me** · tengo que contarte una cosa. cuando nos veamos. por aquí no · `nuria.ink:13`
- 17:46 · **nuria** · me estás asustando · `nuria.ink:14`
- 17:58 · **me** · no es nada, de verdad. bueno sí. ya te contaré · `nuria.ink:15`
- 18:00 · **casal** · Taller abierto este jueves: «Sanar el linaje familiar». Podéis traer a alguien de fuera. · `casal.ink:19`
- 18:00 · **mama** · Esta noche hay sesión para familias en el grupo. Solo un ratito. Para mí es importante. · `mama.ink:11`
- 19:02 · **me** · no me apetece mamá · `mama.ink:12`
- 19:02 · **mama** · Hazlo por mí. · `mama.ink:13`
- 20:05 · **teresa** · Alicia, te has dejado el pendrive en el ordenador 2. Te lo guardo en el mostrador. · `teresa.ink:13`
- 20:30 · **me** · gracias teresa!! no se lo des a nadie porfa · `teresa.ink:14`
- 20:31 · **teresa** · Descuida. · `teresa.ink:15`
- 21:15 · **sonia** · Chicas, buen entreno hoy 💪 Ali, esas colocaciones 👌 · `voley.ink:7`
- 21:30 · **me** · 🥹🥹 · `voley.ink:8`
- 21:31 · **judit** · la reina de la colocación · `voley.ink:9`
- 22:15 · **aina** · alguien sabe si mañana hay clase de 8 · `clase.ink:7`
- 22:20 · **paula** · sí 😭 · `clase.ink:8`
- 22:31 · **sergi** · Recordad que la exposición de dinámicas es en dos semanas. Las parejas en el drive. · `clase.ink:9`
- 22:32 · **oriol** · sergi eres literalmente un profe · `clase.ink:10`
- 22:40 · **me** · sergi me toca contigo? · `clase.ink:11`
- 22:41 · **sergi** · Sí. Empezamos esta semana? · `clase.ink:12`
- 22:50 · **me** · vale · `clase.ink:13`

### D-2 (historial del móvil)

- 09:12 · **berta** · El jueves hay taller.Acordaros de la tarea que había que hacer!! . · `familia.ink:34`
- 09:13 · **berta** · Ali, tú podrías traer a la Nuri. Os lleváis super bien y puede hablar sobre lo bien que ha ido tenerla como am · `familia.ink:35`
- 10:40 · **me** · nooo que a la nuri ni de coña le guste esto, es muy suya y introvertida · `familia.ink:36`
- 10:42 · **mama** · Piensatelo, yo creo que seria una buena opcion · `familia.ink:37`
- 10:50 · **berta** · Ah, acordaros de tener dinero en la cuenta, que a inicio de mes toca pagar el taller de lectura, el voley y la · `familia.ink:38`
- 11:02 · **me** · yo no tengo libretaa mama, ya lo sabes · `familia.ink:39`
- 11:03 · **berta** · Recuerda que tienes la cuenta que te abrimos para cuando empezaste a trabajar ali. Mamá es cotitular porque er · `familia.ink:40`
- 11:05 · **mama** · Lo hablamos luego, no os preocupeis · `familia.ink:41`

### D-1 (historial del móvil)

- 18:20 · **me** · hoy ceno en casa de la nuriii · `familia.ink:42`
- 18:24 · **mama** · Vale. No vuelvas tarde cariño, que ultimamente te noto distante. Vas muy a tu bola, te echamos de menos ya · `familia.ink:43`
- 20:00 · **casal** · Lectura del día: «Hay que dejar morir lo viejo para que nazca lo nuevo.» · `casal.ink:20`
- 21:50 · **me** · nuri · `nuria.ink:16`
- 21:50 · **me** · si algún día no te contesto no te preocupes vale? · `nuria.ink:17`
- 21:51 · **me** · te quiero mucho · `nuria.ink:18`
- 22:31 · **me** · me voy a dormir pronto, estoy reventada · `familia.ink:44`
- 22:33 · **mama** · Descansa 💛 Mañana a las 6:30 meditación como siempre al alba? · `familia.ink:45`
- 22:40 · **nuria** · ??? · `nuria.ink:19`
- 22:40 · **nuria** · qué dices tía · `nuria.ink:20`
- 22:40 · **me** · pol · `pol.ink:10`
- 22:40 · **me** · puedes llevarme a la estación de olot esta noche? · `pol.ink:11`
- 22:41 · **me** · no preguntes porfa · `pol.ink:12`
- 22:44 · **pol** · ahora? · `pol.ink:13`
- 22:44 · **pol** · estás bien? · `pol.ink:14`
- 22:47 · **me** · sí. a las 11 y media en la rotonda del hostalnou · `pol.ink:15`
- 22:49 · **pol** · vale. voy · `pol.ink:16`
- 22:49 · **me** · gracias. te debo una enorme · `pol.ink:17`
- 23:51 · **pol** · ya estás? · `pol.ink:18`
- 23:52 · **pol** · te he visto rara. avísame cuando llegues · `pol.ink:19`
- 23:59 · **nuria** · ali?? · `nuria.ink:21`

### D1

- 06:00 · **casal** · Buenos días, almas 🌅 Esta semana preparamos la Trobada de l'Alba. Siete días de recogimiento. · `casal.ink:24`
- 07:05 · **pilar** · Alicia ayer no viniste y no avisaste · `pilar.ink:15`
- 08:05 · **sergi** · buenos días gente · `clase.ink:17`
- 09:30 · **elena** · Hola, Alicia. Soy Elena, tu tutora. · `elena.ink:8`
- 09:40 · **enric** · Se ha escapado el Rocky. Mestizo negro, collar rojo. Si lo veis, llamadme, por favor 🐕 · `veins.ink:12`
- 10:00 · **laia** · Conexión establecida. Canal cifrado. · `laia.ink:10`
- 10:20 · **teresa** · Buenos días. Ya tenéis los ejemplares en el mostrador. Alicia, el tuyo te lo guardo. · `lectura.ink:18`
- 11:00 · **quim** · Os recuerdo que el cartel tiene que estar en la imprenta en cinco días. · `festa.ink:17`
- 11:20 · **nuria** · Alicia. Me ha llamado tu madre. · `nuria.ink:25`
- 11:30 · **oriol** · alguien ha visto a alicia? hoy tampoco ha venido · `clase.ink:21`
- 12:00 · **ramon** · Yo lo he cogido. Pesa más que mi nieto · `lectura.ink:19`
- 12:40 · **carla** · alguien sabe algo de ali?? · `amigas.ink:16`
- 13:00 · **mireia** · ali · `mireia.ink:23`
- 13:00 · **silvia** · Ali, ¿mañana a la misma hora? · `repas.ink:16`
- 13:05 · **marta** · Alicia, cariño, soy la tía Marta. · `marta.ink:13`
- 13:45 · **paula** · el bocata de la cafetería hoy es un crimen · `clase.ink:24`
- 14:10 · **eric** · eh · `eric.ink:15`
- 16:00 · **claudia** · Clase mañana a las 19 en la sala 2. Traed manta, que el poli está helado. · `ioga.ink:16`
- 17:00 · **rafa** · Examen teórico en Girona dentro de tres días: Alicia Serra, Hugo Camps y Martí Roca. El autobús sale a las 7:3 · `autoescola.ink:14`
- 17:20 · **sonia** · Entreno a las 19:30. Ali, ¿vienes? Eres nuestra colocadora para el partido de dentro de cuatro días. · `voley.ink:13`
- 17:45 · **arnau** · prima · `arnau.ink:15`
- 18:00 · **lluisa** · Turnos de este sábado: Alicia y Toni de 10 a 13, Fàtima de 13 a 16. · `aliments.ink:15`
- 18:00 · **dolors** · Ensayo general en dos días, a las 20:30. Texto aprendido, por favor. · `teatre.ink:15`
- 18:10 · **mireia** · por cierto alguien tiene los apuntes de psico? · `amigas.ink:22`
- 19:00 · **xavier** · Este domingo cantamos en la misa de las 12. Camisa blanca y pantalón negro, como siempre. · `coral.ink:16`
- 20:00 · **pau** · Sábado: salida al Parc Nou. Salimos a las 10 de la plaza. Ali, ¿te encargas de los peques? · `esplai.ink:15`
- 20:10 · **jordi** · Hola cariño. Tu madre me ha dicho que estás unos días en casa de una amiga. ¿Todo bien? Papá · `jordi.ink:14`
- 20:10 · **sonia** · Ali no ha venido. ¿Alguien sabe algo? · `voley.ink:17`
- 21:02 · **mama** · Berta, sigues por alli? · `familia.ink:49`
- 21:30 · **laia** · ¿Algo para el parte de hoy? · `laia.ink:72`
- 21:40 · **aina** · mañana examen de psico, me quiero morir · `clase.ink:26`
- 22:15 · **pol** · oye · `pol.ink:23`
- 22:50 · **carla** · ali si no contestas hoy mañana vamos a tu casa · `amigas.ink:27`
- 23:30 · **dani** · ey · `dani.ink:54`
- 23:45 · **nuria** · - (fin_d1) me voy a dormir. si mañana no sé nada de ti llamo yo a los mossos · `nuria.ink:77`

### D2

- 06:50 · **pilar** · Alicia · `pilar.ink:32`
- 08:30 · **laia** · Buenos días. · `laia.ink:96`
- 08:50 · **aina** · examen de psico en 10 min y no me sé nada · `clase.ink:42`
- 09:40 · **elena** · Buenos días, Alicia. · `elena.ink:31`
- 10:28 · **banco** · BancRural: Se ha realizado una transferencia de 2.840,00 € desde tu cuenta ****4417. Beneficiario: CRA SERVEIS · `banco.ink:8`
- 10:31 · **mama** · Berta, he hecho el pago de lo de Hacienda. Por suerte en la cuenta de Ali había bastante. · `familia.ink:70`
- 10:40 · **nuria** · sigues ahí. quien seas · `nuria.ink:83`
- 10:40 · **nuria** · sigo sin creerme que seas tú · `nuria.ink:91`
- 10:40 · **nuria** · buenos días · `nuria.ink:95`
- 11:00 · **casal** · Gracias a la generosidad de nuestras familias, la sala nueva de la Casa ya tiene tejado 🌹 · `casal.ink:29`
- 11:10 · **marta** · Ali, esta mañana he visto a tu madre en la oficina del banco. · `marta.ink:36`
- 11:15 · **aina** · ha ido fatal · `clase.ink:46`
- 12:00 · **carla** · hoy vamos a tu casa ali, a las 6 · `amigas.ink:43`
- 12:00 · **carla** · vale ali, si dices que estás bien... · `amigas.ink:51`
- 12:10 · **marc** · ali · `marc.ink:82`
- 12:30 · **toni** · Por cierto. Esta mañana ha venido la madre de Alicia. · `aliments.ink:20`
- 13:20 · **roser** · Me he cruzado con la Montse en el mercado. La he saludado y casi ni me ha mirado. · `coral.ink:28`
- 13:20 · **eric** · has visto mi post 🕵️🥖 jajaja · `eric.ink:39`
- 13:30 · **aina** · ali · `aina.ink:116`
- 16:10 · **laia** · Cámaras de la estación de Olot, anteanoche. · `laia.ink:119`
- 17:30 · **sonia** · Recordatorio: partido en tres días a las 11. Convocatoria la víspera. · `voley.ink:36`
- 18:10 · **silvia** · He ido a dejar a Leo en tu casa y no había nadie. Las persianas bajadas. · `repas.ink:29`
- 18:10 · **silvia** · He pasado con Leo por delante de tu casa. Las persianas bajadas a las seis de la tarde. · `repas.ink:32`
- 18:30 · **pol** · oye · `pol.ink:54`
- 18:30 · **conxita** · Montse, ¿estáis bien? Hace días que veo las persianas bajadas a mediodía. · `veins.ink:19`
- 18:40 · **carla** · hemos ido a tu casa · `amigas.ink:44`
- 18:45 · **arnau** · prima · `arnau.ink:33`
- 18:55 · **mama** · Han venido sus amigas. Les he dicho que estaba de retiro. · `familia.ink:81`
- 19:00 · **pau** · Ali, ¿estás? Necesito saberlo. · `esplai.ink:24`
- 19:00 · **ramon** · Gemma, ¿lo has empezado? 😏 · `lectura.ink:23`
- 19:30 · **oriol** · oye anteanoche subí bracons en bici, de noche 🌙 · `clase.ink:47`
- 19:40 · **laura** · ali!! 💛 · `laura.ink:17`
- 20:15 · **mama** · La tía Marta va diciendo cosas en el mercado. · `familia.ink:86`
- 20:30 · **jordi** · Cariño, te llamo un momento. · `jordi.ink:37`
- 20:40 · **imma** · Una pregunta. En el mercado me han dado un folleto de Rosa d'Abril. Retiros de silencio, sanar el alma y no sé · `ioga.ink:20`
- 21:00 · **mireia** · mañana es mi cumple y una de nosotras no viene 💔 · `amigas.ink:54`
- 21:00 · **sergi** · Alicia, te he subido mi mitad de la presentación al drive. · `clase.ink:52`
- 21:00 · **pau** · Biel se queda con los peques. Clara y yo con los medianos. · `esplai.ink:31`
- 21:10 · **iker** · hola guapa 😊 · `iker.ink:20`
- 21:30 · **judit** · ali · `judit.ink:83`
- 21:30 · **laia** · ¿Algo más para el parte? · `laia.ink:130`
- 22:40 · **dani** · oye · `dani.ink:98`
- 23:10 · **berta** · Sé que no eres Ali. · `berta.ink:15`
- 23:10 · **berta** · Sé que vas a volver. · `berta.ink:20`

### D3

- 00:01 · **carla** · FELIZ CUMPLE MIREIA 🎂🎉 · `amigas.ink:70`
- 07:00 · **berta** · Buenos días! Gratitud por un día más. · `familia.ink:117`
- 07:05 · **berta** · Tienes 24 horas para devolverme a mi hermana. · `berta.ink:47`
- 07:15 · **pilar** · Te llamo, nena · `pilar.ink:54`
- 07:20 · **mama** · Llevo tres noches sin dormir. · `familia.ink:118`
- 08:15 · **enric** · ¡Ha vuelto el Rocky! Estaba en el huerto de los Puigdemont, gordo como una vaca 😂 · `veins.ink:25`
- 08:20 · **jordi** · Ali. Esta mañana han venido dos Mossos a casa preguntando por ti. · `jordi.ink:49`
- 08:20 · **jordi** · Buenos días, cariño. ¿Has dormido bien? Papá · `jordi.ink:54`
- 08:45 · **laia** · Buenos días. · `laia.ink:153`
- 09:10 · **nuria** · felicidades mire!! · `amigas.ink:72`
- 10:15 · **elena** · Alicia, he comentado tu situación con la orientadora del centro. · `elena.ink:56`
- 10:30 · **anna** · Me han dicho en el mercado que Ali está de viaje. Busco otra profe hasta que vuelva. · `repas.ink:38`
- 11:00 · **teresa** · Alicia, tengo aún «Nada» a tu nombre. Cuando puedas, me lo devuelves, que hay lista de espera. · `lectura.ink:28`
- 12:10 · **laia** · Lo del banco. CRA Serveis es el nombre comercial de la Associació Comunitat Rosa d'Abril. Sant Joan les Fonts. · `laia.ink:161`
- 12:30 · **berta** · Quizás deberíamos actuar por nuestra cuenta.Hablemos con todo el que la conoce. Amigas, el chico ese, el traba · `familia.ink:120`
- 13:40 · **laia** · Otra cosa. Barcelona ha desarticulado esta semana parte de una red que captaba chicas de pueblo por redes soci · `laia.ink:167`
- 14:25 · **oriol** · ruta colgada en gonpi 🚴🌙 id a verla · `clase.ink:65`
- 15:30 · **laia** · Mi comisario me pregunta por qué pierdo el tiempo con una mayor de edad que se ha ido de casa. · `laia.ink:170`
- 16:00 · **xavier** · Ensayo cancelado este jueves, estoy afónico. Un director afónico, qué ironía. · `coral.ink:34`
- 16:00 · **eric** · oye · `eric.ink:61`
- 17:00 · **nuria** · te dije que te haría tres preguntas. · `nuria.ink:117`
- 17:00 · **nuria** · ali perdona. sé que es una tontería · `nuria.ink:121`
- 17:00 · **nuria** · tres preguntas. · `nuria.ink:124`
- 17:30 · **arnau** · prima el vídeo sigue archivado eh. me debes una skin · `arnau.ink:54`
- 17:30 · **arnau** · prima mi post ya tiene 2.000 visitas 💀 · `arnau.ink:56`
- 17:40 · **pep** · Sin ensayo y sin contralto. Esta semana me aburro hasta yo. · `pep.ink:119`
- 18:00 · **laura** · hoy he ido a una sesión de mindfulness en un jardín de sant joan les fonts · `laura.ink:48`
- 18:00 · **sonia** · Convocatoria del partido mañana a las 20 h por aquí. · `voley.ink:43`
- 18:20 · **laura** · Ali, ¿cómo va el cartel? 🎨 · `festa.ink:24`
- 19:00 · **lluisa** · Alicia, ¿cuento contigo el sábado? · `aliments.ink:27`
- 19:00 · **marta** · Ali, he hablado con tu padre. · `marta.ink:51`
- 19:30 · **pau** · Monis, ¿alguien tiene las llaves del refugi de Can Pericot? En el armario del local solo queda un juego. · `esplai.ink:37`
- 20:00 · **rafa** · Mañana a las 7:30. DNI y la tasa pagada. Ni un minuto tarde. · `autoescola.ink:22`
- 20:00 · **sergi** · Mañana a las 10, reunión con Elena para la exposición. Alicia, ¿vienes? · `clase.ink:71`
- 20:10 · **biel** · oye ali · `biel.ink:96`
- 21:00 · **nuria** · chicas. tengo que contaros una cosa · `amigas.ink:74`
- 21:00 · **casal** · Pedimos luz para una de nuestras familias, que está pasando por un momento muy difícil. · `casal.ink:33`
- 21:10 · **mama** · Jordi me ha llamado gritando. Que le han ido los Mossos a casa. · `familia.ink:126`
- 21:15 · **aina** · oye he visto lo de oriol · `aina.ink:138`
- 21:30 · **dani** · mira mi gonpi · `dani.ink:125`
- 21:30 · **laia** · Parte. ¿Qué tienes? · `laia.ink:183`
- 22:00 · **berta** · 🌹 · `familia.ink:129`
- 22:00 · **pol** · ali solo dime una cosa · `pol.ink:74`
- 22:30 · **dolors** · Ensayo general sin Adela. Alicia ya me avisó: no puede. · `teatre.ink:24`
- 22:30 · **dolors** · Alicia no ha venido al ensayo. ¿Alguien sabe algo? · `teatre.ink:30`
- 23:10 · **guia** · Alicia. Soy quien acompaña a tu madre en el grupo. Ella me ha dado tu número. · `guia.ink:6`
- 23:15 · **queralt** · Ali. · `queralt.ink:55`
- 23:40 · **carla** · fotos de la fiesta ya en gonpi 📸 · `amigas.ink:82`
- 23:40 · **dani** · ey · `dani.ink:132`
- 23:50 · **iker** · oye · `iker.ink:34`

### D4

- 07:00 · **berta** · Buenos días ☀️ Hoy, ayuno de palabras: solo lo necesario. · `familia.ink:140`
- 07:05 · **berta** · Sigues ahí. Lo noto. · `berta.ink:54`
- 07:20 · **hugo** · ali de verdad no vienes?? · `hugo.ink:59`
- 07:20 · **hugo** · ali dónde estás?? · `hugo.ink:62`
- 07:50 · **rafa** · Alicia avisó ayer de que no venía. Al final he llamado a su casa: su madre dice que ha «cambiado de prioridade · `autoescola.ink:35`
- 07:50 · **rafa** · Alicia no se ha presentado al bus. He llamado a su casa y su madre dice que ha «cambiado de prioridades». · `autoescola.ink:38`
- 08:00 · **casal** · Recordatorio: el despacho de la Casa os sigue ayudando, sin coste, a preparar el documento de voluntades antic · `casal.ink:38`
- 08:20 · **rafa** · Alicia. · `rafa.ink:56`
- 09:10 · **enric** · Esta noche ha estado un coche gris parado delante de casa de Montse hasta las tantas. Con una rosa pegada en e · `veins.ink:31`
- 09:30 · **laia** · Esta mañana ha venido a comisaría una tal Núria Gil. · `laia.ink:215`
- 09:30 · **teresa** · Buenos días, Alicia. El pendrive sigue en el cajón. Por si lo necesitas. · `teresa.ink:149`
- 10:15 · **laia** · Padrón. Rosalia Masó Puig, 78 años, empadronada en Sant Salvador de Bianya. · `laia.ink:227`
- 10:50 · **sergi** · Elena dice que la exposición la puedo hacer yo solo si hace falta. · `clase.ink:75`
- 11:00 · **nuria** · ya está. he ido a los mossos · `nuria.ink:208`
- 11:15 · **enric** · Alicia, perdona que te escriba. · `enric.ink:71`
- 11:20 · **sergi** · Alicia. · `sergi.ink:79`
- 11:30 · **laia** · Barcelona me ha contestado. El número de Iker es de un relaciones públicas de un club del Port Olímpic. · `laia.ink:222`
- 11:40 · **mama** · La Marta ha venido a casa. No le he abierto. · `familia.ink:141`
- 12:00 · **marta** · He ido a tu casa. Tu madre no me ha abierto. · `marta.ink:58`
- 12:30 · **rosalia** · 📞 · `rosalia.ink:6`
- 13:00 · **carla** · nuri has ido al final? · `amigas.ink:93`
- 13:10 · **rosalia** · #at: D4 13:10 · `rosalia.ink:7`
- 13:30 · **rafa** · Hugo: aprobado con un 28. ¡Milagro! 🎉 · `autoescola.ink:42`
- 14:00 · **biel** · Salida genial. Nadie perdido, nadie herido. Récord 😎 · `esplai.ink:47`
- 14:30 · **eric** · pilar dice que si vuelves, el puesto es tuyo · `eric.ink:69`
- 15:00 · **laia** · Cada día que pasa, más difícil. Mañana pido permiso para mover a gente. No te prometo nada. · `laia.ink:231`
- 16:10 · **mama** · Es el cuarto dia sin noticias de Ali. No puedo más. Hoy he firmado las voluntades anticipadas en el despacho d · `familia.ink:143`
- 18:00 · **arnau** · prima vas a venir a la comida de la iaia de dentro de tres días? · `arnau.ink:64`
- 18:30 · **imma** · Ali, perdona que te escriba así. · `imma.ink:61`
- 19:10 · **anna** · Ali, Martina ha sacado un 8 en el control de decimales 🎉 · `anna.ink:92`
- 19:20 · **berta** · He llamado a la residencia de Olot. Allí no hay ninguna Rosalia. · `familia.ink:147`
- 19:30 · **pol** · tu madre ha vuelto. con el de la barba · `pol.ink:86`
- 19:30 · **pol** · ali lo siento · `pol.ink:90`
- 20:00 · **jordi** · He hablado con un abogado, cariño. · `jordi.ink:68`
- 20:00 · **sonia** · Convocatoria para mañana a las 11: Judit, Ona, Clara R., Txell C., Paula B., Gemma F. Colocadora: Ona. · `voley.ink:50`
- 20:15 · **claudia** · Hoy éramos cuatro gatos. Ali, te echamos de menos en la esterilla de la esquina. · `ioga.ink:30`
- 20:20 · **ona** · ali me ponen a mí de colocadora 😭 · `ona.ink:137`
- 20:30 · **casal** · Os pedimos discreción con las familias que lo están pasando mal. Si alguien de fuera pregunta, que hable con l · `casal.ink:39`
- 20:30 · **laura** · ali, berta dice que mañana suben ellos a capsec a buscarte · `laura.ink:81`
- 20:30 · **laura** · ali una cosa · `laura.ink:85`
- 20:30 · **laura** · dentro de dos días hay retiro de silencio en el casal. me apunto!! 🌿 · `laura.ink:90`
- 21:00 · **dani** · oye · `dani.ink:155`
- 21:05 · **berta** · ¿Has visto el vídeo que subió Arnau? El de la rotonda. · `familia.ink:152`
- 21:05 · **nuria** · sigo preocupada. mucho · `nuria.ink:213`
- 21:05 · **nuria** · he subido una foto nuestra a gonpi · `nuria.ink:218`
- 21:30 · **carla** · alguien ha visto la foto de nuri en gonpi? 🥺 · `amigas.ink:97`
- 21:30 · **laia** · Parte. · `laia.ink:241`
- 22:00 · **mama** · 🌹 · `familia.ink:158`
- 22:00 · **laura** · ali no me has pasado nada, así que lo he hecho yo 🙃 · `festa.ink:39`
- 22:00 · **laura** · cartel hecho. no es lo mismo que si lo hubiera hecho ali pero bueno 🙃 · `festa.ink:41`
- 22:00 · **dolors** · Buscaremos otra Adela. · `teatre.ink:40`
- 22:10 · **oriol** · oye ali · `oriol.ink:126`
- 23:20 · **mireia** · ali · `mireia.ink:119`
- 23:40 · **desconocido** · hola · `desconocido.ink:8`

### D5

- 00:30 · **iris** · hola ali. soy iris. la del casal · `iris.ink:8`
- 01:10 · **iker** · alicia, una cosa · `iker.ink:48`
- 06:30 · **pilar** · No sé nada de ti, nena. Pero me acuerdo de ti cada mañana al encender el horno · `pilar.ink:117`
- 07:00 · **berta** · Buenos días ☀️ Que hoy pese un poco menos. · `familia.ink:162`
- 08:30 · **laia** · Tengo permiso para dos agentes. Dos. Y a partir de mañana. · `laia.ink:263`
- 10:15 · **lluisa** · Alicia, ¿vienes? Son las diez y cuarto · `aliments.ink:49`
- 10:30 · **nuria** · he soñado otra vez con la casa de las estrellas · `nuria.ink:243`
- 10:40 · **berta** · He ido a la panadería. · `familia.ink:164`
- 11:00 · **elena** · Alicia, he tenido que informar a dirección de tus ausencias. Es el protocolo. · `elena.ink:67`
- 11:00 · **eric** · ali la he liado · `eric.ink:80`
- 11:20 · **pep** · He pensado en lo que me dijiste de tu yaya. · `pep.ink:136`
- 11:30 · **pilar** · Nena, esta mañana ha venido tu hermana. Preguntando por el pan · `pilar.ink:104`
- 12:00 · **aina** · alguien tiene el horario de los finales? · `clase.ink:82`
- 12:15 · **nuria** · me ha llamado la sargento esa. puig · `nuria.ink:268`
- 12:40 · **teresa** · Ha venido la madre de Alicia a devolver el libro. Muy amable. · `lectura.ink:43`
- 13:00 · **eric** · hoy hemos hecho cocas de chicharrones. te guardo una 🍞 · `eric.ink:97`
- 13:00 · **sonia** · Perdemos 1-3. Hemos luchado, chicas. 💪 · `voley.ink:56`
- 13:05 · **teresa** · Ha venido tu madre. · `teresa.ink:155`
- 13:30 · **gemma** · ali · `gemma.ink:72`
- 13:30 · **nuria** · hoy ha jugado el vóley sin ti. han perdido · `nuria.ink:237`
- 13:40 · **ona** · lo de alta y al centro funcionó · `ona.ink:158`
- 15:00 · **sonia** · Ali. · `sonia.ink:78`
- 15:30 · **lluisa** · Hoy 71 lotes. ¡Récord! Gracias, equipazo 🥫 · `aliments.ink:53`
- 16:10 · **toni** · Oye, Alicia, por si lees esto. Esta mañana un chaval en moto me ha parado en el almacén · `aliments.ink:55`
- 16:20 · **mama** · ¿Qué me pongo pasado mañana? Para lo de la yaya. · `familia.ink:174`
- 17:10 · **arnau** · prima la iaia está haciendo canelones como para un regimiento · `arnau.ink:73`
- 17:10 · **arnau** · prima la iaia dice que vayas aunque sea al postre · `arnau.ink:76`
- 17:10 · **arnau** · prima la iaia dice que vayas a la comida aunque sea al postre · `arnau.ink:79`
- 19:00 · **marta** · Tu padre llega mañana. Se queda en casa. · `marta.ink:66`
- 20:00 · **xavier** · Domingo, misa de las 12. Sin falta. · `coral.ink:42`
- 20:00 · **pol** · no paro de pensar que la he cagado · `pol.ink:115`
- 20:30 · **jordi** · Mañana por la tarde subo, cariño. Me quedo en casa de la tía Marta. · `jordi.ink:80`
- 21:00 · **berta** · Mañana subimos a Sant Salvador con la furgoneta. · `familia.ink:181`
- 21:30 · **casal** · Trobada de l'Alba: ropa blanca y calzado cómodo. No hace falta traer nada: ni bolso, ni móvil, ni llaves. A la · `casal.ink:43`
- 21:45 · **laia** · Parte. ¿Qué tienes? · `laia.ink:283`
- 22:00 · **guia** · Buenas noches, Alicia. · `guia.ink:25`
- 22:10 · **mama** · Nena, pasado mañana hace tres años de la yaya. Si lees esto, ven a cenar a casa. · `familia.ink:184`
- 22:20 · **carla** · habéis visto lo que ha subido la cuenta esa del casal? la de la madre de ali · `amigas.ink:104`
- 22:50 · **berta** · Alguien está usando tu móvil. Sé que no eres tú. Devuelvela sana y salva, te daremos todo lo que quieras, lo q · `berta.ink:60`
- 22:50 · **berta** · Rezo para que cada día estes bien. Me duele el alma no saber donde estás o si estás bien. Quiero que vuelvas a · `berta.ink:62`
- 23:15 · **dani** · ey · `dani.ink:182`
- 23:30 · **desconocido** · estás ahí? · `desconocido.ink:93`

### D6

- 00:45 · **iris** · 📞 · `iris.ink:44`
- 01:30 · **laia** · #at: D6 01:30 · `laia.ink:305`
- 07:00 · **casal** · Mañana se sube en ayunas: el desayuno lo compartimos arriba, con el agua de la Casa 🌿 · `casal.ink:47`
- 07:00 · **berta** · Mañana. · `familia.ink:197`
- 07:05 · **berta** · Por dios, devuélveme a mi hermana. Dime algo. · `berta.ink:70`
- 07:05 · **berta** · Mañana te encontraré hermana. Estés donde estés, sé que te encontraré. · `berta.ink:73`
- 07:40 · **pilar** · Nena, ha venido ese chico de la moto. El Dani · `pilar.ink:122`
- 08:00 · **central** · 📻 Canal de la Central de Olot. Aquí llegarán los informes de los agentes que mandes. · `central.ink:6`
- 08:00 · **laia** · Hoy es el día. · `laia.ink:307`
- 09:00 · **sergi** · Mañana ensayo de la exposición a las 10. · `clase.ink:88`
- 09:45 · **eric** · ali · `eric.ink:102`
- 10:00 · **pol** · he ido a los mossos. les he contado todo. lo de tu madre también · `pol.ink:126`
- 10:30 · **iker** · #at: D6 10:30 · `iker.ink:64`
- 10:30 · **conxita** · Mañana hay corte de luz de 18 a 22, lo ha dicho el Ayuntamiento. Cargad los móviles. · `veins.ink:40`
- 11:30 · **nuria** · mira lo que he encontrado en el álbum de mi madre · `nuria.ink:291`
- 12:00 · **laura** · ali ya no me contestas 🥺 · `laura.ink:109`
- 12:30 · **carla** · chicas y si mañana vamos al cráter a ver qué pasa · `amigas.ink:112`
- 13:00 · **dani** · #at: D6 13:00 · `dani.ink:207`
- 13:00 · **mama** · Mesa puesta. Tres platos. · `familia.ink:198`
- 16:00 · **arnau** · prima mañana es la comida. la iaia ha hecho canelones 🤤 · `arnau.ink:86`
- 17:00 · **jordi** · Ya estoy en Olot, en casa de la tía. · `jordi.ink:91`
- 17:30 · **berta** · Subimos. · `familia.ink:200`
- 18:00 · **laia** · ⚠️ Conexión perdida con el terminal. · `laia.ink:317`
- 18:30 · **berta** · Hay un coche de los Mossos delante de la casa. · `familia.ink:202`
- 18:30 · **berta** · Puerta azul. Es aquí. · `familia.ink:206`
- 19:00 · **sonia** · Entreno de recuperación suave mañana a las 18. · `voley.ink:63`
- 20:00 · **quim** · Mañana a las 9 montamos el escenario en la plaza. Brazos fuertes, por favor. · `festa.ink:54`
- 21:00 · **berta** · No hace falta buscarla más, mamá. Sabe qué día es. Vendrá sola. · `familia.ink:212`
- 21:00 · **nuria** · suerte. de verdad · `nuria.ink:282`
- 21:00 · **nuria** · ali... · `nuria.ink:284`
- 21:30 · **pol** · pase lo que pase mañana, aquí estoy · `pol.ink:138`
- 22:00 · **casal** · Gracias por este camino compartido. Nos vemos al otro lado de la noche 🌹 · `casal.ink:48`
- 22:00 · **laia** · ✅ Conexión restablecida. · `laia.ink:318`
- 22:05 · **laia** · Cuatro horas a oscuras. ¿Sigues ahí? · `laia.ink:319`
- 22:30 · **marta** · Tu padre está aquí. No suelta el móvil. · `marta.ink:72`
- 23:00 · **mama** · Nena. Te esperamos. · `familia.ink:214`
- 23:50 · **desconocido** · #at: D6 23:50 · `desconocido.ink:136`

### D7

- 01:30 · **dani** · #at: D7 01:30 · `dani.ink:235`
- 04:00 · **laia** · Las cuatro. Le he pedido a Tráfico que me avise de cualquier coche que baje del valle. · `laia.ink:335`
- 05:00 · **casal** · Es la hora. · `casal.ink:52`
- 05:00 · **berta** · Hoy. · `familia.ink:218`
- 05:00 · **guia** · Hoy amanece para todos. · `guia.ink:42`
- 05:00 · **iris** · estoy en comisaría. llevo aquí toda la noche · `iris.ink:65`
- 05:10 · **laia** · En posición en el cráter. Niebla. No se ve ni la ermita. · `laia.ink:343`
- 05:10 · **laia** · No he dormido. · `laia.ink:346`
- 05:30 · **mama** · Ya vamos. Todas de blanco. · `familia.ink:219`
- 05:30 · **pilar** · Nena, hoy enciendo el horno pensando en ti · `pilar.ink:134`
- 05:31 · **berta** · Ali viene con nosotras. 🌹 · `familia.ink:221`
- 05:40 · **laia** · Iris Ferrer se presentó en comisaría a las tres de la madrugada. Ha declarado todo. · `laia.ink:348`
- 06:05 · **laia** · Suben linternas por el sendero. Diez, doce personas. Todas de blanco. · `laia.ink:344`
- 06:20 · **desconocido** · #at: D7 06:20 · `desconocido.ink:177`
- 06:30 · **casal** · Ha amanecido para todas. 🌹 · `casal.ink:57`
- 06:30 · **berta** · Ya estamos todas. · `familia.ink:225`
- 06:30 · **guia** · Ya ha amanecido. · `guia.ink:44`
- 06:31 · **laia** · 06:31. Entramos. · `laia.ink:362`
- 06:31 · **laia** · Alicia está a salvo. Está conmigo. · `laia.ink:383`
- 06:31 · **laia** · 06:31. Entramos. Ignasi Coll, detenido. Montse y Berta, a salvo. · `laia.ink:405`
- 06:45 · **laia** · Hemos llegado tarde. · `laia.ink:395`
- 06:45 · **laia** · Hay ambulancias en el cráter. Muchas. · `laia.ink:409`
- 07:10 · **berta** · Sólo quería ascender, dejar el dolor y el sufrimiento de este mundo terrenal. · `berta.ink:79`
- 08:00 · **carla** · habéis visto gonpi??? policía en el cráter de santa margarida · `amigas.ink:119`
- 08:10 · **jordi** · Cariño, me ha llamado una sargento de los Mossos. Voy para la comisaría. · `jordi.ink:104`
- 08:10 · **jordi** · Estoy delante de tu casa. No hay nadie. Ni tu madre, ni tu hermana. · `jordi.ink:107`
- 08:30 · **mireia** · y ali? · `amigas.ink:125`
- 08:30 · **marta** · Tu padre se ha ido corriendo a la comisaría. Ali, cariño, qué alegría. · `marta.ink:78`
- 08:30 · **marta** · Tu padre ha ido a tu casa y no hay nadie. Ali, por favor, di algo. · `marta.ink:80`
- 09:00 · **arnau** · prima la comida de la iaia se ha cancelado · `arnau.ink:91`
- 09:00 · **casal** · Este canal ya no está disponible. · `casal.ink:55`
- 09:00 · **iker** · #at: D7 09:00 · `iker.ink:81`
- 09:30 · **desconocido** · estoy en comisaría. me han dejado cargar el móvil 😅 · `desconocido.ink:179`
- 09:45 · **paula** · habéis visto lo del cráter? sale en todas partes · `clase.ink:94`
- 10:00 · **laura** · he visto lo del cráter · `laura.ink:120`
- 10:15 · **nuria** · ME HA LLAMADO · `nuria.ink:310`
- 10:15 · **nuria** · alguien sabe algo? · `nuria.ink:314`
- 10:30 · **mireia** · nuri dice que ali está bien!!! · `amigas.ink:121`
- 11:00 · **pol** · me ha escrito tu tía. que estás bien · `pol.ink:150`
- 11:20 · **mama** · Nena. Perdóname. · `familia.ink:227`

## Personajes

Dónde se les nombra (texto que lee el jugador, sin comentarios). «Desde» es el día del `#at` más cercano por encima; «charla» si está en una charla sin hora.

| Personaje | Se le busca por | Desde | Archivos |
|---|---|---|---|
| laia (Sgt. Puig 🔒) | Sgt, Laia | D1 | familia.ink (1), jordi.ink (1), laia.ink (3) |
| central (Central Mossos · Olot 📻) | Central | D6 | central.ink (2), laia.ink (2) |
| mama (Mamá) | Mamá, Montse | D-6 | coral.ink (1), desconocido.ink (6), familia.ink (20), jordi.ink (6), laia.ink (6), mama.ink (6), marta.ink (2), mireia.ink (1), veins.ink (2) |
| berta (Berta) | Berta | D-8 | arnau.ink (2), berta.ink (4), carla.ink (1), desconocido.ink (5), familia.ink (3), jordi.ink (1), laia.ink (5), laura.ink (5) |
| jordi (Papá) | Papá, Jordi | D-9 | central.ink (1), familia.ink (2), jordi.ink (33) |
| marta (Tía Marta) | Tía | D-40 | aina.ink (2), amigas.ink (4), carla.ink (3), clase.ink (2), desconocido.ink (1), familia.ink (1), jordi.ink (2), laura.ink (1), lectura.ink (1), marta.ink (14), mireia.ink (1), nuria.ink (4), ona.ink (1), paula.ink (3), pol.ink (1), ramon.ink (1) |
| arnau (Arnau 🎮) | Arnau | D2 | arnau.ink (6), eric.ink (1), familia.ink (3), pilar.ink (1) |
| nuria (Nuri 💛) | Nuri | D-10 | amigas.ink (3), carla.ink (1), dani.ink (1), desconocido.ink (3), familia.ink (2), mireia.ink (2), nuria.ink (9), paula.ink (1) |
| carla (Carla) | Carla | D-8 | carla.ink (5), familia.ink (1), judit.ink (3), mireia.ink (3), nuria.ink (2), ona.ink (2), paula.ink (1) |
| mireia (Mireia) | Mireia | D-8 | amigas.ink (3), banco.ink (1), carla.ink (1), jan.ink (1), mireia.ink (7), nuria.ink (1), sergi.ink (4) |
| pol (Pol) | Pol | D-20 | central.ink (4), clara.ink (1), dani.ink (5), familia.ink (1), laia.ink (1), nuria.ink (2), pol.ink (19) |
| dani (Dani) | Dani | D-34 | aliments.ink (1), amigas.ink (2), carla.ink (4), central.ink (11), dani.ink (36), desconocido.ink (4), eric.ink (2), iker.ink (14), judit.ink (2), laia.ink (19), main.ink (1), nuria.ink (2), paula.ink (2), pilar.ink (2), pol.ink (2) |
| sergi (Sergi (clase)) | Sergi | D-4 | aina.ink (3), amigas.ink (3), clase.ink (12), jan.ink (1), mireia.ink (3), paula.ink (2), sergi.ink (15) |
| aina (Aina (clase)) | Aina | D1 | aina.ink (16), clase.ink (1), paula.ink (1) |
| oriol (Oriol (clase)) | Oriol | D-20 | aina.ink (8), clase.ink (3), oriol.ink (21), paula.ink (2) |
| paula (Paula (clase)) | Paula | D-4 | aina.ink (4), paula.ink (7), sergi.ink (1), voley.ink (1) |
| elena (Elena (tutora)) | Elena | D-4 | aina.ink (2), clase.ink (3), elena.ink (5), lluisa.ink (1), sergi.ink (2) |
| pilar (Pilar Forn) | Pilar | D-6 | eric.ink (12), pilar.ink (9) |
| eric (Èric Forn) | Èric | D-6 | familia.ink (1), pilar.ink (2) |
| sonia (Sonia (entrenadora)) | Sonia | D-4 | ona.ink (4), sonia.ink (13), voley.ink (1) |
| judit (Judit 🏐) | Judit | D-8 | carla.ink (2), judit.ink (8), ona.ink (6), sonia.ink (4), voley.ink (3) |
| ona (Ona 🏐) | Ona | D-4 | judit.ink (1), ona.ink (20), sonia.ink (3), voley.ink (5) |
| teresa (Teresa (biblioteca)) | Teresa | D-3 | gemma.ink (1), lectura.ink (2), teresa.ink (24) |
| ramon (Ramon) | Ramon | D2 | gemma.ink (2), lectura.ink (2), ramon.ink (10) |
| gemma (Gemma) | Gemma | D-20 | gemma.ink (12), ioga.ink (2), lectura.ink (1), ramon.ink (1), voley.ink (1) |
| xavier (Xavier (coral)) | Xavier | D5 | pep.ink (2), xavier.ink (7) |
| roser (Roser (coral)) | Roser | D1 | coral.ink (2), pep.ink (6), ramon.ink (1), roser.ink (9), xavier.ink (1) |
| pep (Pep (coral)) | Pep | D3 | coral.ink (1), pep.ink (18) |
| lluisa (Lluïsa (Banc d'Aliments)) | Lluïsa | charla | fatima.ink (2), lluisa.ink (6), toni.ink (1) |
| toni (Toni) | Toni | D-9 | aliments.ink (5), fatima.ink (1), lluisa.ink (5), toni.ink (17) |
| fatima (Fàtima) | Fàtima | D-9 | aliments.ink (6), fatima.ink (5), lluisa.ink (1), toni.ink (1) |
| claudia (Clàudia (ioga)) | Clàudia | charla | claudia.ink (6), gemma.ink (1) |
| imma (Imma) | Imma | D4 | imma.ink (12) |
| pau (Pau (esplai)) | Pau | D3 | biel.ink (4), esplai.ink (2), pau.ink (10), quim.ink (1) |
| clara (Clara (esplai)) | Clara | D2 | clara.ink (8), esplai.ink (1), voley.ink (1) |
| biel (Biel (esplai)) | Biel | D1 | biel.ink (13), clara.ink (3), esplai.ink (6), pau.ink (8) |
| quim (Quim (Ajuntament)) | Quim | D-5 | gemma.ink (3), marc.ink (3), pau.ink (1), quim.ink (8) |
| marc (Marc) | Marc | D-14 | festa.ink (2), marc.ink (12), pol.ink (1), quim.ink (3) |
| laura (Laura) | Laura | D-8 | carla.ink (4), festa.ink (1), gemma.ink (4), laura.ink (13), marc.ink (3), quim.ink (4) |
| conxita (Conxita (veïna)) | Conxita | D-8 | conxita.ink (7), enric.ink (3), laura.ink (1), marta.ink (1), ramon.ink (1), veins.ink (3) |
| enric (Enric (veí)) | Enric | D1 | enric.ink (13), veins.ink (5) |
| rafa (Rafa (autoescola)) | Rafa | D3 | autoescola.ink (1), hugo.ink (5), rafa.ink (11) |
| hugo (Hugo) | Hugo | D-10 | autoescola.ink (4), hugo.ink (11), rafa.ink (1) |
| dolors (Dolors (teatre)) | Dolors | D1 | dolors.ink (6), jan.ink (1), queralt.ink (1), teatre.ink (1) |
| queralt (Queralt) | Queralt | D-18 | dolors.ink (1), jan.ink (2), queralt.ink (12), teatre.ink (1) |
| jan (Jan) | Jan | D-18 | central.ink (2), dolors.ink (1), jan.ink (10), queralt.ink (3), teatre.ink (4) |
| anna (Anna (mare Martina)) | Anna | D4 | anna.ink (14) |
| silvia (Sílvia (mare Leo)) | Sílvia | charla | silvia.ink (6) |
| iris (Iris) | Iris | D3 | iris.ink (7), laia.ink (4), main.ink (1) |
| iker (+34 655 ·· ·· ··) | Iker | D-15 | central.ink (3), dani.ink (7), iker.ink (2), laia.ink (5) |
| guia (+34 639 ·· ·· ··) | Ignasi | D3 | iris.ink (2), laia.ink (14) |
| rosalia (+34 972 ·· ·· ··) | Rosalia | D-3 | central.ink (6), desconocido.ink (3), eric.ink (2), familia.ink (9), laia.ink (12), laura.ink (2), main.ink (2), mama.ink (4), nuria.ink (3), pep.ink (2), ramon.ink (3), rosalia.ink (2), roser.ink (6), toni.ink (2), xavier.ink (3) |
| desconocido (+34 6·· ··· ···) | prepago | D1 | laia.ink (4), mireia.ink (1) |
| banco (BancRural) | BancRural | D-10 | banco.ink (6) |
| casal (Rosa d'Abril 🌹) | Rosa | D-20 | arnau.ink (1), carla.ink (1), casal.ink (1), claudia.ink (1), imma.ink (3), ioga.ink (1), jordi.ink (1), laia.ink (8), marta.ink (3), quim.ink (1), silvia.ink (2), sonia.ink (1), veins.ink (1) |
