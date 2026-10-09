package com.rukia.phone.domain.model

import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CaseTimeTest {
    private val zone = ZoneOffset.UTC
    private val start = LocalDateTime.of(2026, 3, 10, 0, 0).toInstant(zone).toEpochMilli()

    @Test fun `case times count days from D1 and D0 is the day before`() {
        assertEquals(LocalDateTime.of(2026, 3, 12, 22, 15).toInstant(zone).toEpochMilli(), caseTime("D3 22:15", start, zone))
        assertEquals(LocalDateTime.of(2026, 3, 8, 7, 5).toInstant(zone).toEpochMilli(), caseTime("D-1 7:05", start, zone))
        assertNull(caseTime("tomorrow", start, zone))
        assertEquals(1, caseDay(start + 23 * 3_600_000L, start, zone))
        assertEquals(7, caseDay(caseTime("D7 06:30", start, zone)!!, start, zone))
    }

    @Test fun `every #at and #caduca in the stories is a valid case time`() {
        // A time with {...} is written while playing (`#caduca: D{dia + 1} 00:00`, `#caduca: {limite}`); the times
        // passed to it as arguments (`-> charla("D2 10:30") ->`) are checked instead.
        val tag = Regex("""#(at|caduca):\s*([^#\n]+)|-> \w+\("([^"]+)"\)""")
        val bad = File("src/main/assets/cases").walk().filter { it.extension == "ink" }.flatMap { file ->
            file.readLines().filterNot { it.trim().startsWith("//") }
                .flatMap { line -> tag.findAll(line).map { (it.groups[2] ?: it.groups[3])!!.value.trim() } }
                .filter { '{' !in it && caseTime(it, start, zone) == null }.map { "${file.name}: $it" }
        }.toList()
        assertTrue(bad.isEmpty(), "unreadable times: $bad")
    }
}
