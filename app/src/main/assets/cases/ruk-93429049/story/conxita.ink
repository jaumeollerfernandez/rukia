// Conxita, vecina de la calle del Pont. Cotilla y amiga de Montse (el de la barba le ha dado un folleto).
// Vio salir a Alicia aquella noche, y a la furgoneta del Casal llegar de madrugada. Todo lo que le escribas, lo sabrá Montse.

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
    -> opciones
* {dia >= 2} [conxita, mi madre te ha dicho algo de mí?]
    {aviso == 0: -> aviso ->}
    Que estás «de retiro», hija. Que te hacía falta. #delay: 600
    Y me ha dado un folleto muy bonito de su asociación. Una excursión al alba, para familias. Con desayuno. #delay: 6
    Me ha dicho que si quería ir, que ya me avisaría. ¡A mi edad, al alba! #delay: 5
    -> opciones
* {dia >= 3} [conxita, le has contado a mi madre que te escribo?]
    {aviso == 0: -> aviso ->}
    ¡Pues claro, hija! ¿Qué iba a hacer? Tu madre está que no duerme. #delay: 600
    Se puso muy contenta. Y luego muy seria. Me preguntó qué me habías dicho, palabra por palabra. #delay: 6
    -> opciones
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
