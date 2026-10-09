package com.rukia.chat.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.PowerManager
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallRecord
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.LocalCaseId
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.phone.infrastructure.ui.callBackdrop
import com.rukia.phone.infrastructure.media.playMedia
import kotlinx.coroutines.delay

@Composable
fun callLabel(status: CallStatus) = stringResource(
    when (status) {
        CallStatus.RINGING -> R.string.call_incoming
        CallStatus.ANSWERED -> R.string.call_voice
        CallStatus.DECLINED -> R.string.call_missed
    }
)

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
            Text(stringResource(R.string.call_badge), color = Color.White.copy(alpha = 0.88f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
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
        Text(stringResource(R.string.call_incoming_status), Modifier.padding(top = 6.dp), color = Color.White.copy(alpha = 0.75f), fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth().padding(horizontal = 22.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            RoundCallButton(stringResource(R.string.decline), Kit.Decline, iconRotation = 135f, onClick = onDecline)
            RoundCallButton(stringResource(R.string.accept), Kit.Accept, iconRotation = 0f, onClick = onAnswer)
        }
    }
}

/**
 * An answered call, shown like a real one: who's on the line and how long the call has lasted. What the caller
 * says plays from [audio]; the call hangs up when it ends or the player hangs up.
 */
@Composable
fun OngoingCallScreen(caller: Character?, audio: String, onHangUp: () -> Unit) {
    BackHandler {} // hang up with the button
    SystemBars(lightBottomIcons = true)
    val context = LocalContext.current
    val caseId = LocalCaseId.current
    val hangUp by rememberUpdatedState(onHangUp)
    var player by remember { mutableStateOf<MediaPlayer?>(null) }
    var seconds by remember { mutableIntStateOf(0) }
    var muted by remember { mutableStateOf(false) }
    var speaker by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { while (true) { delay(1_000); seconds++ } }
    DisposableEffect(audio) {
        // A missing file just means a silent call the player hangs up themselves.
        player = runCatching { playMedia(context, caseId, audio) { hangUp() } }.getOrNull()
        onDispose { player?.release(); player = null }
    }
    LaunchedEffect(muted, player) { player?.setVolume(if (muted) 0f else 1f, if (muted) 0f else 1f) }
    // Like a real call: the screen goes dark against the ear, so a cheek can't press anything.
    DisposableEffect(Unit) {
        val power = context.getSystemService(PowerManager::class.java)
        val lock = power?.takeIf { it.isWakeLockLevelSupported(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK) }
            ?.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "kimo:call")
            ?.apply { acquire(10 * 60_000L) } // released on hang-up; the timeout only guards against a leak
        onDispose { if (lock?.isHeld == true) lock.release() }
    }

    Column(
        Modifier.fillMaxSize().callBackdrop(caller.tint()).safeDrawingPadding().padding(start = 28.dp, end = 28.dp, top = 48.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.padding(top = 56.dp).border(4.dp, Color.White.copy(alpha = 0.18f), CircleShape)) { Avatar(caller, size = 132) }
        Text(caller?.name.orEmpty(), Modifier.padding(top = 30.dp), color = Color.White, fontSize = 40.sp, lineHeight = 44.sp, fontWeight = FontWeight.SemiBold, letterSpacing = (-1).sp)
        Text("%02d:%02d".format(seconds / 60, seconds % 60), Modifier.padding(top = 6.dp), color = Color.White.copy(alpha = 0.75f), fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth().padding(bottom = 44.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            CallToggle(RukiaIcons.MicOff, stringResource(R.string.mute), muted) { muted = it }
            // ponytail: the speaker button only lights up; the clip always plays through the phone's usual output.
            CallToggle(RukiaIcons.Speaker, stringResource(R.string.speaker), speaker) { speaker = it }
        }
        RoundCallButton(null, Kit.Decline, iconRotation = 135f, contentDescription = stringResource(R.string.hang_up), onClick = onHangUp)
    }
}

/** Round in-call button: glass when off, white when on, as in the kit. */
@Composable
private fun CallToggle(icon: ImageVector, label: String, on: Boolean, onChange: (Boolean) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            Modifier.size(72.dp).clip(CircleShape).background(if (on) Color.White else Color.White.copy(alpha = 0.16f))
                .toggleable(on, role = Role.Switch, onValueChange = onChange),
            contentAlignment = Alignment.Center,
        ) { Icon(icon, label, Modifier.size(26.dp), tint = if (on) Color(0xFF14141F) else Color.White) }
        Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
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
        item { LargeTitle(stringResource(R.string.tab_calls)) }
        if (calls.isEmpty()) item { Text(stringResource(R.string.no_calls), Modifier.padding(horizontal = 20.dp), color = p.subText, fontSize = 15.sp) }
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
