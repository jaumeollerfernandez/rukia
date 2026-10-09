// Dani, 22 años, la conoció por Gonpi. Insistente, posesivo y raro: parece sospechoso, pero es inocente.
// Menudea en Olot (costo, hierba, alguna pastilla los findes) y por eso se oculta; lo que parece «secreto» es eso.
// Su coartada (un concierto en Barcelona) no llega hasta el D3, en Gonpi. Hasta entonces, todo en él da mal rollo:
// sabe que se ha ido, ronda su calle con la moto y tiene un mas «sin cobertura» en la Vall d'en Bas (donde esconde el costo).
// Tonteo con Alicia: un beso en la fiesta de Mireia, que ella llamó «un error». Él lo vivió como el principio de algo.
// Subtrama de confusión: unos del bar le reclaman dinero (D3), él lo confiesa solo (D4) y, si el jugador manda una
// patrulla a su mas (police/actions.json, knot envio_dani en central.ink), los Mossos le requisan el costo (dani_registrado).

=== dani ===
-> historial

= historial
ey fue top lo de anoche 😏 #at: D-34 23:50
dani fue un error. estaba nerviosa, no pasó nada #from: me #at: D-34 23:58
ya claro. un error. por eso me mirabas así #at: D-33 00:02
no me tomes el pelo ali #at: D-33 00:03
no te tomo el pelo. somos amigos y punto #from: me #at: D-33 08:10
amigos 😂 vale guay #at: D-33 08:11
con quién estabas ayer en la plaza? te vi con un chico #at: D-20 21:15
con pol. es mi amigo, dani #from: me #at: D-20 21:30
pol el de la furgo? no me gusta ese tío #at: D-20 21:31
deja de seguirle en gonpi, hazme caso #at: D-20 21:31
no eres quién para decirme eso #from: me #at: D-20 21:50
perdona. me preocupo por ti #at: D-20 21:51
estás despierta? #at: D-12 02:10
tengo algo que te va a gustar 🌿 #at: D-12 02:11
ni de coña dani. ya te dije que no quiero saber nada de eso #from: me #at: D-12 09:00
es solo un poco de hierba, no te hagas la santa #at: D-12 09:05
es que no. y deja de escribirme de madrugada #from: me #at: D-12 09:06
vale vale. no te enfades #at: D-12 09:07
q haces #at: D-5 23:40
nada, estudiando #from: me #at: D-5 23:58
pásate por olot esta semana #at: D-5 23:58
te invito a algo #at: D-5 23:59
no puedo, cosas de familia #from: me #at: D-4 10:12
siempre igual #at: D-4 10:12
-> d1

= d1
ey #at: D1 23:30
ya no contestas? #delay: 600
vale guay #delay: 900
me dejas en visto desde hace tres días #delay: 5
y luego te vas de casa sin decir nada a nadie #delay: 300
~ dani_sospechoso = true
sí, me he enterado. olot es pequeño 😏 #delay: 4 #caduca: D2 03:00
* [quién te lo ha dicho?]
    tengo mis fuentes jajaja #delay: 120
    tranqui que no se lo digo a nadie #delay: 4
    si me dices dónde estás te llevo algo. lo que sea. tengo la moto #delay: 6
* [perdona, estoy liada]
    ya, liada #delay: 120
    bueno avísame cuando tengas tiempo para mí #delay: 10
* [déjame en paz dani]
    wow #delay: 60
    vale vale. ya está #delay: 4
    luego no vengas llorando #delay: 300
* [(sin responder)]
    sigues viva o qué 😂 #delay: 1
- -> charla("D2 22:35") ->
-> d2

= d2
oye #at: D2 22:40
me ha escrito tu hermana por gonpi #delay: 4
preguntando si sé dónde estás #delay: 3
tu familia es rara de cojones #delay: 60
qué pasa contigo? #delay: 5 #caduca: D3 03:00
* [qué le has dicho?]
    que no sé nada. porque no sé nada #delay: 120
    y tu hermana me ha dado mal rollo. me ha dicho que «la luz siempre encuentra el camino». qué coño significa eso #delay: 8
* [no le digas nada a mi hermana, porfa]
    no pensaba. ni la conozco #delay: 120
    me ha dado mal rollo #delay: 4
* [(sin responder)]
- -> charla("D3 21:25") ->
-> d3

= d3
mira mi gonpi #at: D3 21:30
el concierto de barna. para que veas que esa noche estaba allí y no soy ningún psicópata jaja #delay: 4
tu hermana me ha vuelto a escribir. la he bloqueado #delay: 30
-> d3_noche

// Unos del bar le reclaman dinero. Es otro frente que no tiene nada que ver con Alicia, pero suena a «gente peligrosa».
= d3_noche
ey #at: D3 23:40
estás? #delay: 30
una movida. unos tíos del bar de la plaza han ido a mi casa #delay: 5
a reclamarme pasta. nada que ver contigo eh #delay: 4
pero si ves un audi negro por tu calle, dímelo #delay: 6
matrícula de andorra #delay: 3 #caduca: D4 03:00
* [qué pasta les debes?]
    ~ dani_sospechoso = true
    cosas mías. del curro #delay: 120
    tranqui, lo arreglo el finde #delay: 5
