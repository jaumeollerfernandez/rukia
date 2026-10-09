// Grupo «Las de siempre 💅»: Núria, Carla, Mireia y Alicia. Mucho ruido, algo de preocupación.

=== amigas ===
-> historial

= historial
CHICAS el cumple de mireia lo hacemos en el bosc o en mi casa #from: carla #at: D-4 16:20
en tu casa que en el bosc hace un frío que te mueres #from: mireia #at: D-4 16:22
yo voto bosc 🌲🔥 #from: nuria #at: D-4 16:40
yo lo que digáis #from: me #at: D-4 17:05
ali tú siempre «lo que digáis» 🙄 #from: carla #at: D-4 17:05
es que me da igual de verdad jajaja #from: me #at: D-4 17:06
-> d1

= d1
alguien sabe algo de ali?? #from: carla #at: D1 12:40
su madre ha llamado a la mía esta mañana #from: mireia #delay: 300
dice que está con una amiga #from: mireia #delay: 3
con qué amiga si somos todas 😶 #from: carla #delay: 20
yo le he escrito. esperad #from: nuria #delay: 240
@Ali dinos algo aunque sea un sticker #from: carla #delay: 600
por cierto alguien tiene los apuntes de psico? #from: mireia #at: D1 18:10
pídeselos a Sergi que es un empollón #from: carla #delay: 120
no me hables de sergi #from: mireia #delay: 30
qué ha pasado con sergi 👀👀 #from: carla #delay: 5
nada. luego os cuento #from: mireia #delay: 60
ali si no contestas hoy mañana vamos a tu casa #from: carla #at: D1 22:50 #caduca: D2 11:00
* [estoy bien chicas, os cuento pronto 💛]
    ALIIIII #from: carla #delay: 40
    menos mal tía 😭 #from: mireia #delay: 10
    pronto cuándo #from: nuria #delay: 300
* [Estoy bien, no vengáis.]
    «no vengáis.» 😶 #from: carla #delay: 60
    qué seca #from: mireia #delay: 20
    ...vale #from: nuria #delay: 300
* (vienen) [(sin responder)]
    vale pues mañana vamos #from: carla #delay: 1
- -> charla("D2 11:55") ->
-> d2

= d2
{amigas.d1.vienen:
    hoy vamos a tu casa ali, a las 6 #from: carla #at: D2 12:00
    hemos ido a tu casa #from: carla #at: D2 18:40
    nos ha abierto tu hermana. superrara #from: carla #delay: 3
    que estás «de retiro» 😶 #from: mireia #delay: 20
    había velas por todo el pasillo y olía a incienso #from: carla #delay: 10
    y tu madre ni ha salido a saludar #from: mireia #delay: 5
    ali eso es lo de la gente esa con la que va tu madre? #from: nuria #delay: 300
- else:
    vale ali, si dices que estás bien... #from: carla #at: D2 12:00
    pero escribe más porfa #from: mireia #delay: 60
}
mañana es mi cumple y una de nosotras no viene 💔 #from: mireia #at: D2 21:00
en mi casa a las 9. traed algo. lo que sea #from: mireia #delay: 30 #caduca: D3 12:00
* [feliz cumple adelantado mire 🎂💛 este año no puedo]
    jo 🥺 #from: mireia #delay: 120
    te guardamos un trozo de tarta #from: carla #delay: 30
* [(sin responder)]
- -> charla("D2 23:55") ->
-> d3

= d3
FELIZ CUMPLE MIREIA 🎂🎉 #from: carla #at: D3 00:01
graciaaas 😭💛 #from: mireia #delay: 300
felicidades mire!! #from: nuria #at: D3 09:10
{nuria_denuncia:
    chicas. tengo que contaros una cosa #from: nuria #at: D3 21:00
    quien escribe desde el móvil de ali no es ali #from: nuria #delay: 5
    QUÉ #from: carla #delay: 20
    cómo lo sabes #from: mireia #delay: 10
    le he hecho preguntas. no las ha sabido #from: nuria #delay: 30
    no le contestéis nada. mañana voy a comisaría #from: nuria #delay: 5
    me da miedo tía #from: carla #delay: 60
}
fotos de la fiesta ya en gonpi 📸 #from: carla #at: D3 23:40
faltabas tú, ali 💔 #from: mireia #delay: 30
-> charla("D4 12:55") ->
-> d4

