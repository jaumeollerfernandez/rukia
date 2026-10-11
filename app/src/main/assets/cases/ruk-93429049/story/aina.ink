// Aina, de clase. Agobiada perpetua y buena compañera. Ruido: exámenes, apuntes, la exposición.
// Contrapunto de Paula: ella sí cree a Oriol (la vio en la carretera de Bracons), aunque no lo sabe todo.
// Vida propia: suspende el examen de psico (D2) y vive pendiente de la recuperación y de los finales.
// Escribe ella sola el D2 (tras el examen) y el D3 (tras leer el comentario de Oriol), si Alicia no le ha sacado el tema antes.

=== aina ===
-> charla

= charla
{aina_escribe() > 0 and hora >= aina_escribe():
    {dia == 2: -> examen ->}
    {dia == 3: -> comentario ->}
}
- (opciones)
#caduca: {caduca(aina_escribe())}
// Saludos: uno u otro.
* (hola) {not perdon} [aina 💛]
    ALI #delay: 120
    estás viva!! #delay: 3
    -> opciones
* (perdon) {dia >= 2 and not hola} [aina perdona que no conteste a nadie]
    no pasa nada tía #delay: 120
    bueno sí pasa. nos tenías preocupadas. pero no pasa nada 😭 #delay: 5
    -> opciones
* (psico) {dia >= 2 and not examen} [aina qué tal el examen de psico?]
    un 4,8. me quiero morir #delay: 120
    #caduca: D{dia + 1} 00:00
    ** [hay recuperación?]
        en junio. JUNIO ali #delay: 120
        me voy a pasar el verano con el piaget 😭 #delay: 4
    ** [yo no lo hubiera aprobado tampoco]
        mentira. tú lo apruebas todo sin estudiar 🙄 #delay: 120
        bueno. perdón. es que estoy de mal humor #delay: 20
    ** [(sin responder)]
    - - -> opciones
* (apuntes) [aina me pasas los apuntes?]
    te los subo al drive 💛 #delay: 120
    vuelve pronto que paula está insoportable #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [te debo una enorme]
        me debes tres. pero quién cuenta #delay: 120
    ** [los de psico también porfa]
        los de psico no los quieres, créeme #delay: 120
        son míos. por eso tengo un 4,8 #delay: 4
    ** [(sin responder)]
    - - -> opciones
* {dia < 3} [aina mañana hay clase de 8?]
    QUÉ #delay: 120
    no me digas eso que me da un infarto #delay: 3
    sí. dinámicas de grupo con elena. y luego tutoría #delay: 5
    -> opciones
* {dia >= 2} [cómo va la clase sin mí?]
    rara #delay: 120
    sergi hace tu parte de la exposición y dice que vas a suspender igual. no le hagas caso #delay: 5
    paula se ríe de todo y oriol no habla. normal, vamos #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [pobre sergi. dile que lo siento]
        díselo tú, que te contesta en un segundo #delay: 120
        a mí me contesta «Vale.» con punto. me da miedo #delay: 5
    ** [y oriol por qué no habla?]
        oriol nunca habla. bueno, de su bici sí #delay: 120
        de su bici no para #delay: 3
    ** [(sin responder)]
    - - -> opciones
// Si Aina no le ha escrito ya por el comentario.
* (sobre_oriol) {dia >= 3 and not comentario} [aina tú crees lo que dice oriol?]
    yo sí 🙋 #delay: 120
    oriol es raro pero no se inventa cosas. si dice que te vio subiendo la carretera de bracons a la una, es que te vio #delay: 6
    paula dice que ve fantasmas. pero el lobo del año pasado era un perro de pastor ENORME, eh. casi un lobo #delay: 6
    -> bracons ->
    -> opciones
* {dia >= 4} [aina, elena ha dicho algo de mí?]
    que si sigues faltando pierdes la evaluación continua 😭 #delay: 120
    pero lo dijo con cara de pena. creo que te lo guarda #delay: 4
    {aina.examen.gripe:
        le dije que tenías gripe. como me dijiste #delay: 6
    }
    -> opciones
* {dia >= 5} [aina gracias por todo 💛]
    para qué están las amigas que suspenden psico 💛 #delay: 120
    {aina.bracons.si:
        y lo de bracons no se lo he dicho a nadie eh 🤐 #delay: 5
    }
    vuelve ya porfa #delay: 3
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// Era ella o no: lo que contesta Alicia, Aina se lo guarda.
= bracons
#caduca: D{dia + 1} 00:00
* (si) [sí, era yo]
    lo sabía 😭 #delay: 120
    no se lo digo a nadie. pero qué hacías a la una de la mañana en bracons tía #delay: 4
    #caduca: D{dia + 1} 00:00
    ** [no te lo puedo contar]
        vale. vale vale #delay: 120
        pero si necesitas algo me lo dices. aunque sea apuntes #delay: 5
    ** [paseaba]
        a la una. por un puerto. con una linterna en la frente #delay: 120
        vale ali 🙃 #delay: 3
    ** [(sin responder)]
* [no era yo]
    pues alguien igualita a ti con una linterna en la frente 🤔 #delay: 120
* [oriol ve cosas]
    eso dice paula #delay: 120
    yo creo que no. pero bueno, tú sabrás #delay: 5
* [(sin responder)]
- ->->

// D2, después del examen (clase.ink, 11:15).
= examen
ali #at: D2 13:30
ha ido FATAL el de psico #delay: 4
tú estás bien? sergi dice que estás mala #delay: 5
#caduca: D{dia + 1} 00:00
* (gripe) [sí, una gripe horrible]
    ay pobre 😭 #delay: 120
    pues descansa. yo te paso todo #delay: 4
* [no mucho la verdad]
    ... #delay: 120
    quieres hablar? aunque sea por aquí #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [ahora no puedo. pero gracias]
        vale. aquí estoy 💛 #delay: 120
    ** [(sin responder)]
* [no te preocupes por mí. qué tal tú?]
    yo fatal. un 4,8 seguro. lo noto en el alma #delay: 120
    pero bueno, nadie se ha muerto de un suspenso. creo #delay: 5
* [(sin responder)]
- ->->

// D3, después de leer el comentario de Oriol en su ruta (clase.ink, 14:25).
= comentario
oye he visto lo de oriol #at: D3 21:15
el comentario de la ruta #delay: 3
eras tú?? 👀 #delay: 4
-> bracons ->
->->

// La hora de hoy a la que Aina escribe por su cuenta (0: hoy no escribe).
=== function aina_escribe() ===
{
- dia == 2 and not aina.examen and not aina.charla.psico:
    ~ return 13
- dia == 3 and not aina.comentario and not aina.charla.sobre_oriol:
    ~ return 21
- else:
    ~ return 0
}
