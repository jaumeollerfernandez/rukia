// Caso RUK-93429049 (secta «Rosa d'Abril»). Entrada de la historia: variables compartidas e INCLUDEs.
//
// Convenciones de tiempo (ver README del proyecto, sección story/*.ink):
//   #at: D3 22:15      hora absoluta. D1 = día en que empieza el caso. Días negativos = historial previo del móvil.
//   #from: me          mensaje escrito por Alicia (el jugador), para el historial.
//   #caduca: D2 08:00  en la línea anterior a unas elecciones: a esa hora se elige solo «(sin responder)».
//   * [(sin responder)] elección oculta: lo que pasa si el jugador no contesta.
// Nunca días de la semana en el texto: solo «ayer», «anteanoche», «dentro de cuatro días».
//
// Charlas que abre el jugador (contactos y huecos entre escenas): una etiqueta `- (charla)` y debajo una línea que
// solo lleva `#caduca: D{dia + 1} 00:00`. A medianoche se elige solo «(sin responder)» (con `+`, para que no se gaste),
// se vuelve a `charla` y salen las preguntas del día nuevo. En los chats con escenas, la caducidad es la hora de la
// escena siguiente: `#caduca: D2 10:30`.

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
VAR sabe_pienso = false    // Ona: en la lista de Alicia había pienso para perro
VAR sabe_ruta_lotes = false // Toni: Alicia subía lotes a una señora con perra de Sant Salvador

// Pistas falsas y ruido que se puede descartar.
VAR sabe_capsec = false      // conoce Can Pericot, el mas abandonado de Capsec (también con puerta azul)
VAR descarta_capsec = false  // Biel ha confesado que las llaves y la luz de Can Pericot eran suyas
VAR sabe_residencia = false  // ha oído que la Rosalia «está en una residencia»
VAR residencia_falsa = false // Laia ha comprobado que la Rosalia nunca ha estado en ninguna residencia
VAR dani_sospechoso = false
VAR dani_descartado = false
VAR sabe_prepago = false     // Mireia: Alicia compró un móvil barato en un estanco de Olot

// Laura, del pueblo, la está captando el Casal: lo que le cuentes llega a Berta.
VAR laura_sabe = false       // le has dicho a Laura por dónde está Alicia
VAR secta_a_capsec = false   // le has mandado a Laura (y a la secta) a Capsec

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
INCLUDE casal.ink

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

// Grupos del pueblo y actividades: vida normal de Alicia, para que Rosa d'Abril sea una más.
INCLUDE lectura.ink
INCLUDE coral.ink
INCLUDE aliments.ink
INCLUDE ioga.ink
INCLUDE esplai.ink
INCLUDE festa.ink
INCLUDE veins.ink
INCLUDE autoescola.ink
INCLUDE teatre.ink
INCLUDE repas.ink
INCLUDE voley.ink

// Contactos a los que el jugador escribe primero (pestaña Contactos). Cada día abren preguntas nuevas.
// Los adultos del pueblo cotillean: escribirles puede llegar a oídos de Montse (sospecha_familia).
INCLUDE mama.ink
INCLUDE laura.ink
INCLUDE carla.ink
INCLUDE mireia.ink
INCLUDE marc.ink
INCLUDE oriol.ink
INCLUDE sergi.ink
INCLUDE aina.ink
INCLUDE paula.ink
INCLUDE sonia.ink
INCLUDE ona.ink
INCLUDE judit.ink
INCLUDE teresa.ink
INCLUDE ramon.ink
INCLUDE gemma.ink
INCLUDE roser.ink
INCLUDE xavier.ink
INCLUDE pep.ink
INCLUDE toni.ink
INCLUDE lluisa.ink
INCLUDE fatima.ink
INCLUDE claudia.ink
INCLUDE imma.ink
INCLUDE pau.ink
INCLUDE clara.ink
INCLUDE biel.ink
INCLUDE quim.ink
INCLUDE conxita.ink
INCLUDE enric.ink
INCLUDE rafa.ink
INCLUDE hugo.ink
INCLUDE dolors.ink
INCLUDE queralt.ink
INCLUDE jan.ink
INCLUDE anna.ink
INCLUDE silvia.ink

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
