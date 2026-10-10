// Teresa, la bibliotecaria. Discreta: no se lo cuenta a nadie. Alicia usaba el ordenador de la biblioteca para lo que
// no quería que se viera en casa: el billete de bus (señuelo), Camprodon (señuelo), las residencias y el libro de masías.
// Confianza (fia_teresa): preguntarle qué libros sacó es un desliz, así que la prueba con el libro que debe («Nada»,
// grupo de lectura). Si confía, más adelante le da la fotocopia del mas de 1782 (la puerta azul). Si recela, una mentira
// solo cuela si el pueblo aún no habla del móvil; la verdad sí funciona: Teresa es discreta y colabora.

=== teresa ===
-> historial

= historial
Alicia, te has dejado el pendrive en el ordenador 2. Te lo guardo en el mostrador. #at: D-3 20:05
gracias teresa!! no se lo des a nadie porfa #from: me #at: D-3 20:30
Descuida. #at: D-3 20:31
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {fia_teresa > -1} [teresa perdona por lo del libro]
    No sufras, mujer. #delay: 300
    -> opciones
* {fia_teresa > -1} [teresa aún tienes mi pendrive?]
    ~ sabe_residencia = true
    Sí, en el cajón del mostrador. No se lo he dado a nadie. #delay: 300
    Ya que lo dices: el ordenador 2 tenía tus búsquedas abiertas. Las cerré, pero vi algo. #delay: 6
    Horarios de autobús de Olot a Camprodon. Y la web de la residencia Sant Jaume, la página de «familiares». #delay: 8
    No es asunto mío. Te lo digo por si no querías dejarlo a la vista. #delay: 5
    -> opciones
* {dia >= 2 and fia_teresa > -1} [teresa qué libros saqué la última vez?]
    ¿No te acuerdas? Qué cosas. #delay: 300
    A ver, dime tú cuál me debes todavía, que hay lista de espera. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [«nada». ya te lo devuelvo, perdona]
        ~ fia_teresa = 1
        Ese. Que hay tres señoras esperándolo. #delay: 120
        -> libros ->
    ** [«la casa de bernarda alba»]
        -> recela ->
    ** [«el jardín olvidado»]
        -> recela ->
    ** [(sin responder)]
    - - -> opciones
* {fia_teresa == -1} [teresa soy yo, de verdad. tengo la cabeza en otra parte]
    {delatado >= 2:
        ~ fia_teresa = -2
        ~ delata()
        En el pueblo ya se dice que alguien escribe desde su móvil. #delay: 300
        No voy a hablar más con usted. #delay: 4
    - else:
        ~ fia_teresa = 0
        Puede ser. Con lo que estás pasando. #delay: 300
        Perdona la desconfianza, Alicia. Es la costumbre de mirar fichas. #delay: 5
        -> libros ->
    }
    -> opciones
* {fia_teresa == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_teresa = 1
    Lo suponía. #delay: 300
    Pregunte lo que necesite. Y descuide: en esta biblioteca no se cuenta nada a nadie. #delay: 6
    -> libros ->
    -> opciones
// Solo si confía: la fotocopia que Alicia se dejó en la bandeja.
* {fia_teresa >= 1 and dia >= 3} [teresa, fotocopié algo de ese libro?]
    ~ sabe_puerta_azul = true
    Sí. Me pediste la fotocopiadora un sábado. Una página sola. #delay: 300
    Un mas de 1782, con pozo delante y la puerta pintada de azul. «A mitja pujada de Bracons», dice el pie. #delay: 8
    Te dejaste una copia en la bandeja. La tengo en el cajón, con el pendrive. #delay: 5
    -> opciones
* {dia >= 5 and fia_teresa > -1} [teresa qué te dijo mi madre?]
    Que dejas el club. Que ahora tienes «otras lecturas». #delay: 300
    Me dejó un folleto de un retiro. Lo uso de punto de libro. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla

= libros
«Nada», como te decía. Y uno de la sección local: «Masies de la Vall de Bianya». #delay: 6
Me hizo gracia: no se lo lleva nunca nadie. #delay: 5
Lo devolviste con una esquina doblada. En las masías de Sant Salvador. #delay: 6
->->

= recela
~ fia_teresa = -1
... #delay: 120
Alicia no se ha olvidado de un libro en su vida. #delay: 5
No sé quién es usted, pero ese móvil no es suyo. #delay: 6
->->
