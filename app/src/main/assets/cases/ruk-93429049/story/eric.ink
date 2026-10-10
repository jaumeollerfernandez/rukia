// Èric, compañero de la panadería, 19 años. Le gusta Alicia y lo disimula fatal. Primera pista del pan.

=== eric ===
-> historial

= historial
mañana me traes un café y te perdono lo de la masa madre jajaja #at: D-3 13:40
la masa madre se murió sola!! #from: me #at: D-3 15:02
asesina #at: D-3 15:02
-> d1

= d1
eh #at: D1 14:10
te he cubierto el turno de ayer. me debes una birra 🍺 #delay: 3
pilar estaba que trinaba #delay: 5
por cierto dice que faltan seis barras de payés del cierre del otro día #delay: 30
yo no he dicho nada eh 🤐 #delay: 3 #caduca: D2 13:00
* [gracias eric, te debo una enorme]
    dos birras #delay: 120
    y me cuentas qué te pasa, que se te nota #delay: 6
* [qué barras?]
    ~ sabe_pan = true
    las del cierre. el día que cerraste tú #delay: 90
    pilar las cuenta siempre, ya sabes cómo es #delay: 5
    yo le he dicho que se habrían quemado, pero no se lo ha tragado #delay: 6
    tranqui que no pasa nada. pero si eran para alguien, avísame y le digo algo #delay: 10
* [(sin responder)]
    vale, ya me contarás 🍞 #delay: 1
- -> charla("D2 13:15") ->
-> d2

= d2
has visto mi post 🕵️🥖 jajaja #at: D2 13:20
pilar me ha echado una bronca... pero ya lo ha visto medio olot #delay: 4
oye ahora en serio #delay: 600
estás bien? se te echa de menos por aquí. bueno, te echo de menos yo #delay: 8
no he dicho eso #delay: 2 #caduca: D3 13:00
* [jajaja sí lo has dicho]
    borrado. no existe #delay: 60
    cuídate anda #delay: 4
* [estoy bien eric. gracias por cubrirme]
    a mandar #delay: 60
* [(sin responder)]
- -> charla("D3 15:55") ->
-> d3

= d3
oye #at: D3 16:00
pilar está rarísima hoy. te ha llamado? #delay: 3
me ha preguntado si alguna vez te he visto llevar pan a «la señora de arriba» #delay: 5
ni idea de qué habla 🤷 #delay: 4
-> charla("D4 14:25") ->
-> d4

= d4
pilar dice que si vuelves, el puesto es tuyo #at: D4 14:30
y yo también te espero. para la birra digo 🙃 #delay: 4
-> charla("D5 10:55") ->
-> d5

= d5
{not arnau_calla:
    ali la he liado #at: D5 11:00
    ha venido tu hermana y le he dicho lo de «la señora de arriba» sin pensar #delay: 4
    me ha sonreído de una manera rarísima #delay: 5
    perdóname #delay: 3
- else:
    hoy hemos hecho cocas de chicharrones. te guardo una 🍞 #at: D5 13:00
}
-> d6

= d6
// D6: nada.
-> d7

= d7
// D7: nada.
-> DONE

// Huecos para escribirle. Èric lo cuenta todo sin darse cuenta: la pista del pienso (cierta) y la de Capsec (falsa).
// Confianza (fia_eric): preguntarle qué dijo Pilar es un desliz (Alicia estaba delante), y la pone a prueba con la masa madre
// (le debe un café, historial D-3). Si confía, cuenta para quién decía Alicia que era el pan (la Rosalia). Si recela,
// la mentira solo cuela sin rumores; la verdad la cree, pero es un bocazas y lo cuenta en el Forn (delata).
= charla(limite)
- (opciones)
#caduca: {limite}
* [eric porfa lo de las barras no se lo cuentes a nadie más]
    ali ya lo sabe medio olot por mi post 😅 #delay: 120
    perdón. lo archivo #delay: 4
    -> opciones
* {dia >= 2 and fia_eric > -1} [eric te pido un favor. si alguien pregunta por mí, tú no sabes nada]
    ~ sabe_capsec = true
    yo nunca sé nada, es mi superpoder #delay: 120
    oye y ahora que lo dices #delay: 30
    el mes pasado me pediste que te subiera en moto al refugio del esplai, en capsec. el mas de la puerta azul #delay: 6
    que ibas a dejar unas cosas para una acampada. una mochila enorme #delay: 5
    tiene que ver con esto? #delay: 4
    -> opciones
* {dia >= 3 and fia_eric > -1} [qué te dijo pilar exactamente de la señora de arriba?]
    si estabas delante jajaja #delay: 120
    oye estás rarísima. a ver, qué me debes por lo de la masa madre? #delay: 5
    ** [un café]
        ~ fia_eric = 1
        y con leche de avena, que te conozco ☕ #delay: 120
    ** [nada. se murió sola!!]
        ~ fia_eric = 1
        asesina 😂 vale, eres tú #delay: 120
    ** [una birra]
        ~ fia_eric = -1
        la birra es por el turno. lo de la masa madre era un café #delay: 120
        ali? eres tú? #delay: 5
    - - {fia_eric == -1: -> opciones}
    ~ sabe_pan = true
    que alguna vez te vio salir con barras de más y le dijiste que eran para «la señora de arriba» #delay: 120
    y que un día le pediste un saco de pienso de la tienda de al lado. para perro grande #delay: 5
    pilar dice que será una abuela de alguna masía. arriba de dónde, ni idea #delay: 5
    -> opciones
* {fia_eric == -1} [eric soy yo. me equivoco con todo, no duermo]
    {delatado >= 2:
        ~ fia_eric = -2
        ~ delata()
        en el forn ya dicen que alguien tiene tu móvil #delay: 120
        lo siento. no te voy a contar nada más #delay: 4
    - else:
        ~ fia_eric = 0
        vale. perdona. me he rayado #delay: 120
        ~ sabe_pan = true
        lo de pilar: que te vio salir con barras de más para «la señora de arriba». y un saco de pienso para perro grande #delay: 6
    }
    -> opciones
* {fia_eric == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_eric = 1
    ~ delata()
    la policía??? #delay: 60
    vale vale. lo que sea. pero esto se lo tengo que contar a pilar #delay: 5
    -> opciones
// Solo si confía.
* {fia_eric >= 1 and dia >= 3} [eric, alguna vez te dije para quién era el pan?]
    ~ sabe_rosalia = true
    una vez. «para la rosalia, que si no le sube nadie, no baja» #delay: 120
    pensé que era una abuela de tu familia #delay: 5
    -> opciones
* {dia >= 2 and fia_eric > -1} [estoy bien eric. de verdad]
    vale. te creo #delay: 120
    más o menos #delay: 3
    -> opciones
+ [(sin responder)]
- ->->
