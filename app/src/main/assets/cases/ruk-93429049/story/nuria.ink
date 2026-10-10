// Núria, la mejor amiga, estudia en Girona. Conoce a Alicia desde los 6 años: nota enseguida si «Alicia» no escribe como ella.
// Elegir respuestas en el estilo de Alicia (minúscula, sin punto, «nuri») sube confianza_nuria.

=== nuria ===
-> historial

= historial
tía mañana vienes a girona o qué #at: D-3 17:02
no puedo, reunión de las de mi madre #from: me #at: D-3 17:30
otra vez??? #at: D-3 17:31
cada vez vas más a eso ali #at: D-3 17:31
lo sé #from: me #at: D-3 17:45
tengo que contarte una cosa. cuando nos veamos. por aquí no #from: me #at: D-3 17:46
me estás asustando #at: D-3 17:46
no es nada, de verdad. bueno sí. ya te contaré #from: me #at: D-3 17:58
nuri #from: me #at: D-1 21:50
si algún día no te contesto no te preocupes vale? #from: me #at: D-1 21:50
te quiero mucho #from: me #at: D-1 21:51
??? #at: D-1 22:40
qué dices tía #at: D-1 22:40
ali?? #at: D-1 23:59
-> d1

= d1
Alicia. Me ha llamado tu madre. #at: D1 11:20
que si estabas conmigo en girona #delay: 3
le he dicho que no porque NO estás conmigo #delay: 4
qué pasa??? #delay: 40
y me ha dicho que te dejaste el móvil en casa. pero ahora te sale en línea #delay: 25 #caduca: D1 23:00
* [estoy bien nuri, no le digas nada a mi madre porfa]
    ~ confianza_nuria += 1
    vale... #delay: 30
    pero me estás asustando #delay: 4
    dónde estás? #delay: 2 #caduca: D1 23:30
    ** [no te lo puedo decir aún]
        ~ confianza_nuria += 1
        es por lo que me ibas a contar? lo de tu madre y esa gente? #delay: 40
        #caduca: D2 00:00
        *** [sí. ya te contaré, te lo juro]
            vale. júramelo por la iaia #delay: 10
        *** [no es nada, de verdad]
            ~ confianza_nuria -= 1
            ali que nos conocemos desde los 6 años #delay: 8
        *** [(sin responder)]
            vale. no me contestes. genial #delay: 1
        --- -> fin_d1
    ** [En casa de un amigo.]
        ~ confianza_nuria -= 1
        qué amigo #delay: 20
        si tú no tienes amigos fuera de nosotras jajaja #delay: 4
        y desde cuándo escribes con mayúscula y punto?? #delay: 6
        -> fin_d1
    ** [(sin responder)]
        ali porfa. aunque sea un emoji #delay: 1
        -> fin_d1
* [Estoy bien. Tranquila.]
    ~ confianza_nuria -= 1
    «Tranquila.» con punto #delay: 20
    tú quién eres #delay: 5
    ali no escribe así ni borracha #delay: 3 #caduca: D1 23:30
    ** [jajaja tía que soy yo, estoy rarísima estos días]
        ~ confianza_nuria += 1
        ...vale. pues me asustas igual #delay: 25
    ** [Alguien que intenta encontrarla.]
        ~ nuria_sabe = true
        qué #delay: 10
        QUÉ #delay: 2
        cómo que encontrarla. dónde está ali #delay: 4
        y cómo tienes su móvil?? #delay: 3
        si eres de la gente de su madre te juro que #delay: 15
        da igual. no me escribas más #delay: 4
    ** [(sin responder)]
        vale. pues no contestes #delay: 1
    -- -> fin_d1
* [(sin responder)]
    ali porfa. aunque sea un emoji #delay: 1
- (fin_d1) me voy a dormir. si mañana no sé nada de ti llamo yo a los mossos #at: D1 23:45
-> charla("D2 10:30") ->
-> d2

