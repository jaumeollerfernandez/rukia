// Iker, 27, relaciones públicas de un club del Port Olímpic (Barcelona). Amigo de Dani. Número oculto: aparece el D2.
// Hasta el D3 todo en él suena a la red de trata que investiga Barcelona (piso pagado, «trae el DNI», te recojo en Nord,
// «mejor no lo comentes en casa»), y parece una pista falsa: el plan para Alicia era de Dani. Pero es verdad a medias:
// Iker es el contacto de Dani con la parte de la red que no cayó. Cuando Dani no puede pagar su deuda, Iker le presenta
// a los del Audi, les pasa las fotos de Alicia y le escribe a ella para «ayudar a encontrarla» (D5-D6).

=== iker ===
-> historial

= historial
hola alicia! soy iker, el amigo de dani 😊 #at: D-14 19:20
me ha dicho que te interesa lo del club. cuando quieras te lo explico #at: D-14 19:21
quién te ha dado mi número #from: me #at: D-14 22:40
dani jaja. tranqui, es todo legal. 1.500 al mes y la habitación del piso pagada #at: D-14 22:41
de momento mejor no lo comentes en casa. a los padres esto de la noche les asusta 🙈 #at: D-14 22:41
me lo pienso #from: me #at: D-13 09:15
-> d2

= d2
hola guapa 😊 #at: D2 21:10
dani me ha dicho que igual ya estás por aquí #delay: 5
la habitación sigue libre. cuando llegues a nord me escribes y te recojo #delay: 6
trae el dni, que para el contrato hace falta 😘 #delay: 4 #caduca: D3 03:00
* [qué contrato exactamente?]
    relaciones públicas. puerta y reservados #delay: 300
    te lo explico en persona, por aquí mejor no 😉 #delay: 5
* [no estoy en barcelona]
    ah. dani me dijo que sí jaja #delay: 300
    bueno, la oferta sigue. pero no tardes, que hay más chicas #delay: 5
* [(sin responder)]
- -> d3

= d3
oye #at: D3 23:50
hoy ha venido la policía al club. nada que ver contigo eh, cosas de licencias #delay: 5
si alguien te pregunta por mí, no me conoces 🙈 #delay: 6 #caduca: D4 14:00
* [por qué no te iba a conocer?]
    porque no hace falta complicarse, guapa 😘 #delay: 600
    tú y yo solo hemos hablado de trabajo. y ni eso #delay: 5
* [qué policía?]
    la de siempre. papeles, licencias, horarios #delay: 600
    nada que ver contigo. ni conmigo 😉 #delay: 4
* [(sin responder)]
- -> d4

// Ya sabe lo de la deuda. Ahora la busca para la red, con buenas palabras.
= d4
alicia, una cosa #at: D5 01:10
dani está rarísimo. está muy preocupado por ti 🙈 #delay: 5
si estás en algún sitio y necesitas salir, yo te mando a alguien. sin preguntas #delay: 6
solo dime por qué zona estás 😊 #delay: 4 #caduca: D5 14:00
* [por qué te importa dónde estoy?]
    ~ sabe_dani_busca = true
    porque dani me lo ha pedido. está desesperado, guapa #delay: 600
    te busca por todo el valle. pregunta a todo el mundo #delay: 5
    yo solo quiero ayudar 😘 #delay: 4
* [no voy a ir]
    vale guapa. suerte 😘 #delay: 600
* [(sin responder)]
- -> d6

// La víspera de la entrega: aprieta.
= d6
#at: D6 10:30
{dani_detenido and not dani_creido: -> d7}
guapa
la habitación ya está lista. y dani también 😉 #delay: 5
esta tarde paso por la zona. si me dices dónde estás, te recojo yo mismo #delay: 6
nadie de tu familia se va a enterar #delay: 4 #caduca: D6 18:00
* [no me busques]
    nadie te busca, guapa #delay: 600
    solo queremos ayudar 😘 #delay: 3
* [dile a dani que no]
    díselo tú #delay: 600
    a mí no me metas en vuestras cosas 🙈 #delay: 4
* [(sin responder)]
- -> d7

// Si ya se la han llevado, el número deja de existir: no vuelve a escribir.
= d7
#at: D7 09:00
{
- alicia_entregada:
    -> DONE
- dani_no_creido:
    se han llevado a dani a barcelona. dicen que por lo de la red del puerto
    yo no sé nada de esa gente. te lo juro #delay: 6
    si alguien te pregunta por mí, no me conoces #delay: 5
- dani_detenido:
    me han llamado los mossos por dani
    les he dicho que yo solo tenía una habitación. que miren lo que quieran #delay: 6
}
-> DONE
