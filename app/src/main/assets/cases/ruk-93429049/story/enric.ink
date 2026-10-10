// Enric, vecino. Discreto, con perro (Rocky). Vio el coche gris toda la noche delante de casa (y le apuntó la matrícula).
// Ayuda a no caer en la trampa: avisa de que Conxita se lo cuenta todo a Montse. Ruido: Rocky, el huerto, arreglos.

=== enric ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [{dia < 3:enric, ha vuelto el rocky?|enric, qué alegría lo del rocky}]
    {dia < 3:Aún no 😢 Gracias por preguntar, Alicia.|¡Gracias, Alicia! Estaba gordo como una vaca 😂} #delay: 600
    -> opciones
* {dia >= 4} [enric, viste quién había en el coche gris?]
    Un hombre mayor, con barba blanca. Toda la noche ahí. #delay: 600
    Miraba la ventana de tu cuarto. Con la luz del móvil encendida. #delay: 5
    A las seis se fue hacia Sant Joan les Fonts. #delay: 5
    Le apunté la matrícula. Acaba en KDP. Por si te sirve. #delay: 5
    ~ sabe_seat = true
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
* {dia >= 3} [enric, dónde estaba el rocky?]
    En el huerto de los Puigdemont, zampándose las tomateras. Tres días de fiesta. #delay: 600
    Volvió con el collar lleno de barro y sin ninguna gana de disculparse 😂 #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
