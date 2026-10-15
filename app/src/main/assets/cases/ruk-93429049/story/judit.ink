// Judit, del vóley. Cotilla: repite el rumor de Barcelona (pista falsa). Bocazas sin maldad.
// Más despiste: también ha oído lo de Girona y lo de un «novio secreto». Ruido: rodilleras, el partido.
// Vida propia: sus rodilleras nuevas (45 pavos) y el partido del D5.
// Escribe ella sola el D2 para preguntar por el rumor, si Alicia no le ha escrito antes.

=== judit ===
-> charla

= charla
{judit_escribe() > 0 and hora >= judit_escribe():
    -> rumor ->
}
- (opciones)
#caduca: {caduca(judit_escribe())}
* (hola) {not rumor} [judit 🙊]
    mira quién aparece #delay: 120
    ali te lo tengo que preguntar. es verdad que te has ido a barcelona con un tío de 22? #delay: 5
    lo dice todo el vestuario #delay: 3
    -> contesta ->
    -> opciones
* {dia >= 2 and (hola or rumor)} [quién dice eso?]
    ~ dani_sospechoso = true
    la prima de la ex del dani ese. el de la moto #delay: 120
    dice que él se fue a barcelona esa misma noche. justo esa #delay: 5
    blanco y en botella 🥛 #delay: 3
    -> opciones
* [judit has visto mis rodilleras?]
    las tuyas? en tu bolsa, en el maletero de ona. desde el último entreno #delay: 120
    yo me he comprado unas nuevas. negras. preciosas. 45 pavos 😍 #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [y siguen ahí?]
        sí. y tu toalla también. pobre ona, su coche huele a vestuario de 2019 🤢 #delay: 120
        te dejo las mías un día. uno #delay: 4
    ** [45 pavos por unas rodilleras??]
        son una inversión 😎 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [qué más se dice de mí?]
    uff #delay: 120
    que si girona, que si una clínica, que si un novio secreto mayor... #delay: 4
    y la carla dice que te vieron subir a una furgo blanca. pero la carla también dijo que la de 2.º era hija de un futbolista #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [lo de girona no es verdad]
        ~ sabe_girona = true
        vale vale. pues díselo a la carla porque lo cuenta con pelos y señales jajaja #delay: 120
    ** [quién dice lo de la furgo?]
        la carla. que se lo dijo su prima. que se lo dijo no sé quién. ya sabes 🙄 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [judit, no lo cuentes todo porfa]
    yo??? 🙊 #delay: 120
    vale. perdón. lo intento #delay: 4
    pero si me cuentas algo, bueno, que no me lo cuentes jajaja #delay: 4
    -> opciones
* {(dia >= 6 or (dia == 5 and hora >= 13))} [cómo fue el partido?]
    perdimos 😭 #delay: 120
    sin la reina de la colocación somos un desastre #delay: 3
    ona lo hizo bien eh. pero tú eres tú #delay: 4
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// Lo que contesta Alicia al rumor de Barcelona.
= contesta
#caduca: D{dia + 1} 00:00
* [no. y no me he ido con nadie]
    vale vale 🙊 #delay: 120
    pues alguien se lo inventa muy bien #delay: 4
* [jajaja ojalá]
    JAJAJA ali #delay: 120
    eso es un sí o un no #delay: 3
* [y a ti qué más te da]
    uy. vale. perdón 🙊 #delay: 120
    solo preguntaba #delay: 3
* [(sin responder)]
- ->->

// D2: Judit no aguanta más sin preguntar.
= rumor
ali #at: D2 21:30
te lo tengo que preguntar #delay: 3
es verdad que te has ido a barcelona con un tío de 22?? 👀 #delay: 4
lo dice todo el vestuario #delay: 3
-> contesta ->
->->

// La hora de hoy a la que Judit escribe por su cuenta (0: hoy no escribe).
=== function judit_escribe() ===
{dia == 2 and not judit.charla.hola and not judit.rumor:
    ~ return 21
}
~ return 0
