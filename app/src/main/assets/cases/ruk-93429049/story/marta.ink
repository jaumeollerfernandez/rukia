// Marta, hermana del padre, vive en Olot. No soporta a Montse desde que entró en «Rosa d'Abril».
// Es la única adulta de la familia que habla claro de la secta.

=== marta ===
-> historial

= historial
Feliz cumple, mi niña!!! 18 ya 🎂🎉 ¡Qué mayor! Un besazo de la tía #at: D-40 08:02
graciaas tía 💛💛 #from: me #at: D-40 11:15
-> d1

= d1
Alicia, cariño, soy la tía Marta. #at: D1 13:05
Me he cruzado con tu madre en el mercado y me ha dicho que estás de viaje con unas amigas. #delay: 10
Tu madre nunca te deja ir ni a Girona sola. ¿Seguro que estás bien? #delay: 15 #caduca: D2 10:00
* [sí tía, todo bien, no te preocupes]
    Bueno. Si tú lo dices... #delay: 600
    Cualquier cosa, ya sabes dónde estoy. #delay: 5
* [tía, tú qué sabes de la gente con la que va mamá?]
    ~ sabe_secta = true
    Lo de «Rosa d'Abril». #delay: 400
    Tu padre y yo intentamos hablar con ella hace un año. No hubo manera. #delay: 20
    Le dio a ese hombre el dinero del piso de la abuela, ¿lo sabías? Todo. #delay: 15
    Ese «Guía» no es ningún guía. Es un sinvergüenza con muy buena labia. #delay: 10
    ¿Por qué me lo preguntas ahora? ¿Te han hecho algo? #delay: 8 #caduca: D2 10:00
    ** [no, solo quería saberlo]
        Ali, si un día necesitas salir de esa casa, la mía está abierta. Día y noche. #delay: 300
    ** [(sin responder)]
        Ali, si un día necesitas salir de esa casa, la mía está abierta. Día y noche. #delay: 1
* [(sin responder)]
    Me quedo preocupada. Escríbeme, aunque sea un «ok». #delay: 1
- -> d2

= d2
Ali, esta mañana he visto a tu madre en la oficina del banco. #at: D2 11:10
Con un señor mayor, barba blanca, muy bien vestido. Ella firmaba y él miraba. #delay: 6
Ni me ha saludado. Me ha mirado como si no me conociera. #delay: 10
Cariño, ¿te han tocado tus ahorros? #delay: 15 #caduca: D3 10:00
* [sí tía. me lo han quitado todo]
    ~ sabe_secta = true
    Me lo temía. #delay: 300
    Esta noche hablo con tu padre. Esto no puede seguir así. #delay: 10
* [no lo sé tía]
    Pues míralo, cariño. Hoy mismo. #delay: 300
* [(sin responder)]
- -> d3

= d3
Ali, he hablado con tu padre. #at: D3 19:00
Está muy preocupado. Y yo también. #delay: 5
Mañana voy a ir a ver a tu madre. Aunque no me abra la puerta. #delay: 10
-> d4

= d4
He ido a tu casa. Tu madre no me ha abierto. #at: D4 12:00
Las persianas bajadas a mediodía, y olía a incienso desde la calle. #delay: 5
Y había un coche gris aparcado con una rosa dorada pegada en el cristal. #delay: 6
Cariño, estoy muy asustada. #delay: 4
-> d5

= d5
Tu padre llega mañana. Se queda en casa. #at: D5 19:00
Ali, si necesitas un sitio, aquí hay una cama y nadie te va a preguntar nada. #delay: 5
-> d6

= d6
Tu padre está aquí. No suelta el móvil. #at: D6 22:30
Te queremos mucho, Ali. Pase lo que pase. #delay: 5
-> d7

= d7
{rescatada():
    Tu padre se ha ido corriendo a la comisaría. Ali, cariño, qué alegría. #at: D7 08:30
- else:
    Tu padre ha ido a tu casa y no hay nadie. Ali, por favor, di algo. #at: D7 08:30
}
-> DONE
