// Arnau, el primo de 16 años. Memes, partidas y mensajes en ráfaga. No sabe nada... hasta que lo cuenta sin querer (D2).

=== arnau ===
-> historial

= historial
prima #at: D-4 18:12
PRIMA #at: D-4 18:12
me pasas tu cuenta de netflix #at: D-4 18:12
ni de broma #from: me #at: D-4 19:40
😭😭 #at: D-4 19:40
-> d1

= d1
prima #at: D1 17:45
mira este vídeo 💀 (vídeo) #delay: 2
es literalmente tu hermana cuando se pone en modo zen jajajaja #delay: 4
ah no que ahora va de luz y amaneceres y nosequé #delay: 10
berta ya no viene ni a las comidas de la iaia #delay: 6 #caduca: D2 15:00
* [jajajaja]
    VERDAD QUE SÍ #delay: 30
    vale me voy a ranked bye #delay: 4
* [no te metas con berta]
    uy perdón señora #delay: 60
    desde cuándo la defiendes tú #delay: 3
* [(sin responder)]
    me dejas en visto 💔 #delay: 1
- -> charla("D2 18:40") ->
-> d2

= d2
prima #at: D2 18:45
has visto mi post 💀 me he abierto la rodilla en la rotonda #delay: 3
el vídeo es de anteanoche. y quién sale al fondo??? #delay: 30
TÚ #delay: 2
con una bolsa de pan gigante jajajaja #delay: 3
qué hacías ahí a esas horas. te fugabas o qué 😂 #delay: 5 #caduca: D3 15:00
* [jajaja no era yo]
    que sí eras tú, que llevas tu chaqueta verde #delay: 40
    bueno da igual #delay: 4
* [arnau, archiva el vídeo porfa. y no se lo digas a nadie]
    ~ arnau_calla = true
    ostia #delay: 60
    en serio?? #delay: 2
    vale vale. archivado. palabra de primo 🤝 #delay: 10
* [(sin responder)]
    me dejas en visto otra vez 💔 #delay: 1
- -> charla("D3 17:25") ->
-> d3

= d3
{arnau_calla:
    prima el vídeo sigue archivado eh. me debes una skin #at: D3 17:30
- else:
    prima mi post ya tiene 2.000 visitas 💀 #at: D3 17:30
    hasta tu hermana le ha dado like. ella no da like a nada #delay: 5
    y me ha preguntado a qué hora lo grabé. rarísimo #delay: 6
}
-> charla("D4 17:55") ->
-> d4

= d4
prima vas a venir a la comida de la iaia de dentro de tres días? #at: D4 18:00
la iaia ha dicho que si no vienes se enfada #delay: 4
y yo ya me he pedido el muslo 🍗 #delay: 3
-> charla("D5 17:05") ->
-> d5

= d5
prima la iaia dice que vayas a la comida aunque sea al postre #at: D5 17:10
ah y he subido a diamante 💎 #delay: 3
nadie me felicita en esta familia #delay: 60
-> d6

= d6
prima mañana es la comida. la iaia ha hecho canelones 🤤 #at: D6 16:00
si no vienes me como tu parte #delay: 3
-> d7

= d7
prima la comida de la iaia se ha cancelado #at: D7 09:00
todos están rarísimos. mi madre está llorando #delay: 5
qué ha pasado?? #delay: 10
-> DONE

// Huecos para escribirle. Arnau contesta en ráfaga y no se entera de nada, pero lo ve todo.
= charla(limite)
- (opciones)
#caduca: {limite}
* {dia >= 2} [has visto alguna furgo rara por casa de la iaia?]
    una furgo blanca con una rosa dorada en la puerta #delay: 90
    el otro día un señor con barba me dio un folleto en la puerta de la iaia #delay: 3
    la iaia lo tiró a la basura y dijo una palabrota en catalán que no puedo repetir 💀 #delay: 5
    -> opciones
* [arnau la iaia sabe algo de mí?]
    la iaia dice que eres igualita que tu madre de joven #delay: 90
    que también se escapaba de casa y luego volvía cuando tenía hambre jajaja #delay: 4
    y que tu madre era muy distinta antes de «esa gente». eso lo ha dicho bajito #delay: 6
    -> opciones
* {dia >= 3} [arnau gracias por todo. eres el mejor primo]
    ya lo sé 😎 #delay: 60
    me debes una skin igualmente #delay: 3
    -> opciones
+ [(sin responder)]
- ->->
