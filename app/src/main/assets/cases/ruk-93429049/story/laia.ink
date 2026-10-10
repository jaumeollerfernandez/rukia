// Sargento Laia Puig, Mossos d'Esquadra (Olot). Canal cifrado con el jugador: Kimo, hacker de la élite del CNI a quien la
// policía llama cuando un caso ya no tiene salida. Nunca ha dejado uno sin resolver. Laia ha pedido su ayuda para este.
// Le habla de tú y nunca le asigna género.

=== laia ===
// Abierto por la tarde o la noche, el caso empieza al día siguiente y hoy es el D0 (dia = 0):
// Laia presenta el caso esta misma noche y traza la estrategia; el D1 a las 10:00 solo da la salida.
{dia < 1: -> d0}
-> d1

= d0
Conexión establecida. Canal cifrado. #effect: glitch
-> presentacion ->
-> tutorial ->
-> estrategia ->
Buenos días. Son las diez. #at: D1 10:00
Desde ahora el móvil de Alicia está despierto. Su gente va a empezar a escribir. #delay: 4
Lee antes de escribir. Y escribe como ella. #delay: 4
Te escribo esta noche para el parte. #delay: 3
-> d1_noche

= d1
Conexión establecida. Canal cifrado. #at: D1 10:00 #effect: glitch
-> presentacion ->
-> tutorial ->
Te escribo esta noche para el parte. #delay: 3
-> d1_noche

// El encargo: quién es Alicia, las normas y la prisa (el tiempo corre). Sirve para la noche del D0 y para la mañana del D1.
= presentacion
Soy la sargento Laia Puig. Mossos d'Esquadra, comisaría de Olot. #delay: 4
Me ha costado mucho que el CNI te prestara, Kimo. Dicen que nunca has dejado un caso sin cerrar. #delay: 5
Te llamo. Contesta. #delay: 3 #call: audio/laia_encargo.m4a
Te lo dejo también por escrito. #delay: 6
Alicia Serra Vidal. 18 años. Vive en L'Hostalnou de Bianya con su madre, Montse, y su hermana mayor, Berta. #delay: 4
{dia < 1:Desapareció anoche.|Desapareció anteanoche.} Se dejó el móvil en casa, cargando. Este móvil. #delay: 4
{dia < 1:La madre ha venido esta tarde a denunciarlo.|La madre vino ayer a denunciarlo.} Sola, sin hacer ruido. Pidió que no saliera en ningún sitio. #delay: 5
No quiso dejarnos el teléfono. Dice que no saben el PIN. #delay: 4
Puede ser. Pero no me gustó cómo me miraba. #delay: 4
Oficialmente esto no existe. Y tú tampoco. #delay: 4
Normas. #delay: 3
Una: eres Alicia. Escribes como ella. Si alguien sospecha, se acabó. #delay: 3
Dos: el móvil sigue en su casa. Si la madre ve la pantalla encenderse, se acabó. Cuidado con el grupo familiar. #delay: 5
Tres: me lo cuentas todo a mí. A nadie más. #delay: 4 #caduca: D1 23:59
* [Entendido. ¿Por dónde empiezo?]
    Por su gente. Amigas, el ex, el trabajo. Lee antes de escribir. #delay: 5
    Fíjate en cómo escribe ella. Las amigas notan esas cosas. #delay: 4
* [¿Por qué yo y no un equipo?]
    Porque un equipo deja papeles. #delay: 4
    Y porque cuando a un caso ya no le queda nadie, se llama a Kimo. #delay: 3
* [¿Cuánto cobro?]
    Lo hablamos cuando aparezca. Viva. #delay: 5
* [(sin responder)]
    Doy por hecho que lo has leído. #delay: 2
- Otra cosa. #delay: 4
En una desaparición, las primeras horas son las que más valen. Y ya hemos perdido unas cuantas. #delay: 5
Hay que encontrarla cuanto antes. El tiempo corre. #delay: 3
->->

