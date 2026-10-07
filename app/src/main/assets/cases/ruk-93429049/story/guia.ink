// «El Guía» (Ignasi Coll). Escribe tres veces al móvil de Alicia, siempre de noche y con frases de la secta.

=== guia ===
La luz te espera, pequeña. Siempre vuelve a casa quien sabe dónde está su casa. #at: D3 23:10
-> d5

= d5
El séptimo amanecer nos reunirá donde la tierra se abrió. #at: D5 22:00
Solo familias completas. 🌹 #delay: 5
-> d7

= d7
Hoy amanece para todos. #at: D7 05:00
{capturada() and not familia_salvada():
    Ya ha amanecido. #at: D7 06:30 #call: audio/guia_final.m4a
}
-> DONE
