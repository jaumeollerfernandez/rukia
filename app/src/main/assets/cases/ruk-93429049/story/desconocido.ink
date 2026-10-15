// Número prepago: es Alicia. Compró un móvil barato semanas antes de huir. En el mas no hay luz, así que casi no le queda batería.
// Ha visto que su móvil «sale en línea» y escribe para saber quién lo usa. Su estilo: minúscula, sin punto.

=== desconocido ===
-> d4

= d4
hola #at: D4 23:40
si estás leyendo esto y no eres mamá ni berta... quién eres? #delay: 20
mi móvil sale en línea. y yo no lo tengo #delay: 6 #caduca: D5 01:00
* [ali? soy yo, nuri]
    ~ confianza_alicia -= 1
    nuri no sabe mi pin #delay: 30
    y nuri sabe que mi móvil está en casa. no me escribiría a este número desde él #delay: 5
    no me mientas. quién eres #delay: 4 #caduca: D5 01:00
    ** [Trabajo con una sargento de los Mossos. Te estamos buscando.]
        -> verdad
    ** [(sin responder)]
        -> se_va
* [Trabajo con una sargento de los Mossos. Te estamos buscando.]
    -> verdad
* [nena, soy mamá]
    ~ confianza_alicia -= 2
    ... #delay: 60
    mamá nunca escribe en minúscula #delay: 5
    ni tú tampoco, berta #delay: 3
    -> se_va
* [(sin responder)]
    -> se_va

= verdad
~ confianza_alicia += 1
los mossos los llamó mi madre #delay: 40
no os fiéis de ella. ni de berta #delay: 5
cómo sé que no eres de ellos #delay: 6 #caduca: D5 01:00
* [Porque si fuera de ellos, ya sabrían dónde estás.]
    ~ confianza_alicia += 1
    ... #delay: 30
    vale. tiene sentido #delay: 4
* [No lo sabes. Tendrás que confiar.]
    no me sirve #delay: 20
    pero no tengo a nadie más #delay: 6
* [(sin responder)]
    -> se_va
- escucha. no me preguntes dónde estoy. no te lo voy a decir #delay: 10
si los mossos lo saben, mi madre lo sabrá #delay: 4
no me fui por un chico. ni por drogas. ni porque esté loca, diga lo que diga mamá #delay: 8
me fui por unos papeles que firmaron ellas. y que yo no firmé #delay: 6 #caduca: D5 01:00
* [¿Qué papeles?]
    pregunta en qué despacho los firmaron. y qué más firmaron ese día #delay: 40
    yo lo vi en la libreta de mamá. nadie me creyó. «es la ansiedad, nena» #delay: 6
* [¿Y por qué te buscan?]
    porque el día de la yaya tenemos que estar todas #delay: 40
    «solo familias completas» #delay: 5
* [(sin responder)]
- me queda poca batería. aquí no hay luz #delay: 10
mañana a esta hora. si puedo #delay: 4 #caduca: D5 01:00
* [Cuídate, Alicia. Vamos a sacaros de esta, a ti y a ellas.]
    ~ confianza_alicia += 1
    a ellas también? #delay: 40
    gracias #delay: 5
* [Dime solo si estás a salvo.]
    de momento sí #delay: 30
* [¿Necesitas algo?]
    pilas. pan. y que nadie suba #delay: 30
    lo último es lo más importante #delay: 4
* [(sin responder)]
- -> d5

// La ubicación llega al móvil, pero solo sirve si alguien la oye: cogida, o escuchada en el buzón de voz antes de que
// Laia mire a las 01:30 (laia.d6). Si no, Alicia sigue sola esa noche.
= tras_llamada
{llamada_desconocido_contestada:
    ya está. ya lo sabes #delay: 10
- else:
    te lo he dejado en el buzón de voz. escúchalo esta noche, por favor #delay: 10
    no sé si mañana tendré batería #delay: 4
}
que vengan sin sirenas. y que no se lo digan a mi madre #delay: 5
->->

= se_va
vale #delay: 20
entonces eres de ellos #delay: 4
no voy a escribir más #delay: 3
-> d5

