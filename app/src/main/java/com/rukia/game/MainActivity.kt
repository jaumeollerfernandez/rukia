package com.rukia.game

import android.Manifest
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
import com.rukia.phone.PhoneScreen

/** The game: title screen, or the phone of the case being played. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            // A tapped notification goes straight into its case's phone.
            LaunchedEffect(AppLaunch.request) {
                AppLaunch.request?.caseId?.takeIf { requested -> cases.any { it.id == requested } }?.let { caseId = it }
            }
            AnimatedContent(caseId, transitionSpec = { fadeIn() togetherWith fadeOut() }, label = "screen") { id ->
                if (id == null) TitleScreen(cases, onPlay = { caseId = it.id })
                else PhoneScreen(id, onReturnToTitle = { caseId = null })
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
