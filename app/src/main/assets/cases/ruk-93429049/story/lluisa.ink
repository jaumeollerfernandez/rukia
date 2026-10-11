// Lluïsa, coordinadora del Banc d'Aliments y tutora de las prácticas de Alicia. Organizada y bienpensada.
// Pista menor: el lote de «la masía de arriba», la de la señora con perra, solo lo subía Alicia (la Rosalia). Ruido: turnos y récords.
// Vida propia: maestra jubilada, cuenta lotes y persigue el récord de 70 (el D5 hace 71).

=== lluisa ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* (no_voy) {not aliments.d3.voy} [lluïsa siento no poder ir]
    No pasa nada, Alicia. Cuídate mucho. #delay: 600
    -> opciones
* {dia >= 2} [gracias por todo, lluïsa]
    A ti. Los lotes de las masías los sube Toni mientras tanto. #delay: 600
    -> opciones
* [cuántos lotes hicisteis?]
    {dia >= 5:
        71. ¡Récord! 🥫 #delay: 600
        Faltabas tú para el 72, que siempre te llevas el de la masía de arriba. #delay: 5
    - else:
        64 el sábado pasado. Si llegamos a 70, Fàtima hace pastel 🥫 #delay: 600
        Contigo seguro que llegábamos. Siempre te llevas el de la masía de arriba. #delay: 5
    }
    -> opciones
* {dia >= 2} [lluïsa, mis prácticas cuentan aunque falte?]
    Tranquila. Te firmo las horas que llevas, que son muchas. #delay: 600
    Elena me ha preguntado por ti. Le he dicho que eres la voluntaria más formal que he tenido. #delay: 6
    {aliments.d3.voy and dia >= 6:
        Aunque el sábado te esperamos hasta las once 🙏 #delay: 5
    }
    -> opciones
* {dia >= 3} [lluïsa, el lote de la masía de arriba quién lo sube ahora?]
    Toni lo intenta, pero la señora no le abre. #delay: 600
    Ya sabes cómo es: si no lo subes tú, no quiere nada. Dice que «la nena» ya sabe lo que necesita. #delay: 6
    Toni se lo deja en la piedra de la entrada y la perra no le deja ni acercarse 🙏 #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [que le suba también pienso]
        Ya lo hace. Media caja. Esa perra come más que Toni, y mira que es difícil 😉 #delay: 300
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [lluïsa, mi madre ha traído algo al banco?]
    ¡Sí! Muchísimas cosas, qué generosa. Ropa, mantas, hasta una vajilla entera. #delay: 600
    Y tu ropa de vóley y unos libros tuyos. ¿Os mudáis? #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [algo así]
        Pues mucha suerte en la casa nueva. Y no te olvides de nosotros 🥫 #delay: 300
    ** [mis cosas? no le dije nada]
        Uy. Pensaba que lo sabías, Alicia. Lo tengo todo aparte en una caja, por si quieres algo. #delay: 300
        No se ha llevado nadie nada todavía. #delay: 4
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4} [lluïsa, puedo hacer el turno de la semana que viene?]
    ¡Claro! Te pongo con Toni de 10 a 13, como siempre. #delay: 600
    Si no puedes, me avisas y doblo yo. Que ya me toca 😉 #delay: 5
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla
