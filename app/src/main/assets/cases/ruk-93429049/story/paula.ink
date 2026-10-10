// Paula, de clase. La graciosa: café, memes, sarcasmo. Siembra dudas sobre Oriol (que sí dice la verdad).
// Despiste: también repite el rumor de Barcelona y cree que es «un drama de novios».

=== paula ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [paula 💛]
    ALI estás viva!! #delay: 120
    qué pasa contigo tía #delay: 3
    -> opciones
* {dia >= 3 and dia <= 5} [paula has visto el comentario de oriol?]
    lo de la tía de bracons? #delay: 120
    oriol ve fantasmas. el año pasado juró que había visto un lobo y era un perro de pastor #delay: 5
    -> opciones
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
        el de la moto. dani o algo. carla lo cuenta como si lo hubiera visto #delay: 120
        ~ dani_sospechoso = true
    ** [estoy bien, de verdad]
        vale vale. pero me debes un café y una explicación ☕ #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [paula y sergi qué tal?]
    sergi es como google pero borde #delay: 120
    está haciendo tu parte de la exposición y suspira cada cinco minutos para que lo oigamos #delay: 4
    -> opciones
* {dia >= 4} [paula me echas de menos?]
    un poco. no te emociones #delay: 120
    ponte buena ali 💛 #delay: 3
    -> opciones
+ [(sin responder)]
    -> charla
