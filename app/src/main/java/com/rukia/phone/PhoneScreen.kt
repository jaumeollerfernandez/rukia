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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.rukia.chat.infrastructure.ChatModule

/** The simulated phone of case [caseId]: shows the home screen, or the app the player opened. */
@Composable
fun PhoneScreen(caseId: String, onReturnToTitle: () -> Unit) {
    val apps = remember(caseId, onReturnToTitle) { installedApps(caseId, onReturnToTitle) }
    // The story clock starts when the case opens, not when Chats is first opened, so timed events happen on the home screen too.
    val context = LocalContext.current.applicationContext
    remember(caseId) { ChatModule(context, caseId).advanceAll() }
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
        AnimatedContent(
            openId,
            transitionSpec = { (fadeIn() + scaleIn(initialScale = 0.85f)) togetherWith (fadeOut() + scaleOut(targetScale = 0.85f)) },
            label = "app",
        ) { id ->
            val app = apps.find { it.id == id }
            if (app == null) LauncherScreen(apps) { openId = it.id } else app.content()
        }
    }
}

/** Sets status/navigation bar icon colors. The status bar always sits on a dark top bar or wallpaper. Changing [key] re-applies them, e.g. after an overlay changed them. */
@Composable
fun SystemBars(lightBottomIcons: Boolean, key: Any? = null) {
    val activity = LocalActivity.current as? ComponentActivity ?: return
    LaunchedEffect(lightBottomIcons, key) {
        val bottom = if (lightBottomIcons) SystemBarStyle.dark(TRANSPARENT) else SystemBarStyle.light(TRANSPARENT, TRANSPARENT)
        activity.enableEdgeToEdge(SystemBarStyle.dark(TRANSPARENT), bottom)
    }
}
