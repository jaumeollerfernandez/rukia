// Sonia, la entrenadora de vóley. Seria y cercana. Montse ha ido a dar de baja a Alicia y a recuperar el dinero.
// Ruido: el partido del D5 y la convocatoria. Observa que Alicia llevaba semanas mareándose en los entrenos (los ayunos).
// Vida propia: profe de Educación Física, la rodilla rota de cuando jugaba y el partido del D5 sin colocadora.
// Escribe ella sola el D5, después del partido.

=== sonia ===
-> charla

= charla
{sonia_escribe() > 0 and hora >= sonia_escribe():
    -> pista ->
}
- (opciones)
#caduca: {caduca(sonia_escribe())}
* (perdon) [sonia perdona por faltar]
    Lo importante es que estés bien, Ali. ¿Lo estás? #delay: 300
    #caduca: D{dia + 1} 00:00
    ** [sí. solo es una mala racha]
        Las malas rachas se pasan entrenando. Y desayunando. #delay: 300
    ** [no del todo]
        Gracias por decírmelo. #delay: 300
        No te pregunto más. Si quieres hablar, sabes dónde estoy: en la pista, a partir de las cinco. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [sonia, mi madre ha pagado la cuota?]
    ~ sabe_secta = true
    Ahora que lo dices. Tu madre vino {dia == 2:ayer|{dia == 3:anteayer|el otro día}} a darte de baja. Y a pedirme que le devolviera el trimestre. #delay: 300
    Dijo que ahora vas a ir más a lo de su asociación. Rosa d'Abril, o algo así. #delay: 6
    Le dije que eres mayor de edad y que la baja la firmas tú. #delay: 5
    No le gustó nada. #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [no quiero darme de baja]
        Entonces no hay baja. Y el dinero se queda donde está. #delay: 300
    ** [déjala. que haga lo que quiera]
        No es lo que ella quiera, Ali. Es lo que quieras tú. #delay: 300
        Lo dejo en el cajón hasta que me lo digas tú en persona. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia < 4} [sonia hay convocatoria para el partido?]
    La hago la víspera. Si vienes a un entreno, cuento contigo. #delay: 300
    Si no, coloca Ona. Lo hará bien. No tan bien como tú. #delay: 5
    -> opciones
* {dia >= 2} [sonia, por qué crees que me mareaba en los entrenos?]
    Te lo pregunté mil veces. Me decías que «no habías desayunado». #delay: 300
    Tres semanas seguidas sin desayunar, Ali. Y bajando de peso. #delay: 5
    Si alguien en casa te dice que comas menos, eso no es normal. Lo sabes, ¿no? #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [lo sé]
        Bien. Cuando vuelvas, primero desayunas y luego entrenas. Es una orden. #delay: 300
    ** [es por un ayuno de mi madre. solidario]
        Solidario con quién. Tú eres la que se mareaba. #delay: 300
        Perdona. No me meto. Pero me preocupa. #delay: 5
    ** [no es asunto tuyo, sonia]
        Tienes razón. #delay: 300
        Lo es cuando te caes en mi pista. Pero vale. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {(dia >= 6 or (dia == 5 and hora >= 13)) and not pista} [cómo fue el partido?]
    Perdimos 3 a 1. Hemos luchado, chicas 💪. Bueno, tú no. #delay: 300
    Ona ha estado bien. Judit ha estrenado rodilleras y aun así no ha recibido ni una. #delay: 5
    -> opciones
* {dia >= 3} [sonia, judit va diciendo cosas de mí?]
    Judit va diciendo cosas de todo el mundo. #delay: 300
    Ya le he dicho «Judit.» Con eso suele bastar un rato. #delay: 4
    -> opciones
* {dia >= 2} [sonia, qué tal la rodilla?]
    Como siempre: avisa cuando va a llover. #delay: 300
    Mejor que un parte meteorológico. Hoy no llueve, por cierto. #delay: 5
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D5, después del partido (voley.ink, 13:00).
= pista
Ali. #at: D5 15:00
Hoy te he echado de menos en la pista. No te lo digo para que te sientas mal. #delay: 8
Ona ha colocado con el corazón. Pero tú lees el partido. Eso no se enseña. #delay: 6
#caduca: D{dia + 1} 00:00
* [gracias sonia. de verdad]
    No me las des. Vuelve. #delay: 300
* [la próxima juego]
    Te tomo la palabra. Y te la recordaré. #delay: 300
* [(sin responder)]
- ->->

// La hora de hoy a la que Sonia escribe por su cuenta (0: hoy no escribe).
=== function sonia_escribe() ===
{dia == 5 and not sonia.pista:
    ~ return 15
}
~ return 0
