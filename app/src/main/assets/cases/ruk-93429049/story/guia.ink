// Ignasi Coll, «el que acompaña» en Rosa d'Abril. Escribe tres veces al móvil de Alicia, siempre de noche, desde un número sin nombre.
// Nunca dice cómo se llama: su nombre solo sale si el jugador investiga la asociación (ver PISTAS.md).
// Nunca amenaza ni predica: escribe como el terapeuta del grupo de duelo de la madre. La amenaza está en lo que da por hecho.

=== guia ===
Alicia. Soy quien acompaña a tu madre en el grupo. Ella me ha dado tu número. #at: D3 23:10
No te escribo para reñirte. Aquí nadie está enfadado contigo. #delay: 8
Tu madre no duerme. Tu hermana tampoco. Pero no te lo digo para que te sientas culpable. #delay: 12
Dentro de cuatro días hará tres años de tu abuela. Le haría mucha ilusión que estuvieras. #delay: 10
Siempre vuelve a casa quien sabe dónde está su casa. 🌹 #delay: 10 #caduca: D4 23:00
// Contestarle no cambia nada: él no da su nombre ni discute. Escucha, y devuelve la culpa.
* [no voy a volver]
    Nadie te obliga, Alicia. #delay: 600
    Las puertas de casa no se cierran con llave. Solo se dejan abiertas. #delay: 8
* [quién eres?]
    Alguien que escucha. #delay: 600
    Tu madre te hablará de mí, si quieres. Ella sabe quién soy. #delay: 8
* [dejad en paz a mi madre]
    Tu madre está en paz por primera vez en tres años. #delay: 600
    Pregúntaselo a ella. No a mí. #delay: 6
* [(sin responder)]
- -> d5

= d5
Buenas noches, Alicia. #at: D5 22:00
El último amanecer nos reuniremos donde la tierra se abrió. Las familias, completas. #delay: 6
Tu madre te ha guardado el sitio, a su lado. #delay: 8
No hace falta que traigas nada. Solo a ti. #delay: 5 #caduca: D6 23:00
* [no pienso ir]
    Lo sé. #delay: 600
    Por eso te he guardado el sitio de todas formas. #delay: 8
* [mi madre está bien?]
    Mejor que nunca. Ayuna, duerme poco y sonríe. #delay: 600
    ¿Cuánto hacía que no la veías sonreír, Alicia? #delay: 8
* [qué es «donde la tierra se abrió»?]
    Un lugar antiguo. Tu abuela lo conocía. #delay: 600
    Allí ya no pesa nada. 🌹 #delay: 8
* [(sin responder)]
- -> d7

= d7
Hoy amanece para todos. #at: D7 05:00
(espera) #at: D7 06:30
{capturada() and not familia_salvada():
    Ya ha amanecido. #at: D7 06:30 #call: audio/guia_final.m4a
}
-> DONE
