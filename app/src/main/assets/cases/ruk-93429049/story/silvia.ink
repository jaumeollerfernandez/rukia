// Sílvia, madre de Leo (clases de repaso). Trabaja en una inmobiliaria: se fija en las casas. Vio la casa de Montse por fuera.
// Pista menor (se cruza con la libreta, IMG_0401, y el banco): la venta del piso de la yaya, 120.000 €, que no cobró Montse.

=== silvia ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [sílvia perdona por la clase]
    Tranquila. Leo te echa de menos. #delay: 600
    -> opciones
* {dia >= 2} [sílvia, qué viste en mi casa?]
    Persianas bajadas a las seis de la tarde y olor a incienso desde la calle. #delay: 600
    Y en la puerta, pintada pequeñita, una rosa dorada. Antes no estaba. #delay: 6
    -> opciones
* [sílvia, cómo va leo?]
    Mejor en mates, peor en ortografía. Escribe «haber» cuando quiere decir «a ver» 😊 #delay: 600
    Dice que tú se lo explicabas con dibujos. #delay: 4
    -> opciones
* {dia >= 3} [sílvia, sabes algo del piso de la yaya?]
    El de la calle Major de Olot. Se vendió hace dos años, con la competencia. 120.000. #delay: 600
    En Olot todo se sabe. Y lo que se comentó fue raro: el comprador no le pagó a tu madre. Pagó a una cuenta de unas siglas. #delay: 7
    Una asociación, decían. Yo pensé que sería una fundación de la iglesia o algo así. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [qué siglas?]
        Ni idea, Ali. Algo de «servicios». Pregúntaselo al banco, no a una inmobiliaria cotilla 😊 #delay: 300
    ** [mi madre no me lo contó]
        Pues ya ves. A veces los padres se guardan las cosas de dinero. Como los míos. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [sílvia, venden mi casa?]
    No, que yo sepa. Pero la semana pasada pasó un señor mayor por la oficina preguntando cuánto valdría. #delay: 600
    Sin dar nombre. Barba blanca. Muy educado. Le dije que sin la propietaria no podía tasar nada. #delay: 6
    -> opciones
+ [(sin responder)]
    -> charla
