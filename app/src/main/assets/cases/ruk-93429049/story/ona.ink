// Ona, del vóley. Tiene en el maletero la bolsa de deporte de Alicia, con la lista de lo que se llevó (pienso para perro).
// Confianza (fia_ona): solo la pone a prueba si en el pueblo ya se dice que alguien usa el móvil (delatado >= 1): el dorsal
// (el 7, IMG_0394). Si recela, la mentira solo cuela si aún no hay más rumores; la verdad la asusta: bloquea y lo cuenta en
// el vóley (delata). Si Alicia le cuenta para quién es el pienso, confía y le da lo que hay detrás de la lista.

=== ona ===
-> historial

= historial
mañana me llevas al entreno? #from: me #at: D-4 13:00
sí!! paso a las 7 #at: D-4 13:05
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {fia_ona > -1} [ona 💛]
    ALI #delay: 120
    la colocadora ha vuelto?? 🏐 #delay: 3
    -> opciones
* {dia >= 2 and fia_ona > -1} [ona me dejé algo en tu coche?]
    {delatado >= 1: -> prueba ->}
    {fia_ona == -1: -> opciones}
    tu bolsa de deporte!! la tengo en el maletero desde el último entreno #delay: 120
    la abro? #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [sí, mira qué hay]
        ~ sabe_pienso = true
        rodilleras, una toalla que huele fatal y una lista en un papel #delay: 300
        «pan, velas, pilas, cerillas, pienso perro grande, tiritas, pastillas tos» #delay: 8
        vas de acampada o qué 😂 #delay: 4
        y desde cuándo tienes perro? #delay: 5
        #caduca: D{dia + 1} 00:00
        *** [no es para mí. es para la perra de una señora mayor que vive sola]
            ~ fia_ona = 1
            ay qué mona eres 🥺 #delay: 120
        *** [me voy de acampada con unos amigos]
            con perro y todo jajaja vale #delay: 120
        *** [(sin responder)]
    ** [no, déjala. ya la recogeré]
        vale. te la guardo #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {fia_ona == -1} [ona tía soy yo. qué te pasa?]
    {delatado >= 2:
        ~ fia_ona = -2
        ~ delata()
        no. ya no me lo creo #delay: 120
        no me escribas más porfa. me das miedo #delay: 4
    - else:
        ~ fia_ona = 0
        ... #delay: 120
        perdona ali. es que carla nos tiene a todas paranoicas 😅 #delay: 5
        tu bolsa sigue en mi maletero, eh. la he abierto #delay: 30
        ~ sabe_pienso = true
        había una lista: «pan, velas, pilas, cerillas, pienso perro grande, tiritas, pastillas tos». vas de acampada o qué #delay: 8
    }
    -> opciones
* {fia_ona == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_ona = -2
    ~ delata()
    qué??? #delay: 60
    me das miedo. no me escribas más #delay: 4
    se lo voy a contar a sonia #delay: 5
    -> opciones
// Solo si confía: lo que había detrás de la lista.
* {fia_ona >= 1 and dia >= 3} [ona, la lista tenía algo más?]
    ~ sabe_ruta_lotes = true
    detrás, con tu letra: «sábado: lote + pienso → R. (subida bracons)» #delay: 120
    quién es R.? la señora de la perra? 🥺 #delay: 5
    -> opciones
* {dia >= 4 and fia_ona > -1} [ona qué tal de colocadora?]
    fatal. sonia me grita #delay: 120
    vuelve 😭 #delay: 3
    -> opciones
+ [(sin responder)]
    -> charla

= prueba
oye antes una cosa. carla dice que igual no eres tú la que escribe #delay: 120
qué dorsal llevas? #delay: 4
* [el 7]
    vale vale, perdona 😅 #delay: 120
* [el 9]
    -> recela ->
* [el 4]
    -> recela ->
- ->->

= recela
~ fia_ona = -1
... #delay: 120
el 7. siempre has llevado el 7 #delay: 5
quién eres? 😨 #delay: 4
->->
