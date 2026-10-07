// Èric, compañero de la panadería, 19 años. Le gusta Alicia y lo disimula fatal. Primera pista del pan.

=== eric ===
-> historial

= historial
mañana me traes un café y te perdono lo de la masa madre jajaja #at: D-3 13:40
la masa madre se murió sola!! #from: me #at: D-3 15:02
asesina #at: D-3 15:02
-> d1

= d1
eh #at: D1 14:10
te he cubierto el turno de ayer. me debes una birra 🍺 #delay: 3
pilar estaba que trinaba #delay: 5
por cierto dice que faltan seis barras de payés del cierre del otro día #delay: 30
yo no he dicho nada eh 🤐 #delay: 3 #caduca: D2 13:00
* [gracias eric, te debo una enorme]
    dos birras #delay: 120
    y me cuentas qué te pasa, que se te nota #delay: 6
* [qué barras?]
    ~ sabe_pan = true
    las del cierre. el día que cerraste tú #delay: 90
    pilar las cuenta siempre, ya sabes cómo es #delay: 5
    yo le he dicho que se habrían quemado, pero no se lo ha tragado #delay: 6
    tranqui que no pasa nada. pero si eran para alguien, avísame y le digo algo #delay: 10
* [(sin responder)]
    vale, ya me contarás 🍞 #delay: 1
- -> d2

= d2
has visto mi post 🕵️🥖 jajaja #at: D2 13:20
pilar me ha echado una bronca... pero ya lo ha visto medio olot #delay: 4
oye ahora en serio #delay: 600
estás bien? se te echa de menos por aquí. bueno, te echo de menos yo #delay: 8
no he dicho eso #delay: 2 #caduca: D3 13:00
* [jajaja sí lo has dicho]
    borrado. no existe #delay: 60
    cuídate anda #delay: 4
* [estoy bien eric. gracias por cubrirme]
    a mandar #delay: 60
* [(sin responder)]
- -> d3

= d3
oye #at: D3 16:00
pilar está rarísima hoy. te ha llamado? #delay: 3
me ha preguntado si alguna vez te he visto llevar pan a «la señora de arriba» #delay: 5
ni idea de qué habla 🤷 #delay: 4
-> d4

= d4
pilar dice que si vuelves, el puesto es tuyo #at: D4 14:30
y yo también te espero. para la birra digo 🙃 #delay: 4
-> d5

= d5
{not arnau_calla:
    ali la he liado #at: D5 11:00
    ha venido tu hermana y le he dicho lo de «la señora de arriba» sin pensar #delay: 4
    me ha sonreído de una manera rarísima #delay: 5
    perdóname #delay: 3
- else:
    hoy hemos hecho cocas de chicharrones. te guardo una 🍞 #at: D5 13:00
}
-> d6

= d6
// D6: nada.
-> d7

= d7
// D7: nada.
-> DONE
