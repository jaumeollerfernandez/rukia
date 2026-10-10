// Pau, coordinador del esplai. Sabe quién tiene llaves de Can Pericot. Responsable, algo ingenuo con Biel.
// Despiste: el D3 echa en falta un juego de llaves del refugio (las de Alicia, que estaban en su taquilla): empuja hacia
// Capsec (sabe_capsec). Ruido: gincanas, campamento, los peques.

=== pau ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [pau perdona por la salida]
    Tranquila. ¿Estás bien? #delay: 600
    -> opciones
* {sabe_capsec and not descarta_capsec} [pau, quién más tiene llaves del refugi?]
    Tú, yo y Biel. #delay: 600
    Las de Biel siempre están «perdidas» 🙄 #delay: 4
    -> opciones
* [pau, qué hacemos el sábado con los peques?]
    Gincana en el parque. Cada moni trae una prueba ⛺ #delay: 600
    La tuya era la de las pistas escondidas. Los peques la piden cada semana. #delay: 5
    -> opciones
* {dia >= 3} [pau, ha pasado algo en el local?]
    ~ sabe_capsec = true
    Falta un juego de llaves del refugi. El de tu taquilla, Ali. #delay: 600
    No lo tendrás tú, ¿no? Si no, cambio la cerradura de Can Pericot, que no quiero sustos. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [sí, las tengo yo]
        Vale, uf. Pues tráelas cuando vuelvas, que el refugi es del Ayuntamiento y Quim me mata. #delay: 300
    ** [no sé dónde están]
        Pues alguien las tiene. Le preguntaré a Biel, que siempre lo sabe todo y nunca sabe nada 🙄 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [pau, te acuerdas del campamento?]
    ¡Cómo no! La noche que se fue la luz y contaste la historia de la casa de la puerta azul para que los peques no lloraran. #delay: 600
    La Laieta aún la pide. «La de la abuela de la Ali y la perra gigante.» #delay: 6
    Yo siempre pensé que te la inventabas. #delay: 4
    -> opciones
* {dia >= 4} [pau, si alguien pregunta por mí en el esplai?]
    Le diré que estás de exámenes. Lo de siempre. #delay: 600
    Aunque hace dos días vino un señor mayor al local preguntando si teníamos «algún refugio en el valle». Muy educado. #delay: 6
    Le dije que eso era cosa del Ayuntamiento. ¿Lo conoces? #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
