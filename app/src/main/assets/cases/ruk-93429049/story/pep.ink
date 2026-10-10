// Pep, de la coral. Bromista. Vio a Montse y a Berta vestidas de blanco. Fue cartero del valle cuarenta años: conoce
// cada mas. Pista menor: si el jugador ya sabe el nombre de la Rosalia, recuerda que vive arriba, sin luz, con una perra
// que no deja acercarse al buzón. No da el sitio exacto.

=== pep ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [pep 😂]
    ¡La contralto perdida! La Roser está insoportable sin ti. #delay: 600
    -> opciones
* {dia >= 2} [pep, has visto a mi madre?]
    A misa no viene desde hace un año. #delay: 600
    La vi en el mercado con tu hermana. Las dos de blanco, de arriba abajo. Parecían novias. #delay: 6
    Uy, perdona, nena. #delay: 20
    -> opciones
* [pep cuéntame un chiste]
    ¿Qué le dice un cartero a otro? «Te veo en el buzón.» #delay: 600
    Cuarenta años repartiendo y es el mejor que tengo. Por eso me jubilé. #delay: 5
    -> opciones
* {dia >= 2} [pep, de verdad conoces todos los mases del valle?]
    ¡Todos! Y sus perros. Me han mordido en once mases distintos. Tengo las cicatrices numeradas. #delay: 600
    El peor, el de Can Pericot, cuando vivía la Pepita. Un mastín que parecía un ternero. #delay: 6
    Ahora está vacío. Sin Pepita y sin perro. #delay: 5
    -> opciones
* {sabe_rosalia} [pep, tú le llevabas las cartas a la rosalia?]
    ¡La Rosalia! Más de treinta años. Arriba del todo, donde la carretera se pone fea. #delay: 600
    Nunca recibía cartas. Solo el recibo de la luz, hasta que le cortaron la luz y dejó de llegarle hasta eso. #delay: 8
    Siempre con una perra que no me dejaba ni tocar el buzón. Le dejaba el correo en el pozo, con una piedra encima. #delay: 6
    ¿Por qué lo preguntas, nena? #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [la yaya hablaba mucho de ella]
        Eran uña y carne. Si sube alguien a verla, que le lleve pan. Siempre pedía pan. #delay: 300
    ** [por nada, curiosidad]
        Curiosidad. Ya. Igualita que tu yaya 😂 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [pep, la roser qué dice de mí?]
    ¡Que te has ido con un novio a Barcelona! La Roser tiene más imaginación que un culebrón. #delay: 600
    No le hagas caso. Ni le escribas, que se lo cuenta todo a tu madre. #delay: 6
    -> opciones
+ [(sin responder)]
    -> charla
