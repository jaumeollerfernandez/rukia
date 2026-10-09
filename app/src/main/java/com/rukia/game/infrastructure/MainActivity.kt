package com.rukia.game.infrastructure

import com.rukia.game.infrastructure.ui.MainMenu
import com.rukia.game.infrastructure.ui.OptionsScreen
import com.rukia.game.infrastructure.ui.TitleScreen
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
import com.rukia.phone.infrastructure.AppLaunch
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.Language
import com.rukia.phone.infrastructure.ui.PhoneScreen

/** Screens before a case is open. */
private enum class Menu { Main, Cases, Options }

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
            val game = remember { GameModule(applicationContext) }
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
            var caseId by rememberSaveable { mutableStateOf(game.getCurrentCase()?.id) }
            var menu by rememberSaveable { mutableStateOf(Menu.Main) }
            // A tapped notification goes straight into its case's phone.
            LaunchedEffect(AppLaunch.request) {
                AppLaunch.request?.caseId?.takeIf { requested -> game.listCases().any { it.id == requested } }?.let { caseId = it }
            }
            AnimatedContent(caseId ?: menu, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "screen") { screen ->
                when (screen) {
                    Menu.Main -> MainMenu(onStart = { menu = Menu.Cases }, onOptions = { menu = Menu.Options })
                    Menu.Cases -> TitleScreen(game.listCases(), game.countActiveCases(), onBack = { menu = Menu.Main }, onPlay = {
                        caseId = it.id
                        game.playCase(it)
                    })
                    Menu.Options -> OptionsScreen(game, onBack = { menu = Menu.Main })
                    else -> PhoneScreen(
                        screen as String,
                        onReturnToTitle = { caseId = null; menu = Menu.Main },
                        onCaseOver = { game.closeCase() },
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
