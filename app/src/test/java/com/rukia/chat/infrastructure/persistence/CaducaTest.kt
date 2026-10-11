package com.rukia.chat.infrastructure.persistence

import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals

/** `caduca(h)` in RUK-93429049's main.ink: a contact's charla expires at hour `h` today, or at midnight with `h` 0. */
class CaducaTest {
    private val zone = ZoneOffset.UTC
    private val start = LocalDate.of(2026, 1, 1).atStartOfDay(zone).toInstant().toEpochMilli()
    private val function = File("src/main/assets/cases/ruk-93429049/story/main.ink").readText().substringAfter("=== function caduca")

    private fun expiresAt(h: Int): Long? {
        val src = "VAR dia = 0\nVAR hora = 0\n-> DONE\n=== pep ===\nHola. #caduca: {caduca($h)}\n* [a]\n* [(sin responder)]\n- -> DONE\n=== function caduca$function"
        val clock = Clock.fixed(Instant.ofEpochMilli(start + 2 * 86_400_000L + 3_600_000L), zone) // D3 01:00
        return InkStoryEngine({ src }, createTempDirectory().resolve("s.json").toFile(), caseStart = { start }, clock = clock).advance("pep").expiresAt
    }

    @Test fun `expires at the hour the contact writes, or at midnight`() {
        assertEquals(start + 2 * 86_400_000L + 9 * 3_600_000L, expiresAt(9), "D3 09:00")
        assertEquals(start + 2 * 86_400_000L + 17 * 3_600_000L, expiresAt(17), "D3 17:00")
        assertEquals(start + 3 * 86_400_000L, expiresAt(0), "D4 00:00")
    }
}
