// Ramon, jubilado, del club de lectura. Encantador y bocazas: lo que le escribas lo sabrá Montse en el mercado.
// Conoció a la yaya Mercè, pero mezcla a sus dos amigas: pone a la Rosalia en Capsec y a la Pepita en Bracons (al revés).
// Vida propia: el mercado cada mañana, el vermut, los nietos y «El infinito en un junco», que pesa más que el pequeño.
// Si Alicia le pide que no se lo cuente a su madre, ya es tarde: lo reconoce y pide perdón.

=== ramon ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [hola ramon 😊]
    {aviso == 0: -> aviso ->}
    -> opciones
* (yaya) [ramon, conociste a mi yaya mercè?]
    {aviso == 0: -> aviso ->}
    ~ sabe_capsec = true
    ¡Claro! La Mercè. Bailaba sardanas como nadie. #delay: 600
    Siempre subía al monte con sus dos amigas: la Rosalia, la de Capsec, y la Pepita, la del mas de la carretera de Bracons. #delay: 8
    ¿O era al revés? Ay, la cabeza. #delay: 10
    Una de las dos ya nos dejó. La otra, dicen, está en una residencia. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [creo que era al revés, ramon]
        ¡Puede ser! A mi edad, los nombres van y vienen como las golondrinas. #delay: 300
        Lo que no se me olvida es cómo se reían las tres. #delay: 6
    ** [cuál de las dos murió?]
        La Pepita, creo. Hace unos años. Fui al entierro. O fue al de su hermano... #delay: 300
        Ay, nena. Pregúntale a la Roser, que tiene mejor cabeza. Pero no le digas que te lo he dicho 😊 #delay: 6
    ** [(sin responder)]
    - - -> opciones
* [ramon, qué tal el libro del club?]
    {aviso == 0: -> aviso ->}
    «Nada» ya lo he devuelto. Cuatro veces lo he leído y la tía Angustias sigue sin caerme bien. #delay: 600
    Ahora «El infinito en un junco». Pesa más que mi nieto pequeño. La Gemma no lo ha abierto, apuesto un vermut 😊 #delay: 6
    -> opciones
* {dia >= 3 and sabe_residencia} [ramon, en qué residencia está la amiga de la yaya?]
    {aviso == 0: -> aviso ->}
    Eso dice tu madre. Pero mi nuera trabaja en la residencia Sant Jaume de Olot. #delay: 600
    Y le pregunté, que la curiosidad me puede. Allí no hay ninguna Rosalia. Ni ninguna Pepita, claro, que esa ya está con Dios. #delay: 7
    Igual está en otra. O igual tu madre se confunde, como yo con los nombres 😊 #delay: 5
    -> opciones
* {yaya} [ramon, cómo bailaba la yaya?]
    ¡Como una pluma! En la fiesta mayor, cada año, la primera en el corro. #delay: 600
    La última vez que la vi bailar fue con la Rosalia, en la plaza. Se reían como dos niñas. #delay: 6
    -> opciones
// Pedirle que no se lo cuente a Montse: ya es tarde.
* {aviso} [ramon, no le digas a mi madre que te he escrito porfa]
    ¡Uy! #delay: 600
    Pues la he llamado, nena. No he podido esperar al mercado. #delay: 6
    Se ha quedado callada. Yo pensé que de la alegría. ¿He hecho mal? #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [no pasa nada, ramon]
        Ay, menos mal. Es que a mí las cosas se me escapan por la boca como el agua de un cesto 😊 #delay: 300
    ** [un poco sí]
        Perdóname, nena. #delay: 300
        Desde ahora, boca cerrada. Bueno, lo intento. A mi edad, prometer es mucho. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [ramon qué tal el mercado?]
    {aviso == 0: -> aviso ->}
    Hoy las alcachofas a tres euros el kilo. ¡Tres! En mis tiempos, eso era un traje. #delay: 600
    Y he visto a la Conxita, que dice que tu calle parece un velatorio. Las persianas, nena. #delay: 6
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// La primera vez que le escribes, se lo cuenta a tu madre.
= aviso
~ sospecha_familia += 1
¡Alicia! Me dijeron que estabas de viaje. ¡Qué alegría! #delay: 600
Mañana en el mercado le digo a tu madre que me has escrito, que la pobre estará contenta. #delay: 6
->->
