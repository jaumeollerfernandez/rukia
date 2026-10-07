package com.rukia.police.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.Kit
import com.rukia.phone.LocalCaseId
import com.rukia.phone.RukiaIcons
import com.rukia.phone.playMedia
import com.rukia.police.domain.model.RecordedCall

/** Evidence: the calls the player answered. Tapping one replays its voice clip; tapping it again, or another, stops it. */
@Composable
fun CallRecordingsScreen(calls: List<RecordedCall>, onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val c = LocalPolice.current
    val context = LocalContext.current
    val caseId = LocalCaseId.current
    var playing by remember { mutableStateOf<Int?>(null) }
    DisposableEffect(playing) {
        val player = playing?.let { i -> runCatching { playMedia(context, caseId, calls[i].audio) { playing = null } }.getOrNull() }
        onDispose { player?.release() }
    }

    Column(Modifier.fillMaxSize().background(c.background)) {
        SubHeader("Call recordings", onBack)
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).navigationBarsPadding().padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 24.dp),
        ) {
            if (calls.isEmpty()) {
                Text("No calls recorded yet. Calls you answer show up here.", color = c.subText, fontSize = 15.sp)
                return@Column
            }
            Section("Answered calls", footer = "Tap a call to listen to it again.") {
                calls.forEachIndexed { i, call ->
                    if (i > 0) RowDivider()
                    val isPlaying = playing == i
                    ActionRow(
                        if (isPlaying) RukiaIcons.Stop else RukiaIcons.Play, Kit.Accept, call.caller,
                        if (isPlaying) "Playing…" else "Call at ${call.time}",
                        trailing = { Icon(RukiaIcons.Phone, null, Modifier.size(18.dp), tint = c.chevron) },
                    ) { playing = if (isPlaying) null else i }
                }
            }
        }
    }
}
