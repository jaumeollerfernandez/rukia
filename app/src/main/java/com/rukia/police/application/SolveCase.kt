package com.rukia.police.application

import com.rukia.police.domain.model.SearchBoard
import com.rukia.police.domain.model.Search
import com.rukia.police.domain.model.Spot
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.domain.port.SearchLog
import java.time.Clock

/**
 * The searches and whether a squad can go out now: one a case day, none while another is under way, none past the
 * deadline or once solved. A search scoring 100% solves the case when its result comes in. [dayOf] gives the case day.
 */
class GetSearches(
    private val log: SearchLog,
    private val dayOf: (Long) -> Int,
    private val deadline: Long?,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(): SearchBoard {
        val now = clock.millis()
        val all = log.all()
        val today = dayOf(now)
        val solved = all.any { it.accuracy == 100 && now >= it.readyAt }
        val busy = all.any { now < it.readyAt || it.day == today }
        val open = deadline == null || now < deadline
        return SearchBoard(all, today, deadline?.let(dayOf), !solved && !busy && open, solved)
    }
}

/** Sends today's squad to search the circle at [center] of [radius] meters. Its result is told [Search.DURATION] later. */
class SearchZone(
    private val caseFile: CaseFileRepository,
    private val log: SearchLog,
    private val board: GetSearches,
    private val dayOf: (Long) -> Int,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(center: Spot, radius: Double): Search {
        require(board().canSearch) { "No squad can go out now" }
        require(radius >= caseFile.question().tolerance) { "Zone smaller than the tolerance" }
        val now = clock.millis()
        return Search(dayOf(now), center, radius, now, caseFile.question().accuracy(center, radius)).also(log::add)
    }
}
