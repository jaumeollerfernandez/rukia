package com.rukia.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.chat.infrastructure.ui.ChatApp
import com.rukia.police.infrastructure.ui.PoliceApp
import com.rukia.police.infrastructure.ui.PoliceBlue
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class PhoneApp(val id: String, val label: String, val icon: ImageVector, val color: Color, val content: @Composable () -> Unit)

/** Apps on the phone of case [caseId]. Add new ones here. [onReturnToTitle] leaves the phone for the game's title screen. */
fun installedApps(caseId: String, onReturnToTitle: () -> Unit) = listOf(
    PhoneApp("chat", "Chats", Icons.Filled.Email, Color(0xFF25D366)) { ChatApp(caseId) },
    PhoneApp("police", "Police Department", Icons.Filled.Star, PoliceBlue) {
        val context = LocalContext.current.applicationContext
        PoliceApp(caseId, onResetChats = { ChatModule(context, caseId).resetProgress() }, onReturnToTitle = onReturnToTitle)
    },
)

private const val COLUMNS = 4
private val labelShadow = TextStyle(shadow = Shadow(Color.Black.copy(alpha = 0.5f), blurRadius = 6f))

@Composable
fun LauncherScreen(apps: List<PhoneApp>, onOpen: (PhoneApp) -> Unit) {
    SystemBars(lightBottomIcons = true)
    Column(
        Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1A2A6C), Color(0xFF4A2C7A), Color(0xFFB06AB3))))
            .safeDrawingPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
    ) {
        ClockWidget()
        Spacer(Modifier.weight(1f))
        apps.chunked(COLUMNS).forEach { row ->
            Row(Modifier.padding(vertical = 8.dp)) {
                row.forEach { AppIcon(it, Modifier.weight(1f)) { onOpen(it) } }
                repeat(COLUMNS - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun ClockWidget() {
    var now by remember { mutableStateOf(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)) }
    LaunchedEffect(Unit) {
        // State only changes when the minute does, so this doesn't recompose every second.
        while (true) { delay(1_000); now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) }
    }
    Column(Modifier.fillMaxWidth().padding(top = 48.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(now.format(DateTimeFormatter.ofPattern("HH:mm")), color = Color.White, fontSize = 80.sp, fontWeight = FontWeight.Light, style = labelShadow)
        Text(now.format(DateTimeFormatter.ofPattern("EEEE, d MMMM")), color = Color.White, fontSize = 18.sp, style = labelShadow)
    }
}

@Composable
private fun AppIcon(app: PhoneApp, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.clickable(onClick = onClick).padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.size(60.dp).background(app.color, CircleShape), contentAlignment = Alignment.Center) {
            Icon(app.icon, null, Modifier.size(32.dp), tint = Color.White)
        }
        Text(
            app.label, Modifier.padding(top = 6.dp), color = Color.White, fontSize = 13.sp, style = labelShadow,
            textAlign = TextAlign.Center, maxLines = 2, lineHeight = 15.sp,
        )
    }
}
