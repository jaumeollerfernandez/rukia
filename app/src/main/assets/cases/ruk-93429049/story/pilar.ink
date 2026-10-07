// Pilar, la jefa del Forn Can Batlle (Olot). Madruga mucho, va con prisas y escribe con audios... que aquí son textos.
// Llama el D3 (audio/pilar.m4a).

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
* [(sin responder)]
    Mañana te llamo #delay: 1
- -> d3

= d3
Te llamo, nena #at: D3 07:15 #call: audio/pilar.m4a
No lo coges. Bueno #delay: 60
Tú sabrás lo que haces. Pero ten cuidado #delay: 5
// D4: nada.
-> d5

= d5
{not arnau_calla:
    Nena, esta mañana ha venido tu hermana. Preguntando por el pan #at: D5 11:30
    Yo no le he dicho nada. Pero el Èric, el muy bocazas... #delay: 5
    Lo siento, nena. Ten cuidado #delay: 4
- else:
    No sé nada de ti, nena. Pero me acuerdo de ti cada mañana al encender el horno #at: D5 06:30
}
-> d6

= d6
// D6: nada.
-> d7

= d7
Nena, hoy enciendo el horno pensando en ti #at: D7 05:30
-> DONE
