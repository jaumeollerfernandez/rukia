// Hugo, de la autoescuela. Despreocupado y gracioso; no sabe nada del caso. Ruido sobre todo.
// Detalle menor: la semana antes, Alicia le pidió su batería externa y no se la devolvió (el prepago sin luz).

=== hugo ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [hugo 🚗]
    ali!! te guardé sitio en el bus #delay: 300
    {dia >= 4:no viniste 😢|el día del examen no me dejes solo}
    -> opciones
* {dia >= 4} [felicidades por el 28!!]
    GRACIAS #delay: 300
    el bus a girona sin ti fue un drama #delay: 3
    -> opciones
* [hugo has estudiado las señales?]
    he estudiado las que tienen dibujos bonitos #delay: 300
    rafa dice que me presente en 2030 jajajaja #delay: 3
    -> opciones
* {dia >= 2} [hugo, te devolví la batería externa?]
    QUÉ #delay: 300
    no!! te la dejé hace dos semanas porque decías que ibas a estar «sin enchufe unos días» #delay: 4
    pensé que te ibas de acampada. quédatela, total tengo tres #delay: 4
    -> opciones
* {dia >= 3} [hugo, rafa ha dicho algo de mí?]
    que llamó a tu casa y le colgaron #delay: 300
    bueno, que le dijeron algo muy educado y luego le colgaron. rafa estaba ofendidísimo jajaja #delay: 4
    -> opciones
* {dia >= 4} [hugo qué preguntas cayeron?]
    la de la rotonda, la del alcohol y una de una vaca en la carretera 🐄 #delay: 300
    la de la vaca la acerté. es mi especialidad #delay: 3
    #caduca: D{dia + 1} 00:00
    ** [me la dices?]
        si ves una vaca, frenas. no hace falta un carnet para eso #delay: 120
    ** [eres un crack]
        lo sé. díselo a rafa #delay: 120
    ** [(sin responder)]
    - - -> opciones
+ [(sin responder)]
    -> charla
