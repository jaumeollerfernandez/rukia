package com.rukia.police

import com.rukia.police.application.GetSearches
import com.rukia.police.application.SearchZone
import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Search
import com.rukia.police.domain.model.Spot
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.domain.port.SearchLog
import com.rukia.police.infrastructure.persistence.JsonCaseFileRepository
import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SolveCaseTest {
    private val target = Spot(42.2155, 2.4260)
    private val question = CaseQuestion("Where?", target, station = Spot(42.179, 2.493), tolerance = 500.0)
    private val caseFile = object : CaseFileRepository { override fun question() = question }
    private val day = 86_400_000L
    private var now = 0L
    private val clock = object : Clock() {
        override fun getZone() = ZoneOffset.UTC
        override fun withZone(zone: java.time.ZoneId) = this
        override fun instant(): Instant = Instant.ofEpochMilli(now)
    }
    private val log = object : SearchLog {
        val list = mutableListOf<Search>()
        override fun all() = list.toList()
        override fun add(search: Search) { list += search }
        override fun clear() = list.clear()
    }
    private val dayOf = { millis: Long -> (millis / day).toInt() + 1 }
    private val board = GetSearches(log, dayOf, deadline = 3 * day, clock)
    private val search = SearchZone(caseFile, log, board, dayOf, clock)

    @Test fun `accuracy is zero outside, grows as the zone shrinks, and is 100 at the tolerance`() {
        assertEquals(0, question.accuracy(Spot(41.38, 2.17), 5_000.0))
        assertEquals(10, question.accuracy(target, 5_000.0))
        assertEquals(100, question.accuracy(Spot(42.2160, 2.4260), 500.0), "55 m off still counts")
    }

    @Test fun `one search a day, its result only after 15 minutes, 100 solves`() {
        assertEquals(10, search(target, 5_000.0).accuracy)
        assertFalse(board().canSearch, "one a day")
        assertFailsWith<IllegalArgumentException> { search(target, 5_000.0) }
        now = day
        assertTrue(board().canSearch, "the next day")
        search(target, 500.0)
        now += Search.DURATION - 1
        assertFalse(board().solved, "still searching")
        now += 1
        assertTrue(board().solved)
        now = 2 * day
        assertFalse(board().canSearch, "nothing after it's solved")
    }

    @Test fun `no squad past the deadline, nor a zone under the tolerance`() {
        assertFailsWith<IllegalArgumentException> { search(target, 100.0) }
        now = 3 * day
        assertFalse(board().canSearch)
    }

    @Test fun `every case's question file loads`() {
        for (case in File("src/main/assets/cases").listFiles()!!) JsonCaseFileRepository { File(case, it).readText() }.question()
    }
}