= d2
{nuria_sabe:
    sigues ahí. quien seas #at: D2 10:40
    no he ido a los mossos. todavía #delay: 6
    si de verdad estás buscando a ali, demuéstralo #delay: 5
    mañana te hago tres preguntas. cosas que solo sabemos ella y yo #delay: 4
    si fallas una, voy a comisaría y les cuento que alguien tiene su móvil #delay: 4
    -> d2_fin
}
{confianza_nuria < 0:
    sigo sin creerme que seas tú #at: D2 10:40
    mañana te pregunto cosas. a ver si eres ali o no #delay: 5
    -> d2_fin
}
buenos días #at: D2 10:40
ayer al final no me contaste nada #delay: 4
esta noche he soñado contigo #delay: 120
cuando tu iaia nos llevó a ver las estrellas a aquella casa sin luz. te acuerdas? #delay: 6
tú tenías miedo del perro y al final no te separabas de él jajaja #delay: 5 #caduca: D2 23:00
* [claro que me acuerdo 💛]
    ~ confianza_nuria += 1
    algún día volvemos. tú y yo #delay: 40
* [qué perro?]
    ~ confianza_nuria -= 2
    ... #delay: 30
    el perro, ali. bueno, la perra #delay: 4
    qué te pasa #delay: 3
    mañana te voy a preguntar cosas. y más te vale saberlas #delay: 6
* [(sin responder)]
    vale. te leo cuando quieras #delay: 1
- (d2_fin) -> charla("D3 16:55") ->
-> d3

// D3: la prueba. Tres preguntas que solo Alicia sabría. Respuestas en la galería (IMG_0391, IMG_0393) y en Gonpi (posts de @ali.serra).
= d3
{nuria_sabe:
    te dije que te haría tres preguntas. #at: D3 17:00
    si de verdad estás con los mossos o con quien sea, habrás mirado sus cosas. a ver #delay: 6
- else:
    {confianza_nuria >= 2:
        ali perdona. sé que es una tontería #at: D3 17:00
        pero estoy tan rayada que necesito estar segura de que eres tú. tres preguntas y ya #delay: 6
    - else:
        tres preguntas. #at: D3 17:00
        si eres ali, te las sabes sin pensar #delay: 4
    }
}
primera. dónde fuimos en el viaje de fin de curso #delay: 8 #caduca: D4 17:00
* [al port de la selva]
    ~ aciertos_nuria += 1
