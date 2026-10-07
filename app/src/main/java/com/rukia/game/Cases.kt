package com.rukia.game

import com.rukia.phone.DEBUG_CASE_PREFIX

/**
 * A playable case. Its content lives in assets/cases/<id>/ (characters.json, chats/, story/, police/case.json, media/)
 * and its progress in files/cases/<id>/, so cases never share chats or saves. Add a case here and give it a folder.
 */
class GameCase(val id: String, val number: Int, val title: String, val summary: String)

val cases = listOf(
    // Every feature in one place, to try things out: choices, calls with audio, delayed messages and notifications,
    // a group that appears a minute in, contacts and the police question.
    GameCase("test-case", 0, "Test Case", "Every feature of the phone in one place. Rukia calls, Jaume makes you wait, and a group appears after a minute."),
    // Una semana en tiempo real: el jugador es un hacker con el móvil de Alicia y tiene que encontrarla antes del séptimo amanecer.
    GameCase(
        "ruk-93429049", 1, "RUK-93429049",
        "Desaparición de A. S. V., mujer, 18 años, vecina de L'Hostalnou de Bianya (Garrotxa). Vista por última vez hace dos noches. " +
            "Dejó el terminal móvil en el domicilio. La denunciante, la madre, solicita máxima discreción. Sin indicios de violencia hasta la fecha. Prioridad: alta.",
    ),
    // Simulador para probar los .ink: el mismo caso con su propia partida y una barra arriba para adelantar el tiempo.
    GameCase(
        "${DEBUG_CASE_PREFIX}ruk-93429049", 1, "DEBUG:RUK-93429049",
        "Simulador de RUK-93429049. Misma historia y chats, partida aparte, sin notificaciones. La barra de arriba adelanta la hora y los días.",
    ),
)