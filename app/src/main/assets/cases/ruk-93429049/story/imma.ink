// Imma, de ioga. Recién divorciada: el perfil exacto que busca Rosa d'Abril. Estuvo a punto de ir y le dio mala espina.
// Pista menor: aún guarda el folleto: «Trobada de l'Alba · per a famílies», con un volcán y una ermita dibujados (Santa Margarida).
// Vida propia: aprende a vivir sola (duerme en el centro de la cama), ioga y podcasts.
// Escribe ella sola el D4 cuando la vuelven a llamar del Casal.

=== imma ===
-> charla

= charla
{imma_escribe() > 0 and hora >= imma_escribe():
    -> llamada ->
}
- (opciones)
#caduca: {caduca(imma_escribe())}
* [imma 🧘‍♀️]
    ¡Ali! ¿Vuelves ya a la esterilla? #delay: 600
    -> opciones
* (fuiste) {dia >= 3} [imma, al final fuiste a rosa d'abril?]
    ¡Qué va! Me llamaron tres veces en un día. Demasiado amables. #delay: 600
    Me dijeron que la primera sesión era gratis y que luego «cada una aporta lo que siente». Me dio mala espina. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [hiciste bien]
        Eso creo. Aunque a veces pienso que igual me habría venido bien un poco de compañía. #delay: 300
        Luego se me pasa 🧘‍♀️ #delay: 4
    ** [mi madre va]
        Ay, Ali. No lo sabía. Perdona lo de «mala espina». #delay: 300
        Bueno, no. Lo sigo pensando. Pero perdona igual. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* [imma qué tal estás?]
    Mejor. Ya duermo en el centro de la cama 😅 #delay: 600
    El ioga me ayuda. Y los podcasts. Y no coger el teléfono a según quién. #delay: 5
    -> opciones
* {dia >= 3} [imma, aún tienes el folleto de rosa d'abril?]
    Lo tengo en la nevera, debajo de un imán. Para acordarme de no llamar. #delay: 600
    «Trobada de l'Alba · per a famílies 🌹». Con un dibujo de un volcán y una ermita dentro. #delay: 6
    Parece una excursión con desayuno. Pero no pone ni hora ni precio. Solo «la séptima alba». #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [qué es la séptima alba?]
        Ni idea. Lo pregunté y me dijeron que «cuando estuviera preparada lo entendería». #delay: 300
        Ahí colgué. #delay: 4
    ** [hazle una foto y mándamela]
        Uy, se me ha muerto el móvil de la cámara. Te lo enseño en clase, ¿vale? 🧘‍♀️ #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4 and not llamada} [imma, te volvieron a llamar?]
    Ayer. Una chica joven, con voz muy dulce. Me dijo que «aún había sitio para mí». #delay: 600
    ¿Sitio para qué? Ni que fuera un autobús. #delay: 5
    -> opciones
* {dia >= 5} [imma, no vayas eh]
    No pienso. Tú tampoco, ¿eh? Que te conozco. #delay: 600
    -> opciones
+ [(sin responder)]
    -> charla

// D4: la vuelven a llamar del Casal.
= llamada
Ali, perdona que te escriba así. #at: D4 18:30
Me han vuelto a llamar los de Rosa d'Abril. Una chica joven, con voz muy dulce. #delay: 6
Me ha dicho que «aún había sitio para mí». ¿Sitio para qué? Ni que fuera un autobús. #delay: 6
Tu madre va, ¿no? ¿Tú sabes qué es eso? #delay: 5
#caduca: D{dia + 1} 00:00
* [no vayas, imma. hazme caso]
    Vale. Si me lo dices así, no voy. #delay: 600
    Me has dado un poco de miedo, ¿eh? #delay: 4
* [es un grupo de duelo. no sé más]
    De duelo. Pues yo de duelo ya tengo bastante con lo mío 😅 #delay: 600
* [qué le dijiste?]
    Que no. Que no me llamaran más. #delay: 600
    Se quedó callada, como si no supiera qué decir sin el guion. #delay: 5
* [(sin responder)]
- ->->

// La hora de hoy a la que Imma escribe por su cuenta (0: hoy no escribe).
=== function imma_escribe() ===
{dia == 4 and not imma.llamada:
    ~ return 18
}
~ return 0
