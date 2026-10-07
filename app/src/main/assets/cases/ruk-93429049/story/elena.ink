// Elena, tutora del CFGS. Tono formal. Solo escribe en horario laboral.

=== elena ===
-> d1

= d1
Hola, Alicia. Soy Elena, tu tutora. #at: D1 09:30
Llevas dos días sin venir a clase y sin justificar. Recuerda que con un 20 % de faltas pierdes la evaluación continua. #delay: 3
Si hay algún problema, en casa o donde sea, puedes hablar conmigo con total confianza. #delay: 4 #caduca: D2 08:00
* [Hola Elena, estoy enferma, perdona. Llevaré el justificante.]
    De acuerdo. Que te mejores. Te mando los apuntes por el campus. #delay: 1800
* [hola elena. hay problemas en casa]
    Gracias por contármelo, Alicia. #delay: 1200
    ¿Estás en un lugar seguro ahora mismo? #delay: 5 #caduca: D2 08:00
    ** [sí, estoy bien]
        Me alegro. Mi puerta está abierta. Si lo necesitas, hay recursos y gente que puede ayudarte. #delay: 900
    ** [(sin responder)]
        Te escribo mañana. Cuídate. #delay: 1
* [(sin responder)]
    Te lo recuerdo mañana. Un saludo. #delay: 1
- -> d2

= d2
Buenos días, Alicia. #at: D2 09:40
Ayer llamé a tu casa. Me atendió tu madre. #delay: 4
Me dijo que estás unos días «de retiro espiritual». #delay: 5
Te soy sincera: me extrañó. #delay: 4
¿Es así? #delay: 3 #caduca: D3 09:00
* [sí, es así]
    De acuerdo. Lo anoto como ausencia justificada por la familia. #delay: 1200
* [no exactamente]
    Entiendo. #delay: 900
    Alicia, si hay algo que debas contar, lo hablamos. Y si corres peligro, hay que avisar a quien corresponda. #delay: 6
* [(sin responder)]
- -> d3

= d3
Alicia, he comentado tu situación con la orientadora del centro. #at: D3 10:15
No te preocupes, es confidencial. Solo quiero que sepas que estamos aquí. #delay: 5
// D4: nada.
-> d5

= d5
Alicia, he tenido que informar a dirección de tus ausencias. Es el protocolo. #at: D5 11:00
También he pedido que se active el protocolo de absentismo con servicios sociales. No es un castigo: es para ayudarte. #delay: 6
-> d6

= d6
// D6: nada.
-> d7

= d7
// D7: nada.
-> DONE
