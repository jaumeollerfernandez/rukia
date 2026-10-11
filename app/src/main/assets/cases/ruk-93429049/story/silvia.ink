// Sílvia, madre de Leo (clases de repaso). Trabaja en una inmobiliaria: se fija en las casas. Vio la casa de Montse por fuera.
// Pista menor (se cruza con la libreta, IMG_0401, y el banco): la venta del piso de la yaya, 120.000 €, que no cobró Montse.
// Vida propia: lleva meses intentando vender una casa de pueblo que no quiere nadie. Leo, con la ortografía.

=== silvia ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
// Saludos: uno u otro.
* (perdon) {not hola} [sílvia perdona por la clase]
    Tranquila. Leo te echa de menos. #delay: 600
    -> opciones
* (hola) {not perdon} [hola sílvia! qué tal leo?]
    Mejor en mates, peor en ortografía. Escribe «haber» cuando quiere decir «a ver» 😊 #delay: 600
    Dice que tú se lo explicabas con dibujos. #delay: 4
    -> opciones
* {perdon} [y leo qué tal?]
    Mejor en mates, peor en ortografía. Escribe «haber» cuando quiere decir «a ver» 😊 #delay: 600
    Dice que tú se lo explicabas con dibujos. #delay: 4
    -> opciones
* {dia >= 3 or (dia == 2 and hora >= 18)} [sílvia, qué viste en mi casa?]
    Persianas bajadas a las seis de la tarde y olor a incienso desde la calle. #delay: 600
    {repas.d1.manana:
        Llamé al timbre tres veces con Leo de la mano. Nadie. #delay: 5
    }
    Y en la puerta, pintada pequeñita, una rosa dorada. Antes no estaba. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [una rosa?]
        Dorada, al lado del timbre. Del tamaño de una moneda. #delay: 300
        Me fijé porque las fachadas son lo mío. Y esa puerta la tasé hace años, cuando la reformasteis. #delay: 8
    ** [sería mi madre, le ha dado por pintar]
        Pues tiene buen pulso. Yo no sé ni pintarme las uñas. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [sílvia, sabes algo del piso de la yaya?]
    El de la calle Major de Olot. Se vendió hace dos años, con la competencia. 120.000. #delay: 600
    En Olot todo se sabe. Y lo que se comentó fue raro: el comprador no le pagó a tu madre. Pagó a una cuenta de unas siglas. #delay: 7
    Una asociación, decían. Yo pensé que sería una fundación de la iglesia o algo así. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [qué siglas?]
        Ni idea, Ali. Algo de «servicios». Pregúntaselo al banco, no a una inmobiliaria cotilla 😊 #delay: 300
    ** [mi madre no me lo contó]
        Pues ya ves. A veces los padres se guardan las cosas de dinero. Como los míos. #delay: 300
    ** [y tú cómo sabes eso?]
        Porque la competencia es mi antiguo jefe. Y le encanta presumir de ventas 😅 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [sílvia, venden mi casa?]
    No, que yo sepa. Pero la semana pasada pasó un señor mayor por la oficina preguntando cuánto valdría. #delay: 600
    Sin dar nombre. Barba blanca. Muy educado. Le dije que sin la propietaria no podía tasar nada. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [cómo era?]
        Chaqueta de lino, voz muy suave. Me llamó «hija» dos veces y no me conoce de nada. #delay: 300
        Se fue sonriendo, como si ya supiera la respuesta. #delay: 6
    ** [si vuelve, avísame]
        Hecho. Aunque no creo que vuelva. Esa gente no pregunta dos veces. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [qué tal el trabajo, sílvia?]
    Ocho meses con una casa de pueblo en Sant Privat que no quiere nadie. #delay: 600
    Tiene encanto, dice el anuncio. Encanto es que no tiene calefacción 😅 #delay: 6
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
