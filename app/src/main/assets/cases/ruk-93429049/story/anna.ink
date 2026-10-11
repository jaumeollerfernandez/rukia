// Anna, madre de Martina (clases de repaso). Alicia le pedía cobrar en efectivo: preparaba la huida.
// Pista menor: Martina dice que Alicia le prometió presentarle a «una perra gigante que se llama Trufa» (IMG_0391).
// Vida propia: enfermera con doble turno en el CAP de Olot; busca otra profe para Martina «hasta que vuelva».
// Escribe ella sola el D4: Martina ha sacado un 8 en el control de decimales.

=== anna ===
-> charla

= charla
{anna_escribe() > 0 and hora >= anna_escribe():
    -> nota ->
}
- (opciones)
#caduca: {caduca(anna_escribe())}
// Saludos: uno u otro.
* (hola) {not perdon} [hola anna! cómo estáis?]
    ¡Ali! Bien, liadas. Como siempre. #delay: 600
    -> opciones
* (perdon) {not hola} [anna, perdona por las clases]
    No te preocupes, mujer. Primero estás tú. #delay: 600
    -> opciones
* [anna, cómo va martina con las mates?]
    Un 6 en el último ejercicio. Sin ti no es lo mismo 😊 #delay: 600
    #caduca: D{dia + 1} 00:00
    ** [dile que la echo de menos]
        Se lo digo. Pero no le prometas nada, que luego te lo recuerda cada día. #delay: 300
    ** [un 6 está muy bien!]
        Eso le digo yo. Ella quiere un 10. Ha salido a su padre. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* (efectivo) {dia >= 2} [anna, te debo alguna clase?]
    Al revés, te las he pagado todas. Las últimas en efectivo, como me pediste. #delay: 600
    Llevabas semanas pidiéndolo todo en efectivo. Pensé que ahorrabas para algo. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** (carnet) [sí, para el carnet]
        Ya me lo imaginaba. Con lo que cuesta ahora... #delay: 300
    ** [es que el bizum me daba problemas]
        Ya. A mí también, con mi banco. Ni lo pensé. #delay: 300
    ** [algo así]
        Pues que te cunda, sea lo que sea 😊 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia < 4} [anna, el jueves hay clase?]
    Si puedes, sí. Martina tiene control de decimales 📐 #delay: 600
    Y si no, no te preocupes. He encontrado a una chica de Olot «hasta que vuelvas». No es tan buena. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [cuando vuelva, sigo yo?]
        Claro. La otra no explica con dibujos. Martina ya se ha quejado. #delay: 300
    ** [vale, me parece bien la otra chica]
        Ah. Bueno. Ya veremos 😊 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [anna, martina pregunta por mí?]
    Cada día. Y por la perra. #delay: 600
    Dice que le prometiste presentarle a una perra gigante que se llama Trufa. Que vive en una casa con estrellas. #delay: 6
    Yo le he dicho que eso sería un cuento. ¿Es un cuento? 😊 #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no, trufa existe]
        ¡Pues Martina te lo va a recordar cada día hasta que la conozca! #delay: 300
    ** [sí, un cuento]
        Me lo imaginaba. Martina tiene una imaginación... sale a su padre. #delay: 300
    ** [dile que algún día se la presento]
        ¿Ves? Eso es prometer. Te lo apuntará en la libreta de mates. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [anna, mi madre te ha dicho algo?]
    Me la crucé en la farmacia. Le pregunté por ti y me dijo que estabas «en un retiro». #delay: 600
    Con lo poco que te gustan a ti esas cosas. Pensé que lo decía en broma. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [no estoy en ningún retiro]
        Ya me parecía. #delay: 300
        Ali, ¿estás bien? Soy enfermera. Si necesitas algo, lo que sea, me escribes. #delay: 6
        {anna.charla.carnet:
            Y no para el carnet, ¿eh? #delay: 5
        }
    ** [sí, algo así]
        Pues que te sirva. Aunque no te veo yo meditando 😊 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 2} [anna qué tal el trabajo?]
    Doble turno. Hoy veinte vacunas y un señor que se ha desmayado antes de ver la aguja. #delay: 600
    Martina cena con su padre y yo con un yogur. La vida de enfermera 😊 #delay: 6
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D4: la nota de Martina.
= nota
Ali, Martina ha sacado un 8 en el control de decimales 🎉 #at: D4 19:10
Quería que lo supieras. Me ha dicho «díselo a la Ali, que fue ella». #delay: 8
#caduca: D{dia + 1} 00:00
* [un 8!! dile que es una crack]
    Se lo digo. Mañana va al cole con la cabeza muy alta 😊 #delay: 600
* [ha sido ella, no yo]
    Las dos. Pero no se lo digas, que se lo cree. #delay: 600
* [(sin responder)]
- ->->

// La hora de hoy a la que Anna escribe por su cuenta (0: hoy no escribe).
=== function anna_escribe() ===
{dia == 4 and not anna.nota:
    ~ return 19
}
~ return 0