= d4
{nuria_denuncia:
    nuri has ido al final? #from: carla #at: D4 13:00
    sí. dicen que lo están investigando #from: nuria #delay: 60
    o sea que nada #from: mireia #delay: 20
- else:
    alguien ha visto la foto de nuri en gonpi? 🥺 #from: carla #at: D4 21:30
    yo he llorado #from: mireia #delay: 60
}
-> charla("D5 22:15") ->
-> d5

= d5
habéis visto lo que ha subido la cuenta esa del casal? la de la madre de ali #from: carla #at: D5 22:20
lo del cráter? #from: mireia #delay: 60
«solo familias completas». qué mal rollo #from: carla #delay: 10
ali dime que no vas a ir a eso #from: mireia #delay: 30
-> charla("D6 12:25") ->
-> d6

= d6
chicas y si mañana vamos al cráter a ver qué pasa #from: carla #at: D6 12:30
ni loca #from: mireia #delay: 30
yo tampoco. eso es cosa de la policía #from: nuria #delay: 300
os odio. vale #from: carla #delay: 20
-> d7

= d7
habéis visto gonpi??? policía en el cráter de santa margarida #from: carla #at: D7 08:00
{rescatada():
    nuri dice que ali está bien!!! #from: mireia #at: D7 10:30
    😭😭😭😭 #from: carla #delay: 10
    ya os contaré. ahora no puedo #from: nuria #delay: 300
- else:
    y ali? #from: mireia #at: D7 08:30
    nadie sabe nada #from: nuria #delay: 300
}
-> DONE

// Lo que el jugador puede escribir en el grupo entre escena y escena. Si Núria ha dicho que no eres Ali, nadie suelta nada.
= charla(limite)
- (opciones)
#caduca: {limite}
* [os echo de menos 🥺]
    {nuria_denuncia:
        ya #from: carla #delay: 300
    - else:
        VUELVE YA #from: carla #delay: 120
        te guardamos el sitio en el sofá de mire 💛 #from: mireia #delay: 60
    }
    -> opciones
* {dia >= 2 and not nuria_denuncia} [qué os dijo mi hermana exactamente?]
    {amigas.d1.vienen:
        que estabas «en un lugar donde te estás reencontrando» #from: carla #delay: 300
        y que mejor no te escribiéramos, que te «cargábamos la energía» 🙄 #from: mireia #delay: 30
        tía tu hermana me da miedo. te lo digo con cariño #from: carla #delay: 10
    - else:
        no hemos hablado con ella. ni ganas #from: carla #delay: 300
    }
    -> opciones
* {dia >= 2 and not nuria_denuncia} [alguna sabe algo de dani, el de la moto?]
    ~ dani_sospechoso = true
    el de olot? ali tía #from: carla #delay: 300
    mireia dice que os vio juntos en el bar de la plaza #from: carla #delay: 4
    yo no dije eso. dije que lo vi a él mirándote toda la noche. que es distinto #from: mireia #delay: 60
    y que luego se fue detrás de ti con la moto #from: mireia #delay: 5
    -> opciones
* {dia >= 3 and not nuria_denuncia} [os acordáis de las historias que os contaba de la casa de mi iaia?]
    la de la perra que ladraba a los fantasmas?? jajaja #from: carla #delay: 300
    y la otra, la de los murciélagos, que nos dabas miedo con ella en el campamento #from: mireia #delay: 60
    {not nuria_sabe:
        chicas no es momento #from: nuria #delay: 600
    }
    -> opciones
+ [(sin responder)]
- ->->
