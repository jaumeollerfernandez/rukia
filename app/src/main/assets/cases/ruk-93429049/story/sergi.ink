// Sergi, de clase, el responsable. Comparte la exposición con Alicia: el borrador de ella habla de «R., 78 años».
// Vida propia: la exposición (la hace solo si hace falta) y lo de Mireia, que no cuenta a nadie.
// Escribe él solo el D4, después de hablar con Elena: si la hace solo o la esperan.

=== sergi ===
-> historial

= historial
Alicia, ¿has empezado tu parte de la exposición? #at: D-4 19:00
sí, ya casi. es sobre la soledad de la gente mayor en el campo #from: me #at: D-4 21:30
Perfecto. #at: D-4 21:31
-> charla

= charla
{sergi_escribe() > 0 and hora >= sergi_escribe():
    -> solo ->
}
- (opciones)
#caduca: {caduca(sergi_escribe())}
* (perdon) [sergi perdona por lo de la exposición]
    No pasa nada. Pero necesito tu parte. #delay: 120
    ¿Vas a poder terminarla? #delay: 6
    #caduca: D{dia + 1} 00:00
    ** (prometo) [sí, te la paso esta semana]
        Vale. Lo apunto. #delay: 120
    ** [no lo sé, sergi]
        Bueno. Saber que no lo sabes ya es algo. #delay: 120
    ** [(sin responder)]
    - - -> opciones
* (borrador) {dia >= 2} [sergi qué puse yo en mi parte? no tengo el portátil]
    Tienes un borrador en el drive. Te lo copio. #delay: 300
    «Entrevista a R., 78 años. Vive sola en un mas sin electricidad en la Vall de Bianya. Viuda desde 2009. Su única compañía es una perra.» #delay: 20
    «Baja al pueblo una vez al mes. No quiere ni oír hablar de residencias: "de aquí me sacarán con los pies por delante".» #delay: 10
    Está muy bien. Si lo terminas, sacamos un 10. #delay: 6
    -> opciones
* {dia >= 2} [sergi, qué ha pasado con mireia?]
    ~ sabe_girona = true
    Eso es entre Mireia y yo. #delay: 300
    Y tú tampoco tendrías que haberte metido. Lo de Girona no era asunto tuyo. #delay: 8
    #caduca: D{dia + 1} 00:00
    ** [ella me lo pidió]
        Ya lo sé. #delay: 300
        Por eso no te lo reprocho más. Solo un poco. #delay: 6
    ** [tienes razón. perdona]
        No pasa nada. Ya está hecho. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [gracias por cubrirme, sergi]
    Me debes una. Grande. #delay: 120
    {sergi.charla.prometo:
        Y la parte que dijiste que me pasabas. #delay: 5
    }
    -> opciones
* {borrador} [sergi, te dije quién era R.?]
    No. Solo que la conocías «de los lotes del banco» y que no querías poner su nombre porque «no le gustaría». #delay: 300
    Muy profesional. Elena te pondrá un 10 en ética. #delay: 5
    -> opciones
* [sergi, cuándo es la exposición?]
    En una semana. Y tengo mi parte, la tuya a medias y a Paula comiéndose los resúmenes. #delay: 120
    -> opciones
* {dia >= 4} [sergi, estás bien?]
    No mucho. #delay: 300
    Lo de Mireia me tiene fatal. Pero gracias por preguntar. Eres la única. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [si necesitas hablar, aquí estoy]
        Lo sé. Algún día te lo cuento entero. Hoy no. #delay: 300
    ** [mireia también lo está pasando mal]
        Ya. Por eso no digo nada. A nadie. #delay: 300
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D4, después de hablar con Elena (clase.ink, 10:50).
= solo
Alicia. #at: D4 11:20
Elena me deja hacer la exposición solo. Pero preferiría no hacerlo. #delay: 8
#caduca: D{dia + 1} 00:00
* [hazla tú, de verdad. yo ahora no puedo]
    Vale. #delay: 300
    Pondré tu parte con tu nombre. Te la has ganado. #delay: 6
* [dame unos días]
    Tienes hasta el ensayo. Después la hago solo. #delay: 300
* [lo siento mucho, sergi]
    No lo sientas. Termina tu parte. #delay: 300
    O no. Pero dímelo. #delay: 5
* [(sin responder)]
- ->->

// La hora de hoy a la que Sergi escribe por su cuenta (0: hoy no escribe).
=== function sergi_escribe() ===
{dia == 4 and not sergi.solo:
    ~ return 11
}
~ return 0