// Cómo funciona el móvil de Alicia. Se cuenta una sola vez, en el primer contacto (la noche del D0 o la mañana del D1).
= tutorial
Antes de empezar, cómo funciona esto. #delay: 4
Chats: ahí lees y escribes como Alicia. Cuando te toque contestar, eliges entre varias frases. Elige con cuidado: lo que dices ya no se borra. #delay: 6
Si no contestas a tiempo, la conversación sigue sin ti. #delay: 4
Gonpi y Multimedia: sus publicaciones y su galería. Mira fechas, lugares y quién sale en cada foto. #delay: 6
Y la app de Policía: aquí está la clave. #delay: 4
Dentro hay «Resolver el caso». Marcas en el mapa dónde crees que está Alicia, eliges el tamaño de la zona y mandas un equipo. #delay: 6
Solo un intento al día, y el equipo tarda unos minutos en volver con el resultado. #delay: 5
Si Alicia no está en la zona, solo sabremos que has fallado. Si está dentro, te diré con qué precisión. Cuanto más pequeña la zona, más puntos. #delay: 6
Con una zona de unos 500 metros o menos, damos con ella. #delay: 4
Hay un buscador para llegar rápido a un pueblo o una comarca. #delay: 4
Por tanto: no desperdicies los intentos, pero no los dejes pasar. Cada noche, con el parte, te recordaré si hoy no has probado. #delay: 6
->->

// Recordatorio del parte de la noche [n]: si ese día no ha mandado ninguna búsqueda desde la app de Policía. Repite el #caduca de [limite]: el motor lo pierde al reanudar tras la línea con #at.
= recordatorio(n, limite)
{ultimo_intento < n:
    {&Por cierto, hoy no has mandado ninguna búsqueda. Policía → Resolver el caso.|Hoy no has usado tu intento. Un intento por día, y no se acumula.|Sin búsqueda hoy. Aunque sea a ojo, marca una zona: un fallo también descarta sitios.} #delay: 4
}
#caduca: {limite}
->->

// Solo la noche del D0: por qué esperar a mañana y qué hacer mientras tanto.
= estrategia
Una cosa más. Ya es tarde. #delay: 5
A estas horas Alicia no le escribe a nadie. Si su móvil se pone a hablar de noche, la madre lo notará. #delay: 5
Así que esta noche no escribes. Lees. #delay: 3
El plan. #delay: 3
Uno: repasa todos sus chats, de arriba abajo. Cómo escribe, con quién habla, qué dejó pendiente. #delay: 5
Dos: Gonpi y su galería de fotos. La gente cuenta mucho más de lo que cree. #delay: 5
Tres: mañana a las diez el móvil «se despierta». Empieza por su gente: amigas, el ex, el trabajo. #delay: 5
Cuatro: el grupo familiar, ni tocarlo. Se lee, no se escribe. #delay: 4
Y cada noche, a las 21:30, me pasas el parte. #delay: 4 #caduca: D1 09:00
* [¿Y si alguien le escribe esta noche?]
    Alicia está dormida. Ni lo abras. #delay: 6
    Mañana contestas, como haría ella. #delay: 3
* [Entendido. Mañana a las diez.]
    Bien. #delay: 3
* [(sin responder)]
    Lo tomo como un sí. #delay: 2
- Duerme algo. Mañana empezamos. #delay: 4
->->

= d1_noche
¿Algo para el parte de hoy? #at: D1 21:30 #caduca: D2 08:00
-> recordatorio(1, "D2 08:00") ->
* [Aún nada claro.]
    Normal. El primer día todo el mundo miente un poco. #delay: 6
