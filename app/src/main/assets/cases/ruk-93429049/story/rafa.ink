// Rafa, profesor de la autoescuela. Exigente, sarcástico con cariño. La frialdad de Montse al teléfono.
// Pista menor: hace un mes Alicia le preguntó si el puerto de Bracons se cierra de noche o con niebla.
// Vida propia: treinta años de teórica, el café de gasolinera, el bus a Girona del D4 (Hugo aprueba con un 28).
// Escribe él solo el D4, cuando Alicia no aparece en el bus: no es por el examen.

=== rafa ===
-> charla

= charla
{rafa_escribe() > 0 and hora >= rafa_escribe():
    -> bus ->
}
- (opciones)
#caduca: {caduca(rafa_escribe())}
* [rafa perdona por el examen]
    Hay convocatoria cada mes. La plaza no se pierde. La tasa, sí. #delay: 600
    -> opciones
* {dia >= 4} [rafa, qué te dijo mi madre?]
    Que habías «cambiado de prioridades». Y que no te volviera a llamar. #delay: 600
    Muy amable y muy fría. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no he cambiado de prioridades]
        Ya me lo imaginaba. Nadie cambia de prioridades a las siete de la mañana de un examen. #delay: 300
    ** [perdona que te colgara]
        No me colgaste tú. Me colgaron. Distinta cosa. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia < 4} [rafa, hay simulacro esta semana?]
    No. Ahora toca el de verdad. #delay: 600
    Tú ibas por 29. Hugo, por la gracia de Dios. #delay: 5
    -> opciones
* {dia >= 2} [rafa, te acuerdas de lo que te pregunté de bracons?]
    Si cierran el puerto de noche. Sí. #delay: 600
    Te dije que no, que no hay barrera, pero que de noche y con niebla no subiera ni un conductor con veinte años de carnet. #delay: 6
    Y tú me dijiste que no pensabas subir en coche. Me quedé con la duda. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [era para un trabajo de clase]
        Ya. Un trabajo de clase sobre carreteras de montaña de noche. Muy de Integración Social. #delay: 300
    ** [iba a ir andando]
        Andando. De noche. Por Bracons. #delay: 300
        Alicia, eso no te lo voy a preguntar en el examen, pero suspendes igual. Linterna y chaleco, por lo menos. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [rafa, puedo cambiar la convocatoria?]
    Puedes. Rellenas el papel, pagas la tasa otra vez y rezas. 🚗 #delay: 600
    Te guardo la plaza del mes que viene. Sin llamar a tu casa, que me cuelgan. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla

// D4, cuando Alicia no se presenta al bus (autoescola.ink, 07:50).
= bus
Alicia. #at: D4 08:20
El bus ya ha salido. No es por el examen, eso tiene arreglo. #delay: 6
Tu madre me ha hablado de una manera que no me ha gustado. ¿Estás bien? #delay: 5
#caduca: D{dia + 1} 00:00
* [estoy bien, rafa. lo siento]
    Pues entonces nada. El mes que viene, y sin excusas. 🚗 #delay: 600
* [no del todo]
    Vale. #delay: 600
    Yo de esto no sé. Pero sé que a los dieciocho uno puede firmar su propia matrícula. Y lo que haga falta. #delay: 6
* [(sin responder)]
- ->->

// La hora de hoy a la que Rafa escribe por su cuenta (0: hoy no escribe).
=== function rafa_escribe() ===
{dia == 4 and not rafa.bus and not autoescola.d3.aviso:
    ~ return 8
}
~ return 0
