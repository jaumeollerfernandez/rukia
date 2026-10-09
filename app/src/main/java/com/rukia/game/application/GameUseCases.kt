package com.rukia.game.application

import com.rukia.game.domain.model.CaseKind
import com.rukia.game.domain.model.GameCase
import com.rukia.game.domain.port.CaseCatalog
import com.rukia.game.domain.port.CaseProgress
import com.rukia.game.domain.port.CurrentCaseStore
import com.rukia.game.domain.port.LanguageSettings

class ListCases(private val catalog: CaseCatalog) {
    operator fun invoke(): List<GameCase> = catalog.cases()
}

/** The case being played, if there is one and the game still has it. */
class GetCurrentCase(private val catalog: CaseCatalog, private val store: CurrentCaseStore) {
    operator fun invoke(): GameCase? = store.get()?.let { id -> catalog.cases().find { it.id == id } }
}

/** Starts playing [case]: from now on the game opens it on launch. */
class PlayCase(private val store: CurrentCaseStore) {
    operator fun invoke(case: GameCase) = store.set(case.id)
}

/** The case got its verdict: the game no longer opens it on launch. */
class CloseCase(private val store: CurrentCaseStore) {
    operator fun invoke() = store.set(null)
}

/** How many real cases (not training nor debug) are under way. */
class CountActiveCases(private val catalog: CaseCatalog, private val progress: CaseProgress) {
    operator fun invoke(): Int = catalog.cases().count { it.kind == CaseKind.Case && progress.isStarted(it.id) }
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