* [La madre y la hermana hablan raro en su grupo.]
    Raro cómo. #delay: 4
    ** [Van a un grupo de duelo. Un tal Ignasi acompañó a la madre a comisaría.]
        Sí. Barba blanca, muy educado. Habló él más que la madre. #delay: 8
        Pero un grupo de duelo no es delito, y acompañar a alguien a comisaría tampoco. #delay: 5
        Si encuentras algo más que un nombre de pila, me lo pasas. #delay: 3
    ** [No sé. Parece una secta.]
        Las sectas no se denuncian solas. Necesito nombres. #delay: 5
* [Su padre cree que está en casa de una amiga.]
    Eso le dijo la madre, ¿no? #delay: 4
    A nosotros nos dijo que no tenía ni idea de dónde estaba. #delay: 4
    Alguien miente. #delay: 2
* [(sin responder)]
    Silencio. Lo tomaré como un «nada». #delay: 2
- -> consulta("D2 08:25") ->
Descansa. Mañana más. #delay: 3
-> d2

= d2
Buenos días. #at: D2 08:30
La madre llamó anoche a comisaría. Dice que ha encontrado en el ordenador de Alicia la compra de un billete de bus a Barcelona. #delay: 5
Quiere que la busquemos allí. Con el padre. #delay: 4
Muy oportuno. #delay: 3
¿Tienes algo antes de que mueva a nadie? #delay: 4 #caduca: D2 14:00
* [Pol, su ex, la llevó anteanoche a la estación de Olot.]
    Eso encaja con el billete. Pero no me fío de lo que encaja tan bien. #delay: 8
    -> camaras
* [En su galería hay un correo: canceló el billete esa misma noche.]
    ~ descarta_bus = true
    Lo compró y lo canceló antes de ir a la estación. #delay: 20
    No pensaba coger ese bus. Quería que lo creyéramos. #delay: 4
    -> camaras
* (a_barcelona) [Puede que esté en Barcelona, sí.]
    De acuerdo. Pido a los compañeros de Barcelona que pasen por casa del padre. #delay: 10
    Si te equivocas, perdemos un día. Y no nos sobran. #delay: 4
    -> d2_noche
* (a_barcelona_sin) [(sin responder)]
    Entonces hago caso a la madre. Barcelona. #delay: 1
    -> d2_noche

= camaras
Pido las cámaras de la estación de autobuses. Dame unas horas. #delay: 6
Cámaras de la estación de Olot, anteanoche. #at: D2 16:10
23:41. Un coche gris la deja delante. Lleva una mochila y una bolsa de tela grande. #delay: 4
23:55. Sale el bus de Barcelona. Ella no sube. #delay: 4
23:58. Se va andando, hacia la salida de Bianya. #delay: 4
Después, nada. No hay más cámaras por esa zona. #delay: 3
~ descarta_bus = true
No se fue a Barcelona. Se quedó en el valle. #delay: 5
Y la madre o lo sabe o quiere que no lo sepamos. Una de dos. #delay: 4
-> d2_noche

= d2_noche
¿Algo más para el parte? #at: D2 21:30 #caduca: D3 08:00
-> recordatorio(2, "D3 08:00") ->
* [La madre ha vaciado la cuenta de Alicia. Dice que para pagar a Hacienda.]
    Con la madre de cotitular. Legal. #delay: 8
    Y una deuda con Hacienda después de vender un piso heredado es de lo más normal. #delay: 4
    Feo, pero no me sirve. #delay: 3
* (cra) [Dicen que es para Hacienda. Pero en el banco el beneficiario es «CRA Serveis».]
    ~ sabe_cra = true
    Hacienda no se llama CRA. Ni cobra por transferencia a la cuenta de nadie. #delay: 8
    Alguien le ha contado a la familia una cosa y el banco dice otra. #delay: 4
    Mañana te digo quién es CRA. #delay: 3
* [La madre le cuenta a cada uno una versión distinta.]
    Al padre, una amiga. A nosotros, que no sabe nada. #delay: 6
    Esa mujer no busca a su hija. Controla lo que se sabe de ella. #delay: 4
