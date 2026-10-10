// Anna, madre de Martina (clases de repaso). Alicia le pedía cobrar en efectivo: preparaba la huida.
// Pista menor: Martina dice que Alicia le prometió presentarle a «una perra gigante que se llama Trufa» (IMG_0391).

=== anna ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [anna, cómo va martina con las fracciones?]
    Un 6. Sin ti no es lo mismo 😊 #delay: 600
    -> opciones
* {dia >= 2} [anna, te debo alguna clase?]
    Al revés, te las he pagado todas. Las últimas en efectivo, como me pediste. #delay: 600
    Llevabas semanas pidiéndolo todo en efectivo. Pensé que ahorrabas para algo. #delay: 6
    -> opciones
* [anna, el jueves hay clase?]
    Si puedes, sí. Martina tiene control de decimales 📐 #delay: 600
    Y si no, no te preocupes. He encontrado a una chica de Olot «hasta que vuelvas». No es tan buena. #delay: 5
    -> opciones
* {dia >= 3} [anna, martina pregunta por mí?]
    Cada día. Y por la perra. #delay: 600
    Dice que le prometiste presentarle a una perra gigante que se llama Trufa. Que vive en una casa con estrellas. #delay: 6
    Yo le he dicho que eso sería un cuento. ¿Es un cuento? 😊 #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no, trufa existe]
        ¡Pues Martina te lo va a recordar cada día hasta que la conozca! #delay: 300
    ** [sí, un cuento]
        Me lo imaginaba. Martina tiene una imaginación... sale a su padre. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [anna, mi madre te ha dicho algo?]
    Me la crucé en la farmacia. Le pregunté por ti y me dijo que estabas «en un retiro». #delay: 600
    Con lo poco que te gustan a ti esas cosas. Pensé que lo decía en broma. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
