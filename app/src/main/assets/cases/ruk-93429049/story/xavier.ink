// Xavier, director de la coral. Heredó la coral de su maestro, que la recibió de las fundadoras (1979): la yaya Mercè,
// la Rosalia y la Pepita. Ruido (ensayos, afonía), y un recuerdo menor: la Rosalia ya no baja al pueblo, vive valle arriba.

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
    -> opciones
* [xavier, hay ensayo esta semana?]
    El jueves a las 20:30 en la parroquia. Camisa blanca y pantalón negro el domingo, como siempre. #delay: 600
    Las contraltos sin ti suenan a coro de ánimas. #delay: 5
    -> opciones
* {dia >= 2} [xavier, quién fundó la coral?]
    Tu yaya, la Rosalia y la Pepita. En 1979, con veinte años y ninguna partitura. #delay: 600
    Hay una foto colgada en la sacristía. Tu yaya en medio, riéndose de algo. #delay: 6
    Mi maestro decía que la Rosalia era la mejor soprano del valle y que nunca quiso cantar fuera. #delay: 6
    -> opciones
* {dia >= 3} [y la rosalia, sigue viva?]
    Que yo sepa, sí. Ya no baja al pueblo. #delay: 600
    La última vez que la vi fue en el entierro de tu yaya. Cantó sola, sin micro, y se fue andando valle arriba con una perra enorme. #delay: 8
    Nadie se atrevió a ofrecerle el coche. #delay: 5
    -> opciones
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
+ [(sin responder)]
    -> charla
