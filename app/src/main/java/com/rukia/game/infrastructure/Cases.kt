package com.rukia.game.infrastructure

import com.rukia.game.domain.model.Achievement
import com.rukia.game.domain.model.AchievementGroup.Decisions
import com.rukia.game.domain.model.AchievementGroup.FalseLeads
import com.rukia.game.domain.model.AchievementGroup.KeyClues
import com.rukia.game.domain.model.CaseEnding
import com.rukia.game.domain.model.CaseKind
import com.rukia.game.domain.model.EndingTier
import com.rukia.game.domain.model.GameCase
import com.rukia.phone.domain.model.DEBUG_CASE_PREFIX

// Los finales de FINALES.md: cada uno pone `final_caso` en laia.ink.
// Cada final, con la declaración de Laia a la prensa esa misma mañana. Solo cuenta lo que el final de laia.ink da por
// hecho y lo que está en las fichas. En público: iniciales para la víctima y el detenido, y ni rastro del móvil.
private val rukEndings = listOf(
    CaseEnding(1, "Alicia a salvo", EndingTier.Good, "CASO\nRESUELTO", """
        Comisaría de Olot. Comparecencia ante los medios, 12:00.

        Buenos días. Soy la sargento Laia Puig, de los Mossos d'Esquadra de Olot. Leeré una declaración y no habrá preguntas.

        Hace ocho días, A. S. V., una joven de 18 años vecina de L'Hostalnou de Bianya, salió de su casa hacia las once de la noche y no volvió. Dejó el móvil en su habitación. Su madre presentó la denuncia y pidió discreción, y por eso no la hemos hecho pública hasta hoy.

        Esta mañana, a las 6:31, agentes de esta comisaría han entrado en la ermita del cráter de Santa Margarida, en Santa Pau. Había allí unas doce personas vestidas de blanco, reunidas al amanecer para lo que el grupo llamaba la «Trobada de l'Alba». Varias llevaban días en ayunas. Hemos requisado las botellas de agua que iban a repartirse. Ya están en el laboratorio.

        Ha sido detenido un hombre de 61 años, I. C. F., vecino de Sant Joan les Fonts y presidente de la Associació Comunitat Rosa d'Abril, que se presentaba como un grupo de apoyo al duelo. Hace once años, este mismo hombre fue detenido tras la muerte de una mujer de 34 años en un retiro idéntico, en la misma ermita, después de nueve días sin comer y de beber un «agua preparada». Aquella causa se archivó.

        Hemos seguido el dinero. Transferencias de los socios a una sociedad, CRA Serveis, que las familias creían pagos a Hacienda. La venta de un piso de 120.000 euros. Voluntades anticipadas firmadas ante miembros de la propia asociación, con testamentos a su favor. El juzgado de Olot decidirá los cargos. Hablamos, como mínimo, de estafa y de un posible delito contra la vida de las personas.

        La joven está bien. Se fue por voluntad propia para no ir a esa ceremonia, y se refugió en una masía de Sant Salvador de Bianya, en casa de una vecina de 78 años, amiga de su abuela, que la ha cuidado estos días. Esa señora no ha cometido ningún delito y les pido que respeten su intimidad. Una patrulla la ha bajado esta mañana. Su madre y su hermana estaban en el cráter. Están ilesas y prestarán declaración.

        Este caso se ha resuelto gracias a una línea de investigación que no puedo detallar. Doy las gracias a quien la ha hecho posible.

        Si alguien de su familia ha empezado a dar dinero, a ayunar o a firmar papeles para un grupo que le «ayuda con el duelo», llamen al 112. No esperen.

        Gracias.
    """.trimIndent(), "D7 06:31"),
    CaseEnding(2, "Rescate en el cráter", EndingTier.Good, "CASO\nRESUELTO", """
        Comisaría de Olot. Comparecencia ante los medios, 12:00.

        Buenos días. Soy la sargento Laia Puig, de los Mossos d'Esquadra de Olot. Leeré una declaración y no habrá preguntas.

        Hace ocho días, A. S. V., una joven de 18 años vecina de L'Hostalnou de Bianya, salió de su casa de noche y no volvió. Dejó el móvil en su habitación. Su madre presentó la denuncia y pidió discreción.

        La joven se había ido por voluntad propia. Sabemos que pasó estos días escondida en una masía del valle. Sabemos también que, en las últimas horas, alguien del grupo del que huía dio con ella.

        Esta mañana, a las 6:31, agentes de esta comisaría han entrado en la ermita del cráter de Santa Margarida, en Santa Pau. Había allí unas doce personas vestidas de blanco, reunidas al amanecer para lo que llamaban la «Trobada de l'Alba». Entre ellas estaba la joven. La habían subido su madre y su hermana, de la mano, con la ropa blanca puesta encima del jersey. Está bien. Asustada, pero bien.

        Varias de las personas presentes llevaban días en ayunas. Hemos requisado las botellas de agua que se iban a repartir y ya están en el laboratorio.

        Ha sido detenido un hombre de 61 años, I. C. F., vecino de Sant Joan les Fonts y presidente de la Associació Comunitat Rosa d'Abril, que se presentaba como un grupo de apoyo al duelo. Hace once años, este mismo hombre fue detenido tras la muerte de una mujer de 34 años en un retiro idéntico, en la misma ermita, después de nueve días sin comer y de beber un «agua preparada». Aquella causa se archivó.

        La asociación cobraba a sus socios a través de una sociedad, CRA Serveis. Las familias creían que pagaban a Hacienda. Hemos encontrado la venta de un piso de 120.000 euros y voluntades anticipadas firmadas ante miembros del grupo, con testamentos a su favor. El juzgado de Olot decidirá los cargos.

        La madre y la hermana de la joven prestarán declaración. Les pido prudencia: también ellas son víctimas de este hombre.

        Este caso se ha resuelto gracias a una línea de investigación que no puedo detallar. Doy las gracias a quien la ha hecho posible.

        Si alguien de su familia ha empezado a dar dinero, a ayunar o a firmar papeles para un grupo que le «ayuda con el duelo», llamen al 112. No esperen.

        Gracias.
    """.trimIndent(), "D7 06:31"),
    CaseEnding(3, "Alicia sola", EndingTier.Partial, "RESUELTO\nPARCIAL", """
        Comisaría de Olot. Comparecencia ante los medios, 12:00.

        Buenos días. Soy la sargento Laia Puig, de los Mossos d'Esquadra de Olot. Les daré dos noticias. Una buena y una que no lo es.

        La buena. Hace ocho días desapareció A. S. V., una joven de 18 años vecina de L'Hostalnou de Bianya. Esta mañana la hemos encontrado sana y salva. Se fue por voluntad propia, huyendo de un grupo al que pertenece su familia, y estuvo escondida en una masía de Sant Salvador de Bianya, en casa de una vecina de 78 años que la acogió. Ahora está conmigo, en esta comisaría.

        La otra. Esta madrugada ese grupo, la Associació Comunitat Rosa d'Abril, que se presentaba como un grupo de apoyo al duelo, celebró al amanecer una ceremonia en la ermita del cráter de Santa Margarida, en Santa Pau. Lo llamaban la «Trobada de l'Alba». No teníamos a nadie allí. Cuando llegaron las primeras unidades, ya había amanecido.

        Once personas han sido trasladadas al Hospital d'Olot con síntomas de intoxicación. Llevaban días en ayunas y habían bebido un agua que repartía el grupo, y que estamos analizando. Entre ellas están la madre y la hermana de la joven. Están vivas. Su pronóstico es reservado.

        El responsable del grupo no estaba en el cráter. Hemos pedido su busca y captura. Es un hombre de 61 años, I. C. F., vecino de Sant Joan les Fonts: 1,74 de estatura, delgado, pelo blanco y barba blanca cuidada, con una pulsera de cuentas de madera. Conduce un Seat gris. Hace once años ya fue detenido por la muerte de una mujer de 34 años en un retiro idéntico, en la misma ermita. Aquella causa se archivó. Si lo ven, no se acerquen. Llamen al 112.

        No voy a esconderlo. Supimos a tiempo dónde estaba la joven, pero no supimos a tiempo dónde iba a estar su familia. Esa parte del trabajo no la hicimos bien, y es responsabilidad mía.

        Gracias.
    """.trimIndent(), "D7 06:31"),
    CaseEnding(4, "Llegamos tarde", EndingTier.Bad, "CERRADO\nSIN ÉXITO", """
        Comisaría de Olot. Comparecencia ante los medios, 12:00.

        Buenos días. Soy la sargento Laia Puig, de los Mossos d'Esquadra de Olot. Leeré una declaración. No responderé preguntas.

        Hace ocho días, A. S. V., una joven de 18 años vecina de L'Hostalnou de Bianya, salió de su casa hacia las once de la noche y no volvió. Su madre presentó la denuncia y nos pidió que no la hiciéramos pública. Hemos respetado esa petición hasta hoy. Hoy ya no podemos: desde esta mañana tampoco sabemos dónde está su madre.

        Teníamos motivos para creer que, al amanecer de hoy, un grupo llamado Associació Comunitat Rosa d'Abril, que se presentaba como un grupo de apoyo al duelo, iba a reunirse en la ermita del cráter de Santa Margarida, en Santa Pau. Lo llamaban la «Trobada de l'Alba». A las 6:45 nuestros agentes han llegado a la ermita. No había nadie. Solo niebla, y la ropa blanca de los participantes, doblada en el suelo.

        Hemos llegado tarde.

        Desde este momento buscamos a tres personas. La joven, de 18 años: 1,66 de estatura, delgada, pelo castaño oscuro y largo, con una pulsera roja de hilo en la muñeca izquierda. Su madre, M. V., de 52 años. Y su hermana, B. S. V., de 24. Creemos que están con el resto del grupo.

        Buscamos también al responsable de la asociación, I. C. F., de 61 años, vecino de Sant Joan les Fonts. Mide 1,74, es delgado, tiene el pelo y la barba blancos y lleva una pulsera de cuentas de madera. Conduce un Seat gris a nombre de la sociedad CRA Serveis. Hace once años fue detenido tras la muerte de una mujer de 34 años en un retiro idéntico, en esa misma ermita, después de nueve días de ayuno y de beber un «agua preparada». Aquella causa se archivó. Por eso tenemos prisa.

        En el dispositivo trabajan el GRAE, la unidad canina, el helicóptero, los Bombers y los Agents Rurals. Estamos revisando masías, pistas forestales y la zona volcánica de la Garrotxa, y hemos alertado a todos los cuerpos policiales.

        Si han visto a un grupo vestido de blanco, a alguna de estas personas o ese coche, llamen al 112. A cualquier hora. Cualquier detalle sirve.

        Esta comisaría tuvo una semana para evitarlo y no lo evitó. La responsabilidad es mía. Lo siento.
    """.trimIndent(), "D7 06:45"),
    CaseEnding(5, "Escondida, familia a salvo", EndingTier.Partial, "CERRADO\nPARCIAL", """
        Comisaría de Olot. Comparecencia ante los medios, 12:00.

        Buenos días. Soy la sargento Laia Puig, de los Mossos d'Esquadra de Olot.

        Esta mañana, a las 6:31, agentes de esta comisaría han entrado en la ermita del cráter de Santa Margarida, en Santa Pau. Había allí unas doce personas vestidas de blanco, reunidas al amanecer para lo que llamaban la «Trobada de l'Alba», una ceremonia de la Associació Comunitat Rosa d'Abril, que se presentaba como un grupo de apoyo al duelo. Varias llevaban días en ayunas. Hemos requisado las botellas de agua que iban a repartir. Están en el laboratorio.

        Ha sido detenido el presidente de la asociación, I. C. F., de 61 años, vecino de Sant Joan les Fonts. Hace once años ya fue detenido tras la muerte de una mujer de 34 años en un retiro idéntico, en la misma ermita. Aquella causa se archivó. Esta vez el juzgado de Olot tendrá también las cuentas de la sociedad CRA Serveis, por la que pasaba el dinero de los socios, y las voluntades anticipadas que les hacía firmar.

        Entre los presentes estaban una mujer de 52 años y su hija de 24. Están bien. Pero falta alguien: la hija menor, A. S. V., de 18 años, desaparecida de su casa de L'Hostalnou de Bianya hace ocho días. Hasta hoy no lo habíamos hecho público, a petición de la familia.

        Creemos que se fue por voluntad propia, para no ir a esa ceremonia, y que sigue escondida en algún lugar de la Vall de Bianya. No tenemos indicios de que nadie le haya hecho daño. Mide 1,66, es delgada, tiene el pelo castaño oscuro y largo y lleva una pulsera roja de hilo en la muñeca izquierda.

        A los vecinos del valle: si tienen un pajar, una borda o una masía vacía, échenle un vistazo. Si la ven, no la asusten. Llamen al 112.

        Y a ella, si me escucha: Alicia, ya ha terminado. Ya puedes volver.

        Gracias.
    """.trimIndent(), "D7 06:31"),
    CaseEnding(6, "Escondida y sola", EndingTier.Bad, "CERRADO\nSIN ÉXITO", """
        Comisaría de Olot. Comparecencia ante los medios, 12:00.

        Buenos días. Soy la sargento Laia Puig, de los Mossos d'Esquadra de Olot. Leeré una declaración. No responderé preguntas.

        Esta madrugada, una asociación llamada Comunitat Rosa d'Abril, que se presentaba como un grupo de apoyo al duelo, ha celebrado una ceremonia al amanecer en la ermita del cráter de Santa Margarida, en Santa Pau. Lo llamaban la «Trobada de l'Alba». Los participantes llevaban días en ayunas y bebieron un agua que repartía el grupo. Estamos analizando qué contenía.

        A las 6:45 los servicios de emergencia han subido al cráter. Once personas han sido trasladadas al Hospital d'Olot con síntomas de intoxicación. Entre ellas, una mujer de 52 años y su hija de 24, vecinas de L'Hostalnou de Bianya. Están vivas. Su pronóstico es reservado.

        El responsable del grupo, I. C. F., de 61 años, vecino de Sant Joan les Fonts, no ha sido localizado. Hemos pedido su busca y captura. Hace once años ya fue detenido tras la muerte de una mujer de 34 años en un retiro idéntico, en la misma ermita. Aquella causa se archivó. Mide 1,74, es delgado, tiene el pelo y la barba blancos y conduce un Seat gris. Si lo ven, no se acerquen. Llamen al 112.

        Hay una tercera persona de esa familia a la que buscamos desde hace ocho días: la hija menor, A. S. V., de 18 años. Salió de casa una noche y no volvió. Creemos que se fue para no ir a esa ceremonia y que sigue escondida en algún sitio de la Vall de Bianya. Sola. Mide 1,66, es delgada, tiene el pelo castaño oscuro y largo y lleva una pulsera roja de hilo en la muñeca izquierda.

        A los vecinos del valle: revisen pajares, bordas y masías vacías. Si la ven, no la asusten. Llamen al 112.

        No llegamos a tiempo al cráter, y todavía no hemos llegado hasta ella. Seguiremos buscando.
    """.trimIndent(), "D7 06:45"),
)

