// Aina, de clase. Agobiada perpetua y buena compañera. Ruido: exámenes, apuntes, la exposición.
// Contrapunto de Paula: ella sí cree a Oriol (la vio en la carretera de Bracons), aunque no lo sabe todo.

=== aina ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* {dia >= 2} [aina qué tal el examen de psico?]
    un 4,8. me quiero morir #delay: 120
    -> opciones
* [aina me pasas los apuntes?]
    te los subo al drive 💛 #delay: 120
    vuelve pronto que paula está insoportable #delay: 4
    -> opciones
* [aina mañana hay clase de 8?]
    QUÉ #delay: 120
    no me digas eso que me da un infarto #delay: 3
    sí. dinámicas de grupo con elena. y luego tutoría #delay: 5
    -> opciones
* {dia >= 2} [cómo va la clase sin mí?]
    rara #delay: 120
    sergi hace tu parte de la exposición y dice que vas a suspender igual. no le hagas caso #delay: 5
    paula se ríe de todo y oriol no habla. normal, vamos #delay: 4
    -> opciones
* {dia >= 3} [aina tú crees lo que dice oriol?]
    yo sí 🙋 #delay: 120
    oriol es raro pero no se inventa cosas. si dice que te vio subiendo la carretera de bracons a la una, es que te vio #delay: 6
    paula dice que ve fantasmas. pero el lobo del año pasado era un perro de pastor ENORME, eh. casi un lobo #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [sí, era yo]
        lo sabía 😭 #delay: 120
        no se lo digo a nadie. pero qué hacías a la una de la mañana en bracons tía #delay: 4
    ** [no era yo]
        pues alguien igualita a ti con una linterna en la frente 🤔 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [aina, elena ha dicho algo de mí?]
    que si sigues faltando pierdes la evaluación continua 😭 #delay: 120
    pero lo dijo con cara de pena. creo que te lo guarda #delay: 4
    -> opciones
* {dia >= 5} [aina gracias por todo 💛]
    para qué están las amigas que suspenden psico 💛 #delay: 120
    vuelve ya porfa #delay: 3
    -> opciones
+ [(sin responder)]
    -> charla
