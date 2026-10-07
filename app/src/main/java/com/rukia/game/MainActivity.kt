package com.rukia.game

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
            val askNotifications = rememberLauncherForActivityResult(RequestPermission()) {}
            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= 33 &&
                    checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                ) askNotifications.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            var caseId by rememberSaveable { mutableStateOf<String?>(null) }
            var menu by rememberSaveable { mutableStateOf(Menu.Main) }
            // A tapped notification goes straight into its case's phone.
            LaunchedEffect(AppLaunch.request) {
                AppLaunch.request?.caseId?.takeIf { requested -> cases.any { it.id == requested } }?.let { caseId = it }
            }
            AnimatedContent(caseId ?: menu, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "screen") { screen ->
                when (screen) {
                    Menu.Main -> MainMenu(onStart = { menu = Menu.Cases }, onOptions = { menu = Menu.Options })
                    Menu.Cases -> TitleScreen(cases, onBack = { menu = Menu.Main }, onPlay = { caseId = it.id })
                    Menu.Options -> OptionsScreen(onBack = { menu = Menu.Main })
                    else -> PhoneScreen(screen as String, onReturnToTitle = { caseId = null; menu = Menu.Main })
                }
            }
        }
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
