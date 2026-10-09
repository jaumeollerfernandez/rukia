package com.rukia.game.domain.port

import com.rukia.game.domain.model.GameCase

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

/** The interface language picked in Options: [available] maps each language tag to its name, in that language. */
interface LanguageSettings {
    val available: Map<String, String>
    fun get(): String?
    fun set(tag: String)
}
