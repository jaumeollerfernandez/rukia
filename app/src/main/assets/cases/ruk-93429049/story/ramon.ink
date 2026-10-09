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
+ [(sin responder)]
    -> charla

// La primera vez que le escribes, se lo cuenta a tu madre.
= aviso
~ sospecha_familia += 1
¡Alicia! Me dijeron que estabas de viaje. ¡Qué alegría! #delay: 600
Mañana en el mercado le digo a tu madre que me has escrito, que la pobre estará contenta. #delay: 6
->->
