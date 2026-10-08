package com.rukia.game

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.rukia.phone.AppLaunch
import com.rukia.phone.CaseClock
import com.rukia.phone.Language
import com.rukia.phone.PhoneScreen

/** Screens before a case is open. */
private enum class Menu { Main, Cases, Options }

/** Settings key of the case being played: the game opens it on launch until the case gets its verdict. */
private const val CURRENT_CASE = "current_case"
private const val ASKED_EXACT_ALARMS = "asked_exact_alarms"

/** The game: the main menu (Start, Options, Exit), the case list, or the phone of the case being played. */
class MainActivity : ComponentActivity() {
    // The whole interface uses the language picked in Options.
    override fun attachBaseContext(newBase: Context) = super.attachBaseContext(Language.wrap(newBase))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        CaseClock.init(this)
        AppLaunch.handle(intent)
        setContent {
            // Story messages arrive as notifications, which need permission on Android 13+.
            // Then, once, exact alarms so they arrive on time: Android 14+ turns them off by default.
            val askNotifications = rememberLauncherForActivityResult(RequestPermission()) { askExactAlarmsOnce() }
            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= 33 &&
                    checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                ) askNotifications.launch(Manifest.permission.POST_NOTIFICATIONS)
                else askExactAlarmsOnce()
            }

            // The case being played opens straight away on launch, until it gets its verdict.
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            var caseId by rememberSaveable { mutableStateOf(prefs.getString(CURRENT_CASE, null)?.takeIf { id -> cases.any { it.id == id } }) }
            var menu by rememberSaveable { mutableStateOf(Menu.Main) }
            // A tapped notification goes straight into its case's phone.
            LaunchedEffect(AppLaunch.request) {
                AppLaunch.request?.caseId?.takeIf { requested -> cases.any { it.id == requested } }?.let { caseId = it }
            }
            AnimatedContent(caseId ?: menu, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "screen") { screen ->
                when (screen) {
                    Menu.Main -> MainMenu(onStart = { menu = Menu.Cases }, onOptions = { menu = Menu.Options })
                    Menu.Cases -> TitleScreen(cases, onBack = { menu = Menu.Main }, onPlay = {
                        caseId = it.id
                        prefs.edit().putString(CURRENT_CASE, it.id).apply()
                    })
                    Menu.Options -> OptionsScreen(onBack = { menu = Menu.Main })
                    else -> PhoneScreen(
                        screen as String,
                        onReturnToTitle = { caseId = null; menu = Menu.Main },
                        onCaseOver = { prefs.edit().remove(CURRENT_CASE).apply() },
                    )
                }
            }
        }
    }

    private fun askExactAlarmsOnce() {
        if (Build.VERSION.SDK_INT < 31 || getSystemService(AlarmManager::class.java).canScheduleExactAlarms()) return
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        if (prefs.getBoolean(ASKED_EXACT_ALARMS, false)) return
        prefs.edit().putBoolean(ASKED_EXACT_ALARMS, true).apply()
        startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:$packageName")))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        AppLaunch.handle(intent)
    }

    override fun onStart() {
        super.onStart()
        AppLaunch.foreground = true
    }

    override fun onStop() {
        AppLaunch.foreground = false
        super.onStop()
    }
}
