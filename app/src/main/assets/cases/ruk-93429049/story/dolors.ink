// Dolors, directora del grupo de teatro. Maternal y exigente con el texto. Ruido (Lorca, ensayos, la fiesta mayor),
// y un detalle: Montse vino a decirle que Alicia dejaba el teatro «porque ahora tiene otras prioridades».
// Vida propia: profesora de lengua jubilada; el ensayo general del D3 y la obra que aplaza el D4.

=== dolors ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* (obra) [dolors, lo siento por la obra. no voy a poder]
    {dia >= 4 or (dia == 3 and hora >= 22):
        Ya buscaremos otra Adela. #delay: 600
    - else:
        Gracias por decírmelo antes del ensayo, Alicia. Eso es de buena actriz. #delay: 600
    }
    Pero ninguna dirá «¡aquí se acabaron las voces de presidio!» como tú. #delay: 5
    -> opciones
* {dia < 3 and not obra} [dolors, cuándo es el ensayo general?]
    {dia == 1:Pasado mañana|Mañana}, a las 20:30. Texto aprendido, por favor. #delay: 600
    Si no puedes venir, dímelo. No pasa nada, pero dímelo. 🎭 #delay: 5
    -> opciones
* {dia >= 2} [dolors, mi madre ha hablado contigo?]
    Vino ayer al local. Muy correcta. #delay: 600
    Me dijo que dejabas el teatro, que ahora tenías «otras prioridades» y que no te llamáramos. #delay: 6
    Le dije que eso me lo tenías que decir tú. Treinta años dando clase me han enseñado a no fiarme de los recados. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [no lo dejo]
        Me alegro. Tu sitio sigue aquí, Alicia. Con tu nombre en el programa. #delay: 300
    ** [ahora mismo no puedo]
        Lo entiendo. Cuídate mucho. Aquí tienes tu sitio cuando quieras. #delay: 300
    ** [sí, lo dejo]
        Pues me lo has dicho tú. Con eso me basta. #delay: 300
        Aunque no me lo creo del todo. Has dicho «sí» como Adela dice «sí» a Bernarda. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [dolors, por qué elegiste bernarda alba?]
    Porque en cada pueblo hay una casa con las persianas bajadas, Alicia. #delay: 600
    Y porque Lorca escribió la mejor obra sobre madres que encierran a sus hijas «por su bien». #delay: 6
    -> opciones
* {dia >= 4} [dolors, quién hace de adela ahora?]
    Nadie. He dicho que la obra se aplaza. #delay: 600
    Queralt casi me abraza. Jan, como siempre, quería hacer él de Adela. 🎭 #delay: 5
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
