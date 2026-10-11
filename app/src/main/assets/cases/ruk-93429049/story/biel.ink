// Biel, monitor del esplai. Tiene las llaves de Can Pericot y subió allí con su novia, Noa: la luz que vio Marc.
// Si se lo preguntas, lo confiesa y la pista de Capsec se cae (descarta_capsec). Miente fatal: contesta demasiado rápido.
// Ruido: videojuegos, Noa, el súper.
// Vida propia: el súper, el lol y buscar con Noa un sitio donde estar tranquilos.
// Escribe él solo el D3, tras el mensaje de Pau por las llaves: nervioso, sin confesar nada.

=== biel ===
-> charla

= charla
{biel_escribe() > 0 and hora >= biel_escribe():
    -> nervioso ->
}
- (opciones)
#caduca: {caduca(biel_escribe())}
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
    #caduca: D{dia + 1} 00:00
    ** [no se lo digo a pau, tranquilo]
        te quiero. te debo una #delay: 120
        bueno, dos #delay: 4
    ** [se lo tienes que decir tú]
        ya #delay: 120
        vale. cuando acabe la salida. si sobrevivo a los peques #delay: 5
    ** [la mochila no la toquéis]
        ni la hemos mirado #delay: 120
        bueno la noa un poco. por fuera. solo por fuera 😅 #delay: 5
    ** [(sin responder)]
    - - -> opciones
* [biel qué tal el súper?]
    reponiendo yogures. mi vida es un lineal de lácteos #delay: 300
    #caduca: D{dia + 1} 00:00
    ** [algún día serás encargado]
        no me desees eso #delay: 120
    ** [jajaja qué drama]
        drama es el de los bífidus. caducan todos a la vez #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2 and not descarta_capsec} [biel, has hecho algo?]
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
    {descarta_capsec:
        ya sabes dónde nos escondemos 😅 #delay: 5
    - else:
        estamos buscando un sitio para estar tranquilos. en casa de sus padres no se puede ni respirar #delay: 5
    }
    #caduca: D{dia + 1} 00:00
    ** {not descarta_capsec} [y lo habéis encontrado?]
        eh #delay: 60
        no. todavía no. bueno. más o menos #delay: 5
        cambiando de tema #delay: 3
    ** [qué bonito biel]
        calla 😅 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {descarta_capsec} [biel, viste algo raro allí arriba?]
    nada. bueno #delay: 300
    al bajar, una furgo blanca parada en el cruce de capsec, con las luces apagadas. a las tres #delay: 6
    pensé que eran de la caza. nos asustamos y nos fuimos rápido #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [te fijaste en la matrícula?]
        a las tres de la mañana y con la noa gritando que arrancara? no #delay: 120
    ** [seguro que eran cazadores]
        ya. seguro. #delay: 120
        aunque los cazadores no apagan las luces #delay: 5
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D3, después de que Pau pregunte por las llaves en el grupo (esplai.ink, 19:30).
= nervioso
oye ali #at: D3 20:10
tú no habrás dicho nada a pau de nada no? #delay: 4
#caduca: D{dia + 1} 00:00
* [de qué?]
    de nada nada #delay: 30
    olvídalo #delay: 3
* [decir qué, biel?]
    nada. que pau está rayado con las llaves y yo qué sé #delay: 30
    yo no tengo nada que ver eh #delay: 4
* [(sin responder)]
- ->->

// La hora de hoy a la que Biel escribe por su cuenta (0: hoy no escribe).
=== function biel_escribe() ===
{dia == 3 and not descarta_capsec and not biel.nervioso:
    ~ return 20
}
~ return 0
