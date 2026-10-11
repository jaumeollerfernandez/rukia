// Pilar, la jefa del Forn Can Batlle (Olot). Madruga mucho, va con prisas y escribe con audios... que aquí son textos.
// Llama el D3 (audio/pilar.m4a).
// Vida propia: el horno a las cinco, las cuentas del cierre, el Forn sin la chica de los fines de semana.
// Recuerda si Alicia le miente con las barras: lo deja caer después.

=== pilar ===
-> historial

= historial
Alicia mañana entras a las 6 que Èric no puede. Gracias guapa #at: D-6 14:02
vale pilar #from: me #at: D-6 14:30
-> d1

= d1
Alicia ayer no viniste y no avisaste #at: D1 07:05
Así no se puede trabajar. Llámame cuando leas esto #delay: 20
Y otra cosa. El otro día cerraste tú y no fichaste la salida #delay: 3 #caduca: D2 07:00
* [perdona pilar, estoy enferma. lo siento mucho]
    Pues avisa, mujer. Que me has dejado sola con el horno #delay: 1800
    Ponte buena #delay: 10
* [ahora no puedo llamarte, pilar]
    Pues escríbeme. Con los dedos, como todo el mundo #delay: 1800
    Pero dime algo, que no sé si contar contigo el sábado #delay: 6
* [lo de fichar lo arreglo cuando vuelva]
    Lo de fichar es lo de menos, Alicia #delay: 1800
    Lo de más es que no viniste #delay: 4
* [(sin responder)]
    Mañana hablamos #delay: 1
- -> d2

= d2
Alicia #at: D2 06:50
O me dices algo hoy o busco a otra para los fines de semana. Lo siento pero no puedo #delay: 10
Y lo de las barras no me lo invento. Seis. Las cuento siempre #delay: 5 #caduca: D3 07:00
* [pilar perdona, las barras te las pago]
    No es por el dinero, nena #delay: 1800
    Es que no es la primera vez. Mañana te llamo y hablamos #delay: 6
* (mentira) [esas barras no las cogí yo]
    Alicia, que nos conocemos #delay: 1800
    Las cuento siempre. Y solo cerraste tú. No me mientas, que eso sí que no #delay: 6
* [eran para alguien que lo necesita]
    Ya me lo imaginaba #delay: 1800
    Pues avísame y te las aparto, mujer. Pero no te las lleves a escondidas, que parezco tonta #delay: 6
* [busca a otra, pilar. no sé cuándo podré volver]
    Vaya #delay: 1800
    No la busco todavía. Pero no me hagas esperar mucho #delay: 5
* [(sin responder)]
    Mañana te llamo #delay: 1
- -> d3

= d3
Te llamo, nena #at: D3 07:15 #call: audio/pilar.m4a
No lo coges. Bueno #delay: 60
Tú sabrás lo que haces. Pero ten cuidado #delay: 5
{d2.mentira:
    Y no me vuelvas a decir que no fuiste tú. Que no me enfado por el pan #delay: 5
}
#caduca: D4 07:00
* [gracias, pilar]
    De nada. Y come algo, que estás en los huesos #delay: 1800
* [no puedo hablar por teléfono]
    Ya lo veo #delay: 1800
    Pues aquí me tienes. Por escrito, que es más lento pero también vale #delay: 5
* [(sin responder)]
- -> d5

= d5
{arnau_calla: -> d5_horno}
Nena, esta mañana ha venido tu hermana. Preguntando por el pan #at: D5 11:30
Yo no le he dicho nada. Pero el Èric, el muy bocazas... #delay: 5
Lo siento, nena. Ten cuidado #delay: 4
#caduca: D6 07:00
* [qué le dijo eric?]
    Lo de la señora de arriba. Lo del pan #delay: 1800
    Ese chico tiene la boca más grande que el horno #delay: 4
* [gracias por no decir nada]
    Yo no digo nada a nadie, nena. Ni a tu hermana ni al Papa #delay: 1800
* [(sin responder)]
- -> d6

= d5_horno
No sé nada de ti, nena. Pero me acuerdo de ti cada mañana al encender el horno #at: D5 06:30
-> d6

// D6: Dani también ha pasado por el Forn (la deuda de Dani, PISTAS.md).
= d6
Nena, ha venido ese chico de la moto. El Dani #at: D6 07:40
~ sabe_dani_busca = true
Quería saber a quién le subías el pan. Le he dicho que a nadie #delay: 5
Tenía un ojo morado y no paraba de mirar el móvil. Ten cuidado #delay: 5 #caduca: D7 05:00
* [si vuelve, no le digas nada]
    No le he dicho nada y no le diré. Ese chico no me ha gustado nada #delay: 1800
* [gracias pilar]
    De nada. Cierra bien las puertas, estés donde estés #delay: 1800
* [(sin responder)]
- -> d7

= d7
Nena, hoy enciendo el horno pensando en ti #at: D7 05:30
-> DONE
