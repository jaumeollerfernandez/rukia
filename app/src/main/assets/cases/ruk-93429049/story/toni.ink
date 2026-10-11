// Toni, voluntario del Banc d'Aliments. Alicia y él subían lotes a las masías: sabe a qué casas iba (pista cierta).
// Confianza (fia_toni, ver main.ink): preguntarle por la ruta es un desliz (Alicia la sabe), así que la pone a prueba con la
// perra (Trufa, IMG_0391). Si acierta, confía y más adelante cuenta que la Rosalia la esperaba. Si falla, recela:
// se le puede mentir (no funciona si ya corre por el pueblo que alguien usa el móvil) o decirle la verdad (adulto
// sensato: colabora). Si no, no cuenta nada.
// Vida propia: repartidor en una cooperativa agrícola, la bici, el hambre perpetua. Sube solo los lotes mientras Alicia falta.

=== toni ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {fia_toni > -1} [toni perdona por el turno]
    tranqui ali. fàtima es una máquina 💪 #delay: 300
    pero me debes galletas del forn. dos paquetes #delay: 4
    -> opciones
* (ruta_pregunta) {dia >= 2 and fia_toni > -1} [toni, a qué masías subíamos los lotes?]
    me lo preguntas en serio? te has dado un golpe? jajaja #delay: 300
    a ver. cómo se llama la perra de la señora de arriba, que siempre le llevabas chuches? #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [trufa]
        ~ fia_toni = 1
        esa es mi ali 😂 #delay: 120
        -> ruta ->
    ** [rocky]
        -> recela ->
    ** [luna]
        -> recela ->
    ** [(sin responder)]
    - - -> opciones
* {fia_toni == -1} [toni soy yo. estoy fatal, no duermo y me lío con todo]
    {delatado >= 2:
        ~ fia_toni = -2
        ~ delata()
        ya. eso dice todo el mundo que escribe desde el móvil de ali #delay: 300
        no me escribas más #delay: 4
    - else:
        ~ fia_toni = 0
        ... #delay: 300
        vale. perdona. con todo lo que se dice, uno ya no sabe #delay: 5
        ~ sabe_ruta_lotes = true
        las de arriba de sant salvador, en la subida a bracons. can xic, les feixes y la de la perra #delay: 6
    }
    -> opciones
* {fia_toni == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_toni = 1
    «los mossos». por el móvil de ella #delay: 300
    mira. si es para encontrarla, me da igual quién seas #delay: 6
    -> ruta ->
    -> opciones
* {sabe_capsec and fia_toni > -1} [y a capsec subíamos?]
    a capsec? allí no vive nadie desde que murió la pepita #delay: 300
    solo está el refugi del esplai. y está vacío #delay: 4
    {ruta_pregunta:
        ali tú estás bien? que hoy me preguntas cosas que sabes de memoria #delay: 6
    }
    -> opciones
* {dia >= 2 and fia_toni > -1} [toni, qué donó mi madre?]
    cuatro cajas. mantas, una cafetera, un reloj de pared, fotos enmarcadas #delay: 300
    y tu ropa de vóley. lo sabías? #delay: 5
    las fotos no las podemos dar. te las guardo? #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [sí porfa, guárdamelas]
        hecho. hay una tuya de pequeña muy bonita #delay: 300
        con una señora mayor y una perra, delante de una puerta azul. y un pozo #delay: 5
    ** [no hace falta]
        como quieras #delay: 300
        las dejo en el almacén, en la estantería de arriba. por si cambias de idea #delay: 5
    ** [mi ropa de vóley?? por qué?]
        eso le pregunté. me dijo que «ya no la vas a necesitar» #delay: 300
        la he puesto aparte. no la doy hasta que me lo digas tú #delay: 5
    ** [(sin responder)]
    - - -> opciones
// Solo si confía: la Rosalia le dijo que «la nena» iba a quedarse con ella.
* {fia_toni >= 1 and dia >= 3} [toni, la señora de la perra te ha dicho algo de mí?]
    ~ sabe_rosalia = true
    el sábado subí yo solo el lote. la rosalia me preguntó por ti #delay: 300
    «la nena me dijo que vendría a quedarse unos días, que le haría compañía». me hizo gracia #delay: 6
    estás allí, verdad? tranquila. no se lo diré a nadie #delay: 8
    #caduca: D{dia + 1} 00:00
    ** [no se lo digas a nadie, toni. a nadie]
        a nadie. ni a lluïsa #delay: 120
        y si alguien pregunta, yo de masías no sé nada. solo de galletas 🙏 #delay: 5
    ** [no estoy allí]
        vale vale #delay: 120
        pues donde estés, que haya calefacción. allí arriba no hay ni luz #delay: 5
    ** [(sin responder)]
    - - -> opciones
// Con la mochila de la huida (IMG_0413: velas, cerillas, un cargador sin móvil): una masía sin luz.
* {foto_img_0413 and dia >= 2 and fia_toni > -1} [toni, a qué masías del valle no les llega la luz?]
    a casi todas les llega ya. a las de arriba de sant salvador, no #delay: 300
    {fia_toni >= 1:
        la de la señora de la perra va con velas y un generador que no arranca nunca. por eso le subías cerillas #delay: 6
    - else:
        por? te vas de acampada? 😂 #delay: 5
    }
    -> opciones
* {dia >= 2 and fia_toni > -1} [toni qué tal el curro?]
    repartiendo patatas por media garrotxa. hoy 300 kilos #delay: 300
    y la furgo pidiendo jubilación. como yo 😂 #delay: 4
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

= ruta
~ sabe_ruta_lotes = true
a las de arriba de sant salvador, en la subida a bracons. can xic, les feixes y la de la señora de la perra #delay: 6
a esa le subías también pan. y pienso, que la perra se comía media caja 😂 #delay: 5
->->

= recela
~ fia_toni = -1
... #delay: 120
ali no le ha llamado así en la vida #delay: 5
tú no eres ali. quién eres? #delay: 6
->->
