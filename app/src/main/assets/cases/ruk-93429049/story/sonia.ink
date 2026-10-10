// Sonia, la entrenadora de vóley. Seria y cercana. Montse ha ido a dar de baja a Alicia y a recuperar el dinero.
// Ruido: el partido del D5 y la convocatoria. Observa que Alicia llevaba semanas mareándose en los entrenos (los ayunos).

=== sonia ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [sonia perdona por faltar]
    Lo importante es que estés bien, Ali. ¿Lo estás? #delay: 300
    -> opciones
* {dia >= 2} [sonia, mi madre ha pagado la cuota?]
    ~ sabe_secta = true
    Ahora que lo dices. Tu madre vino ayer a darte de baja. Y a pedirme que le devolviera el trimestre. #delay: 300
    Le dije que eres mayor de edad y que la baja la firmas tú. #delay: 5
    No le gustó nada. #delay: 4
    -> opciones
* [sonia hay convocatoria para el partido?]
    Sí. Te he dejado fuera hasta que me digas algo. #delay: 300
    Ona coloca. Lo hará bien. No tan bien como tú. #delay: 5
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
    ** [(sin responder)]
    - - -> opciones
* {dia >= 5} [cómo fue el partido?]
    Perdimos 3 a 1. Hemos luchado, chicas 💪. Bueno, tú no. #delay: 300
    Ona ha estado bien. Judit ha estrenado rodilleras y aun así no ha recibido ni una. #delay: 5
    -> opciones
* {dia >= 3} [sonia, judit va diciendo cosas de mí?]
    Judit va diciendo cosas de todo el mundo. #delay: 300
    Ya le he dicho «Judit.» Con eso suele bastar un rato. #delay: 4
    -> opciones
+ [(sin responder)]
    -> charla
