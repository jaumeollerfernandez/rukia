// Monitores del Esplai de Bianya. Alicia es monitora de los pequeños los sábados.

=== esplai ===
-> historial

= historial
Monis, el sábado gincana en el parque. Cada uno trae una prueba ⛺ #from: pau #at: D-12 20:00
yo hago la de los globos de agua #from: me #at: D-12 20:30
Ali eres la mejor y la peor a la vez #from: biel #at: D-12 20:31
Gracias por el sábado, monis. Los peques no paran de hablar de Ali y los globos 😂 #from: clara #at: D-9 10:00
💛💛 #from: me #at: D-9 11:20
-> d1

= d1
Sábado: salida al Parc Nou. Salimos a las 10 de la plaza. Ali, ¿te encargas de los peques? #from: pau #at: D1 20:00 #caduca: D2 20:00
* [no puedo este sábado, lo siento mucho]
    Vaya. Biel, ¿te los quedas tú? #from: pau #delay: 900
    Sí, pero si lloran os los paso 😂 #from: biel #delay: 300
* [(sin responder)]
    Ali, ¿estás? Necesito saberlo. #from: pau #delay: 1
- -> charla("D2 20:55") ->
-> d2

= d2
Biel se queda con los peques. Clara y yo con los medianos. #from: pau #at: D2 21:00
La madre de la Laieta pregunta por Ali. Dice que la niña le ha hecho un dibujo 🥹 #from: clara #delay: 900
-> d3

= d3
// Pista falsa: falta un juego de llaves del refugio de Can Pericot (las tiene Biel; ver biel.ink).
Monis, ¿alguien tiene las llaves del refugi de Can Pericot? En el armario del local solo queda un juego. #from: pau #at: D3 19:30
yo no #from: clara #delay: 600
ni idea #from: biel #delay: 900
Ali tenía uno, ¿no? Del campamento. #from: pau #delay: 60
pues ya aparecerán. tampoco hay nada que robar ahí arriba 😅 #from: biel #delay: 120
~ sabe_capsec = true
-> charla("D4 13:55") ->
-> d4

= d4
Salida genial. Nadie perdido, nadie herido. Récord 😎 #from: biel #at: D4 14:00
Ali, la Laieta te ha dejado el dibujo en el local. Es una casita con una puerta enorme 🎨 #from: clara #delay: 600
-> charla("D6 23:59") ->
-> d5

= d5
-> d6

= d6
-> d7

= d7
-> DONE

// Lo que el jugador puede escribir en el grupo entre escena y escena.
= charla(limite)
- (opciones)
#caduca: {limite}
* {sabe_capsec and not descarta_capsec} [las llaves del refugi las tengo yo, tranquis]
    Ah, genial. Pues cuando puedas las devuelves, Ali 🙏 #from: pau #delay: 600
    ali... seguro? #from: biel #delay: 120
    biel qué te pasa #from: clara #delay: 60
    nada nada #from: biel #delay: 30
    -> opciones
* {sabe_capsec and not descarta_capsec} [quién ha subido al refugi últimamente?]
    Nadie desde el campamento, que yo sepa. #from: pau #delay: 600
    yo no #from: biel #delay: 10
    biel has contestado en un segundo. tú nunca contestas en un segundo 👀 #from: clara #delay: 120
    😑 #from: biel #delay: 60
    -> opciones
* {dia >= 4} [cómo es el dibujo de la laieta?]
    Una casita con la puerta azul, un perro enorme y un montón de estrellas 🥹 #from: clara #delay: 600
    Dice que es la casa donde vive la Ali ahora. Que se lo contaste tú en la gincana. #from: clara #delay: 6
    La puerta azul de Can Pericot, seguro. Les contaste la historia del refugi en el campamento. #from: pau #delay: 300
    -> opciones
* [os echo de menos, monis]
    Y nosotros a ti. Los peques preguntan cada sábado. #from: pau #delay: 600
    💛 #from: clara #delay: 60
    -> opciones
+ [(sin responder)]
- ->->
