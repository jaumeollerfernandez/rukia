// Marc, 19, del pueblo. Va en tractor por todos los caminos y lo ve todo: la furgoneta del Casal y la luz de Can Pericot.
// Vida propia: la granja (una vaca recién parida), el tractor en la fiesta mayor y Laura, que le gusta y ya no es la misma.
// Escribe él solo el D2: última oportunidad para el tractor en el cartel.

=== marc ===
-> historial

= historial
ali el cartel lleva tractor o qué #at: D-5 20:30
ni de broma #from: me #at: D-5 21:00
lo veremos #at: D-5 21:00
-> charla

= charla
{marc_escribe() > 0 and hora >= marc_escribe():
    -> tractor ->
}
- (opciones)
#caduca: {caduca(marc_escribe())}
* [marc has visto algo raro por el valle?]
    una furgo blanca del casal ese, sube y baja todo el día #delay: 300
    y gente de blanco haciendo yoga en un prado a las 6 de la mañana. con el frío que hace 🥶 #delay: 5
    -> opciones
* {dia >= 4} [has visto luz por algún mas de noche?]
    ~ sabe_capsec = true
    anoche en can pericot, en capsec. a las tantas #delay: 300
    luz de velas o de linterna. el mas está vacío desde que murió la pepita #delay: 5
    okupas o algo. o fantasmas jajaja #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [se lo has dicho a quim?]
        sí. dice que la semana que viene manda a alguien #delay: 120
        o sea nunca #delay: 3
    ** [no te acerques, eh]
        ni loco. yo con el tractor hago ruido. si son fantasmas se enteran 🚜 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 5} [y la furgo del casal, sigue dando vueltas?]
    {secta_a_capsec:
        esta mañana estaba en capsec. han reventado la puerta de can pericot 😳 #delay: 300
        y se han ido con cara de mala leche #delay: 5
    - else:
        subiendo hacia bracons. parando en cada mas. lo pondré en gonpi #delay: 300
    }
    -> opciones
* {not tractor.prometo} [marc cuando vuelva me llevas en el tractor?]
    desfile privado por la plaza 🚜 #delay: 300
    -> opciones
* {dia >= 2} [marc qué tal la granja?]
    ha parido la vaca más fea del valle. le he puesto quim 🐄 #delay: 300
    no se lo digas a quim #delay: 3
    -> opciones
* {dia >= 3} [marc, te gusta laura?]
    qué dices #delay: 300
    bueno #delay: 20
    un poco #delay: 5
    pero ahora va todo el día con tu hermana y habla de amaneceres y de soltar cosas. no la entiendo #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [díselo]
        y que me hable de energías? paso #delay: 120
        bueno. igual. ya veré #delay: 10
    ** [no le cuentes nada de mí a laura]
        tranqui. si apenas me habla #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3 and sabe_puerta_azul} [marc, conoces alguna casa con la puerta azul?]
    dos #delay: 300
    can pericot, en capsec. y otra más arriba, subiendo a bracons. esa con un pozo delante #delay: 5
    en esa no paro nunca. hay una perra enorme que se tira al tractor como si fuera un ciervo #delay: 5
    -> opciones
* {dia >= 4} [marc, la gente de blanco del prado, sigue?]
    cada mañana. más que antes #delay: 300
    ayer eran como veinte. y en vez de yoga cantaban. con los ojos cerrados. daba un poco de mal rollo 🥶 #delay: 5
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D2: última oportunidad para el tractor.
= tractor
ali #at: D2 12:10
última oportunidad #delay: 3
el cartel lleva tractor sí o no 🚜 #delay: 4
#caduca: D{dia + 1} 00:00
* [no, marc]
    lo veremos #delay: 300
* (prometo) [vale. uno pequeñito en una esquina]
    SÍ #delay: 60
    te debo un paseo en tractor. desfile privado por la plaza 🚜 #delay: 4
* [el cartel no lo voy a hacer yo]
    entonces se lo pido a laura #delay: 300
    ella dirá que no. pero se lo pido #delay: 4
* [(sin responder)]
- ->->

// La hora de hoy a la que Marc escribe por su cuenta (0: hoy no escribe).
=== function marc_escribe() ===
{dia == 2 and not marc.tractor:
    ~ return 12
}
~ return 0
