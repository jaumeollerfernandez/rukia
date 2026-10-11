// Gemma, del club de lectura y de ioga. Administrativa del Ayuntamiento. Simpática, cotilla light.
// Pista menor (aviso de la trampa de Laura): Berta y Laura pidieron juntas la plaza para una «meditación al alba».
// Despiste: comparte apellido con otros Ferrer y no tiene nada que ver.
// Vida propia: el Ayuntamiento, el vermut, los libros que no empieza. Escribe ella sola el D5, cuando Teresa cuenta que
// Alicia deja el club.

=== gemma ===
-> charla

= charla
{gemma_escribe() > 0 and hora >= gemma_escribe():
    -> club ->
}
- (opciones)
#caduca: {caduca(gemma_escribe())}
* [gemma has empezado el libro? 😏]
    ali no tú también 🙄 #delay: 300
    #caduca: D{dia + 1} 00:00
    ** [ramon apuesta un vermut a que no]
        ramon apuesta un vermut a todo. y luego se lo bebe él 🙈 #delay: 300
    ** [yo tampoco, tranqui]
        MENTIRA. tú te lo lees todo dos semanas antes y luego nos haces quedar mal #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [gemma, mi madre fue alguna vez a ioga?]
    una vez. se pasó la clase hablando de un señor de su grupo que «la acompañaba» #delay: 300
    clàudia no la volvió a ver #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [qué decía de ese señor?]
        que la había ayudado mucho con lo de tu yaya. que «veía las cosas» #delay: 300
        yo solo veía que no hacía las posturas 🙈 #delay: 4
    ** [(sin responder)]
    - - -> opciones
* [gemma vermut este finde?]
    siempre 🍸 #delay: 300
    pero tú pagas, que yo me he comprado tres libros que no voy a leer 🙈 #delay: 4
    -> opciones
* {dia >= 2} [gemma, en el ayuntamiento se sabe algo de mí?]
    aquí se sabe todo y no se sabe nada 😂 #delay: 300
    quim pregunta por el cartel. y la de servicios sociales por tus prácticas. nada más #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [qué dice quim del cartel?]
        que lo hará laura. que no es lo mismo. que tú dibujas mejor #delay: 300
        eso último lo digo yo, quim no lo diría ni muerto #delay: 4
    ** [qué prácticas?]
        las del banc d'aliments. que tienes que firmar las horas. tranqui, eso espera #delay: 300
    ** [(sin responder)]
    - - -> opciones
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

// D5, después de que Teresa cuente en el grupo que Alicia deja el club (lectura.ink, 12:40).
= club
ali #at: D5 13:30
es verdad que dejas el club?? 😢 #delay: 4
quién me va a contar de qué va el libro #delay: 5
#caduca: D{dia + 1} 00:00
* [no lo dejo. eso lo dice mi madre]
    ah! menos mal 😍 #delay: 300
    pues díselo a teresa, que está de un triste que no veas #delay: 5
* [no lo sé, gemma]
    vale 😢 #delay: 300
    pues te guardo el sitio. el de al lado de la ventana, que es el bueno #delay: 5
* [(sin responder)]
- ->->

// La hora de hoy a la que Gemma escribe por su cuenta (0: hoy no escribe).
=== function gemma_escribe() ===
{dia == 5 and not gemma.club:
    ~ return 13
}
~ return 0
