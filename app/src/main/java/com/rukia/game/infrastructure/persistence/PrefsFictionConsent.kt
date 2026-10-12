package com.rukia.game.infrastructure.persistence

import android.content.Context
import com.rukia.game.domain.port.FictionConsent

/** The fiction notice's consent, in the game's settings. */
class PrefsFictionConsent(context: Context) : FictionConsent {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun given() = prefs.getBoolean(KEY, false)

    override fun give() = prefs.edit().putBoolean(KEY, true).apply()

    private companion object { const val KEY = "fiction_consent" }
}
