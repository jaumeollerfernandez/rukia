package com.rukia.chat.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.game.infrastructure.ui.Barlow
import com.rukia.game.infrastructure.ui.CutCorners
import com.rukia.game.infrastructure.ui.Rajdhani
import com.rukia.game.infrastructure.ui.Term
import com.rukia.game.infrastructure.ui.mono
import com.rukia.game.infrastructure.ui.terminalBackdrop
import com.rukia.phone.domain.model.caseTimeLabel
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.ZoneId
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/** How long a call rings before it goes to voicemail. */
private const val RING_MILLIS = 30_000L

/**
 * A call from the story rings over the whole phone, whatever app is open. Answered, the player hears it there and
 * then; declined or left ringing, it goes to voicemail. Either way it ends up in the Calls app. A contact whose chat
 * lives in another app (the sergeant's, in Police) calls with that app's look.
 */
@Composable
fun CallLayer(caseId: String) {
    val context = LocalContext.current.applicationContext
    val m = remember(caseId) { ChatModule.of(context, caseId) }
    var ringing by remember { mutableStateOf<Chat?>(null) }
    LaunchedEffect(m) {
        while (true) {
            val now = CaseClock.now(caseId)
            ringing = withContext(Dispatchers.IO) { m.listChats().firstOrNull { c -> c.ringingCall?.arrivedBy(now) == true } }
            delay(1_000)
        }
    }
    val chat = ringing ?: return
    val call = chat.ringingCall ?: return
    val caller = m.characters[call.from]
    val end = { answered: Boolean ->
        if (m.listChats().find { it.id == chat.id }?.ringingCall != null) m.endCall(chat.id, answered)
        ringing = null
    }
    // Saved, so a recreated screen keeps the call going instead of ringing again.
    var answered by rememberSaveable(chat.id, call.deliverAt) { mutableStateOf(false) }
    LaunchedEffect(chat.id, call.deliverAt, answered) {
        if (answered) return@LaunchedEffect
        delay((call.deliverAt + RING_MILLIS - CaseClock.now(caseId)).coerceAtLeast(0))
        end(false)
    }
    val audio = call.audio
    val police = caller?.app == "police"
    val start = remember(caseId) { CaseClock.start(context, caseId) }
    val at = caseTimeLabel(call.deliverAt, start, ZoneId.systemDefault()).replace(" ", " · ")
    when {
        answered && audio != null ->
            if (police) SecureOngoingCall(caller, audio) { end(true) } else OngoingCallScreen(caller, audio) { end(true) }
        police -> SecureIncomingCall(caller, at, onAnswer = { if (audio != null) answered = true else end(true) }, onDecline = { end(false) })
        else -> IncomingCallScreen(caller, onAnswer = { if (audio != null) answered = true else end(true) }, onDecline = { end(false) })
    }
}

/** The top strip of a secure call: its state on the left, «encrypted» on the right. */
@Composable
private fun SecureStrip(label: String, color: Color, blink: Boolean) {
    val pulse by rememberInfiniteTransition(label = "strip").animateFloat(1f, 0.2f, infiniteRepeatable(tween(550, easing = { if (it < 0.5f) 0f else 1f }), RepeatMode.Reverse), label = "dot")
    Row(
        Modifier.fillMaxWidth().background(Color(0xD90B121B)).drawBehind {
            drawLine(Term.Cyan.copy(alpha = 0.2f), Offset(0f, size.height), Offset(size.width, size.height), 1.dp.toPx())
        }.statusBarsPadding().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(Modifier.size(8.dp).alpha(if (blink) pulse else 1f).background(color, CircleShape))
            Text(label, style = mono(11, color, FontWeight.SemiBold, 1.5f))
        }
        Row(
            Modifier.border(1.dp, Term.Green.copy(alpha = 0.5f)).padding(horizontal = 7.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(RukiaIcons.Lock, null, Modifier.size(10.dp), tint = Term.Green)
            Text(stringResource(R.string.channel_encrypted), style = mono(9, Term.Green, spacing = 0f))
        }
    }
}

