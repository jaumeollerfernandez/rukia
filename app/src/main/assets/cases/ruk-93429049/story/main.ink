// Caso RUK-93429049 (secta «Rosa d'Abril»). Entrada de la historia: variables compartidas e INCLUDEs.
//
// Convenciones de tiempo (ver README del proyecto, sección story/*.ink):
//   #at: D3 22:15      hora absoluta. D1 = día en que empieza el caso. Días negativos = historial previo del móvil.
//   #from: me          mensaje escrito por Alicia (el jugador), para el historial.
//   #caduca: D2 08:00  en la línea anterior a unas elecciones: a esa hora se elige solo «(sin responder)».
//   * [(sin responder)] elección oculta: lo que pasa si el jugador no contesta.
// Nunca días de la semana en el texto: solo «ayer», «anteanoche», «dentro de cuatro días».

// Reloj del caso. El motor los actualiza antes de cada avance; en Inky se cambian a mano.
VAR dia = 1
VAR hora = 10

// Riesgo de que la familia (la secta) note que alguien usa el móvil. 0-5.
VAR sospecha_familia = 0
VAR confianza_nuria = 0
VAR nuria_sabe = false     // el jugador le ha confesado a Núria que no es Alicia
VAR confianza_alicia = 0
VAR pol_calla = false      // Pol ha prometido no contar lo de la estación
VAR arnau_calla = false    // Arnau ha archivado el vídeo de la rotonda y no se lo dirá a nadie
VAR aciertos_nuria = 0
VAR nuria_denuncia = false // Núria suspendió la prueba y va a ir a los Mossos
VAR secta_sabe_rosalia = false // la familia ha descubierto que Alicia está con Rosalia

// Pistas conseguidas.
VAR sabe_secta = false     // sabe qué es «Rosa d'Abril»
VAR sabe_pan = false       // faltan barras en la panadería
VAR descarta_bus = false
VAR sabe_estrellas = false
VAR sabe_rosalia = false
VAR sabe_puerta_azul = false // sabe que la casa tiene la puerta azul

// Lo que se decide el D6.
VAR patrulla_en_mas = false   // Laia ha mandado agentes al mas de la puerta azul
VAR vigilancia_crater = false // habrá agentes en el cráter de Santa Margarida al amanecer
VAR alicia_a_salvo = false    // Alicia ha bajado con la patrulla
VAR iris_ayuda = false        // Iris va a declarar a los Mossos
VAR caso_resuelto = false     // lo activa la app de Policía si el jugador acierta la pregunta antes del límite

// El sargento que contrata al jugador.
INCLUDE laia.ink
INCLUDE central.ink

// Familia.
INCLUDE familia.ink
INCLUDE berta.ink
INCLUDE banco.ink

// Números ocultos que aparecen más tarde.
INCLUDE rosalia.ink
INCLUDE desconocido.ink
INCLUDE iris.ink
INCLUDE guia.ink
INCLUDE jordi.ink
INCLUDE marta.ink
INCLUDE arnau.ink

// Amigos.
INCLUDE nuria.ink
INCLUDE amigas.ink
INCLUDE pol.ink
INCLUDE dani.ink

// Estudios y trabajo.
INCLUDE clase.ink
INCLUDE elena.ink
INCLUDE pilar.ink
INCLUDE eric.ink
INCLUDE voley.ink

-> DONE

// Finales (D7, a las 06:30). Se evalúan entonces, así que cuentan las decisiones hasta el último momento.
// Alicia está a salvo: bajó con la patrulla, hay agentes en el mas o el jugador resolvió el caso.
=== function rescatada() ===
~ return alicia_a_salvo or patrulla_en_mas or caso_resuelto

// La secta la ha encontrado antes que la policía.
=== function capturada() ===
~ return secta_sabe_rosalia and not rescatada()

// La ceremonia se para: hay agentes en el cráter o Iris ha declarado.
=== function familia_salvada() ===
~ return vigilancia_crater or iris_ayuda
