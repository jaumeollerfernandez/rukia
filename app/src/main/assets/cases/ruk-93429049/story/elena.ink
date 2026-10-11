// Elena, tutora del CFGS. Tono formal. Solo escribe en horario laboral.
// Recuerda lo que Alicia le dice: si le dijo que estaba enferma, el D2 nota que Montse dice otra cosa.

=== elena ===
-> d1

= d1
Hola, Alicia. Soy Elena, tu tutora. #at: D1 09:30
Llevas dos días sin venir a clase y sin justificar. Recuerda que con un 20 % de faltas pierdes la evaluación continua. #delay: 3
Si hay algún problema, en casa o donde sea, puedes hablar conmigo con total confianza. #delay: 4 #caduca: D2 08:00
* (enferma) [Hola Elena, estoy enferma, perdona. Llevaré el justificante.]
    De acuerdo. Que te mejores. Te mando los apuntes por el campus. #delay: 1800
* (casa) [hola elena. hay problemas en casa]
    Gracias por contármelo, Alicia. #delay: 1200
    ¿Estás en un lugar seguro ahora mismo? #delay: 5 #caduca: D2 08:00
    ** [sí, estoy bien]
        Me alegro. Mi puerta está abierta. Si lo necesitas, hay recursos y gente que puede ayudarte. #delay: 900
    ** [no lo sé]
        Entonces lo primero es eso. #delay: 900
        Si en algún momento no lo estás, llama al 112. No te lo digo para asustarte: te lo digo porque es lo que hay que hacer. #delay: 8
    ** [(sin responder)]
        Te escribo mañana. Cuídate. #delay: 1
* [es complicado. ya te contaré]
    De acuerdo. Cuando puedas. #delay: 1200
    No hace falta que sea por aquí. #delay: 5
* [(sin responder)]
    Te lo recuerdo mañana. Un saludo. #delay: 1
- -> d2

= d2
Buenos días, Alicia. #at: D2 09:40
Ayer llamé a tu casa. Me atendió tu madre. #delay: 4
Me dijo que estás unos días «de retiro espiritual». #delay: 5
{d1.enferma:
    Ayer me dijiste que estabas enferma. #delay: 4
}
Te soy sincera: me extrañó. #delay: 4
¿Es así? #delay: 3 #caduca: D3 09:00
* [sí, es así]
    {d1.enferma:
        De acuerdo. Lo anoto como ausencia justificada por la familia. #delay: 1200
        Aunque no sé muy bien cuál de las dos cosas anotar. #delay: 6
    - else:
        De acuerdo. Lo anoto como ausencia justificada por la familia. #delay: 1200
    }
* (no_exacto) [no exactamente]
    Entiendo. #delay: 900
    Alicia, si hay algo que debas contar, lo hablamos. Y si corres peligro, hay que avisar a quien corresponda. #delay: 6
* [no le digas nada a mi madre, porfa]
    Lo que me cuentas a mí no se lo cuento a tu madre. Tienes dieciocho años. #delay: 1200
    Pero necesito saber que estás bien. Con eso me basta. #delay: 6
* [(sin responder)]
- -> d3

= d3
Alicia, he comentado tu situación con la orientadora del centro. #at: D3 10:15
No te preocupes, es confidencial. Solo quiero que sepas que estamos aquí. #delay: 5 #caduca: D4 17:00
* [no quiero que llamen a mi casa]
    La orientadora no llama a las familias sin tu permiso. Eres mayor de edad. #delay: 1200
    Eso te lo puedo asegurar. #delay: 4
* [gracias, elena]
    No hay de qué. Es mi trabajo, pero no solo por eso. #delay: 1200
* [(sin responder)]
- -> d5

= d5
Alicia, he tenido que informar a dirección de tus ausencias. Es el protocolo. #at: D5 11:00
También he pedido que se active el protocolo de absentismo con servicios sociales. No es un castigo: es para ayudarte. #delay: 6 #caduca: D6 17:00
* [no hace falta, de verdad]
    {d1.casa or d2.no_exacto:
        Me dijiste que había problemas en casa. Con eso, sí hace falta. #delay: 1200
    - else:
        Es el protocolo. Pero también es lo que creo que necesitas. #delay: 1200
    }
* [vale. lo entiendo]
    Gracias. Sé que no es fácil. #delay: 1200
* [(sin responder)]
- -> d6

= d6
// D6: nada.
-> d7

= d7
// D7: nada.
-> DONE
