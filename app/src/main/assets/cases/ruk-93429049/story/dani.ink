// Dani, 22 años, la conoció por Gonpi. Insistente, posesivo y raro. Parece sospechoso de que se fuera, y de eso es inocente.
// Primera capa (la trata, pista falsa hasta el D3): le ofreció un «curro» en Barcelona con su amigo Iker (piso pagado, «no lo
// digas en casa»), justo cuando Barcelona desarticula parte de una red que capta chicas así. La noche que ella se fue, él
// estaba en Barcelona. La verdad de entonces: se iba a mudar y la quería allí, para él.
// Segunda capa (la deuda, verdad desde el D3): menudea para un proveedor del Audi negro andorrano, que trabaja para la parte
// de la red que no cayó. Les debe dinero. El D4 le dan una salida que él mismo ha propuesto: entregarles a Alicia.
// Coaccionado y roto, se pasa el D4-D6 preguntando por ella por todo el valle (Toni, Pilar, Èric lo cuentan) y escribiéndole,
// cada vez más desesperado. Si nadie lo frena ni la rescata, la entrega el D6 por la tarde (laia.entrega_tarde) y desaparece.
// Frenarlo (Laia lo detiene, la operación del D6 o avisar a Alicia) solo retrasa la entrega a la madrugada del D7.
// Tonteo con Alicia: un beso en la fiesta de Mireia, que ella llamó «un error». Él lo vivió como el principio de algo.

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
pol el del golf gris? no me gusta ese tío #at: D-20 21:31
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
sí, me he enterado. olot es pequeño 😏 #delay: 4
~ dani_sospechoso = true
te has ido a barna, no? iker dice que aún no le has escrito #delay: 8 #caduca: D2 03:00
* [no estoy en barna]
    ya. y dónde entonces? #delay: 120
    #caduca: D2 03:00
    ** [no te lo voy a decir]
        vale guay. como siempre #delay: 120
    ** [en casa de una amiga]
        de cuál? de la nuri? 😏 #delay: 120
        bueno. mientras no sea con el pol ese #delay: 6
    ** [(sin responder)]
        vale. no me lo digas #delay: 1
* [no le he escrito a iker ni le voy a escribir]
    uf vale, tranqui #delay: 120
    pues allí estarías mejor que en esa casa. te lo digo yo #delay: 6
* [quién te ha dicho que me he ido?]
    tengo mis fuentes jajaja #delay: 120
    tranqui que no se lo digo a nadie #delay: 4
    si me dices dónde estás te llevo algo. lo que sea. tengo la moto #delay: 6
    #caduca: D2 03:00
    ** [no necesito nada, gracias]
        como quieras. si cambias de idea, la moto está #delay: 120
    ** [no te voy a decir dónde estoy]
        vale vale. era por ayudar 🙃 #delay: 120
    ** [(sin responder)]
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
* [nada. necesitaba irme unos días]
    unos días. vale #delay: 120
    pues avisa, que la gente se preocupa. yo me preocupo #delay: 6
* [qué le has dicho?]
    que no sé nada. porque no sé nada #delay: 120
    y tu hermana me ha dado mal rollo. me ha dicho que «la luz siempre encuentra el camino». qué coño significa eso #delay: 8
* [no le digas nada a mi hermana, porfa]
    no pensaba. ni la conozco #delay: 120
    me ha dado mal rollo #delay: 4
* [(sin responder)]
- si estás con el pol ese, dímelo. tengo derecho a saberlo #delay: 60 #caduca: D3 03:00
* [no estoy con pol]
    vale. te creo #delay: 120
    ...más o menos 🙃 #delay: 6
* [no tienes ningún derecho, dani]
    ya. vale. perdona #delay: 120
    es que me rayo #delay: 5
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
~ sabe_audi = true
matrícula de andorra. acaba en 7731, me la sé de memoria #delay: 3 #caduca: D4 03:00
* [qué pasta les debes?]
    ~ dani_sospechoso = true
    cosas mías. del curro #delay: 120
    tranqui, lo arreglo el finde #delay: 5
* [dani, en qué lío estás metido?]
    en ninguno. un malentendido #delay: 90
    ...vale. no es pequeño. mañana te cuento #delay: 6
* [no me escribas más de esto]
    vale. era por si acaso #delay: 90
* [(sin responder)]
    joder, hasta tú #delay: 1
- -> charla("D4 20:55") ->
-> d4

// Se lo cuenta él antes de que se entere por otro lado.
= d4
oye #at: D4 21:00
lo de los del bar. mejor te lo cuento yo antes de que lo oigas por ahí #delay: 5
vendo un poco, ali. costo, hierba, alguna pastilla los findes en el bar #delay: 8
~ dani_droga = true
nada gordo. 200 pavos a la semana y gracias #delay: 5
el del audi es el que me pasa el costo. me fió un kilo y los mossos me lo pillaron en un control #delay: 6
le debo seis mil. y no es del bar, ali. es de barna. conoce a iker #delay: 6
me han dado dos días #delay: 5
tú nunca quisiste saber nada de eso. por eso te lo cuento a ti, no sé #delay: 6
y supongo que por eso me puse tan pesado. sabía que no ibas a decir nunca que sí #delay: 8 #caduca: D5 20:00
* [qué vas a hacer?]
    no sé. pensar #delay: 120
    tengo dos días y una idea de mierda. no me preguntes cuál #delay: 8
* [vete a los mossos, dani]
    y que me maten al salir? #delay: 120
    no ali. tú no sabes cómo es esta gente #delay: 5
