// Fàtima, voluntaria del Banc d'Aliments y auxiliar de enfermería en el hospital de Olot. Sobre todo ruido (turnos, té,
// cocina), pero trabaja en urgencias: hace un mes vio a Montse entrar mareada, sin comer (los ayunos de la Casa).
// Vida propia: turnos de noche en urgencias (la gripe), la harira de su madre, doblar turno en el Banc.
// Secreto pequeño: lo de Montse en urgencias no debería contarlo (secreto profesional). Si Alicia no lo sabía, se arrepiente.

=== fatima ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {dia >= 3} [fàtima gracias por doblar turno 💛]
    ¡De nada! Me debes un té 🫖 #delay: 300
    -> opciones
* [fàtima cómo fue el sábado?]
    {dia >= 5:
        ¡71 lotes! Récord. Lluïsa casi llora 😂 #delay: 600
        Y yo he tenido que hacer el pastel, que lo prometí. #delay: 5
    - else:
        ¡Bien! 64 lotes. Toni se comió media caja de galletas, eso sí 😂 #delay: 600
        Lluïsa dice que si llegamos a 70 hago pastel. #delay: 5
    }
    -> opciones
* {dia >= 2} [qué tal los turnos en el hospital?]
    De noche otra vez. Urgencias está a tope con la gripe 💪 #delay: 600
    Pero me gusta. A las cuatro de la mañana la gente es más sincera. #delay: 6
    -> opciones
* {dia >= 2} [fàtima, has visto a mi madre últimamente?]
    Mmm. Hace un mes, en urgencias. Pero eso ya lo sabes, ¿no? #delay: 600
    Llegó mareada, blanca como la pared. Le hicimos una analítica: azúcar por los suelos. #delay: 6
    Le pregunté si comía bien y me dijo que estaba «haciendo un proceso». Yo solo sé que llevaba días sin comer. #delay: 6
    No te lo digo para preocuparte, eh. Pero vigílala un poco. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no lo sabía]
        Ay. #delay: 300
        Perdona, pensaba que sí. Eso no te lo tendría que haber contado yo: es secreto profesional. #delay: 6
        No se lo digas a nadie, porfa. Ni a ella. #delay: 5
    ** [ya lo sé. hace ayunos]
        Pues vigílala. Y vigílate tú también, que últimamente estás en los huesos. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [me pasas la receta de la harira?]
    ¡Claro! Garbanzos, lentejas, tomate, mucho cilantro y paciencia 🫖 #delay: 600
    La de mi madre lleva un poco de canela. No se lo digas a nadie. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [canela?? me lo apunto]
        Ya verás. La próxima vez que vengas a casa te la hago. #delay: 300
    ** [mejor me invitas y ya 😂]
        ¡Hecho! Trae pan, que tú sabes dónde comprarlo bueno 😉 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [fàtima, si alguien me busca, no has hablado conmigo vale?]
    Vale... #delay: 600
    No sé en qué estás metida, Ali, pero yo no he visto nada. #delay: 6
    Si necesitas una cama, en casa siempre hay una. Y harira. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [gracias, fàtima. de verdad]
        Para eso estamos. Y si es en urgencias, pregunta por mí. #delay: 300
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
