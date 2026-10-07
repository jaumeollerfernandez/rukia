package com.rukia.chat.infrastructure.ui

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallRecord
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.phone.Kit
import com.rukia.phone.LocalCaseId
import com.rukia.phone.RukiaIcons
import com.rukia.phone.SystemBars
import com.rukia.phone.callBackdrop
import com.rukia.phone.glass
import com.rukia.phone.playMedia
import kotlinx.coroutines.delay

fun callLabel(status: CallStatus) = when (status) {
    CallStatus.RINGING -> "Incoming voice call"
    CallStatus.ANSWERED -> "Voice call"
    CallStatus.DECLINED -> "Missed voice call"
}

private fun Character?.tint() = this?.colorValue() ?: Color(0xFF7E57C2)

/** Full-screen ringing call, tinted by the caller's color. */
@Composable
fun IncomingCallScreen(caller: Character?, onAnswer: () -> Unit, onDecline: () -> Unit) {
    BackHandler {} // a ringing call can't be backed out of
    SystemBars(lightBottomIcons = true)
    Ringing()
    val tint = caller.tint()
    Column(
        Modifier.fillMaxSize().callBackdrop(tint).safeDrawingPadding().padding(start = 28.dp, end = 28.dp, top = 48.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier.background(Color.White.copy(alpha = 0.14f), RoundedCornerShape(14.dp)).padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(RukiaIcons.Phone, null, Modifier.size(14.dp), tint = Color.White.copy(alpha = 0.88f))
            Text("Chats voice call", color = Color.White.copy(alpha = 0.88f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
        Box(Modifier.padding(top = 56.dp).size(132.dp), contentAlignment = Alignment.Center) {
            // Two rings spreading out of the avatar, half a beat apart.
            val rings = rememberInfiniteTransition(label = "rings")
            repeat(2) { i ->
                val t by rings.animateFloat(
                    0f, 1f, infiniteRepeatable(tween(2200, easing = LinearOutSlowInEasing), initialStartOffset = StartOffset(i * 1100)), label = "ring$i",
                )
                Box(Modifier.fillMaxSize().graphicsLayer { scaleX = 1 + 0.7f * t; scaleY = 1 + 0.7f * t; alpha = 0.55f * (1 - t) }.background(tint, CircleShape))
            }
            Box(Modifier.border(4.dp, Color.White.copy(alpha = 0.18f), CircleShape)) { Avatar(caller, size = 132) }
        }
        Text(caller?.name.orEmpty(), Modifier.padding(top = 30.dp), color = Color.White, fontSize = 40.sp, lineHeight = 44.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-1).sp)
        Text("Incoming call…", Modifier.padding(top = 6.dp), color = Color.White.copy(alpha = 0.75f), fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth().padding(horizontal = 22.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            RoundCallButton("Decline", Kit.Decline, iconRotation = 135f, onClick = onDecline)
            RoundCallButton("Accept", Kit.Accept, iconRotation = 0f, onClick = onAnswer)
        }
    }
}

/** Heights of the waveform's 40 bars, from the design. */
private val WAVE = intArrayOf(18, 30, 46, 26, 58, 40, 22, 50, 62, 34, 20, 44, 56, 28, 38, 60, 24, 48, 32, 16, 42, 54, 26, 36, 58, 30, 20, 46, 40, 24, 52, 34, 18, 44, 28, 38, 22, 30, 16, 24)

private fun clock(millis: Int) = (millis / 1000).let { "%d:%02d".format(it / 60, it % 60) }

/** An answered call: plays what the caller says from [audio], and hangs up when it ends or the player hangs up. */
@Composable
fun OngoingCallScreen(caller: Character?, audio: String, onHangUp: () -> Unit) {
    BackHandler {} // hang up with the button
    SystemBars(lightBottomIcons = true)
    val context = LocalContext.current
    val caseId = LocalCaseId.current
    val hangUp by rememberUpdatedState(onHangUp)
    var player by remember { mutableStateOf<MediaPlayer?>(null) }
    var seconds by remember { mutableIntStateOf(0) }
    var position by remember { mutableIntStateOf(0) }
    var muted by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { while (true) { delay(1_000); seconds++ } }
    DisposableEffect(audio) {
        // A missing file just means a silent call the player hangs up themselves.
        player = runCatching { playMedia(context, caseId, audio) { hangUp() } }.getOrNull()
        onDispose { player?.release(); player = null }
    }
    LaunchedEffect(player) { while (true) { position = runCatching { player?.currentPosition }.getOrNull() ?: 0; delay(200) } }
    LaunchedEffect(muted, player) { player?.setVolume(if (muted) 0f else 1f, if (muted) 0f else 1f) }
    val duration = remember(player) { runCatching { player?.duration }.getOrNull()?.takeIf { it > 0 } ?: 0 }
    val tint = caller.tint()

    Column(
        Modifier.fillMaxSize().callBackdrop(tint).safeDrawingPadding().padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Box(Modifier.border(3.dp, Color.White.copy(alpha = 0.18f), CircleShape)) { Avatar(caller, size = 56) }
            Column {
                Text(caller?.name.orEmpty(), color = Color.White, fontSize = 28.sp, lineHeight = 32.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-0.5).sp)
                Text("%02d:%02d".format(seconds / 60, seconds % 60), color = Color.White.copy(alpha = 0.75f), fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
        Column(
            Modifier.padding(top = 40.dp).fillMaxWidth().glass(RoundedCornerShape(28.dp), alpha = 0.10f).padding(start = 18.dp, end = 18.dp, top = 20.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(8.dp).background(Color(0xFF3DDC84), CircleShape))
                Text("${caller?.name ?: "Caller"} is speaking", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            val played = if (duration > 0) position.toFloat() / duration else 0f
            Row(Modifier.fillMaxWidth().height(64.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                WAVE.forEachIndexed { i, h ->
                    val color = if (i.toFloat() / WAVE.size < played) Color.White else Color.White.copy(alpha = 0.32f)
                    Box(Modifier.weight(1f).height(h.dp).background(color, RoundedCornerShape(2.dp)))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(clock(position), color = Color.White.copy(alpha = 0.65f), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text(clock(duration), color = Color.White.copy(alpha = 0.65f), fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }
        Spacer(Modifier.weight(1f))
        // Glass when off, white when on, as in the kit.
        Column(Modifier.padding(bottom = 44.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                Modifier.size(72.dp).clip(CircleShape).background(if (muted) Color.White else Color.White.copy(alpha = 0.16f))
                    .toggleable(muted, role = Role.Switch) { muted = it },
                contentAlignment = Alignment.Center,
            ) { Icon(RukiaIcons.MicOff, "Mute", Modifier.size(26.dp), tint = if (muted) Color(0xFF14141F) else Color.White) }
            Text("Mute", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
        RoundCallButton(null, Kit.Decline, iconRotation = 135f, contentDescription = "Hang up", onClick = onHangUp)
    }
}

/** 78 dp call button with a glow in its own color. */
@Composable
private fun RoundCallButton(label: String?, color: Color, iconRotation: Float, contentDescription: String? = label, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        FilledIconButton(
            onClick, Modifier.size(78.dp).shadow(16.dp, CircleShape, ambientColor = color, spotColor = color),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = color),
        ) { Icon(RukiaIcons.Phone, contentDescription, Modifier.size(32.dp).rotate(iconRotation), tint = Color.White) }
        if (label != null) Text(label, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
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
fun CallsScreen(calls: List<CallRecord>, characters: Map<String, Character>) {
    val p = LocalPalette.current
    LazyColumn(Modifier.fillMaxSize()) {
        item { LargeTitle("Calls") }
        if (calls.isEmpty()) item { Text("No calls yet", Modifier.padding(horizontal = 20.dp), color = p.subText, fontSize = 15.sp) }
        items(calls) { call ->
            val missed = call.status == CallStatus.DECLINED
            val color = if (missed) p.red else p.subText
            PersonRow(
                characters[call.from], "${callLabel(call.status)} · ${call.time}", nameColor = if (missed) p.red else Color.Unspecified,
                subtitleIcon = { Icon(RukiaIcons.Phone, null, Modifier.size(14.dp), tint = color) },
            )
        }
    }
}
