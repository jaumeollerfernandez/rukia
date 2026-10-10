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
+ [(sin responder)]
    -> charla

= aviso
~ sospecha_familia += 1
¡Alicia, hija! ¿Dónde estás? #delay: 600
Voy ahora mismo a decirle a tu madre que me has escrito. #delay: 5
->->
