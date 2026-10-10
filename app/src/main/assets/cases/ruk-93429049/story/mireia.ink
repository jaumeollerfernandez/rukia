// Mireia, del grupo «Las de siempre». Ansiosa y cariñosa. Sabe lo del móvil prepago (pista cierta, para Laia).
// Pista falsa de Girona: hace nueve días Alicia la acompañó a una clínica de Girona y le pagó la prueba de embarazo
// (el banco: farmacia de Girona y el Bizum de 13 € de Mireia). El padre es Sergi («no me hables de sergi»).
// Mireia deja correr el rumor de que la embarazada es Alicia, porque desmentirlo la delataría a ella.
// Solo lo confiesa si el jugador la trata con cuidado; si la presiona, se cierra (mireia_cerrada).

=== mireia ===
-> historial

= historial
para qué querías el móvil ese del estanco tía? 😂 #at: D-30 20:14
para emergencias. no se lo digas a nadie porfa #from: me #at: D-30 20:40
🤐 #at: D-30 20:41
ali mañana a las 8 en la parada del bus? 🥺 #at: D-10 22:10
sí. y tranqui, todo va a ir bien 💛 #from: me #at: D-10 22:15
ni a nuri eh #at: D-10 22:15
🤐 #from: me #at: D-10 22:16
cómo estás? 🥺 #at: D-8 21:28
bien. cansada. y tú? #from: me #at: D-8 21:40
rara. pero bien. gracias por todo 🤍 #at: D-8 21:41
ali #at: D1 13:00
tu madre ha llamado a la mía #delay: 4
si alguien pregunta por girona, no fuimos vale? 🥺 #delay: 5
lo sabemos tú y yo y ya #delay: 4
~ sabe_girona = true
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {not nuria_denuncia} [mire te acuerdas del móvil del estanco?]
    ~ sabe_prepago = true
    el nokia de abuela? sí #delay: 120
    lo compraste en el estanco de la plaza, en olot. en efectivo, con lo de la panadería #delay: 5
    me dijiste que era «por si un día mamá me quita el mío» #delay: 5
    pensaba que era broma #delay: 30
    lo llevas tú? #delay: 4
    -> opciones
* [mire cómo estás?]
    {nuria_denuncia:
        no sé quién eres #delay: 120
    - else:
        fatal. no duermo pensando en ti #delay: 120
        y lo de sergi, que ya te contaré cuando vuelvas 🙄 #delay: 5
    }
    -> opciones
* {not nuria_denuncia and dia >= 2 and not mireia_cerrada} [mire, lo de girona...]
    QUÉ #delay: 60
    por aquí no ali. por favor #delay: 4
    ** [no se lo he contado a nadie. ni a nuri. tranquila 💛]
        ~ girona_descartado = true
        ... #delay: 120
        vale #delay: 5
        es que carla va diciendo por ahí que la embarazada eres tú #delay: 5
        y no lo he desmentido. si lo desmiento, preguntan quién era #delay: 6
        perdóname ali. la prueba era mía. y la clínica. tú solo me acompañaste y me la pagaste porque yo no llevaba tarjeta #delay: 8
        sergi no me habla desde entonces #delay: 6
        no te habrás ido por mi culpa, no? dime que no #delay: 10
    ** [mire, la gente dice que estoy embarazada. di la verdad]
        ~ mireia_cerrada = true
        no puedes pedirme eso #delay: 120
        si se entera mi padre me mata #delay: 5
        pensaba que eras mi amiga #delay: 6
    - - -> opciones
* {not nuria_denuncia and dia >= 3 and dia <= 4} [feliz cumple mire 🎂]
    😭😭 graciaaas #delay: 120
    te guardo tarta. ni se te ocurra no volver #delay: 4
    -> opciones
+ [(sin responder)]
    -> charla
