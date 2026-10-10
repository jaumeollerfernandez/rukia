package com.rukia.game.infrastructure

import android.content.Context
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.game.application.ChooseLanguage
import com.rukia.game.application.CloseCase
import com.rukia.game.application.CountActiveCases
import com.rukia.game.application.GetCaseReport
import com.rukia.game.application.GetCurrentCase
import com.rukia.game.application.GetLanguage
import com.rukia.game.application.ListCases
import com.rukia.game.application.PlayCase
import com.rukia.game.application.ReopenCase
import com.rukia.game.domain.model.StoryVariables
import com.rukia.game.domain.port.CaseCatalog
import com.rukia.game.domain.port.CaseOutcome
import com.rukia.game.domain.port.CaseProgress
import com.rukia.game.domain.port.CaseSaves
import com.rukia.game.domain.port.LanguageSettings
import com.rukia.game.infrastructure.persistence.PrefsCaseRecords
import com.rukia.game.infrastructure.persistence.PrefsCurrentCaseStore
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.CaseFolders
import com.rukia.phone.infrastructure.Language
import com.rukia.police.domain.port.Radio
import com.rukia.police.domain.port.SaveFile
import com.rukia.police.infrastructure.PoliceModule
import java.io.File

/** Composition root of the game around the cases: the menus, the case list, the closing report and the options. */
class GameModule(context: Context) {
    private val catalog = CaseCatalog { cases }
    private val current = PrefsCurrentCaseStore(context)
    private val records = PrefsCaseRecords(context)
    private val progress = CaseProgress { CaseClock.isStarted(context, it) }
    // How the week went lives in the chat app's story and the Police app's log.
    private val outcome = object : CaseOutcome {
        override fun storyVariables(caseId: String) = StoryVariables { ChatModule.of(context, caseId).getStoryVariable(it) }
        override fun squads(caseId: String): Pair<Int, Int> = runCatching {
            val police = PoliceModule(context, caseId, save = SaveFile {}, radio = Radio { _, _ -> })
            police.getOperations().dispatched.size to police.squadCount
        }.getOrDefault(0 to 0) // a case without a Police question
    }
    // Like the Police app's "Reset chats", plus the other apps' saves. The home screen layout stays.
    private val saves = CaseSaves { caseId ->
        ChatModule.of(context, caseId).resetProgress()
        CaseClock.reset(context, caseId)
        File(CaseFolders.saves(context, caseId, "phone"), "effects-played.txt").delete()
        listOf("police", "gonpi").forEach { CaseFolders.saves(context, caseId, it).deleteRecursively() }
    }
    private val language = object : LanguageSettings {
        override val available = Language.available
        override fun get() = Language.get(context)
        override fun set(tag: String) = Language.set(context, tag)
    }

    val listCases = ListCases(catalog)
    val getCurrentCase = GetCurrentCase(catalog, current)
    val reopenCase = ReopenCase(saves, records)
    val playCase = PlayCase(current, records, reopenCase)
    val closeCase = CloseCase(catalog, current, outcome, records)
    val getCaseReport = GetCaseReport(catalog, records)
    val countActiveCases = CountActiveCases(catalog, progress, records)
    val languages: Map<String, String> = language.available
    val getLanguage = GetLanguage(language)
    val chooseLanguage = ChooseLanguage(language)
}
