// Gemma, del club de lectura y de ioga. Administrativa del Ayuntamiento. Simpática, cotilla light.
// Pista menor (aviso de la trampa de Laura): Berta y Laura pidieron juntas la plaza para una «meditación al alba».
// Despiste: comparte apellido con otros Ferrer y no tiene nada que ver.

=== gemma ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [gemma has empezado el libro? 😏]
    ali no tú también 🙄 #delay: 300
    -> opciones
* {dia >= 2} [gemma, mi madre fue alguna vez a ioga?]
    una vez. se pasó la clase hablando de un señor de su grupo que «la acompañaba» #delay: 300
    clàudia no la volvió a ver #delay: 4
    -> opciones
* [gemma vermut este finde?]
    siempre 🍸 #delay: 300
    pero tú pagas, que yo me he comprado tres libros que no voy a leer 🙈 #delay: 4
    -> opciones
* {dia >= 2} [gemma, en el ayuntamiento se sabe algo de mí?]
    aquí se sabe todo y no se sabe nada 😂 #delay: 300
    quim pregunta por el cartel. y la de servicios sociales por tus prácticas. nada más #delay: 5
    -> opciones
* {dia >= 3} [gemma, mi hermana pidió algún permiso al ayuntamiento?]
    sí! hace un mes. ella y laura, la de la comisión de fiestas #delay: 300
    la plaza a las seis de la mañana para «una meditación al alba». a las seis, ya ves 😂 #delay: 4
    no fue nadie más que ellas y cuatro de blanco. laura parecía encantada #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [laura? la del insti?]
        la misma. desde hace un tiempo va mucho con tu hermana. pensaba que lo sabías #delay: 300
    ** [qué raro]
        raro es levantarse a las seis para respirar. pero cada uno 🙈 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [gemma, eres familia de algún ferrer de por aquí?]
    de media garrotxa 😂 #delay: 300
    ferrer es como garcía por aquí. ni idea de quién me hablas, pero seguro que es primo de alguien #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
