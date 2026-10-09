// Mireia, del grupo «Las de siempre». Ansiosa y cariñosa. Sabe lo del móvil prepago (pista cierta, para Laia).

=== mireia ===
-> historial

= historial
para qué querías el móvil ese del estanco tía? 😂 #at: D-30 20:14
para emergencias. no se lo digas a nadie porfa #from: me #at: D-30 20:40
🤐 #at: D-30 20:41
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
* {not nuria_denuncia and dia >= 3 and dia <= 4} [feliz cumple mire 🎂]
    😭😭 graciaaas #delay: 120
    te guardo tarta. ni se te ocurra no volver #delay: 4
    -> opciones
+ [(sin responder)]
    -> charla
