package com.rukia.game.infrastructure

import com.rukia.game.domain.model.CaseKind
import com.rukia.game.domain.model.GameCase
import com.rukia.phone.domain.model.DEBUG_CASE_PREFIX

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
