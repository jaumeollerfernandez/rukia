// Teresa, la bibliotecaria. Discreta: no se lo cuenta a nadie. Alicia usaba el ordenador de la biblioteca para lo que
// no quería que se viera en casa: el billete de bus (señuelo), Camprodon (señuelo), las residencias y el libro de masías.

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
* [teresa perdona por lo del libro]
    No sufras, mujer. #delay: 300
    -> opciones
* [teresa aún tienes mi pendrive?]
    ~ sabe_residencia = true
    Sí, en el cajón del mostrador. No se lo he dado a nadie. #delay: 300
    Ya que lo dices: el ordenador 2 tenía tus búsquedas abiertas. Las cerré, pero vi algo. #delay: 6
    Horarios de autobús de Olot a Camprodon. Y la web de la residencia Sant Jaume, la página de «familiares». #delay: 8
    No es asunto mío. Te lo digo por si no querías dejarlo a la vista. #delay: 5
    -> opciones
* {dia >= 2} [teresa qué libros saqué la última vez?]
    «Nada», que aún me debes. Y uno de la sección local: «Masies de la Vall de Bianya». #delay: 300
    Me hizo gracia: no se lo lleva nunca nadie. #delay: 5
    Lo devolviste con una esquina doblada. En las masías de Sant Salvador. #delay: 6
    -> opciones
* {dia >= 5} [teresa qué te dijo mi madre?]
    Que dejas el club. Que ahora tienes «otras lecturas». #delay: 300
    Me dejó un folleto de un retiro. Lo uso de punto de libro. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
