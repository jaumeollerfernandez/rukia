// Jordi, el padre. Vive en Barcelona desde la separación. Escribe como un padre: frases completas y firma «Papá».
// Pista falsa: Montse le ha dicho que Alicia está en casa de una amiga, así que él cree que todo va bien.

=== jordi ===
-> historial

= historial
Hola cariño. ¿Te vienes unos días a Barcelona cuando acabes los exámenes? Papá #at: D-9 19:30
no sé papa, ya veré #from: me #at: D-9 21:10
Vale. La habitación siempre está lista. Papá #at: D-9 21:12
-> d1

= d1
Hola cariño. Tu madre me ha dicho que estás unos días en casa de una amiga. ¿Todo bien? Papá #at: D1 20:10
Llámame cuando puedas. Hace mucho que no hablamos. #delay: 50 #caduca: D2 09:00
* [sí papa todo bien, ya te llamo]
    Me alegro. Pásalo bien. Un beso enorme. Papá #delay: 240
* [papa cuándo hablaste con mamá?]
    Esta mañana. Me llamó ella, cosa rara. #delay: 300
    Estaba muy rara, la verdad. Como siempre desde lo de ese «grupo». #delay: 20
    ¿Por qué lo preguntas? ¿Pasa algo? #delay: 5 #caduca: D2 09:00
    ** [no nada, era por saber]
        Vale. Ya sabes que puedes contarme lo que sea. Papá #delay: 120
    ** [qué sabes tú del grupo de mamá?]
        ~ sabe_secta = true
        Poco. Un grupo de duelo, de meditación. «Rosa d'Abril». Tu madre empezó a ir con Berta después de lo de la yaya. #delay: 180
        A mí no me gustaba que fuera tanto, pero no nos separamos por eso, cariño. Fueron muchas cosas. #delay: 30
        Si a ella le hace bien... Te llamo pronto. Papá #delay: 20
    ** [(sin responder)]
        Bueno, ya me dirás. Papá #delay: 1
* [(sin responder)]
    Supongo que estás liada. Un beso. Papá #delay: 1
- -> charla("D2 20:25") ->
-> d2

= d2
Cariño, te llamo un momento. #at: D2 20:30 #call: audio/jordi.m4a
{llamada_jordi_contestada:
    No has dicho nada. Supongo que había mala cobertura. #delay: 120
- else:
    Te he dejado un mensaje en el buzón. Escúchalo cuando puedas. #delay: 120
}
Escríbeme cuando puedas. Papá #delay: 5 #caduca: D3 09:00
* [papa te he oído. estoy bien. no le digas a mamá que te he escrito]
    Vale. No le diré nada. #delay: 300
    Pero si en dos días no sé de ti, cojo el coche y subo. Papá #delay: 5
* [(sin responder)]
- -> charla("D3 08:15") ->
-> d3

= d3
{laia.d2.a_barcelona or laia.d2.a_barcelona_sin:
    Ali. Esta mañana han venido dos Mossos a casa preguntando por ti. #at: D3 08:20
    Tu madre me dijo que estabas con una amiga. ¿Qué está pasando? #delay: 5
    La he llamado y me ha colgado. #delay: 60
    Estoy muy asustado, cariño. Contéstame, por favor. Papá #delay: 10 #caduca: D3 23:00
- else:
    Buenos días, cariño. ¿Has dormido bien? Papá #at: D3 08:20 #caduca: D3 23:00
}
* [papa estoy bien. de verdad. no vengas]
    Vale. Te creo. Bueno, quiero creerte. Papá #delay: 300
* [papa, mamá me ha quitado el dinero de la cuenta]
    ¿Cómo? #delay: 60
    ¿Todo? #delay: 10
    Si es por la plusvalía del piso de la yaya, eso lo pagó tu madre hace dos años. Me acuerdo porque discutimos. #delay: 30
    Mañana mismo hablo con un abogado. Papá #delay: 8
* [(sin responder)]
- -> charla("D4 19:55") ->
-> d4

= d4
He hablado con un abogado, cariño. #at: D4 20:00
Dice que lo de tu cuenta se puede denunciar, pero que tardará. #delay: 5
Pasado mañana subo a Olot. Quiero verte. Papá #delay: 8 #caduca: D5 20:00
* [vale papa. gracias]
    No me las des. Tendría que haberlo hecho hace un año. Papá #delay: 300
