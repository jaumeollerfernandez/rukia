package com.rukia.chat.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.LocalCaseId
import com.rukia.phone.infrastructure.ui.RukiaIcons
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun ChatListScreen(
    chats: List<Chat>,
    characters: Map<String, Character>,
    now: Long,
    listState: LazyListState,
    onOpen: (Chat) -> Unit,
    onDelete: (Chat) -> Unit,
) {
    var deleting by remember { mutableStateOf<Chat?>(null) }

    LazyColumn(Modifier.fillMaxSize(), listState) {
        item { LargeTitle(stringResource(R.string.tab_chats)) }
        // A chat shows up once its first message arrives, e.g. a group someone creates later; the latest conversation goes first.
        val shown = chats.filter { it.arrivedCount(now) > 0 }.sortedByDescending { it.lastArrived(now)?.deliverAt ?: 0 }
        items(shown, key = { it.id }) { chat ->
            ChatRow(chat, characters, now, onClick = { onOpen(chat) }, onLongClick = { deleting = chat })
        }
    }

    deleting?.let { chat ->
        AlertDialog(
            onDismissRequest = { deleting = null },
            title = { Text(stringResource(if (chat.isGroup) R.string.delete_group_q else R.string.delete_chat_q)) },
            text = { Text(stringResource(R.string.delete_chat_text, chat.displayTitle(characters))) },
            confirmButton = { TextButton({ onDelete(chat); deleting = null }) { Text(stringResource(R.string.delete), color = LocalPalette.current.red) } },
            dismissButton = { TextButton({ deleting = null }) { Text(stringResource(R.string.cancel)) } },
        )
    }
}

/** The chat's picture: the other person's avatar (tap it to see their photo up close), or two overlapping ones for a group. */
@Composable
fun ChatAvatar(chat: Chat, characters: Map<String, Character>, size: Int, showOnline: Boolean = true) {
    if (chat.isGroup) GroupAvatar(characters[chat.participants[0]], characters[chat.participants[1]], size)
    else Avatar(characters[chat.participants.first()], size, online = showOnline && characters[chat.participants.first()]?.onlineAt(CaseClock.now(LocalCaseId.current)) == true, zoomable = true)
}

@Composable
private fun ChatRow(chat: Chat, characters: Map<String, Character>, now: Long, onClick: () -> Unit, onLongClick: () -> Unit) {
    val p = LocalPalette.current
    val last = chat.lastArrived(now)
    val unread = chat.unreadCount(now)
    Row(
        Modifier.fillMaxWidth().combinedClickable(onClick = onClick, onLongClick = onLongClick).padding(start = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChatAvatar(chat, characters, 54)
        Column(Modifier.weight(1f)) {
            Column(Modifier.padding(end = 20.dp, bottom = 10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(chat.displayTitle(characters), Modifier.weight(1f), fontSize = 17.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(
                        last?.let { listTime(it, now, stringResource(R.string.today), stringResource(R.string.yesterday)) }.orEmpty(), Modifier.padding(start = 8.dp), fontSize = 14.sp,
                        color = if (unread > 0) p.tint else p.subText, fontWeight = if (unread > 0) FontWeight.SemiBold else FontWeight.Normal,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.weight(1f)) { last?.let { Preview(chat, characters, it) } }
                    // Hint that the player can answer this chat now.
                    if (chat.arrivedCount(now) == chat.messages.size && chat.visibleChoices().isNotEmpty()) {
                        Icon(RukiaIcons.Mail, stringResource(R.string.can_reply), Modifier.size(18.dp), tint = p.tint)
                    }
                    if (unread > 0) CountBadge(unread)
                }
            }
            HorizontalDivider(thickness = 0.5.dp, color = p.separator)
        }
    }
}

@Composable
private fun Preview(chat: Chat, characters: Map<String, Character>, msg: Message) {
    val p = LocalPalette.current
    if (msg.call != null) {
        val color = if (msg.call == CallStatus.DECLINED) p.red else p.preview
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Icon(RukiaIcons.Phone, null, Modifier.size(15.dp), tint = color)
            Text(callLabel(msg.call), color = color, fontSize = 15.sp, lineHeight = 20.sp, maxLines = 1)
        }
        return
    }
    val sender = when {
        msg.fromPlayer -> stringResource(R.string.you)
        chat.isGroup -> characters[msg.from]?.name ?: msg.from
        else -> null
    }
    Text(
        buildAnnotatedString {
            sender?.let { withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = p.text)) { append("$it: ") } }
            append(msg.text)
        },
        color = p.preview, fontSize = 15.sp, lineHeight = 20.sp, maxLines = 2, overflow = TextOverflow.Ellipsis,
    )
}

