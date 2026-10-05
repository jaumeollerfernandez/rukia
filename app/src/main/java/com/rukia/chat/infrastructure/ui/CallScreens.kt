package com.rukia.chat.infrastructure.ui

import android.media.RingtoneManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallRecord
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.phone.LocalCaseId
import com.rukia.phone.playMedia
import kotlinx.coroutines.delay

private val Decline = Color(0xFFE53935)
private val AcceptGreen = Color(0xFF25D366)

fun callLabel(status: CallStatus) = when (status) {
    CallStatus.RINGING -> "Incoming voice call"
    CallStatus.ANSWERED -> "Voice call"
    CallStatus.DECLINED -> "Missed voice call"
}

fun callColor(status: CallStatus) = if (status == CallStatus.DECLINED) Decline else AcceptGreen

/** Full-screen ringing call. Both buttons only hang up for now. */
@Composable
fun IncomingCallScreen(caller: Character?, onAnswer: () -> Unit, onDecline: () -> Unit) {
    BackHandler {} // a ringing call can't be backed out of
    Ringing()
    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        1f, 1.12f, infiniteRepeatable(tween(700), RepeatMode.Reverse), label = "pulse",
    )
    Column(
        Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0B3D36), Color(0xFF111B21))))
            .safeDrawingPadding()
            .padding(top = 56.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Voice call", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        Spacer(Modifier.height(32.dp))
        Box(Modifier.scale(pulse)) { Avatar(caller, size = 128) }
        Spacer(Modifier.height(24.dp))
        Text(caller?.name.orEmpty(), color = Color.White, fontSize = 30.sp)
        Text("Incoming call…", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            RoundCallButton("Decline", Decline, iconRotation = 135f, onClick = onDecline)
            RoundCallButton("Accept", AcceptGreen, iconRotation = 0f, onClick = onAnswer)
        }
    }
}

/** An answered call: plays what the caller says from [audio], and hangs up when it ends or the player hangs up. */
@Composable
fun OngoingCallScreen(caller: Character?, audio: String, onHangUp: () -> Unit) {
    BackHandler {} // hang up with the button
    val context = LocalContext.current
    val caseId = LocalCaseId.current
    val hangUp by rememberUpdatedState(onHangUp)
    var seconds by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) { while (true) { delay(1_000); seconds++ } }
    DisposableEffect(audio) {
        // A missing file just means a silent call the player hangs up themselves.
        val player = runCatching { playMedia(context, caseId, audio) { hangUp() } }.getOrNull()
        onDispose { player?.release() }
    }
    Column(
        Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0B3D36), Color(0xFF111B21))))
            .safeDrawingPadding()
            .padding(top = 56.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Voice call", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        Spacer(Modifier.height(32.dp))
        Avatar(caller, size = 128)
        Spacer(Modifier.height(24.dp))
        Text(caller?.name.orEmpty(), color = Color.White, fontSize = 30.sp)
        Text("%02d:%02d".format(seconds / 60, seconds % 60), color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
        Spacer(Modifier.weight(1f))
        RoundCallButton("Hang up", Decline, iconRotation = 135f, onClick = onHangUp)
    }
}

@Composable
private fun RoundCallButton(label: String, color: Color, iconRotation: Float, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilledIconButton(
            onClick, Modifier.size(72.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = color),
        ) { Icon(Icons.Filled.Call, label, Modifier.size(34.dp).rotate(iconRotation), tint = Color.White) }
        Text(label, Modifier.padding(top = 8.dp), color = Color.White, fontSize = 14.sp)
    }
}

/** Phone ringtone + vibration while on screen. */
@Composable
private fun Ringing() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
        ringtone?.isLooping = true
        ringtone?.play()
        val vibrator = context.getSystemService(Vibrator::class.java)
        vibrator?.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 800, 1200), 0))
        onDispose {
            ringtone?.stop()
            vibrator?.cancel()
        }
    }
}

@Composable
fun CallsScreen(calls: List<CallRecord>, characters: Map<String, Character>, contentPadding: PaddingValues = PaddingValues()) {
    if (calls.isEmpty()) return Placeholder("No calls yet")
    LazyColumn(contentPadding = contentPadding) {
        items(calls) { call ->
            val caller = characters[call.from]
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(caller, size = 52)
                Column(Modifier.padding(start = 14.dp).weight(1f)) {
                    Text(
                        caller?.name ?: call.from, fontSize = 17.sp, fontWeight = FontWeight.SemiBold,
                        color = if (call.status == CallStatus.DECLINED) Decline else Color.Unspecified,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("↙ ", color = callColor(call.status), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text("${callLabel(call.status)} · ${call.time}", color = LocalPalette.current.subText, fontSize = 14.sp)
                    }
                }
                Box(Modifier.size(40.dp).background(LocalPalette.current.indicator, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.Call, null, Modifier.size(20.dp), tint = LocalPalette.current.primary)
                }
            }
        }
    }
}
