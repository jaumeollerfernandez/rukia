package com.rukia.chat.infrastructure.ui

import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.rukia.R
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.rukia.chat.domain.model.PLAYER_ID
import com.rukia.chat.infrastructure.notifications.VisibleChat
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.ui.ImageViewer
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.LocalCaseId
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.media.rememberMediaImage
import kotlinx.coroutines.delay

/** Rough time a character "types" before a message appears. */
private fun typingMillis(text: String) = (500L + text.length * 30L).coerceAtMost(2_500L)

/** How much of a chat is on screen: [shown] messages, who is typing the next one, and the call ringing, if any. */
class Reveal(val shown: Int, val typingId: String?, val ringing: Message?)

/**
 * Messages from index [revealFrom] on are new: the player's own appear at once, the others one by one after a
 * "typing…" pause. A call rings once its message is revealed; the rest waits until it ends. Everything shown counts
 * as read ([onRead]), and while the chat is on screen its messages don't come as notifications.
 */
@Composable
fun rememberReveal(chat: Chat, revealFrom: Int, onRead: (count: Int) -> Unit): Reveal {
    val caseId = LocalCaseId.current
    var shown by rememberSaveable(chat.id) { mutableIntStateOf(minOf(revealFrom, chat.arrivedCount(CaseClock.now(caseId)))) }
    DisposableEffect(chat.id) {
        VisibleChat.id = chat.id
        onDispose { if (VisibleChat.id == chat.id) VisibleChat.id = null }
    }
    var typingId by remember { mutableStateOf<String?>(null) }
    val ringing = chat.messages.take(shown).lastOrNull { it.call == CallStatus.RINGING }
    LaunchedEffect(chat.messages.size, ringing == null) {
        while (shown < chat.messages.size && chat.messages.take(shown).none { it.call == CallStatus.RINGING }) {
            val next = chat.messages[shown]
            if (!next.fromPlayer) {
                // A delayed message waits silently until shortly before it arrives, then "types".
                val typing = typingMillis(next.text)
                // Checked every second rather than slept through, so a debug case skipping time ahead shows it at once.
                while (next.deliverAt - CaseClock.now(caseId) - typing > 0) delay(minOf(next.deliverAt - CaseClock.now(caseId) - typing, 1_000))
                typingId = next.from
                delay(typing)
            }
            typingId = null
            shown++
        }
    }
    LaunchedEffect(shown) { onRead(shown) }
    return Reveal(shown, typingId, ringing)
}

