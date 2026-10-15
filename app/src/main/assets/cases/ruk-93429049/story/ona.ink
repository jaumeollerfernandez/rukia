// Ona, del vóley. Tiene en el maletero la bolsa de deporte de Alicia, con la lista de lo que se llevó (pienso para perro).
// Confianza (fia_ona): solo la pone a prueba si en el pueblo ya se dice que alguien usa el móvil (delatado >= 1): el dorsal
// (el 7, IMG_0394). Si recela, la mentira solo cuela si aún no hay más rumores; la verdad la asusta: bloquea y lo cuenta en
// el vóley (delata). Si Alicia le cuenta para quién es el pienso, confía y le da lo que hay detrás de la lista.
// Vida propia: el D4 la ponen de colocadora para el partido del D5 y está muerta de nervios. Escribe ella sola el D4 (tras
// la convocatoria) para pedir consejo, y el D5 (tras el partido) si Alicia se lo dio.

=== ona ===
-> historial

= historial
mañana me llevas al entreno? #from: me #at: D-4 13:00
sí!! paso a las 7 #at: D-4 13:05
-> charla

= charla
{ona_escribe() > 0 and hora >= ona_escribe():
    {dia == 4: -> nervios ->}
    {dia == 5: -> partido ->}
}
- (opciones)
#caduca: {caduca(ona_escribe())}
* {fia_ona > -1 and dia < 5} [ona 💛]
    ALI #delay: 120
    la colocadora ha vuelto?? 🏐 #delay: 3
    #caduca: D{dia + 1} 00:00
    ** [aún no. no sé si llego al partido]
        vale 😢 #delay: 120
        sonia está de los nervios. no le digas que te lo he dicho #delay: 5
    ** [ojalá]
        ojalá de verdad #delay: 120
        sin ti los entrenos son un drama. judit no para de hablar #delay: 6
    ** [(sin responder)]
    - - -> opciones
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
            pásalo bien. y llévate la toalla, que esta no 🤢 #delay: 5
        *** [(sin responder)]
    ** [no, déjala. ya la recogeré]
        vale. te la guardo #delay: 120
        pero la toalla la lavo eh, que el coche ya huele a vestuario #delay: 6
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
    #caduca: D{dia + 1} 00:00
    ** [sí. no se lo digas a nadie porfa]
        a quién se lo voy a decir 🥺 #delay: 120
        bueno a judit seguro que no jajaja #delay: 5
    ** [es una historia larga]
        vale vale #delay: 120
        cuando quieras me la cuentas. con un helado o algo #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {(dia >= 6 or (dia == 5 and hora >= 13)) and fia_ona > -1} [ona qué tal el partido?]
    1-3 😭 #delay: 120
    sonia dice que lo he hecho genial pero lo dice por decir #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [seguro que no. sonia no regala nada]
        eso es verdad jajaja #delay: 120
        vale me lo creo un poquito 🥹 #delay: 5
    ** [a la próxima ganáis]
        a la próxima juegas TÚ #delay: 120
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

= prueba
oye antes una cosa. carla dice que igual no eres tú la que escribe #delay: 120
qué dorsal llevas? #delay: 4
#caduca: D{dia + 1} 00:00
* [el 7]
    vale vale, perdona 😅 #delay: 120
* [el 9]
    -> recela ->
* [el 4]
    -> recela ->
* [(sin responder)]
    ~ fia_ona = -1
    no me contestas? 😕 #delay: 1800
- ->->

= recela
~ fia_ona = -1
... #delay: 120
el 7. siempre has llevado el 7 #delay: 5
quién eres? 😨 #delay: 4
->->

// D4, después de la convocatoria (voley.ink, 20:00): la ponen de colocadora.
= nervios
ali me ponen a mí de colocadora 😭 #at: D4 20:20
yo no sé hacer eso. yo recibo y ya #delay: 6
dame un consejo o algo porfa #delay: 4
#caduca: D{dia + 1} 00:00
* (consejo) [alta y al centro. judit remata todo lo que le pongas ahí]
    alta y al centro #delay: 120
    ALTA Y AL CENTRO. me lo apunto en la mano #delay: 5
* [lo vas a hacer genial, de verdad]
    eso lo dices porque me quieres 🥹 #delay: 120
    pero gracias #delay: 4
* [jajaja tú de colocadora? suerte 😂]
    gracias eh 🙃 #delay: 120
    ya sé que no soy tú. no hace falta que me lo recuerdes #delay: 8
* [y por qué no ponen a judit?]
    porque judit remata. si coloca judit, quién remata #delay: 120
    da igual. me toca a mí y ya #delay: 5
* [(sin responder)]
- ->->

// D5, después del partido (voley.ink, 13:00). Solo si Alicia le dio el consejo.
= partido
lo de alta y al centro funcionó #at: D5 13:40
una vez 😂 #delay: 4
pero esa vez judit la clavó. te lo dedico aunque hayamos perdido 💛 #delay: 6
->->

// La hora de hoy a la que Ona escribe por su cuenta (0: hoy no escribe).
=== function ona_escribe() ===
{
- fia_ona < 0:
    ~ return 0
- dia == 4 and not ona.nervios:
    ~ return 20
- dia == 5 and ona.nervios.consejo and not ona.partido:
    ~ return 13
- else:
    ~ return 0
}
