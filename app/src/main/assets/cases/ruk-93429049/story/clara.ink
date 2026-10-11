// Clara, monitora del esplai. Guarda el dibujo de la Laieta: una casa de puerta azul con perro y estrellas.
// Tierna y observadora: se da cuenta de que en Can Pericot no hay perro. Ruido: manualidades, los peques, el local.
// Vida propia: prácticas en una guardería de Olot; los farolillos del esplai.

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
    #caduca: D{dia + 1} 00:00
    ** [no, allí no hay perro]
        Ya decía yo. Entonces es otra casa. #delay: 300
        Qué cosas te cuentan los peques sin darse cuenta. #delay: 5
    ** [es can pericot, sí]
        Pues la Laieta le ha puesto un perro que no cabe por la puerta 😅 #delay: 300
    ** [guárdalo bien porfa]
        Ni lo pienses. Está con chinchetas de las buenas. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* [clara qué manualidad hacéis esta semana?]
    Farolillos con botes de cristal. Nos van a sobrar treinta, como siempre 😅 #delay: 600
    La Laieta ha pintado el suyo de azul. Todo lo pinta de azul últimamente. #delay: 5
    -> opciones
* {dia >= 2} [clara cómo están los peques?]
    Bien. Revoltosos. El Pol pequeño ha mordido a otro, pero con cariño. #delay: 600
    Les he dicho que estás de viaje. La Laieta dice que te has ido «a la casa de las estrellas» 🥺 #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [dile que pronto vuelvo]
        Se lo digo. Te va a hacer otro dibujo. Prepárate para más perros. #delay: 300
    ** [la casa de las estrellas existe, eh 😉]
        ¡Lo sabía! Siempre he pensado que esa historia era verdad. Tenías una cara al contarla... #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [clara, biel está raro?]
    Biel siempre está raro 👀 #delay: 600
    Pero sí, más. Contesta en un segundo, y Biel nunca contesta en un segundo. Algo ha hecho. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [qué crees que ha hecho?]
        Algo con la Noa. Siempre es algo con la Noa 👀 #delay: 300
    ** [pregúntale tú]
        Ni loca. Si le pregunto yo, se pone rojo y no suelta nada. Contigo canta. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [clara qué tal las prácticas?]
    En una guardería de Olot. Esta semana me han vomitado dos veces y me han dicho «te quiero» una. #delay: 600
    Compensa. Creo 🥹 #delay: 5
    -> opciones
* {dia >= 5} [clara gracias por cuidar de todo]
    Para eso estamos. El local te espera. Y los farolillos que sobran también 💛 #delay: 600
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
