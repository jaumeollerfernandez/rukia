package com.rukia.phone

import android.graphics.Color.TRANSPARENT
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.rukia.chat.infrastructure.ChatModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/** How often the open case moves its story on. */
private const val STORY_TICK_MILLIS = 10_000L

/** The simulated phone of case [caseId]: shows the home screen, or the app the player opened. */
@Composable
fun PhoneScreen(caseId: String, onReturnToTitle: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val apps = remember(caseId, onReturnToTitle) { installedApps(context, caseId, onReturnToTitle) }
    // The story runs while the case is open, on any screen: timed lines arrive, choices expire, new chats appear.
    LaunchedEffect(caseId) {
        val chat = ChatModule.of(context, caseId)
        while (true) {
            withContext(Dispatchers.IO) { chat.advanceAll() }
            delay(STORY_TICK_MILLIS)
        }
    }
    var openId by rememberSaveable { mutableStateOf<String?>(null) }
    // Open the app a tapped notification asked for, if it's for this case.
    LaunchedEffect(AppLaunch.request) {
        val request = AppLaunch.request?.takeIf { it.caseId == caseId } ?: return@LaunchedEffect
        AppLaunch.request = null
        AppLaunch.argument = request.argument
        openId = request.app
    }
    // Declared before the app, so the app's own back handlers (e.g. close a chat) win first.
    // On the home screen, back does nothing, like a real phone.
    BackHandler { openId = null }
    CompositionLocalProvider(LocalCaseId provides caseId) {
        // Every app inherits the kit's font through this; their own MaterialTheme calls keep it.
        MaterialTheme(typography = FigtreeTypography) { Column {
            // A debug case gets the time bar on top; the apps below no longer pad for the status bar it covers.
            val debug = isDebugCase(caseId)
            if (debug) DebugTimeBar(caseId)
            Box(Modifier.weight(1f).then(if (debug) Modifier.consumeWindowInsets(WindowInsets.statusBars) else Modifier)) {
                AnimatedContent(
                    openId,
                    transitionSpec = { (fadeIn() + scaleIn(initialScale = 0.85f)) togetherWith (fadeOut() + scaleOut(targetScale = 0.85f)) },
                    label = "app",
                ) { id ->
                    val app = apps.find { it.id == id }
                    if (app == null) LauncherScreen(apps) { openId = it.id } else app.content()
                }
                EffectsLayer(caseId)
            }
        } }
    }
}

/** Sets status/navigation bar icon colors: light icons for dark backgrounds. Changing [key] re-applies them, e.g. after an overlay changed them. */
@Composable
fun SystemBars(lightBottomIcons: Boolean, lightTopIcons: Boolean = true, key: Any? = null) {
    val activity = LocalActivity.current as? ComponentActivity ?: return
    LaunchedEffect(lightBottomIcons, lightTopIcons, key) {
        fun style(light: Boolean) = if (light) SystemBarStyle.dark(TRANSPARENT) else SystemBarStyle.light(TRANSPARENT, TRANSPARENT)
        activity.enableEdgeToEdge(style(lightTopIcons), style(lightBottomIcons))
    }
}
