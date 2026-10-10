// Quim, del Ayuntamiento. Burócrata amable: todo «la semana que viene». Can Pericot es municipal.
// Pista menor: en la subida a Bracons quedan masías que nunca se conectaron a la luz (o se dieron de baja). Ruido: la fiesta mayor.

=== quim ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [quim perdona por el cartel]
    No te preocupes, Alicia. Laura lo ha apañado. #delay: 600
    -> opciones
* {sabe_capsec} [quim, can pericot es del ayuntamiento?]
    Sí. Se lo dejamos al esplai. ¿Por? #delay: 600
    Marc dice que ha visto luz. La semana que viene mando a alguien a mirarlo. #delay: 6
    -> opciones
* [quim, cómo va la fiesta mayor?]
    Orquesta cerrada. Castillo hinchable, pendiente. Cartel, en la imprenta en cinco días 🎉 #delay: 600
    Si ves a Marc, dile que el tractor no es una carroza. #delay: 5
    -> opciones
* {dia >= 2} [quim, cómo ha quedado el cartel de laura?]
    Correcto. Una rosa grande en el centro y mucho dorado. #delay: 600
    No era lo que hablamos, pero dice que es «más espiritual». A mí me parece un anuncio de colonia. #delay: 6
    -> opciones
* {dia >= 3} [quim, en el valle hay casas sin luz?]
    Alguna queda. En la subida a Bracons, las masías de arriba del todo. #delay: 600
    Dos nunca se conectaron y una se dio de baja hace años, cuando murió el marido. La señora no quiso ni hablar del tema. #delay: 7
    Cada invierno le mando la carta del bono social y cada invierno me vuelve. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [qué señora?]
        Eso es protección de datos, Alicia 😉 Pero tu yaya la conocía, seguro. Todas las de la coral se conocían. #delay: 300
    ** [vaya, qué vida más dura]
        Dura y tozuda. Como la mitad del valle. #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [quim, alguien ha pedido permiso para algo en santa margarida?]
    Santa Margarida no es nuestro, es del Parc Natural. #delay: 600
    Pero sí, me llamaron de allí: una asociación quiere hacer «un acto al alba» en la ermita y preguntaban si molestaba a alguien del pueblo. #delay: 6
    Les dije que a esas horas solo molesta a los jabalíes. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
