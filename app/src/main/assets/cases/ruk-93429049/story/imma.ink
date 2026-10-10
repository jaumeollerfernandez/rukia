// Imma, de ioga. Recién divorciada: el perfil exacto que busca Rosa d'Abril. Estuvo a punto de ir y le dio mala espina.
// Pista menor: aún guarda el folleto: «Trobada de l'Alba · per a famílies», con un volcán y una ermita dibujados (Santa Margarida).

=== imma ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [imma 🧘‍♀️]
    ¡Ali! ¿Vuelves ya a la esterilla? #delay: 600
    -> opciones
* {dia >= 3} [imma, al final fuiste a rosa d'abril?]
    ¡Qué va! Me llamaron tres veces en un día. Demasiado amables. #delay: 600
    Me dijeron que la primera sesión era gratis y que luego «cada una aporta lo que siente». Me dio mala espina. #delay: 6
    -> opciones
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
* {dia >= 4} [imma, te volvieron a llamar?]
    Ayer. Una chica joven, con voz muy dulce. Me dijo que «aún había sitio para mí». #delay: 600
    ¿Sitio para qué? Ni que fuera un autobús. #delay: 5
    -> opciones
* {dia >= 5} [imma, no vayas eh]
    No pienso. Tú tampoco, ¿eh? Que te conozco. #delay: 600
    -> opciones
+ [(sin responder)]
    -> charla
