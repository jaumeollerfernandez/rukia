package com.rukia.phone

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

/** The interface language picked in Options. Until the player picks one, the phone's own is used. Case content isn't translated. */
object Language {
    /** Language tag to its name, written in that language. */
    val available = linkedMapOf("en" to "English", "es-ES" to "Español (España)")

    private fun prefs(context: Context) = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun get(context: Context): String? = prefs(context).getString("language", null)

    fun set(context: Context, tag: String) = prefs(context).edit().putString("language", tag).apply()

    /** [context] with the picked language applied; activities wrap their base context with it, notifications their texts. */
    fun wrap(context: Context): Context {
        val tag = get(context) ?: return context
        val locale = Locale.forLanguageTag(tag)
        Locale.setDefault(locale) // dates and weekdays follow too
        val config = Configuration(context.resources.configuration).apply { setLocale(locale) }
        return context.createConfigurationContext(config)
    }
}