// Cada logro, con las variables de main.ink que lo dan (ver PISTAS.md).
private val rukAchievements = listOf(
    Achievement("billete", KeyClues, "El billete señuelo", "Alicia nunca subió al bus de Barcelona.") { it.isTrue("descarta_bus") },
    Achievement("cra", KeyClues, "CRA Serveis", "Seguiste los 2.840 € hasta Rosa d'Abril.") { it.isTrue("sabe_cra") },
    Achievement("estrellas", KeyClues, "Donde vimos las estrellas", "Superaste la prueba de Núria.") { it.isTrue("sabe_estrellas") },
    Achievement("porta_blava", KeyClues, "La porta blava", "Diste con la puerta azul del Mas de la Rosalia.") { it.isTrue("sabe_puerta_azul") },
    Achievement("rosalia", KeyClues, "Un fijo en Sant Salvador", "Supiste que Alicia estaba con la Rosalia.") { it.isTrue("sabe_rosalia") },
    Achievement("confianza", KeyClues, "Confianza", "Alicia confió en ti desde el prepago.") { it.number("confianza_alicia") >= 3 },
    Achievement("precedente", KeyClues, "El precedente", "Encontraste en las fichas lo que pasó hace once años en Santa Margarida.") { it.isTrue("ficha_ignasi") },
    Achievement("barcelona", FalseLeads, "Barcelona", "La cámara de la estación lo dejó claro.") { it.isTrue("descarta_bus") },
    Achievement("girona", FalseLeads, "Girona", "Mireia te contó la verdad.") { it.isTrue("girona_descartado") },
    Achievement("can_pericot", FalseLeads, "Can Pericot", "Ni pozo ni perro.") { it.isTrue("descarta_capsec") },
    Achievement("dani", FalseLeads, "El mas del tío de Dani", "Solo hachís y una báscula.") { it.isTrue("dani_registrado") },
    Achievement("labios", Decisions, "Labios sellados", "Pol o Arnau guardaron el secreto.") { it.isTrue("pol_calla") || it.isTrue("arnau_calla") },
    Achievement("fantasma", Decisions, "Fantasma", "La familia nunca sospechó del móvil.") { it.number("sospecha_familia") == 0 },
    Achievement("iris", Decisions, "Iris declara", "Convenciste a Iris de ir a los Mossos.") { it.isTrue("iris_ayuda") },
    Achievement("vigilancia", Decisions, "Vigilancia al alba", "Mandaste la patrulla al cráter.") { it.isTrue("vigilancia_crater") },
)

