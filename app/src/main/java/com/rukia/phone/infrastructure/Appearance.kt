package com.rukia.phone.infrastructure

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Light or dark for the remote phone's apps, picked in the game's Options. Police, Gonpi and the game's own screens
 * are always dark.
 */
object Appearance {
    private fun prefs(context: Context) = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun isDark(context: Context) = prefs(context).getBoolean("dark_mode", false)

    fun setDark(context: Context, dark: Boolean) = prefs(context).edit().putBoolean("dark_mode", dark).apply()
}

/** Whether the phone's apps are in dark mode; the phone provides it from [Appearance]. */
val LocalDarkMode = staticCompositionLocalOf { false }
