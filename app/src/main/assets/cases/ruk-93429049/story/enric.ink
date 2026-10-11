// Enric, vecino. Discreto, con perro (Rocky). Vio el coche gris toda la noche delante de casa (y le apuntó la matrícula).
// Ayuda a no caer en la trampa: avisa de que Conxita se lo cuenta todo a Montse. Ruido: Rocky, el huerto, arreglos.
// Vida propia: Rocky (se escapa el D1, vuelve el D3), electricista jubilado, paseos de madrugada.
// Escribe él solo el D4, después de que Montse le conteste en el grupo que el coche era de «amigos»: no se queda tranquilo.

=== enric ===
-> charla

= charla
{enric_escribe() > 0 and hora >= enric_escribe():
    -> coche ->
}
- (opciones)
#caduca: {caduca(enric_escribe())}
* (rocky) {dia < 3} [enric, ha vuelto el rocky?]
    Aún no 😢 Gracias por preguntar, Alicia. #delay: 600
    Si lo ves, tiene miedo a los petardos. Llámalo bajito. #delay: 5
    -> opciones
* {dia >= 3 and not rocky} [enric, qué alegría lo del rocky]
    ¡Gracias, Alicia! Estaba gordo como una vaca 😂 #delay: 600
    -> opciones
* {dia >= 3 and rocky} [al final apareció el rocky!]
    ¡Sí! Gracias por preguntar el otro día. #delay: 600
    En el huerto de los Puigdemont, zampándose las tomateras. Tres días de fiesta. #delay: 6
    Volvió con el collar lleno de barro y sin ninguna gana de disculparse 😂 #delay: 5
    -> opciones
* {dia >= 4 and not barba} [enric, viste quién había en el coche gris?]
    -> barba ->
    -> opciones
* [enric, me arreglas la bici cuando vuelva?]
    Claro. La cadena otra vez, ¿no? Te la engraso y la dejo como nueva. #delay: 600
    -> opciones
* {dia >= 2} [enric, puedo contarle cosas a la conxita?]
    A la Conxita, ni la hora, Alicia. #delay: 600
    Lo que le digas por la mañana lo sabe tu madre por la tarde. Y adornado. #delay: 5
    Ella no lo hace con mala intención. Pero lo hace. #delay: 4
    -> opciones
* {dia >= 2} [enric, has visto algo raro en la calle?]
    Mucho movimiento en tu casa. Gente de blanco que entra y sale. #delay: 600
    Y tu madre ha regalado las macetas del balcón. Todas. Con lo que las quería. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [las macetas de la yaya?]
        Los geranios de tu yaya, sí. Se los ha dado a la Conxita. Dice que «ya no los necesitará». #delay: 300
        No sé qué quiere decir. Pero no me gustó cómo lo dijo. #delay: 5
    ** [igual se muda]
        Igual. Pero nadie regala las plantas cuando se muda. Se las lleva. #delay: 300
    ** [(sin responder)]
    - - -> opciones
+ [(sin responder)]
    -> charla

= barba
Un hombre mayor, con barba blanca. Toda la noche ahí. #delay: 600
Miraba la ventana de tu cuarto. Con la luz del móvil encendida. #delay: 5
A las seis se fue hacia Sant Joan les Fonts. #delay: 5
Le apunté la matrícula. Acaba en KDP. Por si te sirve. #delay: 5
~ sabe_seat = true
#caduca: D{dia + 1} 00:00
* [no se lo digas a mi madre]
    No hablo con tu madre desde que me dijo que el coche era de «amigos». Tranquila. #delay: 300
* [gracias, enric]
    No hay de qué. Y cierra bien la ventana, si vuelves. #delay: 300
* [(sin responder)]
- ->->

// D4, después del grupo de vecinos (veins.ink, 09:10): Montse dice que son amigos.
= coche
Alicia, perdona que te escriba. #at: D4 11:15
Lo del coche de esta noche. Tu madre dice que son amigos. Yo no me quedo tranquilo. #delay: 6
¿Tú estás bien? #delay: 4
#caduca: D{dia + 1} 00:00
* [estoy bien, enric. quién era?]
    -> barba ->
* [estoy bien. no te preocupes]
    Me alegro. #delay: 600
    Si quieres saber algo del coche, pregúntame. Lo vi bien. #delay: 5
* [(sin responder)]
- ->->

// La hora de hoy a la que Enric escribe por su cuenta (0: hoy no escribe).
=== function enric_escribe() ===
{dia == 4 and not enric.coche:
    ~ return 11
}
~ return 0
