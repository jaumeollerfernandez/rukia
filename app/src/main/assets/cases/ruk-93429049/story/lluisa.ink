// Lluïsa, coordinadora del Banc d'Aliments y tutora de las prácticas de Alicia. Organizada y bienpensada.
// Pista menor: el lote de «la masía de arriba», la de la señora con perra, solo lo subía Alicia (la Rosalia). Ruido: turnos y récords.

=== lluisa ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [lluïsa siento no poder ir]
    No pasa nada, Alicia. Cuídate mucho. #delay: 600
    -> opciones
* {dia >= 2} [gracias por todo, lluïsa]
    A ti. Los lotes de las masías los sube Toni mientras tanto. #delay: 600
    -> opciones
* [cuántos lotes hicisteis?]
    71. ¡Récord! 🥫 #delay: 600
    Faltabas tú para el 72, que siempre te llevas el de la masía de arriba. #delay: 5
    -> opciones
* {dia >= 2} [lluïsa, mis prácticas cuentan aunque falte?]
    Tranquila. Te firmo las horas que llevas, que son muchas. #delay: 600
    Elena me ha preguntado por ti. Le he dicho que eres la voluntaria más formal que he tenido. #delay: 6
    -> opciones
* {dia >= 3} [lluïsa, el lote de la masía de arriba quién lo sube ahora?]
    Toni lo intenta, pero la señora no le abre. #delay: 600
    Ya sabes cómo es: si no lo subes tú, no quiere nada. Dice que «la nena» ya sabe lo que necesita. #delay: 6
    Toni se lo deja en la piedra de la entrada y la perra no le deja ni acercarse 🙏 #delay: 6
    -> opciones
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
+ [(sin responder)]
    -> charla
