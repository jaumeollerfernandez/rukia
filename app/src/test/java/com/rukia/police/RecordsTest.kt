package com.rukia.police

import com.rukia.police.application.GetRecords
import com.rukia.police.application.ListMugshots
import com.rukia.police.application.ReadRecord
import com.rukia.police.application.RequestRecord
import com.rukia.police.domain.model.PoliceRecord
import com.rukia.police.domain.model.PoliceRecords
import com.rukia.police.domain.model.RecordRequest
import com.rukia.police.domain.model.RecordStatus
import com.rukia.police.domain.port.RecordLog
import com.rukia.police.domain.port.RecordsRepository
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RecordsTest {
    private val hour = 3_600_000L
    private val day = 24 * hour
    private val repo = object : RecordsRepository {
        override fun records() = PoliceRecords(
            hours = listOf(12, 10, 8, 6, 4, 2, 1),
            records = listOf(PoliceRecord("dani", "Daniel Rius", "", "", mugshot = "OLOT · 1"), PoliceRecord("berta", "Berta", "", "")),
        )
    }
    private val log = object : RecordLog {
        var all = listOf<RecordRequest>()
        override fun all() = all
        override fun save(requests: List<RecordRequest>) { all = requests }
        override fun clear() { all = emptyList() }
    }
    private var now = 0L
    private val clock = object : Clock() {
        override fun getZone() = ZoneOffset.UTC
        override fun withZone(zone: java.time.ZoneId?) = this
        override fun instant(): Instant = Instant.ofEpochMilli(now)
    }
    private val dayOf = { millis: Long -> (millis / day).toInt() + 1 }
    private val flags = mutableListOf<String>()
    private val board = GetRecords(repo, log, dayOf, clock)
    private val request = RequestRecord(repo, log, board, clock)
    private val read = ReadRecord(repo, log, { flags += it }, { null }, { "D?" }, clock)

    @Test fun `paperwork takes hours, fewer later in the week, one record at a time`() {
        now = 10 * hour // D1
        assertEquals(12, board().hoursNow)
        assertEquals(10 * hour + 12 * hour, request("dani").readyAt)
        assertFailsWith<IllegalArgumentException>("only one at a time") { request("berta") }
        assertFailsWith<IllegalArgumentException>("not in yet") { read("dani") }
        assertEquals(RecordStatus.Pending, board().rows.first().status)

        now = 22 * hour
        assertEquals(RecordStatus.Ready, board().rows.first().status)
        assertTrue(ListMugshots(repo, log)().isEmpty(), "no police photo until it's read")
        read("dani")
        assertEquals(listOf("ficha_dani"), flags)
        assertEquals(RecordStatus.Read, board().rows.first().status)
        assertEquals(listOf(false, true), ListMugshots(repo, log)().map { it.profile }, "front and profile in the gallery")

        now = 4 * day + 10 * hour // D5
        assertEquals(4 * hour, request("berta").readyAt - now)
    }

    @Test fun `the case's records file loads and every link points to a record`() {
        val records = Json { ignoreUnknownKeys = true }.decodeFromString<PoliceRecords>(File("src/main/assets/cases/ruk-93429049/police/records.json").readText())
        val ids = records.records.map { it.id }.toSet()
        for (r in records.records) for (s in r.sections) for (l in s.links) l.record?.let { assertTrue(it in ids, "${r.id} links to unknown record $it") }
        assertTrue(records.hoursOn(1) > records.hoursOn(5), "D1 slower than D5")
        // Every contact in the agenda has a record (Mamá's is Montse's).
        val contacts = Regex(""""id":\s*"(\w+)"[^}]*?"hidden":\s*true""").findAll(File("src/main/assets/cases/ruk-93429049/characters.json").readText()).map { it.groupValues[1] }.toSet()
        val agenda = Regex(""""id":\s*"(\w+)"""").findAll(File("src/main/assets/cases/ruk-93429049/characters.json").readText()).map { it.groupValues[1] }.toSet() - contacts
        assertTrue(agenda.size > 40 && "iris" !in agenda, "agenda read wrong: $agenda")
        for (id in agenda) assertTrue((if (id == "mama") "montse" else id) in ids, "contact $id has no police record")
    }

    @Test fun `a vehicle only shows up once a record or the story gives it away`() {
        val withCar = object : RecordsRepository {
            override fun records() = PoliceRecords(records = listOf(
                PoliceRecord("ignasi", "Ignasi", "", ""),
                PoliceRecord("seat", "Seat gris", "", "", vehicle = true, unlockedBy = listOf("ficha:ignasi", "sabe_seat")),
            ))
        }
        var plate = false
        val cars = GetRecords(withCar, log, dayOf, clock) { it == "sabe_seat" && plate }
        assertEquals(listOf("ignasi"), cars().rows.map { it.record.id }, "hidden at first")
        assertFailsWith<IllegalArgumentException>("can't be asked for while hidden") { RequestRecord(withCar, log, cars, clock)("seat") }
        plate = true
        assertEquals(listOf("ignasi", "seat"), cars().rows.map { it.record.id }, "a conversation gave the plate")
        plate = false
        log.all = listOf(RecordRequest("ignasi", 0, 0, read = true))
        assertEquals(listOf("ignasi", "seat"), cars().rows.map { it.record.id }, "its driver's record mentions it")
    }
}