* [dani, en qué lío estás metido?]
    en ninguno. un malentendido #delay: 90
    ...vale, uno pequeñito. mañana te cuento #delay: 6
* [no me escribas más de esto]
    vale. era por si acaso #delay: 90
* [(sin responder)]
    joder, hasta tú #delay: 1
- -> charla("D4 22:00") ->
-> d4

// Se lo cuenta él antes de que se entere por otro lado.
= d4
oye #at: D4 22:05
lo de los del bar. mejor te lo cuento yo antes de que lo oigas por ahí #delay: 5
vendo un poco, ali. costo, hierba, alguna pastilla los findes en el bar #delay: 8
~ dani_droga = true
nada gordo. 200 pavos a la semana y gracias #delay: 5
el del audi es el que me pasa el costo. me pidió todo de golpe y ya se lo he dado #delay: 6
tú nunca quisiste saber nada de eso. por eso te lo cuento a ti, no sé #delay: 6
y supongo que por eso me puse tan pesado. sabía que no ibas a decir nunca que sí #delay: 8
-> charla("D5 23:10") ->
-> d5

= d5
ey #at: D5 23:15
he visto lo que ha subido esa cuenta del cráter #delay: 4
es lo de tu familia, no? #delay: 3
{dani_droga:
    lo del bar ya está arreglado. me han dejado el ojo morado pero está arreglado 🙃 #delay: 6
    he dejado lo de vender. no por ti. por mí #delay: 6
}
cuídate de verdad. y perdona si fui pesado #delay: 10
-> d6

// D6: si el jugador mandó una patrulla a su mas, los Mossos le han requisado el costo.
= d6
#at: D6 13:00
{dani_registrado:
    ali #delay: 1
    los mossos han estado en el mas de mi tío con perros #delay: 5
    se han llevado lo que había y me han citado en comisaría #delay: 5
    no sé qué os han dicho de mí. yo de ti no sé nada, te lo juro #delay: 6
    ~ dani_avisado = true
- else:
    si estás ahí, ali #delay: 3
    lo que sea que te pase, mi casa está para lo que quieras. sin rollos #delay: 6
    contesta cuando puedas, aunque sea con un emoji #delay: 8
}
-> d7

= d7
#at: D7 01:30
{dani_registrado and not dani_avisado:
    ali #delay: 1
    los mossos han estado en el mas de mi tío con perros #delay: 5
    se han llevado lo que había. no sé qué buscaban #delay: 6
    yo de ti no sé nada, te lo juro #delay: 6
}
-> DONE

// Lo que el jugador le puede preguntar entre escena y escena. Hasta el D3 se escabulle; después, se explica.
= charla(limite)
- (opciones)
#caduca: {limite}
* [dani dónde estabas anteanoche?]
    {dia < 3:
        por ahí #delay: 120
        por? me echabas de menos? 😏 #delay: 4
    - else:
        te lo he dicho. en barna, en un concierto. mira mi gonpi #delay: 120
    }
    -> opciones
* {dani_sospechoso} [tú me has seguido con la moto?]
    seguido? #delay: 120
    he pasado por tu calle alguna vez. vivo al lado de la gasolinera, paso siempre por ahí #delay: 5
    vale, igual alguna vez de más 🙃 #delay: 30
    no soy un psicópata ali #delay: 4
    -> opciones
* [tienes algún sitio fuera de olot?]
    ~ dani_sospechoso = true
    el mas de mi tío en la vall d'en bas. subo a veces a hacer barbacoas #delay: 120
    por? quieres venir? 😏 #delay: 4
    no hay cobertura eso sí. ni vecinos jajaja #delay: 6
    -> opciones
* {dia >= 2 and not dani_droga} [dani, de qué vives de verdad?]
    del gym y de los repartos #delay: 120
    {dia < 4:
        comida y cosas. por qué esa pregunta? #delay: 5
        ~ dani_sospechoso = true
    - else:
        vale. repartos de los otros. no preguntes más, porfa #delay: 6
    }
    -> opciones
* {dani_droga} [y la droga dónde la guardas?]
    ...en el mas de mi tío. en la cuadra, tras unos sacos #delay: 120
    por eso no quiero que suba nadie. no es por ti, ali. te lo juro #delay: 6
    -> opciones
* {dani_droga} [alguna vez has hecho daño a alguien?]
    una vez, en una discoteca. un tío me tiró el vaso y le rompí la nariz #delay: 150
    me denunciaron. archivaron #delay: 5
    pero a ti no te haría eso jamás #delay: 6
    -> opciones
* {dia >= 2} [por qué me controlabas tanto?]
    yo no te controlaba #delay: 120
    ...vale. un poco. mi ex me puso los cuernos con un colega y me quedé tocado #delay: 6
    me asusta que la gente se vaya sin avisar #delay: 6
    tú sabes que no te haría nada, ali #delay: 4
    -> opciones
* {dia >= 3} [perdona si he sido borde]
    tranqui. yo también he sido un pesado #delay: 120
    -> opciones
+ [(sin responder)]
- ->->
