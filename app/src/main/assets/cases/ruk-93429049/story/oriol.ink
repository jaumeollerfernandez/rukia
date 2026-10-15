// Oriol, de clase. Sube puertos en bici de noche. Vio a una chica sola por la carretera de Bracons: el testigo clave.
// Su testimonio también está en Gonpi (D3), así que nunca se pierde del todo. Confianza (fia_oriol): solo la pone a prueba si
// ya corre que alguien usa el móvil (delatado >= 1): qué le pidió hace unas semanas (los apuntes de dinámicas). Si confía,
// cuenta lo que vio de la masía con el frontal. La verdad le da miedo pero colabora.
// Vida propia: ayuda en el taller de bicis de su padre y prepara su próxima ruta nocturna (el Coll de Bracons por la otra cara).
// Escribe él solo el D4 si nadie le ha preguntado por la chica: «no serías tú no?».

=== oriol ===
-> historial

= historial
ali me pasas los apuntes de dinámicas #at: D-20 21:10
te los mando luego #from: me #at: D-20 21:45
eres un sol #at: D-20 21:45
-> charla

= charla
{oriol_escribe() > 0 and hora >= oriol_escribe():
    -> duda ->
}
- (opciones)
#caduca: {caduca(oriol_escribe())}
// Preguntar por «la chica» como si fuera otra, o decirle que era ella: después de confesarlo, ya no se pregunta así.
* (chica) {sabe_chica() and fia_oriol > -1 and not era_yo and not duda} [oriol cuéntame lo de la chica de bracons]
    {delatado >= 1 and fia_oriol < 1: -> prueba ->}
    {fia_oriol == -1: -> opciones}
    -> lo_que_vio(false) ->
    por? #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [por nada. me ha dado curiosidad]
        ya. a todos os da curiosidad y luego nadie me cree #delay: 120
    ** [igual la conozco]
        pues dile que la próxima vez lleve chaleco reflectante. casi me la como #delay: 120
    ** [(sin responder)]
    - - -> opciones
* (era_yo) {sabe_chica() and fia_oriol > -1 and not duda} [oriol, no le digas a nadie que me viste]
    eras tú??? 😳 #delay: 120
    ali qué hacías ahí a la una de la mañana #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [no te lo puedo contar]
        vale, vale. no digo nada #delay: 30
        pero me debes una explicación. y un café #delay: 5
    ** [subía a ver a alguien]
        a la una. a pie. con una bolsa que pesaba más que tú #delay: 60
        vale. no digo nada. pero ten cuidado, que esa carretera de noche es mala #delay: 6
    ** [(sin responder)]
        ... #delay: 600
        vale. no digo nada #delay: 5
    - - -> opciones
* {(era_yo or duda.yo) and not chica and fia_oriol > -1} [qué viste exactamente esa noche?]
    {delatado >= 1 and fia_oriol < 1: -> prueba ->}
    {fia_oriol == -1: -> opciones}
    pues a ti. supongo #delay: 120
    -> lo_que_vio(true) ->
    -> opciones
* (giro) {dia >= 3 and fia_oriol > -1 and (chica or era_yo or duda)} [{era_yo or duda.yo:y no me viste girar hacia ningún sitio?|y no giró hacia ningún sitio?}]
    {era_yo or duda.yo:seguías subiendo cuando te perdí de vista|seguía subiendo cuando la perdí de vista} #delay: 120
    eso sí: un poco más arriba, en una masía, un perro ladraba como loco. a esas horas. mal rollo #delay: 6
    -> opciones
// Solo si confía.
* {giro and fia_oriol >= 1} [oriol, te fijaste en la masía del perro?]
    con el frontal, de pasada. justo después de una curva cerrada con un banco de piedra #delay: 120
    un pozo delante, eso seguro. le di con la luz y brilló el agua #delay: 5
    la puerta no la vi. estaba oscuro #delay: 4
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
        lo de bracons: a la 1 y pico, pasado el desvío de joanetes. una chica con frontal y una bolsa enorme #delay: 6
    }
    -> opciones
* (mossos) {fia_oriol == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_oriol = 1
    joder #delay: 120
    vale. lo que necesitéis. yo solo la vi esa noche #delay: 5
    -> opciones
* {dia >= 2 and fia_oriol > -1} [oriol, qué ruta haces ahora?]
    bracons por la otra cara. desde joanetes #delay: 120
    de noche, obvio. de día hace calor y hay coches #delay: 4
    mi padre dice que un día me encontrará un jabalí y me lo tendré merecido 🚴🌙 #delay: 5
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// [a_ti]: se lo cuenta a la chica que vio.
= lo_que_vio(a_ti)
a la 1 y pico. pasado el desvío de joanetes, ya en la subida #delay: 120
{a_ti:
    andabas por el arcén con una linterna en la frente y una bolsa enorme. te pesaba un montón #delay: 5
    te pregunté si estabas bien y me hiciste que sí con la mano. sin mirarme #delay: 5
    y la chaqueta verde. eso sí que lo vi #delay: 4
- else:
    andaba por el arcén con una linterna en la frente y una bolsa enorme. le pesaba un montón #delay: 5
    le pregunté si estaba bien y me hizo que sí con la mano. sin mirarme #delay: 5
    chaqueta verde, creo #delay: 4
}
->->

= prueba
espera. dicen por ahí que alguien escribe desde tu móvil #delay: 120
si eres ali: qué te pedí hace unas semanas? #delay: 4
#caduca: D{dia + 1} 00:00
* [los apuntes de dinámicas]
    ~ fia_oriol = 1
    vale. perdona, estoy rayado #delay: 120
* [que te dejara la bici]
    -> recela ->
* [el libro de masías]
    -> recela ->
* [(sin responder)]
    ~ fia_oriol = -1
    vale. ya veo #delay: 1800
- ->->

= recela
~ fia_oriol = -1
... #delay: 120
no. eso no fue #delay: 4
quién eres? #delay: 4
->->

// D4, si nadie le ha preguntado por la chica de Bracons.
= duda
oye ali #at: D4 22:10
igual es una tontería #delay: 4
pero la chica de bracons. no serías tú no? #delay: 5
#caduca: D{dia + 1} 00:00
* (yo) [sí. y no se lo digas a nadie]
    lo sabía 😳 #delay: 120
    no digo nada. te lo juro por mi bici #delay: 5
* [no. yo esa noche dormía]
    vale vale. es que se te parecía mucho #delay: 120
    y llevaba una chaqueta verde como la tuya #delay: 4
* [qué chica?]
    la de mi ruta. lo puse en gonpi #delay: 120
    nada, olvídalo. ya sé que nadie me cree #delay: 4
* [(sin responder)]
- ->->

// La hora de hoy a la que Oriol escribe por su cuenta (0: hoy no escribe).
=== function oriol_escribe() ===
{dia == 4 and fia_oriol >= 0 and not oriol.charla.mossos and not oriol.charla.chica and not oriol.charla.era_yo and not oriol.duda:
    ~ return 22
}
~ return 0

// Alicia ya ha oído hablar de la chica de Bracons: el comentario de Oriol (clase.ink, D3 14:25) o su respuesta en el grupo (D2).
=== function sabe_chica() ===
~ return clase.d2.quien or dia >= 4 or (dia == 3 and hora >= 15)
