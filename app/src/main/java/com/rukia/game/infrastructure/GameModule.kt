package com.rukia.game.infrastructure

import android.content.Context
import com.rukia.game.application.ChooseLanguage
import com.rukia.game.application.CloseCase
import com.rukia.game.application.CountActiveCases
import com.rukia.game.application.GetCurrentCase
import com.rukia.game.application.GetLanguage
import com.rukia.game.application.ListCases
import com.rukia.game.application.PlayCase
import com.rukia.game.domain.port.CaseCatalog
import com.rukia.game.domain.port.CaseProgress
import com.rukia.game.domain.port.LanguageSettings
import com.rukia.game.infrastructure.persistence.PrefsCurrentCaseStore
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.Language

/** Composition root of the game around the cases: the menus, the case list and the options. */
class GameModule(context: Context) {
    private val catalog = CaseCatalog { cases }
    private val current = PrefsCurrentCaseStore(context)
    private val progress = CaseProgress { CaseClock.isStarted(context, it) }
    private val language = object : LanguageSettings {
        override val available = Language.available
        override fun get() = Language.get(context)
        override fun set(tag: String) = Language.set(context, tag)
    }

    val listCases = ListCases(catalog)
    val getCurrentCase = GetCurrentCase(catalog, current)
    val playCase = PlayCase(current)
    val closeCase = CloseCase(current)
    val countActiveCases = CountActiveCases(catalog, progress)
    val languages: Map<String, String> = language.available
    val getLanguage = GetLanguage(language)
    val chooseLanguage = ChooseLanguage(language)
}
