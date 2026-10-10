// Biel, monitor del esplai. Tiene las llaves de Can Pericot y subió allí con su novia, Noa: la luz que vio Marc.
// Si se lo preguntas, lo confiesa y la pista de Capsec se cae (descarta_capsec). Miente fatal: contesta demasiado rápido.
// Ruido: videojuegos, Noa, el súper.

=== biel ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [biel 👀]
    qué #delay: 600
    por qué me miras así por whatsapp #delay: 3
    -> opciones
* {sabe_capsec and not descarta_capsec} [biel, las llaves del refugi las tienes tú, no?]
    ~ descarta_capsec = true
    ... #delay: 600
    vale sí #delay: 30
    subí con la noa hace unas noches. no se lo digas a pau que me mata #delay: 5
    lo dejamos un poco hecho un asco. ya lo limpiaré #delay: 5
    ah, y tu mochila sigue en el armario. no la tocamos. qué llevas ahí, una mudanza? #delay: 6
    -> opciones
* [biel qué tal el súper?]
    reponiendo yogures. mi vida es un lineal de lácteos #delay: 300
    -> opciones
* {dia >= 2} [biel, has hecho algo?]
    nada nada #delay: 5
    por? #delay: 3
    quién te ha dicho algo? #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [nadie. contestas muy rápido]
        es que tengo el móvil en la mano. jugando. al lol. nada más #delay: 60
        ...vale. luego hablamos #delay: 20
    ** [nada, solo preguntaba]
        ah vale vale. todo bien entonces 👍 #delay: 30
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [biel qué tal con la noa?]
    bien. muy bien. demasiado bien #delay: 300
    estamos buscando un sitio para estar tranquilos. en casa de sus padres no se puede ni respirar #delay: 5
    -> opciones
* {descarta_capsec} [biel, viste algo raro allí arriba?]
    nada. bueno #delay: 300
    al bajar, una furgo blanca parada en el cruce de capsec, con las luces apagadas. a las tres #delay: 6
    pensé que eran de la caza. nos asustamos y nos fuimos rápido #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