* [Nada nuevo.]
    Cada día sin noticias juega en contra. No me digas «nada nuevo» muchas veces. #delay: 5
* [(sin responder)]
    Otra noche en silencio. Espero que estés trabajando. #delay: 1
- -> consulta("D3 08:40") ->
Mañana más. #delay: 3
-> d3

= d3
Buenos días. #at: D3 08:45
{descarta_bus:
    He mandado una patrulla a peinar la salida de Olot hacia Bianya. Nada. Ni una cámara de comercio, ni un testigo. #delay: 5
- else:
    Barcelona: nada. El padre no sabe dónde está su hija. Ahora está asustado y llamando a la madre a gritos. #delay: 5
    Hemos perdido un día. #delay: 3
}
{d2_noche.cra:
    Lo del banco. CRA Serveis es el nombre comercial de la Associació Comunitat Rosa d'Abril. Sant Joan les Fonts. Sede: un mas reformado. Lo llaman el Casal. #at: D3 12:10
    Grupos de duelo, mindfulness, retiros. Cuotas, no. Aportaciones voluntarias. Muchas. Hacienda no aparece por ningún lado. #delay: 6
    Presidente: Ignasi Coll Ferrer, 61 años. Una denuncia por estafa hace un año, archivada. #delay: 5
    Ese es el Ignasi del que hablan. Se anuncia como «acompañante». #delay: 3
}
Otra cosa. Barcelona ha desarticulado esta semana parte de una red que captaba chicas de pueblo por redes sociales. #at: D3 13:40
Ofertas de trabajo de noche, piso pagado, «no se lo digas a tu familia». Las recogían en la estación de Nord. #delay: 5
No digo que sea esto. Pero si alguien le ofreció trabajo en Barcelona, quiero saberlo. #delay: 4
Mi comisario me pregunta por qué pierdo el tiempo con una mayor de edad que se ha ido de casa. #at: D3 15:30
Le he dicho que es intuición. No le ha hecho gracia. #delay: 4
Necesito algo sólido pronto. #delay: 3
-> d3_noche

= d3_noche
Parte. ¿Qué tienes? #at: D3 21:30 #caduca: D4 08:00
-> recordatorio(3, "D4 08:00") ->
* {sabe_estrellas} [Su mejor amiga dice que iría «donde vimos las estrellas»: la casa sin luz de una amiga de su abuela.]
    Una casa sin luz en el valle. #delay: 10
    En la Vall de Bianya hay más de cien masías, y la mitad sin luz. #delay: 4
    Pero es lo primero que suena a plan y no a huida. Sigue por ahí. #delay: 5
* {sabe_pan} [Se llevó pan de la panadería. Seis barras. Para alguien más.]
    Pan para alguien que no puede bajar a comprarlo. #delay: 8
    Alguien mayor. O alguien escondido. O las dos cosas. #delay: 4
* [La jefa de la panadería habla de una tal Rosalia. Alicia le sube pan.]
    ~ sabe_rosalia = true
    Rosalia. #delay: 8
    ¿Apellido? ¿Dirección? #delay: 3
    Nada, ¿no? Lo busco en el padrón. Rosalias de más de setenta años en la Vall de Bianya no habrá muchas. #delay: 6
* [Un compañero de clase la vio andando de noche por la carretera de Bracons.]
    Bracons. #delay: 8
    Esa carretera sube hasta la collada. Pocas casas, mucho bosque. #delay: 4
    Si iba andando con peso, no pudo ir muy lejos. #delay: 4
* (iker_parte) {iker.d2} [Un tal Iker, amigo de Dani, le ofrece trabajo en Barcelona: piso pagado y «trae el DNI».]
    ~ dani_sospechoso = true
    Piso pagado, recogida en Nord, el DNI. Es el mismo patrón. #delay: 8
    Paso el número a Barcelona. Y a ese Dani no lo pierdas de vista. #delay: 4
