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
VAR ultimo_intento = 0 // último día del caso en que el jugador mandó una búsqueda desde la app de Policía (lo pone la app)

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
VAR sabe_secta = false     // conoce el nombre «Rosa d'Abril» (el grupo de duelo de la madre)
VAR sabe_cra = false       // Laia sabe que «CRA Serveis», el beneficiario del pago «a Hacienda», es Rosa d'Abril
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
VAR dani_droga = false       // sabe que Dani menudea (lo confiesa él en el D4 o se lo saca el jugador)
VAR dani_registrado = false  // el jugador mandó una patrulla al mas del tío de Dani y los Mossos le requisaron el costo
VAR dani_avisado = false     // Dani ya ha contado lo del registro (para no repetirlo)
VAR dani_detenido = false    // el jugador mandó detener e interrogar a Dani (envio_detencion_dani)
VAR dani_creido = false      // tras la confesión, el jugador le cree: lo sueltan y cuenta lo que vio
VAR dani_no_creido = false   // no le cree: Barcelona se lo lleva y el coche de la noche hace el traslado
VAR llamada_rosalia_contestada = false // lo pone la app cuando el jugador atiende o rechaza una llamada de ese chat
VAR sabe_prepago = false     // Mireia: Alicia compró un móvil barato en un estanco de Olot

// Confianza de los contactos con pistas verdaderas: 1 confía (cuenta más), 0 normal, -1 recela (no cuenta nada hasta que
// se le convence: mentira o verdad, según el personaje), -2 bloqueado (no vuelve a hablar). Ver PISTAS.md, «Confianza».
VAR fia_toni = 0
VAR fia_teresa = 0
VAR fia_eric = 0
VAR fia_ona = 0
VAR fia_oriol = 0
// Cuántos contactos han descubierto que no eres Alicia. Con 1, corre el rumor y otros ponen a prueba; con 2, las mentiras
// ya no cuelan y llega a oídos de Montse (sospecha_familia).
VAR delatado = 0
VAR aviso_delatado = false

// Pista falsa: Girona y la clínica. Parece que Alicia está embarazada y se esconde en Girona; la embarazada era Mireia.
VAR sabe_girona = false        // ha oído lo de Girona (la prueba de embarazo, la clínica, «no fuimos»)
VAR girona_descartado = false  // Mireia ha confesado que la prueba y la clínica eran suyas
VAR mireia_cerrada = false     // el jugador la presionó y Mireia ya no cuenta nada

// Laura, del pueblo, la está captando el Casal: lo que le cuentes llega a Berta.
VAR laura_sabe = false       // le has dicho a Laura por dónde está Alicia
VAR secta_a_capsec = false   // le has mandado a Laura (y a la secta) a Capsec

// Lo que se decide el D6.
VAR patrulla_en_mas = false   // Laia ha mandado agentes al mas de la puerta azul
VAR vigilancia_crater = false // habrá agentes en el cráter de Santa Margarida al amanecer
VAR alicia_a_salvo = false    // Alicia ha bajado con la patrulla
VAR iris_ayuda = false        // Iris va a declarar a los Mossos
VAR caso_resuelto = false     // lo activa la app de Policía si el jugador acierta la pregunta antes del límite
// Fichas policiales (app Policía): las pone la app cuando el jugador lee la ficha que pidió (ver police/records.json).
VAR ficha_dani = false        // la multa de su moto en la carretera de Sant Salvador: abre una pregunta en su charla
VAR ficha_ignasi = false      // el retiro del alba de hace once años en Santa Margarida: abre una consulta a Laia y una pregunta a Iris
// Matrículas que da la historia: desbloquean la ficha del vehículo en Policía (police/records.json, unlockedBy).
VAR sabe_audi = false         // Dani (D3 noche): la matrícula andorrana del Audi
VAR sabe_furgoneta = false    // Conxita: la matrícula de la furgoneta que llegó de madrugada
VAR sabe_seat = false         // Enric, o la consulta a Laia sobre el coche gris
VAR final_caso = 0           // el final al que se ha llegado (1–6, ver FINALES.md); lo lee el juego para el informe de cierre

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
INCLUDE iker.ink

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
// Alguien ha descubierto que no eres Alicia y deja de hablarte.
=== function delata() ===
~ delatado += 1
{delatado == 2:
    ~ sospecha_familia += 1
}

=== function familia_salvada() ===
~ return vigilancia_crater or iris_ayuda
