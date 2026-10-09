// Ona, del vóley. Tiene en el maletero la bolsa de deporte de Alicia, con la lista de lo que se llevó (pienso para perro).

=== ona ===
-> historial

= historial
mañana me llevas al entreno? #from: me #at: D-4 13:00
sí!! paso a las 7 #at: D-4 13:05
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [ona 💛]
    ALI #delay: 120
    la colocadora ha vuelto?? 🏐 #delay: 3
    -> opciones
* {dia >= 2} [ona me dejé algo en tu coche?]
    tu bolsa de deporte!! la tengo en el maletero desde el último entreno #delay: 120
    la abro? #delay: 4
    ** [sí, mira qué hay]
        ~ sabe_pienso = true
        rodilleras, una toalla que huele fatal y una lista en un papel #delay: 300
        «pan, velas, pilas, cerillas, pienso perro grande, tiritas, pastillas tos» #delay: 8
        vas de acampada o qué 😂 #delay: 4
        y desde cuándo tienes perro? #delay: 5
    ** [no, déjala. ya la recogeré]
        vale. te la guardo #delay: 120
    -- -> opciones
* {dia >= 4} [ona qué tal de colocadora?]
    fatal. sonia me grita #delay: 120
    vuelve 😭 #delay: 3
    -> opciones
+ [(sin responder)]
    -> charla