/** Blue unread count, as on each chat row and the conversation's back button. */
@Composable
fun CountBadge(count: Int) {
    Box(
        Modifier.defaultMinSize(22.dp, 22.dp).background(Kit.Tint, RoundedCornerShape(11.dp)).padding(horizontal = 7.dp),
        contentAlignment = Alignment.Center,
    ) { Text("$count", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold) }
}

@Composable
fun ContactsScreen(characters: Map<String, Character>, listState: LazyListState, onOpen: (Character) -> Unit) {
    val p = LocalPalette.current
    val people = characters.values.filterNot { it.hidden }.sortedBy { it.name }
    LazyColumn(Modifier.fillMaxSize(), listState) {
        item {
            LargeTitle(stringResource(R.string.tab_contacts), Modifier.padding(bottom = 0.dp))
            Text(stringResource(R.string.contacts_count, people.size), Modifier.padding(start = 20.dp, bottom = 10.dp), color = p.subText, fontSize = 15.sp)
        }
        people.groupBy { it.name.take(1).uppercase() }.forEach { (letter, group) ->
            item(key = "letter-$letter") {
                HorizontalDivider(thickness = 0.5.dp, color = p.separator)
                Text(
                    letter, Modifier.fillMaxWidth().background(p.section).padding(start = 20.dp, top = 6.dp, bottom = 2.dp),
                    color = p.subText, fontSize = 13.sp, fontWeight = FontWeight.Bold,
                )
            }
            items(group, key = { it.id }) { c ->
                PersonRow(c, if (c.onlineAt(CaseClock.now(LocalCaseId.current))) stringResource(R.string.online) else c.status, onClick = { onOpen(c) })
            }
        }
    }
}

/** Avatar, name and one line under it, as in Contacts and Calls. */
@Composable
fun PersonRow(c: Character?, subtitle: String, nameColor: Color = Color.Unspecified, subtitleIcon: (@Composable () -> Unit)? = null, onClick: (() -> Unit)? = null) {
    Row(
        Modifier.fillMaxWidth().then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier).padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(c, 42, online = c?.onlineAt(CaseClock.now(LocalCaseId.current)) == true, zoomable = true)
        Column(Modifier.weight(1f)) {
            Text(c?.name.orEmpty(), color = nameColor, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            if (subtitle.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    subtitleIcon?.invoke()
                    Text(subtitle, color = LocalPalette.current.subText, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

internal fun dayOf(millis: Long) = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()

/** The day of [millis] as a messaging app says it: "Today", "Yesterday", the weekday within the last week, else the date. */
internal fun dayLabel(millis: Long, now: Long, today: String = "Today", yesterday: String = "Yesterday"): String {
    val day = dayOf(millis)
    return when (ChronoUnit.DAYS.between(day, dayOf(now))) {
        0L -> today
        1L -> yesterday
        in 2L..6L -> day.format(DateTimeFormatter.ofPattern("EEEE")).replaceFirstChar { it.titlecase() }
        else -> day.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
    }
}

/** When a message arrived, for the chat list: its time if it was today, else its day. Messages without a timestamp show their time as saved. */
internal fun listTime(msg: Message, now: Long, today: String = "Today", yesterday: String = "Yesterday") =
    if (msg.deliverAt <= 0 || dayOf(msg.deliverAt) == dayOf(now)) msg.time else dayLabel(msg.deliverAt, now, today, yesterday)
