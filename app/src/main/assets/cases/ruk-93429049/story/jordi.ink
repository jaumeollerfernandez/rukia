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
        Poco. Se llaman «Rosa d'Abril». Tu madre empezó a ir con Berta hace dos años. #delay: 180
        Por eso nos separamos, en parte. Ya lo sabes. #delay: 30
        No me gusta hablar de esto por aquí. Te llamo pronto. Papá #delay: 20
    ** [(sin responder)]
        Bueno, ya me dirás. Papá #delay: 1
* [(sin responder)]
    Supongo que estás liada. Un beso. Papá #delay: 1
- -> d2

= d2
Cariño, te llamo un momento. #at: D2 20:30 #call: audio/jordi.m4a
No has dicho nada. Supongo que había mala cobertura. #delay: 120
Escríbeme cuando puedas. Papá #delay: 5 #caduca: D3 09:00
* [papa te he oído. estoy bien. no le digas a mamá que te he escrito]
    Vale. No le diré nada. #delay: 300
    Pero si en dos días no sé de ti, cojo el coche y subo. Papá #delay: 5
* [(sin responder)]
- -> d3

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
    ~ sabe_secta = true
    ¿Cómo? #delay: 60
    Esa mujer... #delay: 10
    Mañana mismo hablo con un abogado. Y con los Mossos, si hace falta. Papá #delay: 8
* [(sin responder)]
- -> d4

= d4
He hablado con un abogado, cariño. #at: D4 20:00
Dice que lo de tu cuenta se puede denunciar, pero que tardará. #delay: 5
Pasado mañana subo a Olot. Quiero verte. Papá #delay: 8
-> d5

= d5
Mañana por la tarde subo, cariño. Me quedo en casa de la tía Marta. #at: D5 20:30
Si quieres verme, allí estaré. Papá #delay: 5
-> d6

= d6
Ya estoy en Olot, en casa de la tía. #at: D6 17:00
Mañana a primera hora voy a tu casa. Me abra o no me abra tu madre. Papá #delay: 6
-> d7

= d7
{rescatada():
    Cariño, me ha llamado una sargento de los Mossos. Voy para la comisaría. #at: D7 08:10
    Ya llego. Ya llego. Papá #delay: 30
- else:
    Estoy delante de tu casa. No hay nadie. Ni tu madre, ni tu hermana. #at: D7 08:10
    ¿Dónde estáis? Papá #delay: 60
}
-> DONE
