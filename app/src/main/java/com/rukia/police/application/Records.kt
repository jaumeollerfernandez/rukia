package com.rukia.police.application

import com.rukia.police.domain.model.Mugshot
import com.rukia.police.domain.model.OpenedRecord
import com.rukia.police.domain.model.PoliceRecord
import com.rukia.police.domain.model.RecordRequest
import com.rukia.police.domain.model.RecordRow
import com.rukia.police.domain.model.RecordStatus
import com.rukia.police.domain.model.RecordsBoard
import com.rukia.police.domain.port.RecordLog
import com.rukia.police.domain.port.RecordsRepository
import com.rukia.police.domain.port.StoryFlags
import com.rukia.police.domain.port.StoryFacts
import java.time.Clock

private const val HOUR = 3_600_000L

/** An unlock condition met once that record was read: "ficha:ignasi". */
private const val READ = "ficha:"

/**
 * Every record the player knows of, and where its paperwork is. A record with [PoliceRecord.unlockedBy] (a vehicle,
 * say) only shows up once the player read the record that mentions it, or the story gave enough to look it up ([facts]).
 * [dayOf] gives the case day of a time.
 */
class GetRecords(
    private val repo: RecordsRepository,
    private val log: RecordLog,
    private val dayOf: (Long) -> Int,
    private val clock: Clock = Clock.systemDefaultZone(),
    private val facts: StoryFacts = StoryFacts { false },
) {
    operator fun invoke(): RecordsBoard {
        val now = clock.millis()
        val requests = log.all().associateBy { it.record }
        val read = requests.values.filter { it.read }.map { it.record }.toSet()
        val known = { record: PoliceRecord ->
            record.unlockedBy.isEmpty() || record.id in requests || record.unlockedBy.any { condition ->
                if (condition.startsWith(READ)) condition.removePrefix(READ) in read else facts.isTrue(condition)
            }
        }
        val rows = repo.records().records.filter(known).map { record ->
            val request = requests[record.id]
            val status = when {
                request == null -> RecordStatus.Available
                now < request.readyAt -> RecordStatus.Pending
                request.read -> RecordStatus.Read
                else -> RecordStatus.Ready
            }
            RecordRow(record, status, request)
        }
        return RecordsBoard(rows, rows.firstOrNull { it.status == RecordStatus.Pending }?.request, repo.records().hoursOn(dayOf(now)))
    }
}

/** Asks the station for a record. The paperwork takes some hours, fewer as the week goes on, and only one goes through at a time. */
class RequestRecord(
    private val repo: RecordsRepository,
    private val log: RecordLog,
    private val board: GetRecords,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(recordId: String): RecordRequest {
        val now = clock.millis()
        val current = board()
        require(current.canRequest) { "Another record is on its way" }
        require(current.rows.any { it.record.id == recordId && it.status == RecordStatus.Available }) { "Record $recordId can't be requested" }
        return RecordRequest(recordId, now, now + current.hoursNow * HOUR).also { log.save(log.all() + it) }
    }
}

/**
 * Opens a record that has come in: it's marked as read and the story learns it (`ficha_<id>`), which can open new
 * conversations. [timeOf] turns a case time into epoch millis, [labelOf] epoch millis into a case time ("D3 18:40").
 */
class ReadRecord(
    private val repo: RecordsRepository,
    private val log: RecordLog,
    private val flags: StoryFlags,
    private val timeOf: (String) -> Long?,
    private val labelOf: (Long) -> String,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(recordId: String): OpenedRecord {
        val now = clock.millis()
        val record: PoliceRecord = requireNotNull(repo.records().records.find { it.id == recordId }) { "Unknown record $recordId" }
        val request = requireNotNull(log.all().find { it.record == recordId && now >= it.readyAt }) { "Record $recordId hasn't come in" }
        if (!request.read) log.save(log.all().map { if (it.record == recordId) it.copy(read = true) else it })
        flags.set(record.flag)
        return OpenedRecord(record, labelOf(request.readyAt), record.missingSince?.let(timeOf)?.let { now - it })
    }
}

/** Police photos (front and profile) of everyone arrested whose record the player has read, for the phone's gallery. */
class ListMugshots(private val repo: RecordsRepository, private val log: RecordLog) {
    operator fun invoke(): List<Mugshot> {
        val read = log.all().filter { it.read }.map { it.record }.toSet()
        return repo.records().records.filter { it.id in read && it.mugshot != null }.flatMap { r ->
            listOf(false, true).map { profile -> Mugshot(r.id, r.mugshot!!, "$FOLDER/${r.id}_${if (profile) "perfil" else "frontal"}.jpg", profile) }
        }
    }

    companion object { const val FOLDER = "police/resenas" }
}
