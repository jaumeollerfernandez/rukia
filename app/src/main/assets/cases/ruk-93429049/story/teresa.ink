// Teresa, la bibliotecaria. Discreta: no se lo cuenta a nadie. Alicia usaba el ordenador de la biblioteca para lo que
// no quería que se viera en casa: el billete de bus (señuelo), Camprodon (señuelo), las residencias y el libro de masías.
// Confianza (fia_teresa): preguntarle qué libros sacó es un desliz, así que la prueba con el libro que debe («Nada»,
// grupo de lectura). Si confía, más adelante le da la fotocopia del mas de 1782 (pozo, subida a Bracons). Si recela, una mentira
// solo cuela si el pueblo aún no habla del móvil; la verdad sí funciona: Teresa es discreta y colabora.
// Vida propia: el Ayuntamiento quiere cerrar la biblioteca por las tardes; ella prepara un escrito con firmas.
// Escribe ella sola el D4 (el pendrive, si no se lo han preguntado) y el D5 (tras la visita de Montse), si no recela.

=== teresa ===
-> historial

= historial
Alicia, te has dejado el pendrive en el ordenador 2. Te lo guardo en el mostrador. #at: D-3 20:05
gracias teresa!! no se lo des a nadie porfa #from: me #at: D-3 20:30
Descuida. #at: D-3 20:31
-> charla

= charla
{teresa_escribe() > 0 and hora >= teresa_escribe():
    {dia == 4: -> recordatorio ->}
    {dia == 5: -> visita ->}
}
- (opciones)
#caduca: {caduca(teresa_escribe())}
// Pedir perdón por «Nada» y preguntar qué libros sacó se excluyen: quien sabe qué libro debe no pregunta cuál es.
* (perdon) {fia_teresa == 0 and dia < 5 and not prueba} [teresa perdona, aún tengo «nada»]
    ~ fia_teresa = 1
    No sufras, mujer. #delay: 300
    ¿Lo terminaste, al menos? #delay: 8
    #caduca: D{dia + 1} 00:00
    ** [sí. el final me dejó rara]
        Es que no tiene final. Andrea se va y ya está. #delay: 120
        A veces irse es todo lo que puede pasar en una casa así. #delay: 10
    ** [me quedé a medias]
        Pues te guardo el turno en la lista. Detrás de las tres señoras, eso sí. #delay: 120
    ** [(sin responder)]
    - - -> opciones
* (pendrive) {fia_teresa > -1} [teresa aún tienes mi pendrive?]
    ~ sabe_residencia = true
    Sí, en el cajón del mostrador. No se lo he dado a nadie. #delay: 300
    Ya que lo dices: el ordenador 2 tenía tus búsquedas abiertas. Las cerré, pero vi algo. #delay: 6
    Horarios de autobús de Olot a Camprodon. Y la web de la residencia Sant Jaume, la página de «familiares». #delay: 8
    No es asunto mío. Te lo digo por si no querías dejarlo a la vista. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [lo vio alguien más?]
        No. El ordenador 2 está de cara a la pared por algo. #delay: 120
        Y por la tarde solo vienen los de los deberes, que no levantan la vista de la pantalla. #delay: 8
    ** [qué más viste?]
        Nada más. #delay: 120
        Y si hubiera visto algo, tampoco me acordaría. Es una costumbre del oficio. #delay: 8
    ** [guárdamelo, porfa. pasaré a buscarlo]
        Ahí estará. Si viene otra persona a pedirlo, no se lo daré. Ni aunque sea de tu familia. #delay: 120
    ** [(sin responder)]
    - - -> opciones
* (prueba) {dia >= 2 and fia_teresa == 0 and not perdon} [teresa qué libros saqué la última vez?]
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
* (mossos) {fia_teresa == -1} [Trabajo con los Mossos. Buscamos a Alicia.]
    ~ fia_teresa = 1
    Lo suponía. #delay: 300
    Pregunte lo que necesite. Y descuide: en esta biblioteca no se cuenta nada a nadie. #delay: 6
    -> libros ->
    -> opciones
* {perdon and not libros} [teresa, saqué algún libro más?]
    -> libros ->
    -> opciones
