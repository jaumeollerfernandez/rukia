// Iker, 27, relaciones públicas de un club del Port Olímpic (Barcelona). Amigo de Dani. Número oculto: aparece el D2.
// Pista falsa: todo en él suena a la red de trata que investiga Barcelona (piso pagado, «trae el DNI», te recojo en Nord,
// «mejor no lo comentes en casa»). Pero el trabajo es real y legal, aunque cutre. El que tenía un plan para Alicia era Dani:
// le pasó su número (D-15) para llevársela a Barcelona, con él. Iker solo quería llenar la puerta del club.

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
si alguien te pregunta por mí, no me conoces 🙈 #delay: 6
-> d4

= d4
alicia, una cosa #at: D5 01:10
dani está rarísimo. dice que si no vienes es por culpa de un tal pol #delay: 5
yo paso de líos. si no vienes dímelo y le doy la habitación a otra #delay: 6 #caduca: D5 14:00
* [no voy a ir]
    vale guapa. suerte 😘 #delay: 600
* [(sin responder)]
- -> d7

= d7
{
- dani_no_creido:
    se han llevado a dani a barcelona. dicen que por lo de la red del puerto #at: D7 09:00
    yo solo le iba a dar curro a una chica. te lo juro #delay: 6
    el club está limpio. que miren lo que quieran #delay: 5
- dani_detenido:
    me han llamado los mossos por dani #at: D7 09:00
    le he dicho la verdad. que él quería que vinieras. yo solo tenía una habitación #delay: 6
}
-> DONE
