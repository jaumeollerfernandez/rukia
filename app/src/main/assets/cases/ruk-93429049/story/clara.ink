// Clara, monitora del esplai. Guarda el dibujo de la Laieta: una casa de puerta azul con perro y estrellas.

=== clara ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [clara 💛]
    ¡Ali! La Laieta pregunta por ti cada sábado. #delay: 600
    -> opciones
* {dia >= 4} [clara me guardas el dibujo de la laieta?]
    Claro. Lo tengo colgado en el local. #delay: 600
    La puerta azul, el perro, las estrellas... Dice que es la casa de «la abuela de la Ali», la de la historia que les contabas. #delay: 6
    Yo pensaba que era Can Pericot. Pero allí no hay perro, ¿no? #delay: 6
    -> opciones
+ [(sin responder)]
    -> charla