* [Nada nuevo.]
    Otro día perdido. El tiempo corre. #delay: 5
* [(sin responder)]
    ... #delay: 1
- -> consulta("D4 09:25") ->
Mañana más. #delay: 3
-> d4

= d4
{nuria_denuncia:
    Esta mañana ha venido a comisaría una tal Núria Gil. #at: D4 09:30
    Dice que alguien que no es Alicia está usando su móvil. #delay: 4
    La he atendido yo. Le he dicho que lo estamos investigando. #delay: 4
    Me ha mirado como si la sospechosa fuera yo. #delay: 4
    Con ella te has quemado. No le escribas más. #delay: 4
}
{d3_noche.iker_parte:
    Barcelona me ha contestado. El número de Iker es de un relaciones públicas de un club del Port Olímpic. #at: D4 11:30
    El club sale en la investigación de la red. Él, de momento, no está imputado. #delay: 5
    Y adivina quién le pasó el número de Alicia. #delay: 4
}
{sabe_rosalia:
    Padrón. Rosalia Masó Puig, 78 años, empadronada en Sant Salvador de Bianya. #at: D4 10:15
    Dirección: «Disseminat». Ni calle ni número. #delay: 4
    En ese diseminado hay unas cuarenta masías. Desde aquí no sé cuál es la suya. #delay: 5
}
Cada día que pasa, más difícil. Mañana pido permiso para mover a gente. No te prometo nada. #at: D4 15:00
-> d4_noche

= d4_noche
Parte. #at: D4 21:30 #caduca: D5 08:00
-> recordatorio(4, "D5 08:00") ->
* [Me ha llamado una anciana desde un fijo. Preguntaba por «la nena» y por el pan.]
    ~ sabe_rosalia = true
    Dame un minuto. #delay: 10
    Fijo a nombre de Josep Pujol Vila, fallecido en 2009. Sant Salvador de Bianya, diseminado. #delay: 90
    Su viuda: Rosalia Masó. #delay: 4
    Ya tengo la parroquia y el nombre. Me falta la casa. #delay: 5
    Y no puedo mandar patrullas a llamar a cuarenta puertas sin una orden y sin que la madre se entere. #delay: 5
    Necesito que me digas cuál. #delay: 3
* [En Gonpi, una chica del Casal, Iris, ha publicado algo raro. Como si dudara.]
    Las sectas siempre tienen grietas. #delay: 8
    Si alguien de dentro duda, cuídala. Y no la quemes. #delay: 4
* [Nada nuevo.]
    El reloj no se para. Nosotros tampoco deberíamos. #delay: 5
* [(sin responder)]
    ... #delay: 1
- -> consulta("D5 08:25") ->
Mañana más. #delay: 3
-> d5

= d5
Tengo permiso para dos agentes. Dos. Y a partir de mañana. #at: D5 08:30
Mañana te pediré que me digas dónde mirar. Piensa bien qué me vas a decir. #delay: 5
¿Algo de anoche? #delay: 4 #caduca: D5 14:00
* {desconocido.verdad} [Alicia me escribió anoche desde un número prepago.]
    ¿Alicia? ¿Está viva? #delay: 5
    Dame el número. #delay: 3
    Prepago sin registrar. Lo compraron en un estanco de Olot hace un mes. #delay: 120
    Anoche, a las 23:40, se conectó a la antena de la Collada de Bracons. #delay: 5
    Bajo esa antena, en el diseminado de Sant Salvador, hay once masías. Once, no cuarenta. #delay: 6
* {iris.d4} [Una chica del Casal, Iris, dice que les han hecho firmar que lo donan todo «en caso de tránsito».]
    ~ sabe_secta = true
    «En caso de tránsito». #delay: 10
    Eso ya no es una estafa. Es otra cosa. #delay: 4
    Hoy mismo hablo con fiscalía. #delay: 4
