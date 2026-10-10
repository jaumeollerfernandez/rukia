// Queralt, del teatro (hace de Bernarda). Intensa y empática. Recuerda lo que dijo Alicia de su personaje: que Adela se
// equivocó en irse «sin un sitio adonde ir» (Alicia sí tenía uno). Ruido: Lorca, Barcelona, Arte Dramático.

=== queralt ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [queralt 🎭]
    ¡Adela! Bernarda sin Adela no es nada. #delay: 300
    -> opciones
* {dia >= 2} [queralt, te acuerdas de lo que dije de adela en el ensayo?]
    Que la entendías. Que a veces la única forma de salvarte es irte de casa, aunque te llamen loca. #delay: 300
    Y que Adela se equivocó en una cosa: se fue sin un sitio adonde ir. #delay: 6
    Me dio un escalofrío, la verdad. #delay: 5
    -> opciones
* [queralt qué tal barcelona?]
    Caótica. Maravillosa. Carísima. #delay: 300
    En clase hacemos Chéjov y todos lloran. Yo no. Yo lloro con Lorca, que es de casa. #delay: 5
    -> opciones
* {dia >= 2} [queralt, jan sigue con sus bromas?]
    Jan no tiene filtro. Le dije «Jan, no tiene gracia» y se pasó una hora pidiendo perdón. #delay: 300
    Tiene buen fondo. Pero a veces me dan ganas de encerrarlo en la casa de Bernarda. #delay: 5
    -> opciones
* {dia >= 3} [queralt, tú crees que adela tenía razón?]
    En irse, sí. En cómo, no. #delay: 300
    Si yo fuera Adela, me habría ido a un sitio donde nadie me buscara. Lejos de los ojos del pueblo. Con alguien que me quisiera de verdad. #delay: 7
    #caduca: D{dia + 1} 00:00
    ** [eso hice]
        Ali... #delay: 300
        No me digas dónde. Así no tendré que mentir si me preguntan. #delay: 5
        Pero cuídate. Que no acabe como en Lorca. #delay: 4
    ** [es solo una obra]
        Ya. Solo una obra. Por eso me dio un escalofrío. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 5} [queralt, vuelves este finde?]
    Sí. Dolors quiere ensayar igual, aunque se aplace. Dice que el texto no se olvida si se repite. #delay: 300
    Te guardo el abanico de Adela. 🎭 #delay: 4
    -> opciones
+ [(sin responder)]
    -> charla
