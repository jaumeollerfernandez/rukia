package com.rukia.game

import com.rukia.phone.DEBUG_CASE_PREFIX

/** How a case shows in the list: a real case file, a training one, or a debug simulator. */
enum class CaseKind { Case, Training, Debug }

/**
 * A playable case. Its content lives in assets/cases/<id>/ (characters.json, chats/, story/, police/case.json, media/)
 * and its progress in files/cases/<id>/, so cases never share chats or saves. Add a case here and give it a folder.
 * [headline], [stamp] and [facts] fill the case file card; like the rest of a case's content, they aren't translated.
 */
class GameCase(
    val id: String,
    val number: Int,
    val title: String,
    val summary: String,
    val kind: CaseKind = CaseKind.Case,
    val headline: String = title,
    val stamp: String? = null,
    val facts: List<Pair<String, String>> = emptyList(),
)

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
    ),
)
