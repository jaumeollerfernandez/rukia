// Paula, de clase. La graciosa: café, memes, sarcasmo. Siembra dudas sobre Oriol (que sí dice la verdad).
// Despiste: también repite el rumor de Barcelona y cree que es «un drama de novios».
// Vida propia: llega tarde a todo y trabaja de camarera los sábados en la granja del Firal de Olot.

=== paula ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
// Saludos: uno u otro.
* (hola) {not perdon} [paula 💛]
    ALI estás viva!! #delay: 120
    qué pasa contigo tía #delay: 3
    #caduca: D{dia + 1} 00:00
    ** [nada, una semana rara]
        rara cómo. rara de novio o rara de familia #delay: 120
        no contestes. ya me lo dirás con un café ☕ #delay: 4
    ** [estoy mala]
        mala de verdad o mala de no quiero ir a clase #delay: 120
        no juzgo eh. yo estoy mala de lo segundo desde septiembre #delay: 4
    ** [(sin responder)]
    - - -> opciones
* (perdon) {dia >= 2 and not hola} [paula perdona que no conteste]
    perdonada. a cambio de un café #delay: 120
    y de una explicación. el café primero ☕ #delay: 4
    -> opciones
* {dia >= 3 and dia <= 5} [paula has visto el comentario de oriol?]
    lo de la tía de bracons? #delay: 120
    oriol ve fantasmas. el año pasado juró que había visto un lobo y era un perro de pastor #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [y si es verdad?]
        pues sería la primera vez #delay: 120
        bueno. aina le cree. pero aina se cree hasta los horóscopos #delay: 5
    ** [jajaja el lobo]
        EL LOBO. lo contó una semana entera #delay: 120
    ** [(sin responder)]
    - - -> opciones
* [paula llegas tarde a clase?]
    literal estoy corriendo con el café en la mano #delay: 120
    voy tarde voy tarde voy tarde #delay: 2
    -> opciones
* {dia >= 2} [qué se dice de mí en clase?]
    de todo tía #delay: 120
    que te has ido a barna con un tío. que estás en girona. que te ha abducido un ovni #delay: 4
    yo voto ovni 👽 #delay: 3
    #caduca: D{dia + 1} 00:00
    ** [qué tío?]
        ~ dani_sospechoso = true
        el de la moto. dani o algo. carla lo cuenta como si lo hubiera visto #delay: 120
    ** [estoy bien, de verdad]
        vale vale. pero me debes un café y una explicación ☕ #delay: 120
    ** [y tú qué crees?]
        yo creo que si te hubieras ido con un tío me lo habrías dicho para darme envidia #delay: 120
        así que ovni #delay: 3
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [paula y sergi qué tal?]
    sergi es como google pero borde #delay: 120
    está haciendo tu parte de la exposición y suspira cada cinco minutos para que lo oigamos #delay: 4
    -> opciones
* {dia >= 2} [qué tal en la granja del firal?]
    los sábados ahí son la guerra. melindros, chocolate y señoras pidiendo el café «templadito» #delay: 120
    el otro día vino la nuri sola, a vuestra mesa. ni se pidió los melindros #delay: 6
    -> opciones
* {dia >= 4} [paula me echas de menos?]
    un poco. no te emociones #delay: 120
    ponte buena ali 💛 #delay: 3
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