* [lo siento, dani]
    ya #delay: 120
    yo también #delay: 10
* [(sin responder)]
    vale. ni tú #delay: 1
- -> charla("D5 23:10") ->
-> d5

// Ya ha hecho el trato: busca a Alicia para la red. Le escribe como si quisiera ayudarla.
= d5
{dani_detenido: -> d6}
ey #at: D5 23:15
he visto lo que ha subido esa cuenta del cráter #delay: 4
es lo de tu familia, no? #delay: 3
{dani_droga:
    lo de la pasta ya está. me han dejado el ojo morado y me han dado una salida 🙃 #delay: 6
}
ali dime dónde estás #delay: 30
de verdad. te ayudo a salir de aquí. a barna, lejos de tu familia #delay: 5
tengo la moto. en una hora estoy donde digas #delay: 4 #caduca: D6 03:00
* [qué salida te han dado, dani?]
    ~ sabe_dani_busca = true
    no puedo decírtelo #delay: 300
    si te lo digo me matan, ali. no es broma #delay: 6
    tú dime dónde estás y ya está. te lo pido por favor #delay: 5
* [no te voy a decir dónde estoy]
    vale. vale #delay: 200
    ya lo encontraré yo #delay: 30
    quiero decir. que te encontraré para ayudarte. ya sabes lo que quiero decir #delay: 6
* [(sin responder)]
    contesta aunque sea con un emoji #delay: 1
- -> d6

// D6: si el jugador mandó una patrulla a su mas, los Mossos le han requisado el costo.
// Si lo detuvieron y el jugador le creyó, cuenta lo que vio (liberado); si no le creyó, ya no escribe.
= d6
(espera) #at: D6 13:00
{dani_no_creido or (dani_detenido and not dani_creido): -> d7}
{dani_creido: -> liberado -> d7}
{dani_registrado:
    ali #delay: 1
    los mossos han estado en el mas de mi tío con perros #delay: 5
    se han llevado lo que había y me han citado en comisaría #delay: 5
    no sé qué os han dicho de mí. yo de ti no sé nada, te lo juro #delay: 6
    ~ dani_avisado = true
}
// Desesperado: ya sabe la zona (la siguió un sábado), le falta la casa.
~ sabe_dani_busca = true
ali porfa #delay: 3
sé que estás por joanetes. te seguí una vez{liberado or charla.radar:, ya lo sabes|. un sábado. no te enfades} #delay: 6
me falta saber cuál. he preguntado a medio valle #delay: 5
dímelo tú. es mejor que te encuentre yo que otros #delay: 8
no me hagas esto ali #delay: 60 #caduca: D6 17:00
* [no te lo voy a decir]
    vale #delay: 120
    tú lo has querido #delay: 20
    no. no quería decir eso. perdona #delay: 10
* [quiénes son «otros», dani?]
    gente que no pregunta #delay: 120
    olvídalo. no te he dicho nada #delay: 5
* [(sin responder)]
- -> d7

= d7
{dani_frenado:
    (espera) #at: D7 05:00
- else:
    (espera) #at: D7 01:30
}
// Ya la ha entregado (laia.entrega_tarde). Último mensaje y desaparece.
{alicia_entregada and not (dani_detenido and not dani_creido):
    perdóname #delay: 1
    no tenía otra. me iban a matar #delay: 8
    este móvil lo tiro ahora #delay: 6
    -> DONE
}
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
no fuiste ni a capsec ni a barna. subiste en la furgo del banc d'aliments hacia joanetes #delay: 6
~ sabe_ruta_lotes = true
te bajaste en un mas con una perra enorme que me ladró desde lejos. me di la vuelta #delay: 6
no te volveré a escribir #delay: 20
->->

// Lo que el jugador le puede preguntar entre escena y escena. Hasta el D3 se escabulle; después, se explica.
= charla(limite)
- (opciones)
#caduca: {limite}
* [dani dónde estabas la noche que me fui?]
    // Lo de Barcelona lo cuenta él en d3 (D3 21:30).
    {not d3:
        por ahí #delay: 120
        por? me echabas de menos? 😏 #delay: 4
    - else:
        te lo he dicho. en barna, en un concierto. mira mi gonpi #delay: 120
    }
    -> opciones
// Si ya ha confesado que la siguió, no lo niega.
* {dani_sospechoso and not liberado and not charla.radar} [tú me has seguido con la moto?]
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
* {dani_droga} [dani, quiénes son los del audi?]
    los que me pasan el costo. y no solo costo #delay: 120
    trabajan para gente de barna. la del club de iker, la que salió en las noticias #delay: 6
    no preguntes más ali. de verdad #delay: 5
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
// Solo con su ficha (app Policía): la multa del radar lo delata. Cuenta lo mismo que si lo sueltan, sin gastar una salida.
* (radar) {ficha_dani and not liberado} [dani, tu moto salta un radar en la carretera de joanetes. un sábado, hace un mes. qué hacías ahí?]
    ...cómo sabes tú eso #delay: 150
    vale. te seguí. ya está, lo he dicho #delay: 10
    no fuiste ni a capsec ni a barna. subiste en la furgo del banc d'aliments hacia joanetes #delay: 6
    ~ sabe_ruta_lotes = true
    te bajaste en un mas con una perra enorme que me ladró desde lejos. me di la vuelta y al bajar me cazó el radar #delay: 8
    no se lo digas a nadie, porfa. bastante tengo #delay: 5
    -> opciones
* {dia >= 3} [perdona si he sido borde]
    tranqui. yo también he sido un pesado #delay: 120
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
- ->->
