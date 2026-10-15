// Queralt, del teatro (hace de Bernarda). Intensa y empática. Recuerda lo que dijo Alicia de su personaje: que Adela se
// equivocó en irse «sin un sitio adonde ir» (Alicia sí tenía uno). Ruido: Lorca, Barcelona, Arte Dramático.
// Vida propia: estudia Arte Dramático en Barcelona y vuelve los fines de semana. Escribe ella sola el D3 por la noche,
// después del chiste de Jan en el grupo.

=== queralt ===
-> charla

= charla
{queralt_escribe() > 0 and hora >= queralt_escribe():
    -> chiste ->
}
- (opciones)
#caduca: {caduca(queralt_escribe())}
* [queralt 🎭]
    ¡Adela! Bernarda sin Adela no es nada. #delay: 300
    -> opciones
* (dije) {dia >= 2} [queralt, te acuerdas de lo que dije de adela en el ensayo?]
    Que la entendías. Que a veces la única forma de salvarte es irte de casa, aunque te llamen loca. #delay: 300
    Y que Adela se equivocó en una cosa: se fue sin un sitio adonde ir. #delay: 6
    Me dio un escalofrío, la verdad. #delay: 5
    -> opciones
* [queralt qué tal barcelona?]
    Caótica. Maravillosa. Carísima. #delay: 300
    En clase hacemos Chéjov y todos lloran. Yo no. Yo lloro con Lorca, que es de casa. #delay: 5
    -> opciones
* {dia >= 4 or (dia == 3 and hora >= 23)} [queralt, jan sigue con sus bromas?]
    Jan no tiene filtro. Le dije «Jan, no tiene gracia» y se pasó una hora pidiendo perdón. #delay: 300
    Tiene buen fondo. Pero a veces me dan ganas de encerrarlo en la casa de Bernarda. #delay: 5
    -> opciones
* {dije and dia >= 3} [queralt, tú crees que adela tenía razón?]
    En irse, sí. En cómo, no. #delay: 300
    Si yo fuera Adela, me habría ido a un sitio donde nadie me buscara. Lejos de los ojos del pueblo. Con alguien que me quisiera de verdad. #delay: 7
    #caduca: D{dia + 1} 00:00
    // Lo contrario de lo que le dijo después del chiste de Jan, no.
    ** (eso_hice) {not chiste.no_ido} [eso hice]
        {chiste.lo_tengo:Ya me lo dijiste. Y sigo sin querer saber dónde.|Ali...} #delay: 300
        {not chiste.lo_tengo:
            No me digas dónde. Así no tendré que mentir si me preguntan. #delay: 5
        }
        Pero cuídate. Que no acabe como en Lorca. #delay: 4
    ** [es solo una obra]
        Ya. Solo una obra. Por eso me dio un escalofrío. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 5} [queralt, vuelves este finde?]
    Sí. Dolors quiere ensayar igual, aunque se aplace. Dice que el texto no se olvida si se repite. #delay: 300
    Te guardo el abanico de Adela. 🎭 #delay: 4
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D3, después del chiste de Jan en el grupo (teatre.ink, 22:30).
= chiste
Ali. #at: D3 23:15
No le hagas caso a Jan. Es un bocazas con buen fondo. #delay: 5
Y si de verdad te has ido, que sea a un sitio adonde ir. 🎭 #delay: 6
#caduca: D{dia + 1} 00:00
* (lo_tengo) [lo tengo, queralt]
    Pues no me lo digas. Así no tendré que mentir. #delay: 300
    Cuídate. #delay: 3
* (no_ido) {not charla.eso_hice} [no me he ido a ningún sitio]
    Vale. Entonces descansa. Que el ensayo no se va a ninguna parte. #delay: 300
* [(sin responder)]
- ->->

// La hora de hoy a la que Queralt escribe por su cuenta (0: hoy no escribe).
=== function queralt_escribe() ===
{dia == 3 and not queralt.chiste:
    ~ return 23
}
~ return 0
