// Oriol, de clase. Sube puertos en bici de noche. Vio a una chica sola por la carretera de Bracons: el testigo clave.
// Su testimonio también está en Gonpi (D3), así que nunca se pierde del todo. Confianza (fia_oriol): solo la pone a prueba si
// ya corre que alguien usa el móvil (delatado >= 1): qué le pidió hace unas semanas (los apuntes de dinámicas). Si confía,
// cuenta lo que vio de la masía con el frontal. La verdad le da miedo pero colabora.

=== oriol ===
-> historial

= historial
ali me pasas los apuntes de dinámicas #at: D-20 21:10
te los mando luego #from: me #at: D-20 21:45
eres un sol #at: D-20 21:45
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {dia >= 2 and fia_oriol > -1} [oriol cuéntame lo de la chica de bracons]
    {delatado >= 1: -> prueba ->}
    {fia_oriol == -1: -> opciones}
    a la 1 y pico. pasado el desvío de sant salvador, ya en la subida #delay: 120
    andaba por el arcén con una linterna en la frente y una bolsa enorme. le pesaba un montón #delay: 5
    le pregunté si estaba bien y me hizo que sí con la mano. sin mirarme #delay: 5
    chaqueta verde, creo. por? #delay: 4
    -> opciones
* {dia >= 3 and fia_oriol > -1} [y no giró hacia ningún sitio?]
    seguía subiendo cuando la perdí de vista #delay: 120
    eso sí: un poco más arriba, en una masía, un perro ladraba como loco. a esas horas. mal rollo #delay: 6
    -> opciones
// Solo si confía.
* {dia >= 3 and fia_oriol >= 1} [oriol, te fijaste en la masía del perro?]
    con el frontal, de pasada. justo después de una curva cerrada con un banco de piedra #delay: 120
    un pozo delante, eso seguro. le di con la luz y brilló el agua #delay: 5
    la puerta no la vi. estaba oscuro #delay: 4
    -> opciones
* {dia >= 2 and fia_oriol > -1} [oriol, no le digas a nadie que me viste]
    eras tú??? 😳 #delay: 120
    ali qué hacías ahí a la una de la mañana #delay: 4
    vale, vale. no digo nada #delay: 30
    -> opciones
* {fia_oriol == -1} [oriol soy yo. estoy hecha polvo, no me acuerdo ni de lo que comí]
    {delatado >= 2:
        ~ fia_oriol = -2
        ~ delata()
        no cuela. lo siento #delay: 120
        no me escribas más #delay: 4
    - else:
        ~ fia_oriol = 0
        vale... perdona #delay: 120
        lo de bracons: a la 1 y pico, pasado el desvío de sant salvador. una chica con frontal y una bolsa enorme #delay: 6
    }
    -> opciones
* {fia_oriol == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_oriol = 1
    joder #delay: 120
    vale. lo que necesitéis. yo solo la vi esa noche #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla

= prueba
espera. dicen por ahí que alguien escribe desde tu móvil #delay: 120
si eres ali: qué te pedí hace unas semanas? #delay: 4
* [los apuntes de dinámicas]
    ~ fia_oriol = 1
    vale. perdona, estoy rayado #delay: 120
* [que te dejara la bici]
    -> recela ->
* [el libro de masías]
    -> recela ->
- ->->

= recela
~ fia_oriol = -1
... #delay: 120
no. eso no fue #delay: 4
quién eres? #delay: 4
->->
