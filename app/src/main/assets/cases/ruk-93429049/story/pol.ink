// Pol, el exnovio, de Olot. Tiene coche. Anteanoche llevó a Alicia a la estación de autobuses de Olot (la pista falsa).
// Lo que Alicia no le dijo: el rastro hacia la estación lo dejó a propósito.

=== pol ===
-> historial

= historial
ya no me hablas ni para lo de los apuntes eh #at: D-6 20:14
jajaja perdón, semana horrible #from: me #at: D-6 22:03
pol #from: me #at: D-1 22:40
puedes llevarme a la estación de olot esta noche? #from: me #at: D-1 22:40
no preguntes porfa #from: me #at: D-1 22:41
ahora? #at: D-1 22:44
estás bien? #at: D-1 22:44
sí. a las 11 y media en la rotonda del hostalnou #from: me #at: D-1 22:47
vale. voy #at: D-1 22:49
gracias. te debo una enorme #from: me #at: D-1 22:49
ya estás? #at: D-1 23:51
te he visto rara. avísame cuando llegues #at: D-1 23:52
-> d1

= d1
oye #at: D1 22:15
al final cogiste el bus? #delay: 3
tu madre ha venido a mi casa esta tarde #delay: 60
con un tío mayor que no conozco. barba blanca, hablaba superbajito. daba un mal rollo... #delay: 8
me han preguntado si sabía algo de ti. les he dicho que no #delay: 6
de momento #delay: 30 #caduca: D2 01:00
* [no les digas nada de la estación porfa]
    ~ pol_calla = true
    tranqui. no diré nada #delay: 20
    pero me debes una explicación #delay: 4
* [qué tío?]
    ni idea. tu madre le llamaba «guía» o algo así #delay: 25
    es lo de la secta esa de la que me hablaste una vez? #delay: 6 #caduca: D2 01:00
    ** [sí. no les digas nada de mí porfa]
        ~ pol_calla = true
        ~ sabe_secta = true
        joder ali #delay: 15
        vale. no digo nada #delay: 3
    ** [no es una secta, son amigos de mi madre]
        ya. amigos #delay: 20
    ** [(sin responder)]
        vale. ya me contarás #delay: 1
* [sí, ya estoy lejos]
    lejos dónde #delay: 15
    bueno, no me lo digas. mejor no saberlo #delay: 10
* [(sin responder)]
    vale, ni caso. como siempre #delay: 1
- -> d2

= d2
oye #at: D2 18:30
no dejo de darle vueltas a lo de anteanoche #delay: 4
cuando te dejé en la estación no fuiste a los buses #delay: 6
te vi por el retrovisor. te ibas andando hacia la salida de bianya #delay: 5
con una bolsa de pan enorme. pan. a las doce de la noche #delay: 6
~ sabe_pan = true
qué está pasando ali #delay: 10 #caduca: D3 01:00
* [era para el camino jajaja]
    ya. muy graciosa #delay: 60
    si necesitas que te lleve a algún sitio, me lo dices y ya #delay: 6
* [no se lo digas a nadie pol. a nadie]
    ~ pol_calla = true
    vale #delay: 30
    pero si te pasa algo y yo me he callado, no me lo perdono #delay: 6
* [(sin responder)]
    vale. vale #delay: 1
- -> d3

= d3
ali solo dime una cosa #at: D3 22:00
estás a salvo? #delay: 5 #caduca: D4 01:00
* [sí pol. gracias por todo]
    con eso me vale #delay: 60
    cuídate #delay: 4
* [(sin responder)]
    vale #delay: 1
- -> d4

= d4
{pol_calla:
    tu madre ha vuelto. con el de la barba #at: D4 19:30
    no les he dicho nada. ni lo de la estación ni lo del pan #delay: 5
    pero el tío me ha dicho que «la verdad siempre encuentra la luz». qué mal rollo #delay: 6
- else:
    ali lo siento #at: D4 19:30
    tu madre ha vuelto con el de la barba #delay: 4
    les he dicho lo de la estación. que te dejé allí #delay: 5
    y que te fuiste andando hacia bianya #delay: 4
    el tío me miraba de una forma... lo siento de verdad #delay: 10
}
-> d5

= d5
{not pol_calla:
    no paro de pensar que la he cagado #at: D5 20:00
    si te pasa algo por mi culpa... #delay: 10
}
-> d6

= d6
{not pol_calla:
    he ido a los mossos. les he contado todo. lo de tu madre también #at: D6 10:00
    no sé si sirve de algo. pero ya está #delay: 5
- else:
    pase lo que pase mañana, aquí estoy #at: D6 21:30
}
-> d7

= d7
{rescatada():
    me ha escrito tu tía. que estás bien #at: D7 11:00
    pues eso. que me alegro un montón #delay: 10
}
-> DONE
