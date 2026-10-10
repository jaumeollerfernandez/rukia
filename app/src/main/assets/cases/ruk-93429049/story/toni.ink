// Toni, voluntario del Banc d'Aliments. Alicia y él subían lotes a las masías: sabe a qué casas iba (pista cierta).
// Confianza (fia_toni, ver main.ink): preguntarle por la ruta es un desliz (Alicia la sabe), así que la pone a prueba con la
// perra (Trufa, IMG_0391). Si acierta, confía y más adelante cuenta que la Rosalia la esperaba. Si falla, recela:
// se le puede mentir (no funciona si ya corre por el pueblo que alguien usa el móvil) o decirle la verdad (adulto
// sensato: colabora). Si no, no cuenta nada.

=== toni ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {fia_toni > -1} [toni perdona por el turno]
    tranqui ali. fàtima es una máquina 💪 #delay: 300
    -> opciones
* {dia >= 2 and fia_toni > -1} [toni, a qué masías subíamos los lotes?]
    me lo preguntas en serio? te has dado un golpe? jajaja #delay: 300
    a ver. cómo se llama la perra de la señora de arriba, que siempre le llevabas chuches? #delay: 6
    ** [trufa]
        ~ fia_toni = 1
        esa es mi ali 😂 #delay: 120
        -> ruta ->
    ** [rocky]
        -> recela ->
    ** [luna]
        -> recela ->
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
    -> opciones
* {dia >= 2 and fia_toni > -1} [toni, qué donó mi madre?]
    cuatro cajas. mantas, una cafetera, un reloj de pared, fotos enmarcadas #delay: 300
    y tu ropa de vóley. lo sabías? #delay: 5
    las fotos no las podemos dar. te las guardo? #delay: 4
    ** [sí porfa, guárdamelas]
        hecho. hay una tuya de pequeña muy bonita #delay: 300
        con una señora mayor y una perra, delante de una puerta azul. y un pozo #delay: 5
    ** [no hace falta]
        como quieras #delay: 300
    -- -> opciones
// Solo si confía: la Rosalia le dijo que «la nena» iba a quedarse con ella.
* {fia_toni >= 1 and dia >= 3} [toni, la señora de la perra te ha dicho algo de mí?]
    ~ sabe_rosalia = true
    el sábado subí yo solo el lote. la rosalia me preguntó por ti #delay: 300
    «la nena me dijo que vendría a quedarse unos días, que le haría compañía». me hizo gracia #delay: 6
    estás allí, verdad? tranquila. no se lo diré a nadie #delay: 8
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