@Composable
private fun SecureAvatar(name: String, size: Int) {
    Box(
        Modifier.size(size.dp).clip(CutCorners).background(Color(0xFF1C2A3A)).border(2.dp, Term.Cyan, CutCorners),
        contentAlignment = Alignment.Center,
    ) { Text(initials(name), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = (size * 0.4f).sp, color = Term.Cyan) }
}

/** The sergeant ringing on the encrypted channel (design: PoliceCall, incoming). [at] is when, in case time. */
@Composable
private fun SecureIncomingCall(caller: Character?, at: String, onAnswer: () -> Unit, onDecline: () -> Unit) {
    BackHandler {}
    SystemBars(lightBottomIcons = true)
    Ringing()
    val name = caller?.name.orEmpty()
    Column(Modifier.fillMaxSize().terminalBackdrop(glow = true)) {
        SecureStrip(stringResource(R.string.secure_call_incoming_strip), Term.Amber, blink = true)
        Column(
            Modifier.weight(1f).fillMaxWidth().navigationBarsPadding().padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(R.string.secure_call_priority, at), style = mono(11, Term.Muted, spacing = 2f))
            Box(Modifier.padding(top = 22.dp).size(220.dp), contentAlignment = Alignment.Center) {
                val rings = rememberInfiniteTransition(label = "rings")
                repeat(3) { i ->
                    val t by rings.animateFloat(0f, 1f, infiniteRepeatable(tween(2400, easing = LinearOutSlowInEasing), initialStartOffset = StartOffset(i * 800)), label = "ring$i")
                    Box(
                        Modifier.size(120.dp).graphicsLayer { scaleX = 0.6f + 1.3f * t; scaleY = 0.6f + 1.3f * t; alpha = 0.9f * (1 - t) }
                            .border(2.dp, Term.Cyan.copy(alpha = 0.7f), CircleShape),
                    )
                }
                SecureAvatar(name, 112)
            }
            Text(stringResource(R.string.secure_call_incoming), Modifier.padding(top = 18.dp), style = mono(11, Term.Amber, spacing = 2f))
            Text(name, Modifier.padding(top = 4.dp), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 36.sp, color = Term.Text, textAlign = TextAlign.Center)
            if (caller?.status?.isNotEmpty() == true) Text(caller.status, Modifier.padding(top = 4.dp), fontFamily = Barlow, fontSize = 15.sp, color = Term.Body)
            Row(Modifier.padding(top = 20.dp).fillMaxWidth().border(1.dp, Term.Cyan.copy(alpha = 0.25f)).background(Color(0xD90C1620))) {
                Fact(stringResource(R.string.secure_call_channel), "OMI-7 · E2E", Term.Green, Modifier.weight(1f))
                Fact(stringResource(R.string.secure_call_recording), stringResource(R.string.secure_call_automatic), Term.Amber, Modifier.weight(1f))
            }
            Spacer(Modifier.weight(1f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    Modifier.weight(1f).heightIn(min = 64.dp).clip(CutCorners).background(Color(0x1FE5383B)).border(1.dp, Term.RedText.copy(alpha = 0.7f), CutCorners)
                        .clickable(role = Role.Button, onClick = onDecline),
                    contentAlignment = Alignment.Center,
                ) { Text(stringResource(R.string.decline).uppercase(), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 2.sp, color = Term.RedText) }
                Box(
                    Modifier.weight(1.4f).heightIn(min = 64.dp).clip(CutCorners).background(Term.Green).clickable(role = Role.Button, onClick = onAnswer),
                    contentAlignment = Alignment.Center,
                ) { Text(stringResource(R.string.answer).uppercase(), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 2.sp, color = Color(0xFF062014)) }
            }
            Text(stringResource(R.string.secure_call_note), Modifier.padding(top = 12.dp), style = mono(10, Term.Dim, spacing = 0f).copy(textAlign = TextAlign.Center))
        }
    }
}

