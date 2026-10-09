// Laura, 18, del pueblo (comisión de fiestas, Gonpi @lauravila_). Simpática y servicial... y la está captando el Casal.
// Todo lo que le cuentes llega a Berta. Señales para el jugador atento: su comentario en el post del Casal (D4),
// «una sesión de mindfulness en un jardín de Sant Joan les Fonts» (D3) y que se haya hecho amiga de Berta.

=== laura ===
-> historial

= historial
ali el cartel para cuándo?? 🎨 #at: D-6 19:05
la semana que viene, te lo juro #from: me #at: D-6 22:12
te tomo la palabra 🌻 #at: D-6 22:13
-> d2

= d2
ali!! 💛 #at: D2 19:40
me he enterado de todo. tu madre está fatal la pobre, la vi ayer en el mercado #delay: 5
si necesitas cualquier cosa, comida, ropa, que te suba algo, lo que sea, me lo dices #delay: 6
no se lo cuento a nadie, te lo prometo 🤞 #delay: 4 #caduca: D3 18:00
* [gracias laura, estoy bien 💛]
    vale guapa. aquí estoy para lo que sea 🌻 #delay: 120
* [ahora mismo me iría bien una manta jajaja]
    dime dónde y te la subo esta noche!! #delay: 60
    tengo el coche de mi madre 🚗 #delay: 4 #caduca: D3 18:00
    ** [mejor no. ya te diré]
        vale vale. cuando quieras 🌻 #delay: 120
    ** [por capsec, cerca del refugi del esplai]
        ~ laura_sabe = true
        ~ secta_a_capsec = true
        capsec!! vale. mañana te la subo 💛 #delay: 60
    ** {sabe_rosalia or sabe_estrellas or sabe_puerta_azul} [subiendo hacia bracons, pasado sant salvador]
        ~ laura_sabe = true
        ~ secta_sabe_rosalia = true
        uy qué lejos. vale, ya miro cómo 🌻 #delay: 60
    ** [(sin responder)]
* [(sin responder)]
- -> d3

= d3
hoy he ido a una sesión de mindfulness en un jardín de sant joan les fonts #at: D3 18:00
qué paz tía 🌿 #delay: 3
tu hermana estaba allí. súper maja. hemos hablado un montón de ti 🥰 #delay: 6
{secta_a_capsec:
    al final no pude subir la manta, perdona!! 😞 #delay: 30
}
#caduca: D4 18:00
* [laura porfa no le cuentes nada de mí a mi hermana]
    {laura_sabe:
        ups #delay: 120
        es que me ha preguntado si sabía algo y le he dicho lo de la manta 😬 #delay: 5
        pero solo eso!! perdona 🙈 #delay: 4
    - else:
        uy, vale. es que me ha preguntado si sabía algo #delay: 120
        ya no digo nada 🤐 #delay: 4
    }
* [qué te ha contado berta?]
    que estás en «un proceso muy bonito» y que vas a volver para algo importante 🥹 #delay: 120
    que se nota que te quiere un montón #delay: 4
    y que el casal no es lo que la gente dice. que es como una familia 🌹 #delay: 6
* [(sin responder)]
- -> d4

= d4
{
- laura_sabe and secta_a_capsec:
    ali, berta dice que mañana suben ellos a capsec a buscarte #at: D4 20:30
    que te echan mucho de menos 🥹 #delay: 4
    yo solo le dije lo de la manta, eh!! #delay: 10
- laura_sabe:
    ali una cosa #at: D4 20:30
    le he dicho a berta lo de bracons. sin querer, de verdad 🙈 #delay: 5
    me ha dado las gracias llorando. ha dicho que ignasi se iba a poner muy contento #delay: 6
    eso es bueno, no? 🥹 #delay: 8
- else:
    dentro de dos días hay retiro de silencio en el casal. me apunto!! 🌿 #at: D4 20:30
    tu hermana dice que me va a ir genial #delay: 4
}
-> d6

= d6
{laura_sabe:
    ali ya no me contestas 🥺 #at: D6 12:00
    berta dice que mañana es un día muy especial y que estarás. de verdad? #delay: 6
}
-> d7

= d7
{familia_salvada():
    he visto lo del cráter #at: D7 10:00
    ali yo no sabía nada. te lo juro 😭 #delay: 10
    y yo me iba a apuntar al retiro #delay: 30
}
-> DONE