/**
 * Messages from index [revealFrom] on are new: the player's own appear at once, the others one by one after a
 * "typing…" pause. Choices only show once everything has been revealed. Calls aren't shown: they ring over the whole
 * phone and live in the Calls app; while one rings, what follows waits.
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
) {
    val caseId = LocalCaseId.current
    val reveal = rememberReveal(chat, revealFrom, onRead)
    val shown = reveal.shown
    val typingId = reveal.typingId
    val ringing = reveal.ringing
    val listState = rememberLazyListState()
    // Keep the newest bubble in view, including the typing dots.
    LaunchedEffect(shown, typingId) {
        val last = shown - 1 + if (typingId != null) 1 else 0
        if (last >= 0) listState.animateScrollToItem(last)
    }

    val p = LocalPalette.current
    val solo = characters[chat.participants.singleOrNull()]
    val subtitle = when {
        typingId != null && solo != null -> stringResource(R.string.typing)
        typingId != null -> stringResource(R.string.typing_name, characters[typingId]?.name ?: typingId.orEmpty())
        solo != null -> if (solo.onlineAt(CaseClock.now(caseId))) stringResource(R.string.online) else solo.status
        else -> (chat.participants.map { characters[it]?.name ?: it } + stringResource(R.string.you)).joinToString()
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
                    // A day label when the day changed; each bubble carries its own time.
                    val newDay = msg.deliverAt > 0 && (prev == null || prev.deliverAt <= 0 || dayOf(prev.deliverAt) != dayOf(msg.deliverAt))
                    if (newDay) {
                        Text(
                            dayLabel(msg.deliverAt, CaseClock.now(caseId), stringResource(R.string.today), stringResource(R.string.yesterday)),
                            Modifier.fillMaxWidth().padding(top = if (i == 0) 0.dp else 12.dp, bottom = 2.dp),
                            color = p.subText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center,
                        )
                    }
                    val first = prev == null || prev.from != msg.from || prev.call != null
                    if (msg.call == null) Bubble(msg, characters[msg.from], showName = chat.isGroup && first && !msg.fromPlayer, first = first)
                }
                typingId?.let { id ->
                    item(key = "typing") { TypingBubble(characters[id], first = visible.lastOrNull()?.from != id) }
                }
            }
            val choices = chat.visibleChoices()
            if (shown == chat.messages.size && ringing == null && choices.isNotEmpty()) {
                Choices(choices, onChoose)
            } else {
                Spacer(Modifier.navigationBarsPadding().height(8.dp))
            }
        }
    }
}

/** Translucent bar: back with the other chats' unread count, then the chat's picture, name and status centered. */
@Composable
private fun Header(chat: Chat, characters: Map<String, Character>, subtitle: String, otherUnread: Int, onBack: () -> Unit) {
    val p = LocalPalette.current
    val backLabel = stringResource(R.string.back_to_chats)
    var showMembers by remember { mutableStateOf(false) }
    if (showMembers) {
        AlertDialog(
            onDismissRequest = { showMembers = false },
            confirmButton = { TextButton({ showMembers = false }) { Text(stringResource(android.R.string.ok)) } },
            title = { Text(chat.displayTitle(characters)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    chat.participants.forEach { id ->
                        val c = characters[id]
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Avatar(c, 40)
                            Text(c?.name ?: id, fontWeight = FontWeight.Medium)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Avatar(characters[PLAYER_ID], 40)
                        Text(stringResource(R.string.you), fontWeight = FontWeight.Medium)
                    }
                }
            },
        )
    }
    Column(Modifier.background(p.bar)) {
        Row(Modifier.statusBarsPadding().padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Row(
                Modifier.width(76.dp).heightIn(min = 44.dp).clickable(onClick = onBack).semantics { contentDescription = backLabel },
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Icon(RukiaIcons.ChevronLeft, null, Modifier.size(26.dp), tint = p.tint)
                if (otherUnread > 0) CountBadge(otherUnread)
            }
            Column(
                Modifier.weight(1f).then(if (chat.isGroup) Modifier.clickable { showMembers = true } else Modifier),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
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
    // Many replies cover the conversation: they can be folded down to read it, and opened again.
    var folded by rememberSaveable(choices) { mutableStateOf(false) }
    HorizontalDivider(thickness = 0.5.dp, color = p.separator)
    Column(
        Modifier.fillMaxWidth().background(p.bar).navigationBarsPadding().padding(start = 14.dp, end = 14.dp, top = 10.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FoldHeader(folded, choices.size, { folded = !folded }) {
            Text(stringResource(R.string.choose_reply), Modifier.padding(horizontal = 4.dp), color = p.subText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
        if (!folded) choices.forEach { (i, text) ->
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

/**
 * The replies' header: [title], and a button that folds them down or opens them again. With only a couple of replies
 * there's nothing to fold, so it's just the title. When folded it says how many are waiting ([count]).
 */
@Composable
internal fun FoldHeader(folded: Boolean, count: Int, onToggle: () -> Unit, color: Color = LocalPalette.current.tint, title: @Composable () -> Unit) {
    if (count <= FOLD_FROM && !folded) return title()
    val label = stringResource(if (folded) R.string.choices_show else R.string.choices_hide)
    Row(
        Modifier.fillMaxWidth().heightIn(min = 36.dp).clickable(role = Role.Button, onClick = onToggle).semantics { contentDescription = label },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) { title() }
        if (folded) Text(pluralStringResource(R.plurals.choices_waiting, count, count), color = color, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Icon(if (folded) RukiaIcons.ChevronUp else RukiaIcons.ChevronDown, null, Modifier.padding(start = 6.dp).size(22.dp), tint = color)
    }
}

/** From how many replies they can be folded. */
private const val FOLD_FROM = 2

/** Rounded bubble; the corner on the speaker's side is tight when it continues a run. */
private fun bubbleShape(mine: Boolean, first: Boolean) = if (mine) {
    RoundedCornerShape(topStart = 20.dp, topEnd = if (first) 20.dp else 6.dp, bottomEnd = 6.dp, bottomStart = 20.dp)
} else {
    RoundedCornerShape(topStart = if (first) 20.dp else 6.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 6.dp)
}

@Composable
private fun Bubble(msg: Message, sender: Character?, showName: Boolean, first: Boolean) {
    val mine = msg.fromPlayer
    // As in WhatsApp: the sender's picture sits beside the first bubble of a run; the rest line up under it without one.
    Row(
        Modifier.fillMaxWidth().padding(top = if (first) 10.dp else 2.dp),
        horizontalArrangement = if (mine) Arrangement.End else Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
    if (!mine) { if (first) Avatar(sender, 28) else Spacer(Modifier.size(28.dp)) }
    Column(horizontalAlignment = if (mine) Alignment.End else Alignment.Start) {
        if (showName && sender != null) {
            Text(sender.name, Modifier.padding(start = 12.dp, bottom = 2.dp), color = sender.colorValue(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
        // A sent picture, above its text. Until the file is added to the case, only the text shows.
        var viewing by remember { mutableStateOf(false) }
        rememberMediaImage(msg.image)?.let {
            Image(
                it, null,
                Modifier.padding(bottom = 2.dp).widthIn(max = 270.dp).clip(bubbleShape(mine, first)).clickable(role = Role.Image) { viewing = true },
                contentScale = ContentScale.FillWidth,
            )
        }
        if (viewing && msg.image != null) ImageViewer(msg.image) { viewing = false }
        val fg = if (mine) Color.White else LocalPalette.current.text
        Column(
            Modifier.widthIn(max = 270.dp).background(if (mine) Kit.Tint else LocalPalette.current.inBubble, bubbleShape(mine, first))
                .padding(start = 14.dp, end = 12.dp, top = 9.dp, bottom = 6.dp),
        ) {
            Text(msg.text, color = fg, fontSize = 16.sp, lineHeight = 21.sp)
            if (msg.time.isNotEmpty()) Text(msg.time, Modifier.align(Alignment.End), color = fg.copy(alpha = 0.65f), fontSize = 11.sp, lineHeight = 14.sp)
        }
    }
    }
}

/** "…" bubble shown where the next message will appear, while its sender types it, with their picture on its left. */
@Composable
private fun TypingBubble(sender: Character?, first: Boolean) {
    val transition = rememberInfiniteTransition(label = "typing")
    Row(Modifier.padding(top = if (first) 10.dp else 2.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        if (first) Avatar(sender, 28) else Spacer(Modifier.size(28.dp))
        Row(
            Modifier.background(LocalPalette.current.inBubble, bubbleShape(mine = false, first = first))
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
}
