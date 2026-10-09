package com.rukia.game.infrastructure.persistence

import android.content.Context
import com.rukia.game.domain.port.CurrentCaseStore

/** The case being played, in the game's settings. */
class PrefsCurrentCaseStore(context: Context) : CurrentCaseStore {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun get(): String? = prefs.getString(KEY, null)

    override fun set(caseId: String?) = prefs.edit().apply { if (caseId == null) remove(KEY) else putString(KEY, caseId) }.apply()

    private companion object { const val KEY = "current_case" }
}