= d5
{confianza_alicia < 1:
    // Alicia no se fía: esta noche no escribe.
    -> fin_d5
}
estás ahí? #at: D5 23:30
batería al 4 % #delay: 5
hoy ha pasado una furgoneta blanca por el camino. muy despacio. mirando #delay: 8
la señora con la que estoy no se ha enterado de nada. mejor #delay: 5
si vienen, me escondo en el bosque. me lo conozco #delay: 6 #caduca: D6 01:00
* [Dime dónde estás. Mandaremos una patrulla antes que ellos.]
    {confianza_alicia >= 3:
        ~ sabe_puerta_azul = true
        no lo puedo escribir. si mi madre lee mi móvil, lo lee también #delay: 60
        te llamo. cógelo #delay: 5
        // Con confianza máxima, da la ubicación exacta por teléfono (audio). Laia la escucha y la saca esa noche (laia.rescate_llamada).
        📞 #delay: 20 #call: audio/alicia_ubicacion.m4a
        -> tras_llamada ->
    - else:
        no. lo siento #delay: 60
        aún no me fío de nadie #delay: 4
    }
// Avisarla de Dani: se esconde cuando suba la moto y la entrega se retrasa a la madrugada (dani_frenado).
* {sabe_dani_busca} [Dani te está buscando. Pregunta por ti en todo el valle. Si oyes una moto, escóndete.]
    ~ alicia_avisada = true
    ~ dani_frenado = true
    dani? #delay: 40
    debe dinero a gente de barna. se lo oí una noche, hablando por teléfono #delay: 6
    vale. hay un pozo seco detrás de la casa. la rosalia lo tapa con leña #delay: 6
    si oigo una moto, me meto ahí #delay: 4
* [¿Tu madre y tu hermana irán a la ceremonia?]
    sí. vestidas de blanco #delay: 40
    por eso no basta con encontrarme. hay que pararlo #delay: 5
    ellas no saben lo que hacen. o no quieren saberlo #delay: 6
* [¿Cómo estás?]
    con frío #delay: 40
    y con una perra que no me deja ni ir al baño sola #delay: 5
    la señora me hace sopa de pan. está buena. no le digas que lo he dicho, que se viene arriba #delay: 6
* [(sin responder)]
- me apago #delay: 20
-> fin_d5

= fin_d5
-> d6

// Todo se decide a las 23:50: si la red ya se la ha llevado (laia.entrega_tarde), el prepago calla.
= d6
{confianza_alicia < 1: -> fin_d6}
(espera) #at: D6 23:50
// Ya está en comisaría desde la madrugada (laia.rescate_llamada): no hace falta que escriba.
{alicia_entregada or alicia_llamo: -> fin_d6}
{patrulla_en_mas:
    han venido dos mossos
    les he visto desde el bosque. no he bajado #delay: 5
    los has mandado tú? #delay: 4 #caduca: D7 02:00
    -> pregunta_mossos
}
{secta_sabe_rosalia:
    están aquí
    la furgoneta. abajo, en el camino. berta está dentro #delay: 5
    estoy en el bosque. hace mucho frío #delay: 6
    me queda un 1 % #delay: 4
    si no escribo más... #delay: 10
    -> fin_d6
}
// Detenido o camino de Barcelona, Dani no puede subir.
{alicia_avisada and not dani_detenido and not dani_no_creido:
    esta tarde ha subido una moto. era dani #delay: 1
    me he metido en el pozo. le he oído preguntarle a la rosalia por «la nena» #delay: 8
    ella le ha dicho que vive sola. se ha ido. pero ha dicho que volvería #delay: 6
}
mañana amanece #delay: 5
ojalá no #delay: 5
-> fin_d6

= pregunta_mossos
* [Sí. Baja con ellos. Estarás a salvo.]
    ~ alicia_a_salvo = true
    ... #delay: 60
    vale. bajo #delay: 5
    gracias. de verdad. no sé ni cómo te llamas #delay: 6
* [(sin responder)]
    me quedo aquí hasta que amanezca #delay: 1
- -> fin_d6

= fin_d6
-> d7

// Si la rescataron, escribe desde comisaría. Si se la llevó la familia o la red, el prepago no vuelve a encenderse.
= d7
(espera) #at: D7 06:20
{not rescatada(): -> DONE}
estoy en comisaría. me han dejado cargar el móvil 😅 #at: D7 09:30
mamá y berta están... {familia_salvada(): bien. en otra sala. no me dejan verlas aún | en el hospital} #delay: 8
gracias. de verdad #delay: 10
algún día me dirás cómo te llamas? #delay: 5 #caduca: D8 00:00
* [Kimo.]
    kimo #delay: 30
    vale. gracias, kimo 💛 #delay: 5
* [Algún día.]
    vale. me lo apunto #delay: 30
    en la libreta negra. al lado de las estrellas #delay: 5
* [(sin responder)]
- -> DONE
