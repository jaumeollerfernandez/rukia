package com.rukia.game.domain.port

import com.rukia.game.domain.model.CaseRecord
import com.rukia.game.domain.model.GameCase
import com.rukia.game.domain.model.StoryVariables

/** Every case the game has, in the order the case list shows them. */
fun interface CaseCatalog {
    fun cases(): List<GameCase>
}

/** The case being played: the game opens it on launch until it gets its verdict. */
interface CurrentCaseStore {
    fun get(): String?
    fun set(caseId: String?)
}

/** Whether a case's week has started (it was opened, and not reset since). */
fun interface CaseProgress {
    fun isStarted(caseId: String): Boolean
}

/** How a case's week went, read from its save. The game backs it with the chat app's story and the Police app's log. */
interface CaseOutcome {
    fun storyVariables(caseId: String): StoryVariables

    /** Police squads sent, and how many the case has. */
    fun squads(caseId: String): Pair<Int, Int>
}

/** What the player got out of each case, kept apart from the case's save. */
interface CaseRecords {
    fun get(caseId: String): CaseRecord?
    fun save(caseId: String, record: CaseRecord)
}

/** Each case's save (chats, clock, the apps' data), which the game wipes to play the case again. */
fun interface CaseSaves {
    fun wipe(caseId: String)
}

/** The interface language picked in Options: [available] maps each language tag to its name, in that language. */
interface LanguageSettings {
    val available: Map<String, String>
    fun get(): String?
    fun set(tag: String)
}
