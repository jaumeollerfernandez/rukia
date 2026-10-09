// Montse, por privado. Trampa: tiene el móvil de Alicia guardado. Cualquier mensaje le dice que alguien lo usa.
// A cambio, se le escapa algo: lo que planean para «el amanecer».

=== mama ===
-> historial

= historial
Nena, ¿has comido? Hoy toca ayuno pero tú come algo, que tienes entreno 🌸 #at: D-6 12:10
sí mamá #from: me #at: D-6 13:40
Esta noche Ignasi quiere hablar contigo. Solo un ratito. Para ti es importante. #at: D-3 18:00
no me apetece mamá #from: me #at: D-3 19:02
Hazlo por mí. #at: D-3 19:02
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [mamá]
    ~ sospecha_familia += 2
    ¿Alicia? #delay: 60
    ... #delay: 120
    Tu móvil está aquí, nena. En la cocina. Lo acabo de ver encenderse. #delay: 10 #effect: glitch
    Si eres tú, vuelve. Ignasi dice que si vuelves antes del amanecer, todo está perdonado. #delay: 8
    Si no eres tú, que Dios te perdone. #delay: 20
    -> opciones
* {sabe_rosalia} [mamá, dónde está la rosalia?]
    ~ sospecha_familia += 2
    ~ secta_sabe_rosalia = true
    ¿La Rosalia? #delay: 60
    ¿Por qué me preguntas por la Rosalia? #delay: 5
    ... #delay: 300
    Gracias, nena. Ya sé dónde tengo que buscarte. 🌹 #delay: 10 #effect: glitch
    -> opciones
* [te quiero mamá]
    ~ sospecha_familia += 2
    Nena. #delay: 60
    Yo también. Más que a nada. #delay: 5
    Te tengo la ropa blanca planchada encima de la cama. Vuelve a casa. #delay: 10
    -> opciones
+ [(sin responder)]
    -> charla