// Solo si confía: la fotocopia que Alicia se dejó en la bandeja.
* (fotocopia) {fia_teresa >= 1 and dia >= 3} [teresa, fotocopié algo de ese libro?]
    Sí. {mossos:Alicia me pidió|Me pediste} la fotocopiadora un sábado. Una página sola. #delay: 300
    Un mas de 1782, con un pozo delante y un banco de piedra junto a la entrada. «Damunt de Joanetes, camí de Bracons», dice el pie. #delay: 8
    {mossos:Se dejó|Te dejaste} una copia en la bandeja. La tengo en el cajón, con el pendrive. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [sabes si esa casa sigue en pie?]
        No lo sé. El libro tiene cuarenta años y la mitad de esas masías ya son ruinas o casas de turismo. #delay: 120
        Esa, por la foto, todavía tenía a alguien dentro. Había ropa tendida. #delay: 10
    ** [no se lo digas a nadie, vale?]
        ¿A quién se lo iba a decir? #delay: 120
        Ni siquiera sé por qué te interesa. Y prefiero seguir sin saberlo. #delay: 8
    ** [(sin responder)]
    - - -> opciones
* {(dia >= 6 or (dia == 5 and hora >= 13)) and fia_teresa > -1} [teresa qué te dijo mi madre?]
    {mossos:Que Alicia deja el club. Que ahora tiene «otras lecturas».|Que dejas el club. Que ahora tienes «otras lecturas».} #delay: 300
    Me dejó un folleto de un retiro. Lo uso de punto de libro. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [yo no dejo el club]
        Ya me lo imaginaba. #delay: 120
        Tu sitio sigue en la lista. Las listas de una biblioteca no las cambia nadie más que yo. #delay: 8
    ** [qué pone el folleto?]
        Un amanecer y letras doradas. No lo he leído, si te soy sincera. #delay: 120
        Pero el papel es muy bueno. Mejor que el de nuestros carnés 😅 #delay: 8
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2 and dia < 7 and fia_teresa > -1} [teresa qué tal la biblio?]
    Peleando. El Ayuntamiento quiere cerrar por las tardes. #delay: 300
    Estoy recogiendo firmas. Si las tardes cierran, ¿adónde van los chavales que no tienen ordenador en casa? #delay: 10
    #caduca: D{dia + 1} 00:00
    ** [apúntame. firmo seguro]
        Ya te apunté yo el primer día. Sabía que querrías. #delay: 120
    ** [ánimo teresa]
        Gracias, mujer. Ánimo me sobra. Lo que me faltan son firmas. #delay: 120
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// A la policía le habla de Alicia; a «Alicia», de tú.
= libros
{charla.mossos:
    «Nada», que aún lo tiene. Y uno de la sección local: «Masies de la Vall de Bianya». #delay: 6
    Me hizo gracia: no se lo lleva nunca nadie. #delay: 5
    Lo devolvió con una esquina doblada. En las masías de arriba de Joanetes. #delay: 6
- else:
    «Nada», que aún lo tienes. Y uno de la sección local: «Masies de la Vall de Bianya». #delay: 6
    Me hizo gracia: no se lo lleva nunca nadie. #delay: 5
    Lo devolviste con una esquina doblada. En las masías de arriba de Joanetes. #delay: 6
}
->->

= recela
~ fia_teresa = -1
... #delay: 120
Alicia no se ha olvidado de un libro en su vida. #delay: 5
No sé quién es usted, pero ese móvil no es suyo. #delay: 6
->->

// D4: el pendrive sigue en el cajón (solo si Alicia aún no le ha preguntado por él).
= recordatorio
Buenos días, Alicia. El pendrive sigue en el cajón. Por si lo necesitas. #at: D4 09:30
No hay prisa. Solo que no me gusta guardar cosas ajenas sin que su dueña lo sepa. #delay: 8
->->

// D5, después de que Montse devuelva «Nada» (lectura.ink, 12:40).
= visita
{charla.mossos:Ha venido la madre de Alicia.|Ha venido tu madre.} #at: D5 13:05
No le he dicho nada del pendrive. Ni me lo ha preguntado. #delay: 10
->->

// La hora de hoy a la que Teresa escribe por su cuenta (0: hoy no escribe).
=== function teresa_escribe() ===
{
- fia_teresa < 0:
    ~ return 0
- dia == 4 and teresa.charla.mossos:
    ~ return 0
- dia == 4 and not teresa.charla.pendrive and not teresa.recordatorio:
    ~ return 9
- dia == 5 and not teresa.visita:
    ~ return 13
- else:
    ~ return 0
}
