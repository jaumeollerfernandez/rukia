// Roser, de la coral. Cantaba con la yaya Mercè, la Rosalia y la Pepita. Habla mucho, también con Montse.
// Explica por qué hay dos masías con la puerta azul.

=== roser ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [hola roser]
    {aviso == 0: -> aviso ->}
    -> opciones
* [roser, la yaya mercè cantaba en la coral, no?]
    {aviso == 0: -> aviso ->}
    ~ sabe_capsec = true
    ~ sabe_residencia = true
    ¡Si la fundó ella! Con la Rosalia y la Pepita. Las tres sopranos del valle. #delay: 600
    La Mercè les pintó la puerta de casa a las dos. Azul, de la misma lata. Decía que así siempre se encontrarían. #delay: 8
    La Pepita murió, la pobre. Y la Rosalia, me dijo tu madre, está en una residencia de Olot. Qué pena. #delay: 6
    ¿Dónde vivían? Una en Capsec y otra por Sant Salvador, eso seguro. Cuál en cada sitio... ya no lo sé, nena. #delay: 8
    -> opciones
* {dia >= 2} [roser, qué llevaba mi madre en la bolsa?]
    {aviso == 0: -> aviso ->}
    Velas blancas, de esas largas. Y una tela blanca enorme, como para coser vestidos. #delay: 600
    -> opciones
* {dia >= 2} [roser, tienes fotos de la yaya en la coral?]
    {aviso == 0: -> aviso ->}
    ¡Una de 1979! Las tres sopranos en la escalera de la iglesia. La he colgado en Gonpi, mira mi perfil. #delay: 600
    Tu yaya en medio, la Pepita a la izquierda con su moño y la Rosalia a la derecha, con la perra a los pies. Siempre con perra, esa mujer. #delay: 8
    -> opciones
* {dia >= 3} [roser, la rosalia tenía perro?]
    {aviso == 0: -> aviso ->}
    ¡Siempre! Una detrás de otra, y todas con nombre de dulce. Canela, Galeta... la última no me acuerdo. #delay: 600
    Unas perras enormes, que asustaban. Pero eran más buenas que el pan. #delay: 5
    -> opciones
* {dia >= 4} [roser, mi madre sigue cantando?]
    {aviso == 0: -> aviso ->}
    En la coral no. Dice que ahora canta «con su familia». Al amanecer, en un prado. #delay: 600
    Yo le dije que a esas horas no canta ni el gallo. No se rió. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla

= aviso
~ sospecha_familia += 1
¡Ay, nena, qué alegría! Se lo digo a tu madre, que estará contentísima. #delay: 600
->->
