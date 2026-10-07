package com.rukia.police

import com.rukia.police.application.DispatchSquad
import com.rukia.police.application.GetOperations
import com.rukia.police.domain.model.Dispatch
import com.rukia.police.domain.model.Operation
import com.rukia.police.domain.model.Operations
import com.rukia.police.domain.model.Squad
import com.rukia.police.domain.port.DispatchLog
import com.rukia.police.domain.port.OperationsRepository
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class OperationsTest {
    private val ops = Operations(
        squads = listOf(Squad("day", "Two officers", "D6 08:00", "D6 12:00"), Squad("night", "Last car", "D6 22:00", "D7 02:00")),
        operations = listOf(Operation("farm", "The farm", "envio_mas"), Operation("watch", "Watch the crater", "envio_vigilancia", squads = listOf("night"))),
    )
    private val repo = object : OperationsRepository { override fun operations() = ops }
    private val log = object : DispatchLog {
        val sent = mutableListOf<Dispatch>()
        override fun all() = sent.toList()
        override fun add(dispatch: Dispatch) { sent += dispatch }
        override fun clear() = sent.clear()
    }
    private val times = mapOf("D6 08:00" to 800L, "D6 12:00" to 1200L, "D6 22:00" to 2200L, "D7 02:00" to 2600L)
    private fun at(millis: Long): Clock = Clock.fixed(Instant.ofEpochMilli(millis), ZoneOffset.UTC)

    @Test fun `a squad can go out once, while its time range is open, only where it's allowed`() {
        val reports = mutableListOf<Pair<String, String>>()
        assertNull(GetOperations(repo, log, times::get, at(700))().squad, "too early")

        val morning = GetOperations(repo, log, times::get, at(900))()
        assertEquals("day", morning.squad?.id)
        assertEquals(listOf("farm"), morning.operations.map { it.id }, "the crater watch is a night job")
        assertFailsWith<IllegalArgumentException> { DispatchSquad(repo, log, { c, k -> reports += c to k }, times::get, at(900))("watch") }

        DispatchSquad(repo, log, { c, k -> reports += c to k }, times::get, at(900))("farm")
        assertEquals(listOf("central" to "envio_mas"), reports)
        assertNull(GetOperations(repo, log, times::get, at(1000))().squad, "the morning squad is gone")
        assertFailsWith<IllegalArgumentException> { DispatchSquad(repo, log, { _, _ -> }, times::get, at(1000))("farm") }

        val night = GetOperations(repo, log, times::get, at(2300))()
        assertEquals("night", night.squad?.id)
        assertEquals(listOf("farm", "watch"), night.operations.map { it.id })
        assertEquals(listOf(Dispatch("farm", "day", 900)), night.dispatched)
    }
}