/** The game's cases, in the order the case list shows them. Add a case here and give it a folder in assets/cases/. */
val cases = listOf(
    // Una semana en tiempo real: el jugador es un hacker con el móvil de Alicia y tiene que encontrarla antes del séptimo amanecer.
    GameCase(
        "ruk-93429049", 1, "RUK-93429049",
        "Mujer, 18 años. Dejó el terminal móvil en el domicilio. La denunciante, la madre, solicita máxima discreción. Sin indicios de violencia hasta la fecha.",
        headline = "Desaparición de A. S. V.", // las iniciales no se separan al saltar de línea
        stamp = "PRIORIDAD ALTA",
        facts = listOf(
            "LUGAR" to "L'Hostalnou de Bianya, Garrotxa",
            "VISTA POR ÚLTIMA VEZ" to "Hace dos noches",
            "PRUEBA" to "Terminal móvil de la víctima",
            // El «séptimo amanecer» lo tiene que descubrir el jugador: aquí solo la prisa.
            "PLAZO" to "Lo antes posible",
        ),
        days = 7,
        endingVoice = "SGT. LAIA (MOSSOS)",
        endings = rukEndings,
        achievements = rukAchievements,
    ),
    // Every feature in one place, to try things out: choices, calls with audio, delayed messages and notifications,
    // a group that appears a minute in, contacts and the police question.
    GameCase(
        "test-case", 0, "Test Case",
        "Every feature of the phone in one place. Rukia calls, Jaume makes you wait, and a group appears after a minute.",
        kind = CaseKind.Training,
    ),
    // Simulador para probar los .ink: el mismo caso con su propia partida y una barra arriba para adelantar el tiempo.
    GameCase(
        "${DEBUG_CASE_PREFIX}ruk-93429049", 1, "DEBUG:RUK-93429049",
        "Simulador · partida aparte, sin notificaciones",
        kind = CaseKind.Debug,
        days = 7, endingVoice = "SGT. LAIA (MOSSOS)", endings = rukEndings, achievements = rukAchievements,
    ),
)
