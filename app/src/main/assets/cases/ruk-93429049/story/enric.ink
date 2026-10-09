// Enric, vecino. Discreto, con perro (Rocky). Vio el coche gris toda la noche delante de casa.

=== enric ===
-> charla

= charla
- (opciones)
#caduca: D{dia + 1} 00:00
* [{dia < 3:enric, ha vuelto el rocky?|enric, qué alegría lo del rocky}]
    {dia < 3:Aún no 😢 Gracias por preguntar, Alicia.|¡Gracias, Alicia! Estaba gordo como una vaca 😂} #delay: 600
    -> opciones
* {dia >= 4} [enric, viste quién había en el coche gris?]
    Un hombre mayor, con barba blanca. Toda la noche ahí. #delay: 600
    Miraba la ventana de tu cuarto. Con la luz del móvil encendida. #delay: 5
    A las seis se fue hacia Sant Joan les Fonts. #delay: 5
    -> opciones
+ [(sin responder)]
    -> charla
