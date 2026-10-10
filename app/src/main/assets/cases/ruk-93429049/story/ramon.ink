// Ramon, jubilado, del club de lectura. Encantador y bocazas: lo que le escribas lo sabrá Montse en el mercado.
// Conoció a la yaya Mercè, pero mezcla a sus dos amigas: pone a la Rosalia en Capsec y a la Pepita en Bracons (al revés).

=== ramon ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [hola ramon 😊]
    {aviso == 0: -> aviso ->}
    -> opciones
* [ramon, conociste a mi yaya mercè?]
    {aviso == 0: -> aviso ->}
    ~ sabe_capsec = true
    ¡Claro! La Mercè. Bailaba sardanas como nadie. #delay: 600
    Siempre subía al monte con sus dos amigas: la Rosalia, la de Capsec, y la Pepita, la del mas de la carretera de Bracons. #delay: 8
    ¿O era al revés? Ay, la cabeza. #delay: 10
    Una de las dos ya nos dejó. La otra, dicen, está en una residencia. #delay: 6
    -> opciones
* [ramon, qué libro leéis en el club?]
    {aviso == 0: -> aviso ->}
    «La plaça del Diamant». Yo ya lo he leído cuatro veces. La Gemma, ninguna. #delay: 600
    Teresa dice que esta vez lo empieza. Yo apuesto un vermut a que no 😊 #delay: 5
    -> opciones
* {dia >= 3 and sabe_residencia} [ramon, en qué residencia está la amiga de la yaya?]
    {aviso == 0: -> aviso ->}
    Eso dice tu madre. Pero mi nuera trabaja en la residencia Sant Jaume de Olot. #delay: 600
    Y le pregunté, que la curiosidad me puede. Allí no hay ninguna Rosalia. Ni ninguna Pepita, claro, que esa ya está con Dios. #delay: 7
    Igual está en otra. O igual tu madre se confunde, como yo con los nombres 😊 #delay: 5
    -> opciones
* {dia >= 2} [ramon, cómo bailaba la yaya?]
    {aviso == 0: -> aviso ->}
    ¡Como una pluma! En la fiesta mayor, cada año, la primera en el corro. #delay: 600
    La última vez que la vi bailar fue con la Rosalia, en la plaza. Se reían como dos niñas. #delay: 6
    -> opciones
+ [(sin responder)]
    -> charla

// La primera vez que le escribes, se lo cuenta a tu madre.
= aviso
~ sospecha_familia += 1
¡Alicia! Me dijeron que estabas de viaje. ¡Qué alegría! #delay: 600
Mañana en el mercado le digo a tu madre que me has escrito, que la pobre estará contenta. #delay: 6
->->
