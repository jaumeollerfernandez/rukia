// Pau, coordinador del esplai. Sabe quién tiene llaves de Can Pericot. Responsable, algo ingenuo con Biel.
// Despiste: el D3 echa en falta un juego de llaves del refugio (las de Alicia, que estaban en su taquilla): empuja hacia
// Capsec (sabe_capsec). Ruido: gincanas, campamento, los peques.
// Vida propia: estudia Magisterio en Girona y vuelve los fines de semana; organiza la salida al Parc Nou.

=== pau ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
// Saludos: uno u otro.
* (perdon) {not hola} [pau perdona por la salida]
    Tranquila. ¿Estás bien? #delay: 600
    #caduca: D{dia + 1} 00:00
    ** [sí, solo una mala semana]
        Pues cuídate. Biel se queda con los peques. Que Dios nos ayude 😉 #delay: 300
    ** [no mucho]
        Si necesitas algo, aquí estoy. Y si no quieres contarlo, también. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* (hola) {not perdon} [hola pau!]
    ¡Ali! ¿Cómo va? Aquí, con la salida y los peques. #delay: 600
    -> opciones
* {sabe_capsec and not descarta_capsec} [pau, quién más tiene llaves del refugi?]
    Tú, yo y Biel. #delay: 600
    Las de Biel siempre están «perdidas» 🙄 #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [seguro que no las tiene biel?]
        Biel dice que no. #delay: 300
        Y Biel nunca miente. Mal. Miente fatal, quiero decir. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia < 4} [pau, qué hacemos el sábado con los peques?]
    Salida al Parc Nou. Salimos a las 10 de la plaza. #delay: 600
    {pau.charla.perdon:
        Los tuyos se los queda Biel. Ya sabes. #delay: 5
    - else:
        ¿Te encargas tú de los peques? Siempre te hacen más caso a ti. #delay: 5
    }
    -> opciones
// Si Alicia ya dijo en el grupo si tiene las llaves o no, Pau no se lo pregunta otra vez.
* (falta) {dia >= 3 and not esplai.charla.tengo_llaves and not esplai.charla.no_llaves} [pau, ha pasado algo en el local?]
    ~ sabe_capsec = true
    Falta un juego de llaves del refugi. El de tu taquilla, Ali. #delay: 600
    No lo tendrás tú, ¿no? Si no, cambio la cerradura de Can Pericot, que no quiero sustos. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** (tengo) [sí, las tengo yo]
        Vale, uf. Pues tráelas cuando vuelvas, que el refugi es del Ayuntamiento y Quim me mata. #delay: 300
    ** (no_se) [no sé dónde están]
        Pues alguien las tiene. Le preguntaré a Biel, que siempre lo sabe todo y nunca sabe nada 🙄 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [pau, te acuerdas del campamento?]
    ¡Cómo no! La noche que se fue la luz y contaste la historia de la casa de la puerta azul para que los peques no lloraran. #delay: 600
    La Laieta aún la pide. «La de la abuela de la Ali y la perra gigante.» #delay: 6
    Yo siempre pensé que te la inventabas. #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [no me la inventaba]
        ¿No? Pues los peques van a querer ir de excursión 😉 #delay: 300
    ** [un poco sí]
        Lo sabía. Tienes un don para los cuentos. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [pau, si alguien pregunta por mí en el esplai?]
    Le diré que estás de exámenes. Lo de siempre. #delay: 600
    Aunque hace dos días vino un señor mayor al local preguntando si teníamos «algún refugio en el valle». Muy educado. #delay: 6
    Le dije que eso era cosa del Ayuntamiento. ¿Lo conoces? #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no. cómo era?]
        Barba blanca. Una sonrisa de esas que no sabes si te está vendiendo algo. #delay: 300
    ** [no le digas nada a nadie de mí]
        Nada a nadie. Prometido. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [pau qué tal por girona?]
    Exámenes de didáctica. Y el tren de vuelta, que cada semana llega más tarde. #delay: 600
    Cuando acabe Magisterio, me compro un coche. O una bici, que no me da para más 😉 #delay: 6
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
