// Toni, voluntario del Banc d'Aliments. Alicia y él subían lotes a las masías: sabe a qué casas iba (pista cierta).

=== toni ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [toni perdona por el turno]
    tranqui ali. fàtima es una máquina 💪 #delay: 300
    -> opciones
* {dia >= 2} [toni, a qué masías subíamos los lotes?]
    ~ sabe_ruta_lotes = true
    me lo preguntas en serio? te has dado un golpe? jajaja #delay: 300
    a las de arriba de sant salvador, en la subida a bracons. can xic, les feixes y la de la señora de la perra #delay: 6
    a esa le subías también pan. y pienso, que la perra se comía media caja 😂 #delay: 5
    -> opciones
* {sabe_capsec} [y a capsec subíamos?]
    a capsec? allí no vive nadie desde que murió la pepita #delay: 300
    solo está el refugi del esplai. y está vacío #delay: 4
    -> opciones
* {dia >= 2} [toni, qué donó mi madre?]
    cuatro cajas. mantas, una cafetera, un reloj de pared, fotos enmarcadas #delay: 300
    y tu ropa de vóley. lo sabías? #delay: 5
    las fotos no las podemos dar. te las guardo? #delay: 4
    ** [sí porfa, guárdamelas]
        hecho. hay una tuya de pequeña muy bonita #delay: 300
        con una señora mayor y una perra, delante de una puerta azul. y un pozo #delay: 5
    ** [no hace falta]
        como quieras #delay: 300
    -- -> opciones
+ [(sin responder)]
    -> charla
