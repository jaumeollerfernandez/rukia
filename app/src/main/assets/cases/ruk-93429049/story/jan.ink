// Jan, del teatro. Bromista sin filtro, buen fondo. Pista falsa: Alicia le preguntó por el piso vacío de su tío en
// Girona (sabe_girona). Ruido: fiestas de Girona, Pepe el Romano «que no sale».

=== jan ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [jan no tiene gracia lo de escaparse 🙄]
    perdóoon. ya me riñó queralt #delay: 300
    -> opciones
* {dia >= 2} [jan, tu tío aún tiene el piso de girona?]
    ~ sabe_girona = true
    el del barri vell? sí, vacío #delay: 300
    me lo preguntaste hace un mes, te acuerdas? te dije que sin problema #delay: 5
    las llaves están debajo del felpudo jajaja como siempre #delay: 4
    estás allí??? #delay: 30
    -> opciones
* [jan qué tal de pepe el romano?]
    el mejor papel del mundo. no salgo, no hablo, y todas se pelean por mí 😎 #delay: 300
    dolors dice que es el personaje más importante de la obra. yo creo que lo dice para que no me vaya #delay: 5
    -> opciones
* {dia >= 2} [jan, alguien más sabe lo del piso?]
    ehhh #delay: 300
    se lo dije a la mireia una vez. que buscaba sitio para estar tranquila con sergi. hace semanas #delay: 5
    y a ti. y a medio grupo de girona. soy una tumba jajaja #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [jan!!]
        perdóoon x2. no lo volveré a hacer. probablemente #delay: 300
    ** [y fue alguien?]
        ni idea. mi tío solo va en navidad. hay dos tazas en el fregadero desde hace siglos, si eso te dice algo #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [jan cuándo es la próxima fiesta en girona?]
    TODAS LAS NOCHES 🎉 #delay: 300
    vente y te presento a gente que no hace teatro. gente normal. creo #delay: 4
    -> opciones
* {dia >= 4} [jan gracias por no preguntar]
    yo nunca pregunto, por eso me cuentan todo jajaja #delay: 300
    vale. eso no ha quedado bien. pero lo digo con cariño 💛 #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
