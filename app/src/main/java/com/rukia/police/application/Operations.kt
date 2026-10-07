package com.rukia.police.application

import com.rukia.police.domain.model.Dispatch
import com.rukia.police.domain.model.OperationsBoard
import com.rukia.police.domain.model.Squad
import com.rukia.police.domain.port.DispatchLog
import com.rukia.police.domain.port.OperationsRepository
import com.rukia.police.domain.port.Radio
import java.time.Clock

/** The squad that can be sent right now: its time range is open and it hasn't gone out yet. */
private fun openSquad(repo: OperationsRepository, log: DispatchLog, now: Long, timeOf: (String) -> Long?): Squad? {
    val used = log.all().map { it.squad }.toSet()
    return repo.operations().squads.firstOrNull { s ->
        s.id !in used && (timeOf(s.from) ?: Long.MAX_VALUE) <= now && now < (timeOf(s.until) ?: 0)
    }
}

/** [timeOf] turns a case time ("D6 08:00") into epoch millis. */
class GetOperations(
    private val repo: OperationsRepository,
    private val log: DispatchLog,
    private val timeOf: (String) -> Long?,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(): OperationsBoard {
        val squad = openSquad(repo, log, clock.millis(), timeOf)
        val operations = squad?.let { s -> repo.operations().operations.filter { it.allows(s) } }.orEmpty()
        return OperationsBoard(squad, squad?.let { timeOf(it.until) }, operations, log.all())
    }
}

/** Sends the open squad to [operationId]: it's logged and the officers' report starts playing in the story. */
class DispatchSquad(
    private val repo: OperationsRepository,
    private val log: DispatchLog,
    private val radio: Radio,
    private val timeOf: (String) -> Long?,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(operationId: String) {
        val now = clock.millis()
        val squad = requireNotNull(openSquad(repo, log, now, timeOf)) { "No squad available now" }
        val operation = requireNotNull(repo.operations().operations.find { it.id == operationId }) { "Unknown operation $operationId" }
        require(operation.allows(squad)) { "${squad.id} can't go to $operationId" }
        log.add(Dispatch(operation.id, squad.id, now))
        radio.report(repo.operations().channel, operation.knot)
    }
}
