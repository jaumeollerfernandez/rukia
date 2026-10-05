package com.rukia.chat.infrastructure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message

@Composable
fun ChatListScreen(
    chats: List<Chat>,
    characters: Map<String, Character>,
    now: Long,
    onOpen: (Chat) -> Unit,
    onDelete: (Chat) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var deleting by remember { mutableStateOf<Chat?>(null) }

    LazyColumn(contentPadding = contentPadding) {
        // A chat shows up once its first message arrives, e.g. a group someone creates later.
        items(chats.filter { it.arrivedCount(now) > 0 }, key = { it.id }) { chat ->
            ChatRow(chat, characters, now, onClick = { onOpen(chat) }, onLongClick = { deleting = chat })
        }
    }

    deleting?.let { chat ->
        AlertDialog(
            onDismissRequest = { deleting = null },
            title = { Text(if (chat.isGroup) "Delete group?" else "Delete chat?") },
            text = { Text("\"${chat.displayTitle(characters)}\" and its messages will be removed.") },
            confirmButton = { TextButton({ onDelete(chat); deleting = null }) { Text("Delete") } },
            dismissButton = { TextButton({ deleting = null }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun ChatRow(chat: Chat, characters: Map<String, Character>, now: Long, onClick: () -> Unit, onLongClick: () -> Unit) {
    val last = chat.messages.lastOrNull { it.arrivedBy(now) }
    Row(
        Modifier.fillMaxWidth().combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            characters[chat.participants.first()], size = 52,
            online = chat.participants.any { characters[it]?.online == true },
        )
        // Unread chats stand out like in WhatsApp: bold name and preview, green time and a count badge.
        val unread = chat.unreadCount(now)
        Column(Modifier.padding(start = 14.dp).weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    chat.displayTitle(characters), Modifier.weight(1f), fontSize = 17.sp,
                    fontWeight = if (unread > 0) FontWeight.Bold else FontWeight.Medium,
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                )
                Text(
                    last?.time.orEmpty(), fontSize = 12.sp,
                    color = if (unread > 0) Accent else LocalPalette.current.subText,
                    fontWeight = if (unread > 0) FontWeight.Bold else FontWeight.Normal,
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    last?.let { preview(chat, characters, it) }.orEmpty(), Modifier.weight(1f),
                    color = if (unread > 0) MaterialTheme.colorScheme.onBackground else LocalPalette.current.subText,
                    fontWeight = if (unread > 0) FontWeight.SemiBold else FontWeight.Normal,
                    fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis,
                )
                if (unread > 0) {
                    Box(
                        Modifier.padding(start = 8.dp).defaultMinSize(minWidth = 22.dp, minHeight = 22.dp)
                            .background(Accent, CircleShape).padding(horizontal = 6.dp),
                        contentAlignment = Alignment.Center,
                    ) { Text("$unread", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

private fun preview(chat: Chat, characters: Map<String, Character>, msg: Message) = when {
    msg.call != null -> "📞 ${callLabel(msg.call)}"
    msg.fromPlayer -> "✓✓ ${msg.text}"
    chat.isGroup -> "${characters[msg.from]?.name ?: msg.from}: ${msg.text}"
    else -> msg.text
}

@Composable
fun ContactsScreen(characters: Map<String, Character>, contentPadding: PaddingValues = PaddingValues(), onOpen: (Character) -> Unit) {
    LazyColumn(contentPadding = contentPadding) {
        items(characters.values.sortedBy { it.name }, key = { it.id }) { c ->
            Row(
                Modifier.fillMaxWidth().clickable { onOpen(c) }.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(c, size = 48, online = c.online)
                Column(Modifier.padding(start = 14.dp)) {
                    Text(c.name, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    val status = if (c.online) "online" else c.status
                    if (status.isNotEmpty()) Text(status, color = LocalPalette.current.subText, fontSize = 14.sp, maxLines = 1)
                }
            }
        }
    }
}
