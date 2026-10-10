// Marc, 19, del pueblo. Va en tractor por todos los caminos y lo ve todo: la furgoneta del Casal y la luz de Can Pericot.

=== marc ===
-> historial

= historial
ali el cartel lleva tractor o qué #at: D-5 20:30
ni de broma #from: me #at: D-5 21:00
lo veremos #at: D-5 21:00
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [marc has visto algo raro por el valle?]
    una furgo blanca del casal ese, sube y baja todo el día #delay: 300
    y gente de blanco haciendo yoga en un prado a las 6 de la mañana. con el frío que hace 🥶 #delay: 5
    -> opciones
* {dia >= 4} [has visto luz por algún mas de noche?]
    ~ sabe_capsec = true
    anoche en can pericot, en capsec. a las tantas #delay: 300
    luz de velas o de linterna. el mas está vacío desde que murió la pepita #delay: 5
    okupas o algo. o fantasmas jajaja #delay: 4
    -> opciones
* {dia >= 5} [y la furgo del casal, sigue dando vueltas?]
    {secta_a_capsec:
        esta mañana estaba en capsec. han reventado la puerta de can pericot 😳 #delay: 300
        y se han ido con cara de mala leche #delay: 5
    - else:
        subiendo hacia bracons. parando en cada mas. lo pondré en gonpi #delay: 300
    }
    -> opciones
* [marc cuando vuelva me llevas en el tractor?]
    desfile privado por la plaza 🚜 #delay: 300
    -> opciones
* {dia >= 2} [marc qué tal la granja?]
    ha parido la vaca más fea del valle. le he puesto quim 🐄 #delay: 300
    no se lo digas a quim #delay: 3
    -> opciones
* {dia >= 3 and sabe_puerta_azul} [marc, conoces alguna casa con la puerta azul?]
    dos #delay: 300
    can pericot, en capsec. y otra más arriba, subiendo a bracons. esa con un pozo delante #delay: 5
    en esa no paro nunca. hay una perra enorme que se tira al tractor como si fuera un ciervo #delay: 5
    -> opciones
* {dia >= 4} [marc, la gente de blanco del prado, sigue?]
    cada mañana. más que antes #delay: 300
    ayer eran como veinte. y en vez de yoga cantaban. con los ojos cerrados. daba un poco de mal rollo 🥶 #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
