// Carla, del grupo «Las de siempre». Cotilla y leal. Repite todos los rumores de Olot (pista falsa de Dani y Barcelona)
// y es la única que ve venir a Laura. Si Núria ha dicho que no eres Ali, no suelta nada.
// Vida propia: estudia Estética (prácticas de cejas), organiza el cumple de Mireia (D3) y tiene el top negro de Alicia.

=== carla ===
-> historial

= historial
ali me dejas tu top negro para el finde #at: D-8 17:30
vale pero me lo devuelves #from: me #at: D-8 18:02
obvio 😇 #at: D-8 18:02
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [carla 🥺]
    {nuria_denuncia:
        nuri dice que no eres ali #delay: 120
        no me escribas más #delay: 3
    - else:
        ALI #delay: 60
        contesta en el grupo tía que nos tienes a todas locas #delay: 3
    }
    -> opciones
* {not nuria_denuncia} [has oído algo de mí por ahí?]
    ~ dani_sospechoso = true
    en olot dicen de todo #delay: 120
    que te has ido a barcelona con el dani ese de la moto #delay: 4
    el que dicen que pasa costo en el bar de la plaza #delay: 5
    que estás embarazada. que te ha metido tu madre en lo de las meditaciones esas #delay: 5
    yo digo que no, que tú nunca harías nada sin contárnoslo #delay: 6
    ...no? #delay: 10
    #caduca: D{dia + 1} 00:00
    ** [lo de dani no es verdad]
        ya lo sé tía #delay: 120
        pero díselo a olot entero. yo sola no puedo 🙄 #delay: 4
    ** [qué meditaciones?]
        lo de tu madre. las de blanco #delay: 120
        mi madre dice que es un grupo de duelo. pero que hay que estar muy triste para ir vestida así todo el día 😶 #delay: 6
    ** [y tú qué crees?]
        yo creo que si te hubieras ido con el dani me lo habrías dicho #delay: 120
        y que no tienes tan mal gusto #delay: 3
    ** [(sin responder)]
    - - -> opciones
* (embarazo) {not nuria_denuncia and dia >= 2} [quién dice que estoy embarazada?]
    ~ sabe_girona = true
    la prima de judit. trabaja en una farmacia de girona #delay: 120
    dice que te vio comprar una prueba de embarazo. hace como dos semanas #delay: 5
    con otra chica que no se quitaba la capucha #delay: 4
    yo no se lo he dicho a nadie eh #delay: 30
    bueno. a mireia. y se ha puesto blanca #delay: 6
    #caduca: D{dia + 1} 00:00
    ** (desmiente) [no estoy embarazada, carla]
        vale vale #delay: 120
        ya me lo imaginaba. pues lo digo a quien haga falta #delay: 4
    ** [no se lo cuentes a nadie más]
        a nadie #delay: 120
        bueno a judit no. vale. a nadie #delay: 4
    ** [(sin responder)]
    - - -> opciones
* {not nuria_denuncia and amigas.d1.vienen and dia >= 2} [qué viste en mi casa?]
    velas por todo el pasillo. olor a incienso #delay: 120
    y en la entrada tres vestidos blancos colgados, como de comunión #delay: 5
    uno era de tu talla ali #delay: 8
    #caduca: D{dia + 1} 00:00
    ** [de mi talla?]
        de tu talla. y con una rosa bordada en el pecho #delay: 120
        ali qué es eso #delay: 4
    ** [será para lo de la yaya]
        ah. puede ser #delay: 120
        perdona. no lo había pensado 💔 #delay: 4
    ** [(sin responder)]
    - - -> opciones
* {not nuria_denuncia and dia >= 3} [carla tú te fías de laura?]
    laura vila? es un amor #delay: 120
    bueno... últimamente va rara. todo el día con frases de amaneceres #delay: 5
    y el otro día la vi subiéndose al coche de tu hermana. ya te digo yo que esa acaba de blanco #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [laura con berta?]
        en el coche de tu hermana. con una bolsa de ropa blanca #delay: 120
        yo te aviso. luego no digas que no te aviso #delay: 4
    ** [no le digas nada de mí a laura]
        jamás. esa ya habla como tu hermana #delay: 120
    ** [(sin responder)]
    - - -> opciones
// Con el meme del gato meditando (IMG_0397): Alicia fue una vez a una reunión «del alba» y no quiso volver.
* {foto_img_0397 and not nuria_denuncia} [te acuerdas del meme del gato meditando que te pasé?]
    JAJAJA «fingiendo paz interior» #delay: 120
    me lo mandaste un domingo a las siete de la mañana. dijiste que te habían llevado a una «del alba» y que no volvías ni muerta #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [y te conté qué pasó allí?]
        que había un señor mayor que hablaba muy bajito y todo el mundo asentía #delay: 120
        y que tu hermana lloraba de lo feliz que estaba. que daba más miedo que una peli 😶 #delay: 5
    ** [ni me acordaba]
        normal tía, eras un zombi a esas horas 😂 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {not nuria_denuncia} [carla, mi top negro?]
    😇 #delay: 120
    te lo devuelvo cuando vuelvas. es un rehén #delay: 4
    así vuelves #delay: 3
    -> opciones
* {not nuria_denuncia and dia >= 2} [qué tal estética?]
    hoy practicábamos cejas entre nosotras #delay: 120
    a la de al lado le he dejado una más alta que la otra. parece que te esté preguntando algo todo el rato 😭 #delay: 5
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
