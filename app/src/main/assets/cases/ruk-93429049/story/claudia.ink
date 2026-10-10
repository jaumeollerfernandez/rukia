// Clàudia, la profesora de ioga y fisioterapeuta. El contraste sano con Rosa d'Abril: sin promesas, sin presión.
// Pista menor: la que vino a repartir folletos del Casal era una chica joven, muy pálida y rubia (Iris).

=== claudia ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [clàudia cómo va la clase?]
    Cuatro gatos y una manta. Te echamos de menos en la esquina 🧘‍♀️ #delay: 600
    -> opciones
* {dia >= 2} [clàudia, conoces rosa d'abril?]
    ~ sabe_secta = true
    Uno de ellos vino a una clase a repartir folletos. Gente muy amable. Le pedí que lo hiciera fuera. #delay: 600
    No los conozco, la verdad. A mí eso de «sanar el alma» me queda grande. #delay: 5
    Ali, si alguna vez necesitas hablar con alguien de fuera, aquí estoy. #delay: 6
    -> opciones
* [clàudia me duele la espalda]
    ¿Otra vez la colocación? Hombros abajo, Ali. Siempre los llevas en las orejas. #delay: 600
    Gato-vaca diez veces por la mañana. Y si no pasa, vienes a la consulta y te miro. #delay: 5
    -> opciones
* {dia >= 3} [clàudia, quién vino a repartir los folletos?]
    Una chica joven. Muy pálida, rubia, el pelo muy largo y liso. Toda de blanco. #delay: 600
    Tenía cara de no haber dormido en semanas. Me dio pena echarla, la verdad. #delay: 6
    Al salir le ofrecí un té y me dijo que no podía. Que «estaba en un proceso». #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [te dijo cómo se llamaba?]
        No. Solo que era «de la Casa». Lo dijo como quien dice «de la familia». #delay: 300
    ** [qué raro todo]
        Raro no. Triste. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [clàudia, se puede ayunar muchos días sin que pase nada?]
    Depende de muchas cosas. Pero sin control médico, y sin beber bien, no. #delay: 600
    A partir de unos días te mareas, te bajan las defensas, el corazón se resiente. #delay: 6
    ¿Por qué lo preguntas, Ali? ¿Estás comiendo? #delay: 5
    -> opciones
* {dia >= 5} [clàudia gracias por estar ahí]
    Siempre. Sin cuota y sin permanencia 😊 #delay: 600
    -> opciones
+ [(sin responder)]
    -> charla
