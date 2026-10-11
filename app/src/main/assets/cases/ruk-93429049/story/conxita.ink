// Conxita, vecina de la calle del Pont. Cotilla y amiga de Montse (el de la barba le ha dado un folleto).
// Vio salir a Alicia aquella noche, y a la furgoneta del Casal llegar de madrugada. Todo lo que le escribas, lo sabrá Montse.
// Vida propia: la ventana, el grupo de vecinos, los geranios de la yaya que ahora riega ella.
// Si Alicia le pide que no se lo cuente a Montse, no sabe mentir: se lo dirá si le pregunta.

=== conxita ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [hola conxita]
    {aviso == 0: -> aviso ->}
    -> opciones
* [conxita, viste algo la noche que me fui?]
    {aviso == 0: -> aviso ->}
    A las once y pico te vi salir por la puerta de atrás. Con una bolsa enorme. #delay: 600
    Y a las tres de la madrugada aparcó delante de tu casa la furgoneta blanca de los amigos de tu madre. Bajaron tres, con linternas. #delay: 8
    Apunté la matrícula, por si acaso. Acababa en HFT. #delay: 6
    ~ sabe_furgoneta = true
    Pensé que se iban de excursión. A esas horas. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [por qué apuntaste la matrícula?]
        Costumbre, hija. Desde que le robaron el coche al de enfrente apunto todas las que no conozco. #delay: 300
        Tengo una libreta. No se lo digas a nadie, que me llaman cotilla 😳 #delay: 6
    ** [se lo has contado a mi madre?]
        Lo de la furgoneta no. Eran sus amigos, ¿para qué? #delay: 300
        Lo de que te fuiste con la bolsa, sí. Me preguntó. #delay: 5
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [conxita, mi madre te ha dicho algo de mí?]
    {aviso == 0: -> aviso ->}
    Que estás «de retiro», hija. Que te hacía falta. #delay: 600
    Y me ha dado un folleto muy bonito de su asociación. Una excursión al alba, para familias. Con desayuno. #delay: 6
    Me ha dicho que si quería ir, que ya me avisaría. ¡A mi edad, al alba! #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no vayas, conxita]
        ¡Uy! ¿Por qué? Si tu madre dice que es precioso. #delay: 300
        Bueno, si tú lo dices... Total, a esas horas yo duermo. #delay: 6
    ** [dónde es?]
        En un volcán, con una ermita. El nombre no me acuerdo, hija. Con desayuno, eso sí. #delay: 300
        A mi edad subir un volcán a oscuras. ¡Qué cosas tiene tu madre! #delay: 5
    ** [(sin responder)]
    - - -> opciones
* {aviso} [conxita, le has contado a mi madre que te escribo?]
    ¡Pues claro, hija! ¿Qué iba a hacer? Tu madre está que no duerme. #delay: 600
    Se puso muy contenta. Y luego muy seria. Me preguntó qué me habías dicho, palabra por palabra. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [no se lo cuentes más, porfa]
        Ay, hija, ¿y qué le digo si me pregunta? Yo no sé mentir. #delay: 300
        Bueno. Le diré que no me acuerdo. Eso sí lo sé hacer 😳 #delay: 6
    ** [y qué le dijiste?]
        Pues lo que me pusiste. Palabra por palabra. Tengo buena memoria para eso. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [conxita, de quién son los geranios de tu balcón?]
    {aviso == 0: -> aviso ->}
    ¡De tu yaya! Me los ha dado tu madre. Dice que donde va no hacen falta macetas. #delay: 600
    Yo los cuido, ¿eh? Riego cada día. #delay: 4
    -> opciones
+ [(sin responder)]
    -> charla

= aviso
~ sospecha_familia += 1
¡Alicia, hija! ¿Dónde estás? #delay: 600
Voy ahora mismo a decirle a tu madre que me has escrito. #delay: 5
->->
