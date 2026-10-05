package com.rukia.police.infrastructure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.SystemBars
import com.rukia.police.domain.model.Verdict
import com.rukia.police.infrastructure.PoliceModule
import kotlinx.coroutines.launch

val PoliceBlue = Color(0xFF1A237E)
internal val Danger = Color(0xFFD32F2F)

/** Police Department app. [onResetChats] wipes the chat app's save and [onReturnToTitle] leaves the phone; the phone wires both in. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoliceApp(caseId: String, onResetChats: () -> Unit, onReturnToTitle: () -> Unit) {
    SystemBars(lightBottomIcons = false)
    val context = LocalContext.current.applicationContext
    val m = remember(caseId) { PoliceModule(context, caseId, save = { onResetChats() }) }
    var confirming by remember { mutableStateOf(false) }
    var solving by rememberSaveable { mutableStateOf(false) }
    var verdict by rememberSaveable { mutableStateOf<Verdict?>(null) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    MaterialTheme(colorScheme = lightColorScheme(primary = PoliceBlue)) {
        verdict?.let { return@MaterialTheme VerdictScreen(it, onReturnToTitle) }
        if (solving) {
            return@MaterialTheme SolveCaseScreen(m.getCaseQuestion(), onBack = { solving = false }) { verdict = m.solveCase(it.id) }
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Police Department", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PoliceBlue, titleContentColor = Color.White),
                )
            },
            snackbarHost = { SnackbarHost(snackbar) },
            containerColor = Color(0xFFF5F6FA),
        ) { padding ->
            Column(Modifier.padding(padding).padding(16.dp)) {
                Section("Game")
                ActionCard(Icons.Filled.Search, Danger, "Solve the case", "One attempt only. Choose wisely") { solving = true }
                Spacer(Modifier.height(8.dp))
                ActionCard(Icons.AutoMirrored.Filled.ExitToApp, PoliceBlue, "Return to title", "Leave the case. Progress is kept", onReturnToTitle)
                Spacer(Modifier.height(24.dp))
                Section("Administration")
                ActionCard(Icons.Filled.Refresh, Danger, "Reset chats", "Delete every conversation and story choice") { confirming = true }
            }
        }

        if (confirming) {
            AlertDialog(
                onDismissRequest = { confirming = false },
                icon = { Icon(Icons.Filled.Warning, null, tint = Danger) },
                title = { Text("Reset all chats?") },
                text = {
                    Text(
                        "All data related to this save file will be deleted: every conversation, call and choice " +
                            "made in the story. The story will start again from the beginning. This can't be undone."
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        confirming = false
                        onResetChats()
                        scope.launch { snackbar.showSnackbar("Chats have been reset") }
                    }) { Text("Delete everything", color = Danger) }
                },
                dismissButton = { TextButton({ confirming = false }) { Text("Cancel") } },
            )
        }
    }
}

@Composable
private fun Section(title: String) {
    Text(title, Modifier.padding(bottom = 8.dp), color = PoliceBlue, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
}

@Composable
private fun ActionCard(icon: ImageVector, tint: Color, title: String, subtitle: String, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(
            Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(44.dp).background(tint.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = tint)
            }
            Column(Modifier.padding(start = 14.dp)) {
                Text(title, fontSize = 17.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
