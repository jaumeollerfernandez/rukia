// Roser, de la coral. Cantaba con la yaya Mercè, la Rosalia y la Pepita. Habla mucho, también con Montse.
// Explica por qué hay dos masías con la puerta azul.
// Vida propia: el mercado, las sopranos sin Montse y su foto de 1979 en Gonpi. Si Alicia le pide que no se lo cuente
// a su madre, ya es tarde.

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
    #caduca: D{dia + 1} 00:00
    ** [la rosalia vivía por sant salvador, creo]
        ¡Puede ser! Tú tienes la cabeza de tu yaya, nena. Yo ya mezclo hasta las letras de las canciones. #delay: 300
    ** [en qué residencia está?]
        En la grande de Olot. Sant Jaume, creo. Eso me dijo tu madre en el mercado. #delay: 300
        Le dije de ir a verla con la coral y me dijo que mejor no, que no reconoce a nadie. Pobrecita. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3 or (dia == 2 and hora >= 14)} [roser, qué llevaba mi madre en la bolsa?]
    {aviso == 0: -> aviso ->}
    Velas blancas, de esas largas. Y una tela blanca enorme, como para coser vestidos. #delay: 600
    #caduca: D{dia + 1} 00:00
    ** [vestidos para qué?]
        Eso le pregunté yo. Me dijo «para una celebración». Con una cara de celebración... 😅 #delay: 300
    ** [estará de reformas, como dijiste]
        ¡Eso pensé! Aunque nadie reforma con tela blanca, nena. #delay: 300
    ** [(sin responder)]
    - - -> opciones
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
// Con la foto de la yaya delante de la masía (IMG_0391): la perra Trufa y el pozo son de la Rosalia, no de la Pepita.
* {foto_img_0391 and dia >= 2} [roser, la perra de la rosalia no se llamaría trufa?]
    {aviso == 0: -> aviso ->}
    ¡Trufa! ¡Esa! Nombre de dulce, ¿ves? Ya te lo decía yo. #delay: 600
    La última. Ya debe de ser viejita, como su dueña. ¿Y tú cómo lo sabes, nena? #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [tengo una foto de pequeña con la yaya. sale en la caseta]
        ¡Ay, qué bonito! Delante de la puerta azul, seguro. Con el pozo y el banco de piedra. #delay: 300
        La de la Pepita no tenía pozo, que en Capsec bajaban a la fuente con cántaros. #delay: 6
    ** [me lo contó la yaya]
        Tu yaya lo contaba todo con canciones. Hasta los nombres de las perras. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [roser, mi madre sigue cantando?]
    {aviso == 0: -> aviso ->}
    En la coral no. Dice que ahora canta «con su familia». Al amanecer, en un prado. #delay: 600
    Yo le dije que a esas horas no canta ni el gallo. No se rió. #delay: 5
    -> opciones
// Pedirle que no se lo cuente a Montse: ya es tarde.
* {aviso} [roser, no le digas a mi madre que te he escrito]
    ¡Ay, nena! #delay: 600
    Si ya se lo he dicho. Por teléfono, en cuanto me escribiste. ¿Por qué? ¿Estáis enfadadas? #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [un poco. no le digas nada más porfa]
        Nada más. Te lo prometo por la Virgen del Mont. #delay: 300
        Aunque tu madre me pregunta a mí, ¿eh? Yo no voy detrás de nadie 😅 #delay: 6
    ** [no, nada. déjalo]
        Uy, con ese «déjalo» ya me quedo yo más tranquila 🙂 #delay: 300
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

= aviso
~ sospecha_familia += 1
¡Ay, nena, qué alegría! Se lo digo a tu madre, que estará contentísima. #delay: 600
->->
