package com.rukia.calls.infrastructure.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.rukia.calls.domain.model.VoiceCall
import com.rukia.calls.domain.port.CallLog
import com.rukia.calls.infrastructure.CallsModule
import com.rukia.phone.domain.model.caseTimeLabel
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.LocalDarkMode
import com.rukia.phone.infrastructure.media.mediaDurationMillis
import com.rukia.phone.infrastructure.media.playMedia
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.ZoneId

val CallsGreen = Color(0xFF34C759)
/** The app's colors, light or dark as picked in the game's Options. */
private class CallsPalette(val background: Color, val card: Color, val text: Color, val subText: Color, val separator: Color, val segment: Color, val segmentOn: Color)

private val Light = CallsPalette(Color(0xFFF2F2F7), Color.White, Color(0xFF111114), Color(0xFF6C6C70), Color(0xFFE2E2E7), Color(0xFFE3E3E8), Color.White)
private val Dark = CallsPalette(Color.Black, Color(0xFF1C1C1E), Color(0xFFF2F2F7), Color(0xFFA1A1A8), Color(0xFF38383C), Color(0xFF2C2C30), Color(0xFF48484E))

private fun clip(millis: Long) = (millis / 1000).let { "%d:%02d".format(it / 60, it % 60) }

/**
 * The Calls app: every call to the phone, newest first. Answered ones were heard live; the rest went to voicemail and
 * wait here, marked until they're played. Tap play to listen again; [log] is the phone's call history.
 */
@Composable
fun CallsApp(caseId: String, log: CallLog) {
    val dark = LocalDarkMode.current
    val c = if (dark) Dark else Light
    SystemBars(lightBottomIcons = dark, lightTopIcons = dark)
    val context = LocalContext.current
    val m = remember(log) { CallsModule(log) }
    var tick by remember { mutableIntStateOf(0) } // bumped after listening, to update the marks
    val calls by produceState(m.listCalls(), m, tick) { while (true) { value = withContext(Dispatchers.IO) { m.listCalls() }; delay(3_000) } }
    var voicemailOnly by rememberSaveable { mutableStateOf(false) }
    var playing by remember { mutableStateOf<VoiceCall?>(null) }
    DisposableEffect(playing) {
        val audio = playing?.audio
        val player = audio?.let { runCatching { playMedia(context, caseId, it) { playing = null } }.getOrNull() }
        onDispose { player?.release() }
    }
    val durations by produceState(emptyMap<String, Long>(), calls) {
        value = withContext(Dispatchers.IO) { calls.mapNotNull { it.audio }.distinct().associateWith { mediaDurationMillis(context, caseId, it) ?: 0L } }
    }
    val start = remember(caseId) { CaseClock.start(context, caseId) }
    val zone = remember { ZoneId.systemDefault() }
    val shown = calls.filter { !voicemailOnly || it.voicemail }

    Column(Modifier.fillMaxSize().background(c.background).statusBarsPadding()) {
        Text(
            stringResource(R.string.app_calls), Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 10.dp),
            fontSize = 34.sp, lineHeight = 40.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.6).sp, color = c.text,
        )
        Row(Modifier.padding(horizontal = 16.dp).fillMaxWidth().background(c.segment, RoundedCornerShape(9.dp)).padding(2.dp).selectableGroup()) {
            listOf(false to R.string.calls_all, true to R.string.calls_voicemail).forEach { (only, label) ->
                val on = voicemailOnly == only
                Box(
                    Modifier.weight(1f).height(32.dp).clip(RoundedCornerShape(7.dp)).background(if (on) c.segmentOn else Color.Transparent)
                        .selectable(on, role = Role.Tab) { voicemailOnly = only },
                    contentAlignment = Alignment.Center,
                ) { Text(stringResource(label), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = c.text) }
            }
        }
        if (shown.isEmpty()) Text(
            stringResource(if (voicemailOnly) R.string.calls_no_voicemail else R.string.no_calls),
            Modifier.padding(20.dp), color = c.subText, fontSize = 15.sp,
        )
        LazyColumn(Modifier.fillMaxSize().padding(top = 12.dp), contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp)) {
            items(shown, key = { "${it.chatId}:${it.at}" }) { call ->
                val on = playing == call
                CallRow(c, call, caseTimeLabel(call.at, start, zone).replace(" ", " · "), durations[call.audio] ?: 0L, on) {
                    if (on) playing = null
                    else {
                        m.listenTo(call)
                        tick++
                        playing = call
                    }
                }
                HorizontalDivider(Modifier.padding(start = 60.dp), thickness = 0.5.dp, color = c.separator)
            }
        }
    }
}

/** One call: the caller's initial, who and when, answered or voicemail, its length, and play. A new voicemail has a dot. */
@Composable
private fun CallRow(c: CallsPalette, call: VoiceCall, whenLabel: String, duration: Long, playing: Boolean, onPlay: () -> Unit) {
    val new = call.voicemail && !call.heard
    val label = stringResource(if (playing) R.string.recording_pause else R.string.recording_play, call.caller)
    Column(Modifier.fillMaxWidth().background(c.card).padding(horizontal = 12.dp, vertical = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(8.dp).background(if (new) Kit.Tint else Color.Transparent, CircleShape))
            val tint = runCatching { Color(android.graphics.Color.parseColor(call.color)) }.getOrDefault(Color(0xFF7E57C2))
            Box(Modifier.size(40.dp).background(tint, CircleShape), contentAlignment = Alignment.Center) {
                Text(call.caller.firstOrNull { it.isLetter() }?.uppercase() ?: "?", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            }
            Column(Modifier.weight(1f)) {
                Text(call.caller, fontSize = 17.sp, fontWeight = if (new) FontWeight.Bold else FontWeight.Medium, color = c.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(if (call.voicemail) RukiaIcons.Voicemail else RukiaIcons.Phone, null, Modifier.size(14.dp), tint = c.subText)
                    Text(
                        listOf(stringResource(if (call.voicemail) R.string.call_voicemail else R.string.call_answered), whenLabel).joinToString(" · "),
                        fontSize = 13.sp, color = c.subText, maxLines = 1, overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            if (duration > 0) Text(clip(duration), fontSize = 13.sp, color = c.subText)
            if (call.audio != null) Box(
                Modifier.size(36.dp).background(if (playing) Kit.Tint else CallsGreen, CircleShape).clickable(role = Role.Button, onClick = onPlay)
                    .semantics { contentDescription = label },
                contentAlignment = Alignment.Center,
            ) { Icon(if (playing) RukiaIcons.Stop else RukiaIcons.Play, null, Modifier.size(16.dp), tint = Color.White) }
        }
        if (playing) {
            val progress = remember { Animatable(0f) }
            LaunchedEffect(duration) { if (duration > 0) progress.animateTo(1f, tween(duration.toInt(), easing = LinearEasing)) }
            Box(Modifier.padding(start = 60.dp, top = 8.dp).fillMaxWidth().height(3.dp).background(c.separator)) {
                Box(Modifier.fillMaxWidth(progress.value).fillMaxHeight().background(Kit.Tint))
            }
        }
    }
}
