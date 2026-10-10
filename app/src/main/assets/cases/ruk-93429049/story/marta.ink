// Marta, hermana del padre, vive en Olot. No soporta a Montse desde que entró en «Rosa d'Abril».
// Habla claro, pero es la cuñada resentida desde la separación: Montse y Berta la pintan como cotilla, y el jugador duda.

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
    ¿Lo del grupo de duelo? «Rosa d'Abril», o algo así. #delay: 400
    Cariño, ya sabes que tu madre y yo no nos tragamos desde lo de tu padre. No soy la más indicada. #delay: 20
    Dice que le ha ido muy bien, y no seré yo quien le quite eso. Lo de la yaya la dejó hecha polvo. #delay: 8
    Solo digo que el piso de la abuela se vendió muy rápido. Y que yo no he visto ni un mueble nuevo en esa casa. #delay: 15
    ¿Por qué me lo preguntas ahora? ¿Te han hecho algo? #delay: 8 #caduca: D2 10:00
    ** [no, solo quería saberlo]
        Ali, si un día necesitas salir de esa casa, la mía está abierta. Día y noche. #delay: 300
    ** [(sin responder)]
        Ali, si un día necesitas salir de esa casa, la mía está abierta. Día y noche. #delay: 1
* [(sin responder)]
    Me quedo preocupada. Escríbeme, aunque sea un «ok». #delay: 1
- -> charla("D2 11:05") ->
-> d2

= d2
Ali, esta mañana he visto a tu madre en la oficina del banco. #at: D2 11:10
Con un señor mayor, barba blanca, muy bien vestido. Un gestor, por el traje. Ella firmaba y él miraba. #delay: 6
Ni me ha saludado. Me ha mirado como si no me conociera. #delay: 10
¿Tu madre tiene problemas de dinero, cariño? No quiero meterme, pero me preocupa. #delay: 15 #caduca: D3 10:00
* [dice que era para pagar a hacienda lo del piso de la yaya]
    ¿A Hacienda? #delay: 300
    Hacienda no te espera en la oficina con un señor de barba, cariño. Se paga por internet. #delay: 8
    Pero bueno. Yo qué sé. Esta noche hablo con tu padre. #delay: 10
* [no lo sé tía]
    Pues pregúntale. Hoy mismo. #delay: 300
* [(sin responder)]
- -> charla("D3 18:55") ->
-> d3

= d3
Ali, he hablado con tu padre. #at: D3 19:00
Está muy preocupado. Y yo también. #delay: 5
Mañana voy a ir a ver a tu madre. Aunque no me abra la puerta. #delay: 10
-> charla("D4 11:55") ->
-> d4

= d4
He ido a tu casa. Tu madre no me ha abierto. #at: D4 12:00
Las persianas bajadas a mediodía, y olía a incienso desde la calle. #delay: 5
Y había un coche gris aparcado con una rosa dorada pegada en el cristal. #delay: 6
Cariño, estoy muy asustada. #delay: 4
-> charla("D5 18:55") ->
-> d5

= d5
Tu padre llega mañana. Se queda en casa. #at: D5 19:00
Ali, si necesitas un sitio, aquí hay una cama y nadie te va a preguntar nada. #delay: 5
-> charla("D6 22:25") ->
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

// Huecos para escribirle. Marta es de la familia del padre: de la yaya Mercè (la madre de Montse) sabe poco y de oídas.
= charla(limite)
- (opciones)
#caduca: {limite}
* [tía, te acuerdas de las amigas de la yaya mercè?]
    ~ sabe_capsec = true
    ~ sabe_residencia = true
    Poco, cariño. La madre de tu madre y yo no nos tratábamos mucho. #delay: 300
    Sé que subía mucho al valle a ver a dos amigas de juventud. Las dos en masías, sin luz. #delay: 6
    Una era de Capsec, la Pepita de Can Pericot. Esa murió hace unos años. #delay: 5
    La otra, de por Sant Salvador. El nombre no me sale. Tu madre me dijo que está en una residencia. #delay: 6
    ¿Por qué lo preguntas? #delay: 4
    -> opciones
* {dia >= 2} [tía, si un día me hiciera falta, vendrías a buscarme sin decírselo a mamá?]
    Dime dónde y voy ahora mismo. Con el coche y con tu padre si hace falta. #delay: 300
    No hace falta que me expliques nada. #delay: 5
    -> opciones
* {dia >= 4} [tía, has vuelto a ver ese coche gris?]
    Esta mañana, delante del mercado. #delay: 300
    El de la barba hablaba con la Conxita, tu vecina. Le daba un folleto y ella asentía mucho. #delay: 6
    Yo de esa no me fiaría. Lo cuenta todo. #delay: 4
    -> opciones
* [te quiero tía]
    Y yo a ti, mi niña. Mucho. #delay: 300
    -> opciones
+ [(sin responder)]
- ->->
