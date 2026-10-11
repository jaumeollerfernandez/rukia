// Xavier, director de la coral. Heredó la coral de su maestro, que la recibió de las fundadoras (1979): la yaya Mercè,
// la Rosalia y la Pepita. Ruido (ensayos, afonía), y un recuerdo menor: la Rosalia ya no baja al pueblo, vive valle arriba.
// Vida propia: profe de música en la escuela de Olot; afónico desde el D3 (té con miel). No habla de la vida de nadie.

=== xavier ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [xavier perdona por faltar]
    Tranquila. El «Cant dels ocells» te espera. #delay: 600
    -> opciones
* {dia >= 3} [xavier cómo va la voz?]
    Afónico perdido. Dirijo con las cejas. #delay: 600
    #caduca: D{dia + 1} 00:00
    ** [té con miel]
        Llevo tres litros. Ya zumbo. #delay: 300
    ** [y en la escuela cómo das clase?]
        Con un xilófono. Los niños están encantados: es la primera vez que no les grito 🎶 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia < 3} [xavier, hay ensayo esta semana?]
    El jueves a las 20:30 en la parroquia. Camisa blanca y pantalón negro el domingo, como siempre. #delay: 600
    Las contraltos sin ti suenan a coro de ánimas. #delay: 5
    -> opciones
* {dia >= 3 and dia < 7} [xavier, al final no hay ensayo?]
    No. Cancelado. Un director sin voz es un señor moviendo los brazos. #delay: 600
    Lo que sí hay es misa. Si puedes venir, aunque sea a escuchar, Roser se calla un rato. #delay: 6
    -> opciones
* (fundo) {dia >= 2} [xavier, quién fundó la coral?]
    Tu yaya, la Rosalia y la Pepita. En 1979, con veinte años y ninguna partitura. #delay: 600
    Hay una foto colgada en la sacristía. Tu yaya en medio, riéndose de algo. #delay: 6
    Mi maestro decía que la Rosalia era la mejor soprano del valle y que nunca quiso cantar fuera. #delay: 6
    -> opciones
* {fundo and dia >= 3} [y la rosalia, sigue viva?]
    Que yo sepa, sí. Ya no baja al pueblo. #delay: 600
    La última vez que la vi fue en el entierro de tu yaya. Cantó sola, sin micro, y se fue andando valle arriba con una perra enorme. #delay: 8
    Nadie se atrevió a ofrecerle el coche. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [qué cantó?]
        El «Cant dels ocells». Medio pueblo lloraba. El otro medio no sabía dónde mirar. #delay: 300
    ** [valle arriba hacia dónde?]
        Hacia Sant Salvador, por la carretera. No sé más, Alicia. Ni me corresponde. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [xavier, mi madre canta en otro coro?]
    Tu madre dejó la coral hace dos años. Me dijo que ahora cantaba «en otro sitio». #delay: 600
    Pensé que se había ido a la de Olot. Pero allí no la conocen. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [es un grupo de su asociación]
        Ah. Pues que canten bonito. Aunque las canciones de tu madre últimamente eran todas tristes. #delay: 300
    ** [ni idea, no me lo cuenta]
        Ya. Tu madre siempre fue de guardarse las cosas. Como tu yaya, la verdad. #delay: 300
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
