package com.rukia.chat.infrastructure.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.chat.infrastructure.notifications.VisibleChat
import com.rukia.phone.Kit
import com.rukia.phone.RukiaIcons
import com.rukia.phone.rememberMediaImage
import kotlinx.coroutines.delay

/** Rough time a character "types" before a message appears. */
private fun typingMillis(text: String) = (500L + text.length * 30L).coerceAtMost(2_500L)

/**
 * Messages from index [revealFrom] on are new: the player's own appear at once, the others one by one after a
 * "typing…" pause. Choices only show once everything has been revealed.
 */
@Composable
fun ChatScreen(
    chat: Chat,
    characters: Map<String, Character>,
    revealFrom: Int,
    /** Unread chats other than this one, badged on the back button. */
    otherUnread: Int,
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

    val p = LocalPalette.current
    val solo = characters[chat.participants.singleOrNull()]
    val subtitle = when {
        typingId != null && solo != null -> "typing…"
        typingId != null -> "${characters[typingId]?.name ?: typingId} is typing…"
        solo != null -> if (solo.onlineAt(System.currentTimeMillis())) "online" else solo.status
        else -> (chat.participants.map { characters[it]?.name ?: it } + "You").joinToString()
    }
    val visible = chat.messages.take(shown)

    Box {
        Column(Modifier.fillMaxSize().background(p.background)) {
            Header(chat, characters, subtitle, otherUnread, onBack)
            LazyColumn(
                Modifier.weight(1f),
                state = listState,
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
            ) {
                itemsIndexed(visible) { i, msg ->
                    val prev = visible.getOrNull(i - 1)
                    // A time label whenever the clock moved on, with the day when it changed ("Yesterday 22:33").
                    val newDay = msg.deliverAt > 0 && (prev == null || prev.deliverAt <= 0 || dayOf(prev.deliverAt) != dayOf(msg.deliverAt))
                    if (msg.time.isNotEmpty() && (newDay || msg.time != prev?.time)) {
                        Text(
                            if (newDay) "${dayLabel(msg.deliverAt, System.currentTimeMillis())} ${msg.time}" else msg.time,
                            Modifier.fillMaxWidth().padding(top = if (i == 0) 0.dp else 12.dp, bottom = 2.dp),
                            color = p.subText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center,
                        )
                    }
                    val first = prev == null || prev.from != msg.from || prev.call != null
                    if (msg.call != null) CallPill(msg)
                    else Bubble(msg, characters[msg.from], showName = chat.isGroup && first && !msg.fromPlayer, first = first)
                }
                typingId?.let { id ->
                    item(key = "typing") { TypingBubble(first = visible.lastOrNull()?.from != id) }
                }
            }
            val choices = chat.visibleChoices()
            if (shown == chat.messages.size && ringing == null && choices.isNotEmpty()) {
                Choices(choices, onChoose)
            } else {
                Spacer(Modifier.navigationBarsPadding().height(8.dp))
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

/** Translucent bar: back with the other chats' unread count, then the chat's picture, name and status centered. */
@Composable
private fun Header(chat: Chat, characters: Map<String, Character>, subtitle: String, otherUnread: Int, onBack: () -> Unit) {
    val p = LocalPalette.current
    Column(Modifier.background(p.bar)) {
        Row(Modifier.statusBarsPadding().padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Row(
                Modifier.width(76.dp).heightIn(min = 44.dp).clickable(onClick = onBack).semantics { contentDescription = "Back to Chats" },
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Icon(RukiaIcons.ChevronLeft, null, Modifier.size(26.dp), tint = p.tint)
                if (otherUnread > 0) CountBadge(otherUnread)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                ChatAvatar(chat, characters, 40, showOnline = false)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(chat.displayTitle(characters), fontSize = 13.sp, lineHeight = 16.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    if (subtitle.isNotEmpty()) Text(subtitle, color = p.subText, fontSize = 11.sp, lineHeight = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Spacer(Modifier.width(76.dp))
        }
        HorizontalDivider(thickness = 0.5.dp, color = p.separator)
    }
}

@Composable
private fun Choices(choices: List<IndexedValue<String>>, onChoose: (Int) -> Unit) {
    val p = LocalPalette.current
    // Ignore a second tap that lands before the screen updates with the new choices.
    var picked by remember(choices) { mutableStateOf(false) }
    HorizontalDivider(thickness = 0.5.dp, color = p.separator)
    Column(
        Modifier.fillMaxWidth().background(p.bar).navigationBarsPadding().padding(start = 14.dp, end = 14.dp, top = 10.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Choose your reply", Modifier.padding(horizontal = 4.dp), color = p.subText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        choices.forEach { (i, text) ->
            val shape = RoundedCornerShape(24.dp)
            Row(
                Modifier.fillMaxWidth().heightIn(min = 48.dp).clip(shape).background(p.choiceBackground).border(1.dp, p.choiceBorder, shape)
                    .clickable(enabled = !picked, role = Role.Button) { picked = true; onChoose(i) }
                    .padding(start = 18.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(text, Modifier.weight(1f), color = p.tint, fontSize = 17.sp, fontWeight = FontWeight.Medium)
                Box(Modifier.size(32.dp).background(Kit.Tint, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(RukiaIcons.Send, null, Modifier.size(18.dp), tint = Color.White)
                }
            }
        }
    }
}

/** Rounded bubble; the corner on the speaker's side is tight when it continues a run. */
private fun bubbleShape(mine: Boolean, first: Boolean) = if (mine) {
    RoundedCornerShape(topStart = 20.dp, topEnd = if (first) 20.dp else 6.dp, bottomEnd = 6.dp, bottomStart = 20.dp)
} else {
    RoundedCornerShape(topStart = if (first) 20.dp else 6.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 6.dp)
}

@Composable
private fun Bubble(msg: Message, sender: Character?, showName: Boolean, first: Boolean) {
    val mine = msg.fromPlayer
    Column(
        Modifier.fillMaxWidth().padding(top = if (first) 10.dp else 2.dp),
        horizontalAlignment = if (mine) Alignment.End else Alignment.Start,
    ) {
        if (showName && sender != null) {
            Text(sender.name, Modifier.padding(start = 12.dp, bottom = 2.dp), color = sender.colorValue(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
        // A sent picture, above its text. Until the file is added to the case, only the text shows.
        rememberMediaImage(msg.image)?.let {
            Image(
                it, null, Modifier.padding(bottom = 2.dp).widthIn(max = 270.dp).clip(bubbleShape(mine, first)),
                contentScale = ContentScale.FillWidth,
            )
        }
        Text(
            msg.text,
            Modifier.widthIn(max = 270.dp).background(if (mine) Kit.Tint else LocalPalette.current.inBubble, bubbleShape(mine, first))
                .padding(horizontal = 14.dp, vertical = 9.dp),
            color = if (mine) Color.White else LocalPalette.current.text, fontSize = 16.sp, lineHeight = 21.sp,
        )
    }
}

/** A call in the conversation: a centered pill, green for a call that happened, red for a missed one. */
@Composable
private fun CallPill(msg: Message) {
    val p = LocalPalette.current
    val status = msg.call ?: return
    val missed = status == CallStatus.DECLINED
    val fg = if (missed) p.missedText else p.callText
    Box(Modifier.fillMaxWidth().padding(top = 12.dp), contentAlignment = Alignment.Center) {
        Row(
            Modifier.background(if (missed) p.missedBackground else p.callBackground, RoundedCornerShape(18.dp)).padding(horizontal = 16.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(RukiaIcons.Phone, null, Modifier.size(18.dp), tint = fg)
            Text(listOf(callLabel(status), msg.time).filter { it.isNotEmpty() }.joinToString(" · "), color = fg, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

/** "…" bubble shown where the next message will appear, while its sender types it. */
@Composable
private fun TypingBubble(first: Boolean) {
    val transition = rememberInfiniteTransition(label = "typing")
    Row(
        Modifier.padding(top = if (first) 10.dp else 2.dp).background(LocalPalette.current.inBubble, bubbleShape(mine = false, first = first))
            .padding(horizontal = 14.dp, vertical = 14.dp),
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
