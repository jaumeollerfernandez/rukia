// Judit, del vóley. Cotilla: repite el rumor de Barcelona (pista falsa). Bocazas sin maldad.
// Más despiste: también ha oído lo de Girona y lo de un «novio secreto». Ruido: rodilleras, el partido.

=== judit ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [judit 🙊]
    mira quién aparece #delay: 120
    ali te lo tengo que preguntar. es verdad que te has ido a barcelona con un tío de 22? #delay: 5
    lo dice todo el vestuario #delay: 3
    -> opciones
* {dia >= 2} [quién dice eso?]
    ~ dani_sospechoso = true
    la prima de la ex del dani ese. el de la moto #delay: 120
    dice que él se fue a barcelona esa misma noche. justo esa #delay: 5
    blanco y en botella 🥛 #delay: 3
    -> opciones
* [judit has visto mis rodilleras?]
    las viejas? las tiré, daban asco #delay: 120
    me he comprado unas nuevas. negras. preciosas. 45 pavos 😍 #delay: 4
    -> opciones
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
* {dia >= 5} [cómo fue el partido?]
    perdimos 😭 #delay: 120
    sin la reina de la colocación somos un desastre #delay: 3
    ona lo hizo bien eh. pero tú eres tú #delay: 4
    -> opciones
+ [(sin responder)]
    -> charla