* [Nada.]
    Vale. #delay: 3
* [(sin responder)]
- -> d5_noche

= d5_noche
Parte. ¿Qué tienes? #at: D5 21:45 #caduca: D6 08:00
-> recordatorio(5, "D6 08:00") ->
* [El grupo de la madre sube al cráter de Santa Margarida al alba. De blanco y sin móviles.]
    Ahí estaré yo. Con todo lo que me dejen llevar. #delay: 8
    Pero si antes no sacamos a Alicia de donde esté, de poco servirá. #delay: 5
* [Una furgoneta blanca del Casal va parando en cada mas camino de Bracons.]
    Entonces van por delante de nosotros. #delay: 8
    Mañana a primera hora quiero un sitio. Uno. #delay: 4
* {sabe_puerta_azul} [La casa tiene la puerta azul.]
    Una puerta azul. #delay: 8
    Lo apunto. No voy a mandar a nadie a mirar puertas sin saber dónde están. #delay: 5
* [Nada nuevo.]
    «Nada nuevo» no me sirve. Cada hora cuenta. #delay: 4
* [(sin responder)]
- -> consulta("D6 07:55") ->
Mañana es el día. Duerme algo. #delay: 3
-> d6

// D6: el jugador decide dónde van los agentes desde la app de Policía (police/actions.json).
// Los informes llegan al chat «Central» (central.ink) y activan patrulla_en_mas / vigilancia_crater.
= d6
Hoy es el día. #at: D6 08:00
Tengo dos agentes y un coche hasta mediodía. Y otra salida esta noche, la última. #delay: 4
Ya tienes acceso a la app de la Policía. Desde ahí decides adónde van. #delay: 5
Los informes te llegarán por el canal de la Central. Una salida, un sitio. Elige bien. #delay: 4
-> d6_apagon

= d6_apagon
⚠️ Conexión perdida con el terminal. #at: D6 18:00 #effect: blackout
✅ Conexión restablecida. #at: D6 22:00
Cuatro horas a oscuras. ¿Sigues ahí? #at: D6 22:05
Queda la última salida. Al amanecer solo podemos estar en un sitio. #delay: 5
{patrulla_en_mas:
    El agente sigue en el mas, vigilando el camino. A ese no lo muevo. #delay: 4
}
Tú decides, desde la app. Yo iré con ellos. #delay: 4
Mañana amanece. #delay: 3
-> d7

// D7: el amanecer. Todo se resuelve a las 06:30 (ver las funciones del final de main.ink).
= d7
{vigilancia_crater:
    En posición en el cráter. Niebla. No se ve ni la ermita. #at: D7 05:10
    Suben linternas por el sendero. Diez, doce personas. Todas de blanco. #at: D7 06:05
- else:
    No he dormido. #at: D7 05:10
    {iris_ayuda:
        Iris Ferrer se presentó en comisaría a las tres de la madrugada. Ha declarado todo. #at: D7 05:40
        Fiscalía ha mandado una unidad al cráter. Van justos. #delay: 5
    }
}
{
- familia_salvada() and (rescatada() or capturada()): -> final_bueno
- rescatada(): -> final_alicia_sola
- capturada(): -> final_malo
- else: -> final_escondida
}

// Alicia a salvo y la ceremonia parada. Si la secta la había encontrado, la rescatan en el mismo cráter.
= final_bueno
06:31. Entramos. #at: D7 06:31
Ignasi Coll, detenido. Las botellas, requisadas. Ya veremos qué llevaban. #delay: 30
Montse y Berta están bien. Asustadas. Berta grita que les hemos robado la luz. #delay: 8
{capturada():
    Y entre ellos estaba Alicia. La traían de la mano su madre y su hermana, con la ropa blanca encima del jersey. #delay: 10
    Está bien. Temblando, pero bien. #delay: 4
- else:
    Y Alicia está en el coche patrulla de Bianya, con una manta y una perra que no se separa de ella. #delay: 10
}
Alguien quiere hablar contigo. #delay: 20 #call: audio/alicia_final.m4a
Buen trabajo. De verdad. #delay: 60
Esta conversación nunca ha existido. Borra lo que tengas que borrar. #delay: 5
-> fin