* [a salou]
* [a l'estartit]
* [(sin responder)]
    -> no_contesta
- segunda. qué te regalé por tu cumple #delay: 30 #caduca: D4 17:00
* [la pulsera roja]
    ~ aciertos_nuria += 1
* [unos pendientes]
* [una funda del móvil]
* [(sin responder)]
    -> no_contesta
- tercera. cómo se llamaba la perra de la casa de las estrellas #delay: 30 #caduca: D4 17:00
* [trufa]
    ~ aciertos_nuria += 1
* [rocky]
* [luna]
* [(sin responder)]
    -> no_contesta
- {aciertos_nuria == 3: -> aprueba | -> suspende}

= aprueba
~ confianza_nuria += 2
~ sabe_estrellas = true
{nuria_sabe:
    vale. #delay: 60
    te creo. no sé por qué, pero te creo #delay: 5
    ali me dijo una cosa hace meses, medio en broma #delay: 10
    que si algún día tenía que escapar de su casa, iría «donde vimos las estrellas» #delay: 5
    la casa de una señora amiga de su iaia. en el valle. sin luz, con una perra #delay: 6
    no sé dónde está. éramos pequeñas, era de noche y subimos mucho en coche. nada más #delay: 6
    encuéntrala tú antes que ellos. por favor #delay: 5
    -> fin_d3
- else:
    vale. eres tú. perdona #delay: 40
    es que me estoy volviendo loca #delay: 4
    me lo dijiste hace meses. que si un día tenías que irte de casa, irías donde vimos las estrellas #delay: 8
    estás allí? #delay: 5 #caduca: D4 12:00
    -> pregunta_estrellas
}

= pregunta_estrellas
* [no te lo puedo decir nuri. pero estoy bien]
    vale. con eso me vale #delay: 30
    no se lo digo a nadie. ni a carla ni a mire #delay: 4
* [(sin responder)]
    vale. tu silencio también es un sí #delay: 1
- -> fin_d3

= suspende
~ confianza_nuria = -5
~ nuria_denuncia = true
{aciertos_nuria} de 3 #delay: 40
tú no eres ali #delay: 5
ali no fallaría eso ni dormida #delay: 4
no sé quién eres ni qué quieres. no me escribas más #delay: 6
mañana voy a comisaría #delay: 3
-> fin_d3

= no_contesta
~ confianza_nuria -= 1
vale. no contestar también es una respuesta #delay: 1
-> fin_d3

= fin_d3
{not nuria_denuncia:
    -> charla("D4 20:55") ->
}
-> d4

= d4
{nuria_denuncia:
    ya está. he ido a los mossos #at: D4 11:00
    ahora lo saben #delay: 4
    -> fin_d4
}
{sabe_estrellas:
    he subido una foto nuestra a gonpi #at: D4 21:05
    para que sepas que pienso en ti #delay: 5
    si estás donde creo, mira las estrellas esta noche. yo también las miraré ✨ #delay: 8
- else:
    sigo preocupada. mucho #at: D4 21:05
    aunque sea un emoji, ali #delay: 10
}
-> fin_d4

= fin_d4
{not nuria_denuncia:
    -> charla("D5 10:25") ->
}
-> d5

= d5
{nuria_denuncia: -> d5_denuncia}
{sabe_estrellas:
    ~ sabe_puerta_azul = true
    he soñado otra vez con la casa de las estrellas #at: D5 10:30
    y me he acordado de una cosa #delay: 4
    la puerta era azul. azul fuerte. y había un pozo delante #delay: 5
    no sé si te sirve #delay: 3
- else:
    hoy ha jugado el vóley sin ti. han perdido #at: D5 13:30
    todo es una mierda sin ti, ali #delay: 10
}
-> fin_d5

= fin_d5
-> charla("D6 11:25") ->
-> d6

// Si fue a comisaría el D4, Laia la ha llamado y Núria vuelve a hablar, con recelo.
= d5_denuncia
~ nuria_sabe = true
~ confianza_nuria = 0
me ha llamado la sargento esa. puig #at: D5 12:15
me ha contado algo. no todo #delay: 5
dice que eres de los buenos. no sé si creérmelo #delay: 6
solo dime una cosa. ali está bien? #delay: 4 #caduca: D5 23:59
* [Creo que sí. Y la vamos a encontrar.]
    ~ confianza_nuria += 1
    vale #delay: 60
    perdona por lo de los mossos. tenía miedo #delay: 5
* [(sin responder)]
- -> fin_d5

= d6
{confianza_nuria >= 2:
    ~ sabe_rosalia = true
    ~ sabe_puerta_azul = true
    mira lo que he encontrado en el álbum de mi madre #at: D6 11:30
    (foto) #delay: 3 #image: chat/nuria_mas.jpg
    sois tú y tu iaia. delante de la casa de las estrellas #delay: 5
    detrás pone a boli: «mas de la rosalia. estiu 2012» #delay: 6
    la puerta azul ✨ #delay: 4
- else:
    {nuria_sabe:
        suerte. de verdad #at: D6 21:00
    - else:
        ali... #at: D6 21:00
    }
}
-> d7

= d7
{rescatada():
    ME HA LLAMADO #at: D7 10:15
    ME HA LLAMADO ALI #delay: 2
    {nuria_sabe: gracias. quien seas | ali, te he oído la voz. no sé ni qué escribir} #delay: 20
- else:
    alguien sabe algo? #at: D7 10:15
}
-> DONE

// Entre escena y escena, el jugador puede escribirle. Núria contesta según lo que crea: si sabe que no eres Ali
// (nuria_sabe) habla de «ella»; si cree que eres Ali, las preguntas raras le hacen desconfiar.
= charla(limite)
~ temp habla = not nuria_denuncia or dia >= 5
- (opciones)
#caduca: {limite}
* {habla and not nuria_sabe} [nuri te echo de menos 💛]
    ~ confianza_nuria += 1
    y yo a ti tonta #delay: 60
    vuelve ya porfa #delay: 4
    -> opciones
* {habla and not nuria_sabe and dia >= 2} [cuéntame lo de la casa de las estrellas, que me da paz]
    {confianza_nuria >= 1:
        jo ali 🥹 #delay: 90
        tu iaia mercè nos llevó dos veranos seguidos #delay: 5
        el primero a una casa vacía, de una amiga suya que estaba en el hospital. dormimos con sacos en el suelo y había murciélagos 😭 #delay: 8
        el segundo a la de la señora de la perra. esa es la de las estrellas #delay: 6
        las dos con la puerta azul, que tu iaia se empeñaba en pintarlas jajaja #delay: 5
        no me preguntes dónde, yo iba dormida en el coche #delay: 4
    - else:
        ~ confianza_nuria -= 1
        ali era tu iaia, no la mía #delay: 120
        tú te acuerdas mejor que yo, no? #delay: 4
    }
    -> opciones
// Recuperar a Núria si recela (confianza negativa) sin haberle confesado nada: una sola vez, y no cuela si ya corre el rumor.
* {habla and not nuria_sabe and confianza_nuria < 0} [nuri perdona. estoy fatal y escribo sin pensar 💛]
    {delatado >= 1:
        ~ confianza_nuria -= 1
        carla dice que alguien está usando tu móvil #delay: 120
        y empiezo a creérmelo #delay: 5
    - else:
        ~ confianza_nuria = 0
        ... #delay: 120
        vale tía. pero me tienes muy rayada #delay: 5
    }
    -> opciones
* {habla and not nuria_sabe} [sabes algo de pol?]
    pol? me ha escrito por gonpi preguntando si sé algo de ti #delay: 120
    está rayadísimo. qué le hiciste jajaja #delay: 4
    -> opciones
* {habla and dia >= 2} [{nuria_sabe:¿Conoces a un tal Dani, de Olot? Le escribe mucho.|y dani, el de la moto, te ha dicho algo?}]
    ~ dani_sospechoso = true
    el pesado de la moto? #delay: 120
    dicen que anda metido en cosas, que vende en el bar de la plaza. no sé si es verdad #delay: 5
    me escribió por gonpi hace un mes preguntando dónde vivía {nuria_sabe:ali|tú}. le bloqueé #delay: 5
    y la semana pasada {nuria_sabe:ella|tú} me {nuria_sabe:dijo|dijiste} que la seguía una moto por la carretera del hostalnou. no sé si era él #delay: 8
    -> opciones
* {habla and not nuria_sabe and dia >= 1} [Núria, ¿cuándo la viste por última vez?]
    ~ confianza_nuria -= 2
    «la»? #delay: 60
    a quién #delay: 3
    ali por qué hablas como si fueras otra #delay: 5
    -> opciones
* {habla and nuria_sabe} [¿Cuándo la viste por última vez?]
    hace nueve días. vino a girona en bus, con mireia. comimos las tres y mireia no dijo ni mu #delay: 90
    estaba rarísima. me dijo que su madre había firmado unos papeles y que ella no pensaba firmar #delay: 6
    y que si un día desaparecía, que no me fiara de lo que dijera su madre #delay: 5
    pensé que exageraba. como siempre #delay: 4
    -> opciones
* {habla and nuria_sabe and dia >= 2} [Si tuviera que esconderse, ¿adónde iría?]
    {confianza_nuria >= 1:
        a girona no. sabe que es lo primero que mirarían #delay: 120
        a casa de su padre tampoco, se llevan fatal #delay: 4
        a algún sitio sin gente. le gusta el monte. y le dan miedo los coches de noche #delay: 6
    - else:
        y te lo voy a decir a ti? #delay: 60
    }
    -> opciones
+ [(sin responder)]
- ->->
