// Mireia, del grupo «Las de siempre». Ansiosa y cariñosa. Sabe lo del móvil prepago (pista cierta, para Laia).
// Pista falsa de Girona: hace nueve días Alicia la acompañó a una clínica de Girona y le pagó la prueba de embarazo
// (el banco: farmacia de Girona y el Bizum de 13 € de Mireia). El padre es Sergi («no me hables de sergi»).
// Mireia deja correr el rumor de que la embarazada es Alicia, porque desmentirlo la delataría a ella.
// Solo lo confiesa si el jugador la trata con cuidado; si la presiona, se cierra (mireia_cerrada).
// Vida propia: su cumpleaños (D3), el ciclo de Administración y lo de Sergi, que la tiene sin dormir.
// Escribe ella sola el D4 por la noche, si el rumor de Girona sigue corriendo: la culpa.

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
#caduca: D2 00:00
* [no fuimos. tranquila 🤐]
    gracias 🥺 #delay: 120
    te debo la vida #delay: 3
* [mire qué pasa?]
    nada. que tu madre pregunta cosas. y yo me pongo nerviosa #delay: 120
    tú solo di que no fuimos #delay: 4
* [(sin responder)]
- -> charla

= charla
{mireia_escribe() > 0 and hora >= mireia_escribe():
    -> culpa ->
}
- (opciones)
#caduca: {caduca(mireia_escribe())}
* {not nuria_denuncia} [mire te acuerdas del móvil del estanco?]
    ~ sabe_prepago = true
    el nokia de abuela? sí #delay: 120
    lo compraste en el estanco de la plaza, en olot. en efectivo, con lo de la panadería #delay: 5
    me dijiste que era «por si un día mamá me quita el mío» #delay: 5
    pensaba que era broma #delay: 30
    lo llevas tú? #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [sí. y no se lo digas a nadie]
        🤐 #delay: 120
        como siempre #delay: 3
    ** [no sé dónde lo dejé]
        pues vaya emergencia jajaja #delay: 120
        perdón. no tiene gracia 🥺 #delay: 10
    ** [(sin responder)]
    - - -> opciones
* [mire cómo estás?]
    {nuria_denuncia:
        no sé quién eres #delay: 120
    - else:
        fatal. no duermo pensando en ti #delay: 120
        y lo de sergi, que ya te contaré cuando vuelvas 🙄 #delay: 5
    }
    -> opciones
* {not nuria_denuncia and dia >= 2 and not mireia_cerrada and not girona_descartado} [mire, lo de girona...]
    QUÉ #delay: 60
    por aquí no ali. por favor #delay: 4
    -> girona ->
    -> opciones
* {not nuria_denuncia and dia >= 3 and dia <= 4} [feliz cumple mire 🎂]
    😭😭 graciaaas #delay: 120
    te guardo tarta. ni se te ocurra no volver #delay: 4
    -> opciones
* {not nuria_denuncia and dia >= 4} [qué tal la fiesta?]
    bien #delay: 120
    bueno. sin ti y sin sergi. o sea bien a medias #delay: 4
    carla se puso a bailar encima del sofá y se cargó una lámpara. eso sí estuvo bien 😂 #delay: 6
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// Girona: con cuidado lo confiesa; presionada, se cierra.
= girona
#caduca: D{dia + 1} 00:00
* [no se lo he contado a nadie. ni a nuri. tranquila 💛]
    ~ girona_descartado = true
    ... #delay: 120
    vale #delay: 5
    es que carla va diciendo por ahí que la embarazada eres tú #delay: 5
    y no lo he desmentido. si lo desmiento, preguntan quién era #delay: 6
    perdóname ali. la prueba era mía. y la clínica. tú solo me acompañaste y me la pagaste porque yo no llevaba tarjeta #delay: 8
    sergi no me habla desde entonces #delay: 6
    no te habrás ido por mi culpa, no? dime que no #delay: 10
    #caduca: D{dia + 1} 00:00
    ** [no, mire. no es por ti]
        vale 😭 #delay: 120
        gracias. de verdad #delay: 4
    ** [no puedo contarte por qué. pero no es por ti]
        vale. vale #delay: 120
        cuando puedas me lo cuentas. yo te he contado lo mío 🤍 #delay: 5
    ** [(sin responder)]
        ali? #delay: 600
* {carla.charla.oido} [mire, la gente dice que estoy embarazada. di la verdad]
    ~ mireia_cerrada = true
    no puedes pedirme eso #delay: 120
    si se entera mi padre me mata #delay: 5
    pensaba que eras mi amiga #delay: 6
* [(sin responder)]
- ->->

// D4 por la noche, si el rumor sigue corriendo y no se lo ha contado.
= culpa
ali #at: D4 23:20
no duermo #delay: 4
{carla.charla.desmiente:carla dice que tú le has dicho que no. pero la gente sigue con lo de girona|carla sigue diciendo lo de girona. que eras tú}. y yo callada como una rata 🥺 #delay: 6
lo siento mucho #delay: 4
-> girona ->
->->

// La hora de hoy a la que Mireia escribe por su cuenta (0: hoy no escribe).
=== function mireia_escribe() ===
{dia == 4 and not nuria_denuncia and not mireia_cerrada and not girona_descartado and not mireia.culpa:
    ~ return 23
}
~ return 0
