// Dani, 22 años, la conoció por Gonpi. Insistente, posesivo y raro: parece sospechoso, pero es inocente.
// Pista falsa principal (la trata): le ofreció un «curro» en Barcelona con su amigo Iker (piso pagado, «no lo digas en casa»),
// justo cuando Barcelona desarticula una red que capta chicas así. Y la noche que ella se fue, él estaba en Barcelona.
// La verdad: se iba a mudar y la quería allí, para él. Si el jugador lo manda detener (envio_detencion_dani), lo confiesa
// y el jugador decide si le cree. Si le cree, cuenta lo que vio siguiéndola (stitch liberado): una pista verdadera.
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
oye una cosa seria #at: D-15 01:20
un colega mío de barna, el iker, lleva las relaciones públicas de un club en el port olímpic #at: D-15 01:20
busca chicas para la puerta y los reservados. 1.500 al mes y piso pagado #at: D-15 01:21
tú vales para eso. y te quitas de encima a tu familia 😏 #at: D-15 01:21
le he pasado tu número #at: D-15 01:22
por qué le pasas mi número sin preguntarme #from: me #at: D-15 09:30
para ayudarte joder #at: D-15 09:31
estás despierta? #at: D-12 02:10
tengo algo que te va a gustar 🌿 #at: D-12 02:11
ni de coña dani. ya te dije que no quiero saber nada de eso #from: me #at: D-12 09:00
es solo un poco de hierba, no te hagas la santa #at: D-12 09:05
es que no. y deja de escribirme de madrugada #from: me #at: D-12 09:06
vale vale. no te enfades #at: D-12 09:07
iker dice que si vas, mejor sin decir nada en casa. ya sabes cómo son #at: D-9 00:40
te pago yo el bus. y en un mes me mudo yo también. estaríamos juntos 😏 #at: D-9 00:41
no estaríamos juntos dani. y no he dicho que sí #from: me #at: D-9 08:20
vale. pero piénsatelo. allí nadie te controla #at: D-9 08:21
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
te has ido a barna, no? 😏 iker dice que aún no le has escrito #delay: 8
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
- si estás con el pol ese, dímelo. tengo derecho a saberlo #delay: 60
-> charla("D3 21:25") ->
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
// Si lo detuvieron y el jugador le creyó, cuenta lo que vio (liberado); si no le creyó, ya no escribe.
= d6
#at: D6 13:00
{dani_no_creido or (dani_detenido and not dani_creido): -> d7}
{dani_creido: -> liberado -> d7}
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
{dani_creido and not liberado: -> liberado ->}
{dani_registrado and not dani_avisado and not dani_detenido:
    ali #delay: 1
    los mossos han estado en el mas de mi tío con perros #delay: 5
    se han llevado lo que había. no sé qué buscaban #delay: 6
    yo de ti no sé nada, te lo juro #delay: 6
}
-> DONE

// Lo suelta la policía (el jugador le ha creído). Se le escapa una pista verdadera: la siguió un sábado.
= liberado
ali #delay: 1
me han soltado. con una orden de alejamiento. supongo que me lo merezco #delay: 6
lo que les dije es verdad. quería que te vinieras conmigo. que fueras mía. ya sé cómo suena #delay: 8
te seguí una vez con la moto. hace un mes, un sábado #delay: 10
no fuiste ni a capsec ni a barna. subiste en la furgo del banc d'aliments hacia sant salvador #delay: 6
~ sabe_ruta_lotes = true
te bajaste en un mas con una perra enorme que me ladró desde lejos. me di la vuelta #delay: 6
no te volveré a escribir #delay: 20
->->

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
* {dia >= 2} [dani, qué le dijiste a iker de mí?]
    que vales para eso. y que te ibas a venir conmigo #delay: 120
    por? te ha escrito? si se pone pesado me lo dices #delay: 5
    -> opciones
* {dia >= 3} [tú querías que me fuera a barcelona contigo?]
    {dia < 5:
        quería que salieras de esa casa. ya está #delay: 120
        allí estaríamos bien. tú y yo #delay: 5
        no me lo pongas tan raro jaja #delay: 6
    - else:
        sí. vale. sí #delay: 120
        me iba a mudar. pensé que si te venías serías mía de una vez #delay: 8
        ya sé cómo suena. lo siento #delay: 6
    }
    -> opciones
* {dia >= 3} [perdona si he sido borde]
    tranqui. yo también he sido un pesado #delay: 120
    -> opciones
+ [(sin responder)]
- ->->