// Alicia a salvo, pero nadie ha parado la ceremonia.
= final_alicia_sola
Alicia está a salvo. Está conmigo. #at: D7 06:31
Pero en el cráter no había nadie nuestro. Cuando han llegado los primeros, ya había amanecido. #delay: 10
Hay ambulancias. Muchas. #delay: 6
Su madre y su hermana están entre los que se han llevado al hospital. Vivas. De momento. #delay: 10
Ignasi Coll no estaba. Ya lo encontraremos. #delay: 6
Alguien quiere hablar contigo. #delay: 30 #call: audio/alicia_final.m4a
Hiciste lo que pudiste. Yo también. No basta, pero es lo que hay. #delay: 60
-> fin

// La secta encontró a Alicia y nadie ha parado la ceremonia.
= final_malo
Hemos llegado tarde. #at: D7 06:45 #effect: hacked
En el cráter solo quedaba la niebla. Y la ropa blanca doblada en la ermita. #delay: 10
No sabemos dónde están. Ni Montse, ni Berta, ni Alicia. #delay: 6
Lo siento. #delay: 10
-> fin

// Nadie la encontró: ni la policía ni la secta. Sigue escondida en el bosque.
= final_escondida
{familia_salvada():
    06:31. Entramos. Ignasi Coll, detenido. Montse y Berta, a salvo. #at: D7 06:31
    De Alicia, nada. Sigue escondida en algún sitio del valle. #delay: 8
    Si te escribe, dile que ya puede volver. #delay: 5
- else:
    Hay ambulancias en el cráter. Muchas. #at: D7 06:45
    Su madre y su hermana están entre los que se han llevado al hospital. Vivas. De momento. #delay: 10
    Y Alicia sigue en algún sitio del valle. Sola. #delay: 6
}
-> fin

// Tras cada parte: una comprobación para el día siguiente. Solo una por noche, y cada pregunta se puede hacer una vez.
// Las opciones salen según lo que el jugador haya averiguado en otros chats. [limite]: hasta cuándo espera Laia.
= consulta(limite)
{delatado >= 1 and not aviso_delatado:
    ~ aviso_delatado = true
    Antes de nada. Me llega que en Olot se comenta que alguien escribe desde el móvil de Alicia. #delay: 4
    Quien te ha pillado ya no te va a contar nada. Y lo hablarán. Ve con cuidado: si llega a la madre, se acabó. #delay: 5
}
{
- dia <= 1: Antes de cerrar. Mañana tengo una hora de un agente para comprobar algo. Una cosa. ¿Qué miro? #delay: 4
- else: {~¿Quieres que compruebe algo? Una cosa, no más.|Una consulta para mañana. Elige bien.|Tengo un hueco para una comprobación. ¿Qué miro?} #delay: 4
}
#caduca: {limite}
* {sabe_residencia or dia >= 3} [Comprueba si en alguna residencia de Olot hay una tal Rosalia.]
    ~ residencia_falsa = true
    Hecho. #delay: 300
    En las tres residencias de Olot, ninguna Rosalia. Ni ahora ni en los últimos diez años. #delay: 5
    Quien te lo haya dicho, o se equivoca o miente. #delay: 4
* {sabe_capsec} [¿De quién es Can Pericot, un mas abandonado de Capsec?]
    Era de Josepa Pericot. Murió hace seis años. Los sobrinos se lo cedieron al Ayuntamiento. #delay: 400
    Ahora lo usa el esplai del pueblo como refugio. Las llaves las tienen los monitores. #delay: 5
    Una casa vacía, sin luz, y Alicia es monitora. No lo descarto. #delay: 4