* (no_subas) [no subas aún, papa]
    Lo pensaré, cariño. #delay: 300
    Pero no te prometo nada. Llevo un año pensándolo todo y así nos va. Papá #delay: 8
* [(sin responder)]
- -> d5

= d5
Mañana por la tarde subo, cariño. Me quedo en casa de la tía Marta. #at: D5 20:30
Si quieres verme, allí estaré. Papá #delay: 5 #caduca: D6 16:00
* [no sé si podré, papa]
    Con saber que lo has leído me basta. Papá #delay: 300
* [te quiero, papa]
    ... #delay: 300
    Y yo a ti, cariño. Más de lo que te he sabido decir. Papá #delay: 20
* [(sin responder)]
- -> d6

= d6
Ya estoy en Olot, en casa de la tía. #at: D6 17:00
Mañana a primera hora voy a tu casa. Me abra o no me abra tu madre. Papá #delay: 6 #caduca: D7 07:00
* [no vayas a casa, papa]
    ¿Por qué? #delay: 300
    Si me dices por qué, no voy. Si no, voy. Papá #delay: 6
* [ten cuidado]
    ¿Cuidado de qué, cariño? ¿De tu madre? #delay: 300
    Ya no entiendo nada. Pero lo tendré. Papá #delay: 6
* [(sin responder)]
- -> d7

= d7
{rescatada():
    Cariño, me ha llamado una sargento de los Mossos. Voy para la comisaría. #at: D7 08:10
    Ya llego. Ya llego. Papá #delay: 30
- else:
    Estoy delante de tu casa. No hay nadie. Ni tu madre, ni tu hermana. #at: D7 08:10
    ¿Dónde estáis? Papá #delay: 60
}
-> DONE

// Huecos para escribirle. Jordi recuerda poco y mal, pero lo intenta.
= charla(limite)
- (opciones)
#caduca: {limite}
* [papa te acuerdas de la casa de la amiga de la yaya mercè? la de las estrellas]
    Vagamente, cariño. Fuimos un par de veranos, cuando eras muy pequeña. #delay: 300
    Una masía sin luz, con un perro que me mordió el pantalón. Me acuerdo del perro más que de la casa. #delay: 6
    Subíamos por la carretera de Bracons... o por la de Capsacosta. Hace quince años, perdóname. #delay: 8
    Tu madre lo sabrá mejor. Papá #delay: 4
    -> opciones
* {dia >= 2} [papa mamá te ha pedido dinero alguna vez?]
    ~ sabe_secta = true
    El año pasado. Diez mil euros «para un curso». No se los di. #delay: 300
    Desde entonces no me coge el teléfono. Hasta esta semana. Papá #delay: 6
    -> opciones
// Con la libreta de mamá (IMG_0401): el dinero del piso de la yaya no está en ninguna parte.
* {foto_img_0401 and dia >= 2} [papa, sabes por cuánto vendió mamá el piso de la yaya?]
    Por ciento veinte mil. Me lo dijo la gestoría, que aún me escriben a mí por error. #delay: 300
    Y a mí tu madre me dijo que no le había quedado nada. Ciento veinte mil euros no se gastan en dos años, cariño. Papá #delay: 8
    #caduca: {limite}
    ** [en una libreta suya pone «c.r.a.» al lado]
        ¿C.R.A.? No me suena de nada. #delay: 300
        Pero tu madre nunca ha apuntado en una libreta nada que no fuera dinero. Guárdate esa foto. Papá #delay: 6
    ** [ya. no sé dónde está]
        Ni yo. Y eso es lo que me preocupa. Papá #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3 and not d4.no_subas} [papa no subas. de verdad]
    Lo siento, cariño. Esta vez no te voy a hacer caso. Papá #delay: 300
    -> opciones
* {dia >= 2} [papa qué tal por barcelona?]
    Bien. Solo. Arreglando ascensores que nadie da las gracias por que funcionen. #delay: 300
    El otro día salí en bici hasta Montserrat. Pensé en ti, que de pequeña me decías que la montaña tenía dedos. Papá #delay: 8
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
- ->->
