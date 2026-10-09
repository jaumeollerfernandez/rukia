// Carla, del grupo «Las de siempre». Cotilla y leal. Repite todos los rumores de Olot (pista falsa de Dani y Barcelona)
// y es la única que ve venir a Laura. Si Núria ha dicho que no eres Ali, no suelta nada.

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
    que estás embarazada. que te ha metido tu madre en la secta esa #delay: 5
    yo digo que no, que tú nunca harías nada sin contárnoslo #delay: 6
    ...no? #delay: 10
    -> opciones
* {not nuria_denuncia and amigas.d1.vienen and dia >= 2} [qué viste en mi casa?]
    velas por todo el pasillo. olor a incienso #delay: 120
    y en la entrada tres vestidos blancos colgados, como de comunión #delay: 5
    uno era de tu talla ali #delay: 8
    -> opciones
* {not nuria_denuncia and dia >= 3} [carla tú te fías de laura?]
    laura vila? es un amor #delay: 120
    bueno... últimamente va rara. todo el día con frases de amaneceres #delay: 5
    y el otro día la vi subiéndose al coche de tu hermana. ya te digo yo que esa acaba de blanco #delay: 6
    -> opciones
+ [(sin responder)]
    -> charla
