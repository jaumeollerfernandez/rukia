// Hugo, de la autoescuela. Despreocupado y gracioso; no sabe nada del caso. Ruido sobre todo.
// Detalle menor: la semana antes, Alicia le pidió su batería externa y no se la devolvió (el prepago sin luz).
// Vida propia: el examen teórico del D4 (aprueba con un 28, «¡milagro!»). Escribe él solo el D4, antes de que salga el bus.

=== hugo ===
-> charla

= charla
{hugo_escribe() > 0 and hora >= hugo_escribe():
    -> bus ->
}
- (opciones)
#caduca: {caduca(hugo_escribe())}
* [hugo 🚗]
    ali!! #delay: 300
    {dia >= 4:
        no viniste 😢 #delay: 3
    - else:
        te guardo sitio en el bus. el día del examen no me dejes solo #delay: 3
    }
    -> opciones
* {dia >= 5 or (dia == 4 and hora >= 14)} [felicidades por el 28!!]
    GRACIAS #delay: 300
    el bus a girona sin ti fue un drama #delay: 3
    martí roca no habla. viajé dos horas con un señor de cartón #delay: 4
    -> opciones
* {dia < 4} [hugo has estudiado las señales?]
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
* {dia >= 5 or (dia == 4 and hora >= 14)} [hugo qué preguntas cayeron?]
    la de la rotonda, la del alcohol y una de una vaca en la carretera 🐄 #delay: 300
    la de la vaca la acerté. es mi especialidad #delay: 3
    #caduca: D{dia + 1} 00:00
    ** [me la dices?]
        si ves una vaca, frenas. no hace falta un carnet para eso #delay: 120
    ** [eres un crack]
        lo sé. díselo a rafa #delay: 120
    ** [(sin responder)]
    - - -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D4, antes del bus a Girona (autoescola.ink, 07:30).
= bus
{autoescola.d3.aviso:
    ali de verdad no vienes?? #at: D4 07:20
    el bus sale en 10 min. aún llegas #delay: 4
- else:
    ali dónde estás?? #at: D4 07:20
    el bus sale en 10 min #delay: 4
    rafa ya está contando cabezas 😬 #delay: 3
}
#caduca: D4 08:00
* [no voy, hugo. lo siento]
    QUÉ #delay: 60
    me dejas solo con martí roca? 😭 #delay: 3
* [suerte!!]
    eso significa que no vienes 😢 #delay: 60
    vale. si apruebo te lo dedico #delay: 4
* [(sin responder)]
    ali?? #delay: 1
    bueno. me voy. deséame suerte aunque sea mentalmente #delay: 600
- ->->

// La hora de hoy a la que Hugo escribe por su cuenta (0: hoy no escribe).
=== function hugo_escribe() ===
{dia == 4 and not hugo.bus:
    ~ return 7
}
~ return 0
