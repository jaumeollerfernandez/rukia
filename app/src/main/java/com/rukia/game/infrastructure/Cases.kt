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
private val rukEndings = listOf(
    CaseEnding(1, "Alicia a salvo", EndingTier.Good, "CASO\nRESUELTO", "«06:31. Entramos.» … «Alicia está en el coche patrulla de Bianya.»", "D7 06:31"),
    CaseEnding(2, "Rescate en el cráter", EndingTier.Good, "CASO\nRESUELTO", "«06:31. Entramos.» … «Entre ellos estaba Alicia.»", "D7 06:31"),
    CaseEnding(3, "Alicia sola", EndingTier.Partial, "RESUELTO\nPARCIAL", "«Alicia está a salvo. Está conmigo.»", "D7 06:31"),
    CaseEnding(4, "Llegamos tarde", EndingTier.Bad, "CERRADO\nSIN ÉXITO", "«Hemos llegado tarde.»", "D7 06:45"),
    CaseEnding(5, "Escondida, familia a salvo", EndingTier.Partial, "CERRADO\nPARCIAL", "«06:31. Entramos. Ignasi Coll, detenido.»", "D7 06:31"),
    CaseEnding(6, "Escondida y sola", EndingTier.Bad, "CERRADO\nSIN ÉXITO", "«Hay ambulancias en el cráter.»", "D7 06:45"),
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