* {dani_sospechoso} [Mira la coartada de un tal Dani, el de la moto. La agobiaba mucho.]
    ~ dani_descartado = true
    Daniel Rius, 22 años, Olot. Una multa por velocidad. #delay: 400
    Y una denuncia archivada por tenencia de hachís, hace un año. En comisaría lo conocen: menudeo, nada serio. #delay: 5
    {dani_droga:
        Me cuentas lo que ya sabía. Un camello de barrio no secuestra a nadie, Kimo. #delay: 5
    }
    La noche que ella se fue, pagó con tarjeta en Barcelona a las 23:50 y a las 02:10. La segunda, en un club del Port Olímpic. #delay: 5
    Aquí no estaba, no. Pero en Barcelona sí. La misma noche que el billete de ella. #delay: 4
* {sabe_girona and not girona_descartado} [Mira si Alicia fue a una clínica de Girona hace unos días.]
    Datos clínicos, ni con orden tan rápido. Pero la cámara de la entrada del centro de salud sexual de Girona, sí. #delay: 400
    Hace nueve días, 10:12. Alicia entra con otra chica, con capucha. Salen juntas a las 12:40. #delay: 5
    Quién era la paciente, no me lo van a decir. #delay: 4
    Si está embarazada, cambia el caso. Una chica así no se esconde en el monte. Busca médicos cerca. #delay: 5
* {sabe_prepago} [Alicia compró un móvil prepago en un estanco de Olot hace un mes.]
    Sin el número no lo puedo rastrear. Y en el estanco no piden nombre. #delay: 300
    Si algún día te escribe un número que no conoces, apúntalo y me lo pasas. Al momento. #delay: 4
* {sabe_secta and not sabe_cra} [¿Qué es «Rosa d'Abril», el grupo de duelo de la madre?]
    ~ sabe_cra = true
    Associació Comunitat Rosa d'Abril. Sant Joan les Fonts. Nombre comercial: CRA Serveis. #delay: 400
    Grupos de duelo, mindfulness, retiros. Aportaciones voluntarias. #delay: 5
    Presidente: Ignasi Coll Ferrer, 61 años. Una denuncia por estafa hace un año, archivada. #delay: 5
    Por ahora, nada que pueda llevar a un juez. #delay: 3
* {dia >= 4 and sabe_cra} [La madre ha firmado «las voluntades anticipadas» en «el despacho de siempre». ¿Qué firmó?]
    ~ sabe_secta = true
    El registro de voluntades anticipadas es de Salud. Me deben un favor. #delay: 400
    Montse Vidal. Documento registrado hoy. Testigos: Ignasi Coll Ferrer y Berta Serra. #delay: 5
    Pide que no la reanimen. Pase lo que pase. #delay: 5
    Y hoy mismo, en una notaría de Olot, un testamento. Heredera universal: la Associació Comunitat Rosa d'Abril. #delay: 6
    La hermana, lo mismo. En primavera. #delay: 4
    Una mujer sana de 52 años que no quiere que la reanimen y lo deja todo a su grupo de duelo. La misma semana. #delay: 6
    Eso ya no es una estafa, Kimo. #delay: 4
* {dia >= 4} [¿De quién es el coche gris con una rosa dorada que ronda su casa?]
    ~ sabe_secta = true
    ~ sabe_cra = true
    Seat gris a nombre de CRA Serveis. Es la Associació Comunitat Rosa d'Abril, de Sant Joan les Fonts. #delay: 300
    Tres multas de aparcamiento en Olot. Lo conduce Ignasi Coll. #delay: 5
    Y anteanoche lo pararon en un control de Sant Joan les Fonts a las tres de la madrugada. Volvía del valle. #delay: 5
+ [Nada por ahora.]
    Vale. #delay: 3
+ [(sin responder)]
- ->->

= fin
// Fin del caso.
-> DONE
