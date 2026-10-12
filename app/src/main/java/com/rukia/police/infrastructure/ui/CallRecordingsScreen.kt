package com.rukia.police.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.game.infrastructure.ui.Barlow
import com.rukia.game.infrastructure.ui.Rajdhani
import com.rukia.game.infrastructure.ui.Term
import com.rukia.game.infrastructure.ui.mono
import com.rukia.game.infrastructure.ui.terminalBackdrop
import com.rukia.phone.infrastructure.LocalCaseId
import com.rukia.phone.infrastructure.media.mediaDurationMillis
import com.rukia.phone.infrastructure.media.playMedia
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.police.domain.model.RecordedCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.sin

private const val BARS = 44

private fun clip(millis: Long) = (millis / 1000).let { "%d:%02d".format(it / 60, it % 60) }

/**
 * Evidence: the calls the player answered, newest first, numbered as exhibits. Tapping one plays its voice clip;
 * tapping it again, or another, stops it. Those never played ([unheard]) are marked; [onHeard] is called on playing one.
 * [caseTimeOf] writes a moment as "D3 07:15".
 */
@Composable
fun CallRecordingsScreen(
    calls: List<RecordedCall>, unheard: Set<String>, caseTimeOf: (Long) -> String,
    onHeard: (RecordedCall) -> Unit, onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)
    SystemBars(lightBottomIcons = true)
    val context = LocalContext.current
    val caseId = LocalCaseId.current
    var playing by remember { mutableStateOf<String?>(null) }
    DisposableEffect(playing) {
        val player = playing?.let { audio -> runCatching { playMedia(context, caseId, audio) { playing = null } }.getOrNull() }
        onDispose { player?.release() }
    }
    val durations by produceState(emptyMap<String, Long>(), calls) {
        value = withContext(Dispatchers.IO) { calls.associate { it.audio to (mediaDurationMillis(context, caseId, it.audio) ?: 0L) } }
    }

    Column(
        Modifier.fillMaxSize().terminalBackdrop().verticalScroll(rememberScrollState()).safeDrawingPadding()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.size(44.dp).border(1.dp, Term.Cyan.copy(alpha = 0.35f)).clickable(role = Role.Button, onClick = onBack), contentAlignment = Alignment.Center) {
                Icon(RukiaIcons.ArrowLeft, stringResource(R.string.back), Modifier.size(22.dp), tint = Term.Text)
            }
            Column {
                Text(stringResource(R.string.recordings_kicker), style = mono(10, Term.Cyan, spacing = 1.5f))
                Text(stringResource(R.string.police_recordings), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 27.sp, lineHeight = 29.sp, color = Term.Text)
            }
        }
        if (calls.isEmpty()) {
            Text(stringResource(R.string.no_recordings), fontFamily = Barlow, fontSize = 15.sp, color = Term.Muted)
            return@Column
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Stat("${calls.size}", stringResource(R.string.recordings_count), Term.Text, Term.Cyan.copy(alpha = 0.3f), Modifier.weight(1f))
            Stat(clip(durations.values.sum()), stringResource(R.string.recordings_total), Term.Text, Term.Cyan.copy(alpha = 0.3f), Modifier.weight(1f))
            val new = calls.count { it.audio in unheard }
            Stat("$new", stringResource(R.string.recordings_unheard), if (new > 0) Term.Amber else Term.Text, if (new > 0) Term.Amber else Term.Cyan.copy(alpha = 0.3f), Modifier.weight(1f))
        }
        calls.forEachIndexed { k, call ->
            val on = playing == call.audio
            RecordingCard(
                call, exhibit = calls.size - k, isNew = call.audio in unheard, playing = on, duration = durations[call.audio] ?: 0L,
                whenLabel = if (call.at > 0) caseTimeOf(call.at).replace(" ", " · ") else call.time, seed = k,
            ) {
                if (!on) onHeard(call)
                playing = if (on) null else call.audio
            }
        }
        Text(stringResource(R.string.recordings_note), style = mono(10, Term.Dim, spacing = 0f).copy(lineHeight = 14.sp))
    }
}

