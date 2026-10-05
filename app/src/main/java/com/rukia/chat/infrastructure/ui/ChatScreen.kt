package com.rukia.chat.infrastructure.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.chat.infrastructure.notifications.VisibleChat
import kotlinx.coroutines.delay

/** Rough time a character "types" before a message appears. */
private const val AVATAR_SIZE = 32

private fun typingMillis(text: String) = (500L + text.length * 30L).coerceAtMost(2_500L)

/**
 * Messages from index [revealFrom] on are new: the player's own appear at once, the others one by one after a
 * "typing…" pause. Choices only show once everything has been revealed.
 */
@Composable
fun ChatScreen(
    chat: Chat,
    characters: Map<String, Character>,
    profile: PlayerProfile,
    revealFrom: Int,
    onBack: () -> Unit,
    onRead: (count: Int) -> Unit,
    onChoose: (Int) -> Unit,
    onEndCall: (answered: Boolean) -> Unit,
) {
    var shown by remember(chat.id) { mutableIntStateOf(minOf(revealFrom, chat.arrivedCount(System.currentTimeMillis()))) }
    // While this chat is on screen, its arriving messages show here instead of as notifications.
    DisposableEffect(chat.id) {
        VisibleChat.id = chat.id
        onDispose { if (VisibleChat.id == chat.id) VisibleChat.id = null }
    }
    var typingId by remember { mutableStateOf<String?>(null) }
    // A call rings once its message is revealed; the rest of the conversation waits until it ends.
    val ringing = chat.messages.take(shown).lastOrNull { it.call == CallStatus.RINGING }
    LaunchedEffect(chat.messages.size, ringing == null) {
        while (shown < chat.messages.size && chat.messages.take(shown).none { it.call == CallStatus.RINGING }) {
            val next = chat.messages[shown]
            if (!next.fromPlayer) {
                // A delayed message waits silently until shortly before it arrives, then "types".
                val typing = typingMillis(next.text)
                delay((next.deliverAt - System.currentTimeMillis() - typing).coerceAtLeast(0))
                typingId = next.from
                delay(typing)
            }
            typingId = null
            shown++
        }
    }
    val listState = rememberLazyListState()
    // Everything on screen counts as read.
    LaunchedEffect(shown) { onRead(shown) }
    // Keep the newest bubble in view, including the typing dots.
    LaunchedEffect(shown, typingId) {
        val last = shown - 1 + if (typingId != null) 1 else 0
        if (last >= 0) listState.animateScrollToItem(last)
    }

    val solo = characters[chat.participants.singleOrNull()]
    val subtitle = when {
        typingId != null && solo != null -> "typing…"
        typingId != null -> "${characters[typingId]?.name ?: typingId} is typing…"
        solo != null -> if (solo.online) "online" else solo.status
        else -> (chat.participants.map { characters[it]?.name ?: it } + "You").joinToString()
    }
    val visible = chat.messages.take(shown)

    Box {
        Column(Modifier.fillMaxSize().background(LocalPalette.current.wallpaper)) {
            GreenBar(
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Avatar(characters[chat.participants.first()], size = 38)
                        Column(Modifier.padding(start = 10.dp)) {
                            Text(chat.displayTitle(characters), fontSize = 17.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            if (subtitle.isNotEmpty()) {
                                Text(subtitle, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
                },
            )
            LazyColumn(
                Modifier.weight(1f).padding(horizontal = 8.dp),
                state = listState,
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                itemsIndexed(visible) { i, msg ->
                    val firstOfRun = i == 0 || visible[i - 1].from != msg.from
                    Bubble(msg, characters[msg.from], profile, showName = chat.isGroup && firstOfRun, tail = firstOfRun)
                }
                typingId?.let { id ->
                    item(key = "typing") { TypingBubble(characters[id], tail = visible.lastOrNull()?.from != id) }
                }
            }
            Column(Modifier.navigationBarsPadding().padding(horizontal = 8.dp, vertical = 6.dp)) {
                if (shown == chat.messages.size && ringing == null) Choices(chat.choices, onChoose)
            }
        }
        if (ringing != null) {
            // Answering a call with audio plays it before hanging up; without audio it ends right away.
            var answered by remember(ringing) { mutableStateOf(false) }
            val audio = ringing.audio
            if (answered && audio != null) {
                OngoingCallScreen(characters[ringing.from], audio, onHangUp = { onEndCall(true) })
            } else {
                IncomingCallScreen(
                    characters[ringing.from],
                    onAnswer = { if (audio != null) answered = true else onEndCall(true) },
                    onDecline = { onEndCall(false) },
                )
            }
        }
    }
}

@Composable
private fun Choices(choices: List<String>, onChoose: (Int) -> Unit) {
    // Ignore a second tap that lands before the screen updates with the new choices.
    var picked by remember(choices) { mutableStateOf(false) }
    choices.forEachIndexed { i, text ->
        val shape = RoundedCornerShape(20.dp)
        Text(
            text,
            Modifier.fillMaxWidth().padding(vertical = 3.dp)
                .shadow(1.dp, shape).clip(shape)
                .background(LocalPalette.current.inBubble)
                .clickable(enabled = !picked) { picked = true; onChoose(i) }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            color = LocalPalette.current.primary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

/** "…" bubble shown where [sender]'s next message will appear, while they type it. */
@Composable
private fun TypingBubble(sender: Character?, tail: Boolean) {
    val shape = RoundedCornerShape(topStart = if (tail) 0.dp else 8.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp)
    val transition = rememberInfiniteTransition(label = "typing")
    Row(Modifier.fillMaxWidth().padding(top = if (tail) 6.dp else 2.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(Modifier.size(AVATAR_SIZE.dp)) { if (tail) Avatar(sender, AVATAR_SIZE) }
        Row(
            Modifier.shadow(0.5.dp, shape).background(LocalPalette.current.inBubble, shape).padding(horizontal = 14.dp, vertical = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            repeat(3) { i ->
                // Each dot pulses a little after the previous one, so the three ripple.
                val alpha by transition.animateFloat(
                    0.25f, 1f,
                    infiniteRepeatable(tween(450), RepeatMode.Reverse, initialStartOffset = StartOffset(i * 150)),
                    label = "dot$i",
                )
                Box(Modifier.size(8.dp).alpha(alpha).background(LocalPalette.current.subText, CircleShape))
            }
        }
    }
}

@Composable
private fun Bubble(msg: Message, sender: Character?, profile: PlayerProfile, showName: Boolean, tail: Boolean) {
    val mine = msg.fromPlayer
    // Square corner on the speaker's side of the first bubble in a run fakes the WhatsApp tail.
    val shape = RoundedCornerShape(
        topStart = if (tail && !mine) 0.dp else 8.dp,
        topEnd = if (tail && mine) 0.dp else 8.dp,
        bottomStart = 8.dp, bottomEnd = 8.dp,
    )
    // Avatar only on the first bubble of a run; the rest keep the same indent.
    val avatar = @Composable {
        Box(Modifier.size(AVATAR_SIZE.dp)) {
            if (tail) if (mine) PlayerAvatar(profile, AVATAR_SIZE) else Avatar(sender, AVATAR_SIZE)
        }
    }
    Row(
        Modifier.fillMaxWidth().padding(top = if (tail) 6.dp else 2.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, if (mine) Alignment.End else Alignment.Start),
    ) {
        if (!mine) avatar()
        Column(
            Modifier.widthIn(min = 80.dp, max = 280.dp)
                .shadow(0.5.dp, shape)
                .background(if (mine) LocalPalette.current.outBubble else LocalPalette.current.inBubble, shape)
                .padding(start = 9.dp, end = 9.dp, top = 6.dp, bottom = 4.dp)
        ) {
            if (showName && !mine && sender != null) {
                Text(sender.name, color = sender.colorValue(), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
            if (msg.call != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(34.dp).background(callColor(msg.call).copy(alpha = 0.15f), CircleShape), contentAlignment = Alignment.Center) {
                        Icon(Icons.Filled.Call, null, Modifier.size(18.dp), tint = callColor(msg.call))
                    }
                    Text(callLabel(msg.call), Modifier.padding(start = 10.dp), fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            } else {
                Text(msg.text, fontSize = 15.sp)
            }
            Row(Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                Text(msg.time, color = LocalPalette.current.subText, fontSize = 11.sp)
                if (mine) Text(" ✓✓", color = ReadTicks, fontSize = 11.sp)
            }
        }
        if (mine) avatar()
    }
}
