// Sonia, la entrenadora de vóley. Seria y cercana. Montse ha ido a dar de baja a Alicia y a recuperar el dinero.

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
+ [(sin responder)]
    -> charla
