// Ignasi Coll, «el que acompaña» en Rosa d'Abril (la familia lo llama solo Ignasi). Escribe tres veces al móvil de Alicia, siempre de noche.
// Nunca amenaza ni predica: cariño, paciencia y culpa. La amenaza está en lo que da por hecho.

=== guia ===
Alicia. Soy Ignasi. Tu madre me ha dado tu número. #at: D3 23:10
No te escribo para reñirte. Aquí nadie está enfadado contigo. #delay: 8
Sé que tienes miedo. El miedo es lo último que se suelta, y siempre duele. #delay: 10
Tu madre no duerme. Tu hermana tampoco. Pero no te lo digo para que te sientas culpable. #delay: 12
Cuando estés preparada, la puerta de la Casa está abierta. Siempre vuelve a casa quien sabe dónde está su casa. 🌹 #delay: 10
-> d5

= d5
Buenas noches, Alicia. #at: D5 22:00
El último amanecer nos reuniremos donde la tierra se abrió. Las familias, completas. #delay: 6
Tu madre te ha guardado el sitio, a su lado. #delay: 8
No hace falta que traigas nada. Solo a ti. #delay: 5
-> d7

= d7
Hoy amanece para todos. #at: D7 05:00
{capturada() and not familia_salvada():
    Ya ha amanecido. #at: D7 06:30 #call: audio/guia_final.m4a
}
-> DONE