@Composable
private fun Stat(value: String, label: String, color: Color, border: Color, modifier: Modifier) {
    Column(modifier.border(1.dp, border).background(if (color == Term.Amber) Term.Amber.copy(alpha = 0.08f) else Color.Transparent).padding(10.dp)) {
        Text(value, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = color)
        Text(label, style = mono(9, if (color == Term.Amber) Term.Amber else Term.Muted, spacing = 0f))
    }
}

/** One call: play button, who and when, its exhibit number, a waveform that moves while it plays, and its progress. */
@Composable
private fun RecordingCard(
    call: RecordedCall, exhibit: Int, isNew: Boolean, playing: Boolean, duration: Long, whenLabel: String, seed: Int, onToggle: () -> Unit,
) {
    val label = stringResource(if (playing) R.string.recording_pause else R.string.recording_play, call.caller)
    Column(
        Modifier.fillMaxWidth().background(if (playing) Term.Cyan.copy(alpha = 0.08f) else Color(0xEB0C1620))
            .border(1.dp, if (playing) Term.Cyan else if (isNew) Term.Amber.copy(alpha = 0.6f) else Term.Cyan.copy(alpha = 0.2f)).padding(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                Modifier.size(48.dp).background(if (playing) Term.Cyan else if (isNew) Term.Amber else Term.Muted.copy(alpha = 0.55f))
                    .clickable(role = Role.Button, onClick = onToggle).semantics { contentDescription = label },
                contentAlignment = Alignment.Center,
            ) { Icon(if (playing) RukiaIcons.Stop else RukiaIcons.Play, null, Modifier.size(20.dp), tint = Term.Ink) }
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(call.caller, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, lineHeight = 20.sp, color = Term.Text)
                    if (isNew) Box(Modifier.size(7.dp).background(Term.Amber, CircleShape))
                }
                if (call.from.isNotEmpty()) Text(call.from, fontFamily = Barlow, fontSize = 13.sp, color = Term.Muted, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("EV-%02d".format(exhibit), style = mono(10, Term.Cyan, spacing = 0f))
                Text(whenLabel, style = mono(12, Term.Text, spacing = 0f))
            }
        }
        Waveform(seed, playing, Modifier.padding(top = 10.dp).fillMaxWidth().height(28.dp))
        Row(Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val progress = remember(playing) { Animatable(0f) }
            LaunchedEffect(playing, duration) { if (playing && duration > 0) progress.animateTo(1f, tween(duration.toInt(), easing = LinearEasing)) }
            Box(Modifier.weight(1f).height(3.dp).background(Term.Muted.copy(alpha = 0.2f))) {
                if (playing) Box(Modifier.fillMaxWidth(progress.value).fillMaxHeight().background(Term.Cyan))
            }
            Text(if (duration > 0) clip(duration) else "—", style = mono(11, Term.Muted, spacing = 0f))
        }
    }
}

/** Bars of a made-up waveform, different for each call; they bounce while it plays. */
@Composable
private fun Waveform(seed: Int, playing: Boolean, modifier: Modifier) {
    val transition = rememberInfiniteTransition(label = "wave")
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.Bottom) {
        repeat(BARS) { i ->
            val height = (6 + abs(sin(i * 0.9 + seed * 1.7) * 14 + sin(i * 0.37 + seed) * 8)).coerceAtMost(28.0).toFloat()
            val bounce = if (playing) transition.animateFloat(
                0.4f, 1f, infiniteRepeatable(tween(400), RepeatMode.Reverse, initialStartOffset = StartOffset((i % 7) * 110)), label = "bar$i",
            ).value else 1f
            Box(
                Modifier.weight(1f).height(height.dp).graphicsLayer { scaleY = bounce; transformOrigin = TransformOrigin(0.5f, 1f) }
                    .background(if (playing) Term.Cyan else Term.Cyan.copy(alpha = 0.35f)),
            )
        }
    }
}
