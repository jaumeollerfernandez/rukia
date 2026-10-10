// Rafa, profesor de la autoescuela. Exigente, sarcástico con cariño. La frialdad de Montse al teléfono.
// Pista menor: hace un mes Alicia le preguntó si el puerto de Bracons se cierra de noche o con niebla.

=== rafa ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [rafa perdona por el examen]
    Hay convocatoria cada mes. La plaza no se pierde. La tasa, sí. #delay: 600
    -> opciones
* {dia >= 4} [rafa, qué te dijo mi madre?]
    Que habías «cambiado de prioridades». Y que no te volviera a llamar. #delay: 600
    Muy amable y muy fría. #delay: 5
    -> opciones
* [rafa, hay simulacro esta semana?]
    El viernes. El que saque menos de 27 no se presenta 😈 #delay: 600
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
