// Iris, 20 años, de Rosa d'Abril. Era amiga de Alicia en el Casal. Duda. Solo escribe de madrugada, a escondidas.
// Su primer mensaje llega la noche del D4 (pasada la medianoche).

=== iris ===
-> d4

= d4
hola ali. soy iris. la del casal #at: D5 00:30
tu hermana dice que no tienes el móvil #delay: 10
pero si alguien lee esto: ali tenía razón #delay: 6
lo de los papeles #delay: 4 #caduca: D5 02:00
* [¿Qué papeles?]
    las voluntades. las anticipadas, las de los médicos. eso es lo que te dicen #delay: 60
    pero el mismo día, en el despacho de la casa, firmas otro papel. no te dejan leerlo con calma #delay: 5
    lo donas todo a la comunidad «en caso de tránsito». casa, cuentas, todo #delay: 4
    y nos ha dado una fecha #delay: 5
* [¿Quién eres?]
    alguien que también firmó #delay: 60
    y que ahora no puede dormir #delay: 5
// Solo con la ficha de Ignasi (app Policía): Iris adelanta cuándo y dónde, un día antes de su llamada.
* {ficha_ignasi} [¿Sabes lo que pasó hace once años en la ermita de Santa Margarida?]
    cómo sabes eso #delay: 90
    ignasi dice que aquella mujer «no estaba preparada». que nosotros sí #delay: 8
    la trobada es pasado mañana, al alba. en la misma ermita #delay: 6
    vamos todos. de blanco. en ayunas #delay: 5
* [(sin responder)]
- no puedo escribir más. aquí revisan los móviles #delay: 20
borro esto #delay: 3
-> d5

= d5
// La llamada de Iris llega en la madrugada del D6 (ver D6).
-> d6

= d6
📞 #at: D6 00:45 #call: audio/iris.m4a
por si no lo has cogido: es a las seis y media. en el cráter de santa margarida #delay: 60
ignasi lleva unas botellas en la furgoneta. dice que es «agua de luz» #delay: 6
no voy a ir. creo #delay: 10 #caduca: D6 02:00
* [Iris, ve a los Mossos de Olot. Pregunta por la sargento Puig.]
    ~ iris_ayuda = true
    y qué les digo #delay: 60
    todo. lo de los papeles, las botellas, el dinero #delay: 5
    vale. mañana. si me dejan salir #delay: 20
* [(sin responder)]
- -> d7

= d7
{iris_ayuda:
    estoy en comisaría. llevo aquí toda la noche #at: D7 05:00
    me han dado un café horrible. es lo mejor que me ha pasado en meses #delay: 10
}
// Si Iris no ha ido a los Mossos, no vuelve a escribir.
-> DONE
