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
    y nuri me llama «tía», no «ali» con interrogante #delay: 5
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
* [(sin responder)]
- -> d5

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
        busca la puerta azul #delay: 5
    - else:
        no. lo siento #delay: 60
        aún no me fío de nadie #delay: 4
    }
* [¿Tu madre y tu hermana irán a la ceremonia?]
    sí. vestidas de blanco #delay: 40
    por eso no basta con encontrarme. hay que pararlo #delay: 5
    ellas no saben lo que hacen. o no quieren saberlo #delay: 6
* [(sin responder)]
- me apago #delay: 20
-> fin_d5

= fin_d5
-> d6

= d6
{confianza_alicia < 1: -> fin_d6}
{patrulla_en_mas:
    han venido dos mossos #at: D6 23:50
    les he visto desde el bosque. no he bajado #delay: 5
    los has mandado tú? #delay: 4 #caduca: D7 02:00
    -> pregunta_mossos
}
{secta_sabe_rosalia:
    están aquí #at: D6 23:50
    la furgoneta. abajo, en el camino. berta está dentro #delay: 5
    estoy en el bosque. hace mucho frío #delay: 6
    me queda un 1 % #delay: 4
    si no escribo más... #delay: 10
    -> fin_d6
}
mañana amanece #at: D6 23:50
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

= d7
{
- alicia_a_salvo or patrulla_en_mas:
    estoy en comisaría. me han dejado cargar el móvil 😅 #at: D7 09:30
    mamá y berta están... {familia_salvada(): bien. en otra sala. no me dejan verlas aún | en el hospital} #delay: 8
    gracias. de verdad #delay: 10
    algún día me dirás cómo te llamas? #delay: 5
    -> DONE
- capturada():
    // El prepago murió en el bosque.
    -> DONE
- confianza_alicia >= 1:
    está amaneciendo #at: D7 06:20
    desde aquí se ve todo el valle #delay: 6
    -> escondida_final
- else:
    -> DONE
}

= escondida_final
{familia_salvada(): -> se_ha_acabado}
hay ambulancias en el cráter. lo veo desde aquí #at: D7 07:30
mamá #delay: 20
-> DONE

= se_ha_acabado
he visto en gonpi coches de policía en el cráter #at: D7 07:30
es verdad? se ha acabado? #delay: 5 #caduca: D7 23:59
* [Es verdad. Tu madre y tu hermana están a salvo. Ya puedes bajar.]
    ~ caso_resuelto = true
    ... #delay: 60
    voy a bajar #delay: 5
    gracias #delay: 4
* [(sin responder)]
    me quedo un poco más. por si acaso #delay: 1
- -> DONE