@Composable
private fun Fact(label: String, value: String, color: Color, modifier: Modifier) {
    Column(modifier.padding(horizontal = 12.dp, vertical = 9.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(label, style = mono(9, Term.Dim, spacing = 1.5f))
        Text(value, style = mono(12, color, spacing = 0f))
    }
}

/** The sergeant on the line (design: PoliceCall, ongoing): who, how long, a live waveform, mute, speaker, hang up. */
@Composable
private fun SecureOngoingCall(caller: Character?, audio: String, onHangUp: () -> Unit) {
    BackHandler {}
    SystemBars(lightBottomIcons = true)
    var muted by remember { mutableStateOf(false) }
    var speaker by remember { mutableStateOf(true) }
    val seconds = callAudio(audio, muted, onHangUp)
    val clock = "%02d:%02d".format(seconds / 60, seconds % 60)
    val name = caller?.name.orEmpty()
    Column(Modifier.fillMaxSize().terminalBackdrop()) {
        SecureStrip(stringResource(R.string.secure_call_live, clock), Term.Green, blink = true)
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecureAvatar(name, 56)
            Column(Modifier.weight(1f)) {
                Text(name, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 24.sp, color = Term.Text)
                if (caller?.status?.isNotEmpty() == true) Text(caller.status, fontFamily = Barlow, fontSize = 13.sp, color = Term.Muted)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(clock, style = mono(22, Term.Text, FontWeight.SemiBold, 0f))
                Text("● REC", style = mono(9, Term.RedText, spacing = 1f))
            }
        }
        // The waveform moves while she speaks; still when muted.
        val wave = rememberInfiniteTransition(label = "voice")
        Row(
            Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(64.dp).border(1.dp, Term.Cyan.copy(alpha = 0.25f)).background(Color(0xD90C1620))
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp), verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(40) { i ->
                val h = 0.2f + 0.7f * abs(sin(i * 1.7f) * cos(i * 0.6f))
                val scale = if (muted) 0.25f else wave.animateFloat(0.25f, 1f, infiniteRepeatable(tween(450), RepeatMode.Reverse, initialStartOffset = StartOffset((i * 37) % 900)), label = "bar$i").value
                Box(
                    Modifier.weight(1f).fillMaxHeight(h).graphicsLayer { scaleY = scale; transformOrigin = TransformOrigin.Center }
                        .background(if (muted) Term.Cyan.copy(alpha = 0.3f) else if (i % 9 == 0) Term.Amber else Term.Cyan),
                )
            }
        }
        Text(
            stringResource(if (muted) R.string.secure_call_muted else R.string.secure_call_speaking),
            Modifier.padding(start = 16.dp, end = 16.dp, top = 6.dp).fillMaxWidth(), style = mono(9, Term.Dim, spacing = 1f).copy(textAlign = TextAlign.End),
        )
        Spacer(Modifier.weight(1f))
        Column(
            Modifier.fillMaxWidth().background(Color(0xFF0B121B)).drawBehind { drawLine(Term.Cyan.copy(alpha = 0.25f), Offset.Zero, Offset(size.width, 0f), 1.dp.toPx()) }
                .navigationBarsPadding().padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Tool(stringResource(if (muted) R.string.secure_call_silenced else R.string.mute).uppercase(), muted, Modifier.weight(1f)) { muted = !muted }
                // ponytail: the speaker button only lights up; the clip always plays through the phone's usual output.
                Tool(stringResource(R.string.speaker).uppercase(), speaker, Modifier.weight(1f)) { speaker = !speaker }
            }
            Box(
                Modifier.fillMaxWidth().heightIn(min = 58.dp).clip(CutCorners).background(Term.Red).clickable(role = Role.Button, onClick = onHangUp),
                contentAlignment = Alignment.Center,
            ) { Text(stringResource(R.string.hang_up).uppercase(), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 19.sp, letterSpacing = 2.sp, color = Color.White) }
        }
    }
}

@Composable
private fun Tool(label: String, on: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier.heightIn(min = 64.dp).background(if (on) Term.Cyan.copy(alpha = 0.16f) else Color(0xFF0C1620))
            .border(1.dp, if (on) Term.Cyan else Term.Cyan.copy(alpha = 0.25f)).clickable(role = Role.Switch, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Text(label, style = mono(10, if (on) Term.Cyan else Term.Body, spacing = 1f)) }
}
