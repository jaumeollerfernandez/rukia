package com.rukia.game.application

import com.rukia.game.domain.model.CaseKind
import com.rukia.game.domain.model.CaseRecord
import com.rukia.game.domain.model.CaseReport
import com.rukia.game.domain.model.GameCase
import com.rukia.game.domain.port.CaseCatalog
import com.rukia.game.domain.port.CaseOutcome
import com.rukia.game.domain.port.CaseProgress
import com.rukia.game.domain.port.CaseRecords
import com.rukia.game.domain.port.CaseSaves
import com.rukia.game.domain.port.CurrentCaseStore
import com.rukia.game.domain.port.LanguageSettings

class ListCases(private val catalog: CaseCatalog) {
    operator fun invoke(): List<GameCase> = catalog.cases()
}

/** The case being played, if there is one and the game still has it. */
class GetCurrentCase(private val catalog: CaseCatalog, private val store: CurrentCaseStore) {
    operator fun invoke(): GameCase? = store.get()?.let { id -> catalog.cases().find { it.id == id } }
}

/** Starts playing [case]: from now on the game opens it on launch. A closed case starts over. */
class PlayCase(private val store: CurrentCaseStore, private val records: CaseRecords, private val reopen: ReopenCase) {
    operator fun invoke(case: GameCase) {
        if (records.get(case.id)?.closed == true) reopen(case.id)
        store.set(case.id)
    }
}

/**
 * The case's story reached an ending: the game no longer opens it on launch, and records how it went (the ending,
 * the achievements, the squads sent). Null if the story's `final_caso` isn't one of the case's endings.
 */
class CloseCase(
    private val catalog: CaseCatalog,
    private val store: CurrentCaseStore,
    private val outcome: CaseOutcome,
    private val records: CaseRecords,
) {
    operator fun invoke(caseId: String): CaseRecord? {
        store.set(null)
        val case = catalog.cases().find { it.id == caseId } ?: return null
        val story = outcome.storyVariables(caseId)
        val ending = case.endings.find { it.number == story[ENDING_VARIABLE] } ?: return null
        val run = case.achievements.filter { it.unlocked(story) }.map { it.id }.toSet()
        val (sent, squads) = outcome.squads(caseId)
        val before = records.get(caseId) ?: CaseRecord()
        return CaseRecord(ending.number, run, sent, squads, before.achievements + run, before.endings + ending.number)
            .also { records.save(caseId, it) }
    }

    companion object { const val ENDING_VARIABLE = "final_caso" }
}

/** A closed case's report. Null while the case is open. */
class GetCaseReport(private val catalog: CaseCatalog, private val records: CaseRecords) {
    operator fun invoke(caseId: String): CaseReport? {
        val record = records.get(caseId)?.takeIf { it.closed } ?: return null
        val case = catalog.cases().find { it.id == caseId } ?: return null
        return CaseReport(case, record).takeIf { case.endings.any { it.number == record.ending } }
    }
}

/** Opens a closed case again: its save is wiped and the week starts over. Achievements and endings found are kept. */
class ReopenCase(private val saves: CaseSaves, private val records: CaseRecords) {
    operator fun invoke(caseId: String) {
        saves.wipe(caseId)
        records.get(caseId)?.let { records.save(caseId, it.copy(ending = null, run = emptySet(), squadsSent = 0)) }
    }
}

/** How many real cases (not training nor debug) are under way: started and not closed. */
class CountActiveCases(private val catalog: CaseCatalog, private val progress: CaseProgress, private val records: CaseRecords) {
    operator fun invoke(): Int = catalog.cases().count { it.kind == CaseKind.Case && progress.isStarted(it.id) && records.get(it.id)?.closed != true }
}

/** The picked language; until the player picks one, the phone's own ([deviceLanguage]) if the game has it. */
class GetLanguage(private val settings: LanguageSettings) {
    operator fun invoke(deviceLanguage: String): String? =
        settings.get() ?: settings.available.keys.firstOrNull { it.startsWith(deviceLanguage) }
}

class ChooseLanguage(private val settings: LanguageSettings) {
    operator fun invoke(tag: String) {
        require(tag in settings.available) { "Unknown language $tag" }
        settings.set(tag)
    }
}
