// Vecinos de la calle del Pont, donde viven Alicia, Montse y Berta. Montse está en el grupo y contesta muy serena.

=== veins ===
-> historial

= historial
Recordatorio: el martes pasan a recoger trastos viejos. Dejadlos delante de casa antes de las 8. #from: conxita #at: D-8 19:00
Gracias, Conxita. #from: enric #at: D-8 19:30
-> d1

= d1
Se ha escapado el Rocky. Mestizo negro, collar rojo. Si lo veis, llamadme, por favor 🐕 #from: enric #at: D1 09:40
Lo comparto en el grupo del pueblo, Enric. #from: conxita #delay: 600
Enviamos luz para que vuelva pronto ☀️ #from: mama #delay: 1800
-> charla("D2 18:25") ->
-> d2

= d2
Montse, ¿estáis bien? Hace días que veo las persianas bajadas a mediodía. #from: conxita #at: D2 18:30
Muy bien, Conxita. Estamos de recogimiento unos días. Gracias por preguntar ☀️ #from: mama #delay: 3600
-> charla("D3 08:10") ->
-> d3

= d3
¡Ha vuelto el Rocky! Estaba en el huerto de los Puigdemont, gordo como una vaca 😂 #from: enric #at: D3 08:15
Qué alegría, Enric 🥰 #from: conxita #delay: 600
-> charla("D4 09:05") ->
-> d4

= d4
Esta noche ha estado un coche gris parado delante de casa de Montse hasta las tantas. Con una rosa pegada en el cristal. ¿Alguien sabe de quién es? #from: enric #at: D4 09:10
Son amigos nuestros, Enric. No te preocupes. #from: mama #delay: 1800
-> charla("D6 10:25") ->
-> d5

= d5
-> d6

= d6
Mañana hay corte de luz de 18 a 22, lo ha dicho el Ayuntamiento. Cargad los móviles. #from: conxita #at: D6 10:30
-> d7

= d7
-> DONE

// Trampa: Montse está en este grupo. Si el jugador escribe aquí, sabe que alguien usa el móvil de su hija.
= charla(limite)
- (opciones)
#caduca: {limite}
* [hola enric, ojalá aparezca el rocky 🐕]
    -> pillada ->
    -> opciones
* {dia >= 2} [gracias por preguntar, conxita 💛]
    -> pillada ->
    -> opciones
* {dia >= 4} [enric, yo también he visto ese coche gris]
    -> pillada ->
    -> opciones
+ [(sin responder)]
- ->->

= pillada
~ sospecha_familia += 2
{sospecha_familia <= 2:
    ¿Alicia? #from: mama #delay: 120
    Ali, ¿eres tú? 😳 #from: conxita #delay: 30
    Perdonad. El móvil de Alicia lo tengo yo, en casa. Alguien nos está gastando una broma de muy mal gusto. #from: mama #delay: 300 #effect: glitch
    Madre mía, qué cosas 😳 #from: conxita #delay: 120
- else:
    Otra vez. #from: mama #delay: 120 #effect: glitch
    Quien seas, deja en paz a mi familia. #from: mama #delay: 10
}
->->
