// Dani, 22 años, la conoció por Gonpi. Insistente y raro: parece sospechoso, pero es inocente.
// Su coartada (un concierto en Barcelona) no llega hasta el D3, en Gonpi. Hasta entonces, todo en él da mal rollo:
// sabe que se ha ido, ronda su calle con la moto y tiene un mas «sin cobertura» en la Vall d'en Bas.

=== dani ===
-> historial

= historial
q haces #at: D-5 23:40
nada, estudiando #from: me #at: D-5 23:58
pásate por olot esta semana #at: D-5 23:58
te invito a algo #at: D-5 23:59
no puedo, cosas de familia #from: me #at: D-4 10:12
siempre igual #at: D-4 10:12
-> d1

= d1
ey #at: D1 23:30
ya no contestas? #delay: 600
vale guay #delay: 900
me dejas en visto desde hace tres días #delay: 5
y luego te vas de casa sin decir nada a nadie #delay: 300
~ dani_sospechoso = true
sí, me he enterado. olot es pequeño 😏 #delay: 4 #caduca: D2 03:00
* [quién te lo ha dicho?]
    tengo mis fuentes jajaja #delay: 120
    tranqui que no se lo digo a nadie #delay: 4
    si me dices dónde estás te llevo algo. lo que sea. tengo la moto #delay: 6
* [perdona, estoy liada]
    ya, liada #delay: 120
    bueno avísame cuando tengas tiempo para mí #delay: 10
* [déjame en paz dani]
    wow #delay: 60
    vale vale. ya está #delay: 4
    luego no vengas llorando #delay: 300
* [(sin responder)]
    sigues viva o qué 😂 #delay: 1
- -> charla("D2 22:35") ->
-> d2

= d2
oye #at: D2 22:40
me ha escrito tu hermana por gonpi #delay: 4
preguntando si sé dónde estás #delay: 3
tu familia es rara de cojones #delay: 60
qué pasa contigo? #delay: 5 #caduca: D3 03:00
* [qué le has dicho?]
    que no sé nada. porque no sé nada #delay: 120
    y tu hermana me ha dado mal rollo. me ha dicho que «la luz siempre encuentra el camino». qué coño significa eso #delay: 8
* [no le digas nada a mi hermana, porfa]
    no pensaba. ni la conozco #delay: 120
    me ha dado mal rollo #delay: 4
* [(sin responder)]
- -> charla("D3 21:25") ->
-> d3

= d3
mira mi gonpi #at: D3 21:30
el concierto de barna. para que veas que esa noche estaba allí y no soy ningún psicópata jaja #delay: 4
tu hermana me ha vuelto a escribir. la he bloqueado #delay: 30
-> charla("D5 23:10") ->
-> d5

= d5
ey #at: D5 23:15
he visto lo que ha subido esa cuenta del cráter #delay: 4
es lo de tu familia, no? #delay: 3
cuídate de verdad. y perdona si fui pesado #delay: 10
-> d6

= d6
// D6: nada.
-> d7

= d7
// D7: nada.
-> DONE

// Lo que el jugador le puede preguntar entre escena y escena. Hasta el D3 se escabulle; después, se explica.
= charla(limite)
- (opciones)
#caduca: {limite}
* [dani dónde estabas anteanoche?]
    {dia < 3:
        por ahí #delay: 120
        por? me echabas de menos? 😏 #delay: 4
    - else:
        te lo he dicho. en barna, en un concierto. mira mi gonpi #delay: 120
    }
    -> opciones
* {dani_sospechoso} [tú me has seguido con la moto?]
    seguido? #delay: 120
    he pasado por tu calle alguna vez. vivo al lado de la gasolinera, paso siempre por ahí #delay: 5
    vale, igual alguna vez de más 🙃 #delay: 30
    no soy un psicópata ali #delay: 4
    -> opciones
* [tienes algún sitio fuera de olot?]
    ~ dani_sospechoso = true
    el mas de mi tío en la vall d'en bas. subo a veces a hacer barbacoas #delay: 120
    por? quieres venir? 😏 #delay: 4
    no hay cobertura eso sí. ni vecinos jajaja #delay: 6
    -> opciones
* {dia >= 3} [perdona si he sido borde]
    tranqui. yo también he sido un pesado #delay: 120
    -> opciones
+ [(sin responder)]
- ->->
