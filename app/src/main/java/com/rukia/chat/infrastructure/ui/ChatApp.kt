package com.rukia.chat.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.chat.infrastructure.notifications.VisibleChat
import com.rukia.phone.AppLaunch
import com.rukia.phone.SystemBars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ChatApp(caseId: String) {
    val context = LocalContext.current.applicationContext
    val m = remember(caseId) { ChatModule.of(context, caseId) }
    // Lets notifications know which case is on screen.
    DisposableEffect(caseId) {
        VisibleChat.caseId = caseId
        onDispose { if (VisibleChat.caseId == caseId) VisibleChat.caseId = null }
    }
    var profile by remember { mutableStateOf(m.getProfile()) }
    val scope = rememberCoroutineScope()

    RukiaTheme(dark = profile.darkMode) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            // Story chats play their opening lines right away, so they show up as unread messages.
            var chats by remember(m) {
                m.advanceAll()
                mutableStateOf(m.listChats())
            }
            // The story keeps moving while the app is open (timed lines, expired choices): pick up what it wrote.
            LaunchedEffect(m) {
                while (true) {
                    delay(5_000)
                    chats = withContext(Dispatchers.IO) { m.advanceAll(); m.listChats() }
                }
            }
            var openId by rememberSaveable { mutableStateOf<String?>(null) }
            // Messages from this index on are animated in; after a restart everything is shown at once.
            var revealFrom by rememberSaveable { mutableIntStateOf(Int.MAX_VALUE) }
            val open = chats.find { it.id == openId }
            // The call screen restyles the system bars; re-apply ours once a call ends.
            SystemBars(lightBottomIcons = profile.darkMode, lightTopIcons = profile.darkMode, key = open?.ringingCall)
            var tab by rememberSaveable { mutableStateOf(Tab.Chats) }
            val openChat = { chat: Chat ->
                // What already arrived shows at once; anything newer is animated in when it arrives.
                revealFrom = chat.arrivedCount(System.currentTimeMillis())
                m.advanceChat(chat.id)
                chats = m.listChats()
                openId = chat.id
            }
            // A tapped notification asks for a specific chat (the phone only passes it on for this case).
            LaunchedEffect(AppLaunch.argument) {
                val id = AppLaunch.argument ?: return@LaunchedEffect
                AppLaunch.argument = null
                chats.find { it.id == id }?.let(openChat)
            }
            // Ticks so the chat list picks up delayed messages as they arrive.
            val now by produceState(System.currentTimeMillis()) {
                while (true) { delay(1_000); value = System.currentTimeMillis() }
            }
            if (open == null) {
                val unreadChats = chats.count { it.unreadCount(now) > 0 }
                HomeScreen(tab, onTab = { tab = it }, profile, unreadChats) { current ->
                    when (current) {
                        Tab.Chats -> ChatListScreen(
                            chats, m.characters, now,
                            onOpen = openChat,
                            onDelete = { chat -> m.deleteChat(chat.id); chats = m.listChats() },
                        )
                        Tab.Calls -> CallsScreen(remember(chats) { m.listCalls() }, m.characters)
                        // Opens the 1-to-1 chat with the contact, starting it the first time.
                        Tab.Contacts -> ContactsScreen(m.characters) { c -> openChat(m.createChat(listOf(c.id))) }
                        Tab.Profile -> ProfileScreen(
                            profile,
                            onPickAvatar = { uri ->
                                scope.launch {
                                    val path = withContext(Dispatchers.IO) { m.avatars.import(uri) }
                                    if (path != null) profile = m.updateProfile(profile.copy(avatarPath = path))
                                }
                            },
                            onUpdate = { profile = m.updateProfile(it) },
                        )
                    }
                }
            } else {
                // Back to the list, refreshed so the chat no longer shows as unread.
                val close = { openId = null; chats = m.listChats() }
                BackHandler(onBack = close)
                ChatScreen(
                    open, m.characters, revealFrom,
                    otherUnread = chats.count { it.id != open.id && it.unreadCount(now) > 0 },
                    onBack = close,
                    onRead = { count -> m.markChatRead(open.id, count) },
                    onChoose = { choice ->
                        m.chooseReply(open.id, choice)
                        chats = m.listChats()
                    },
                    onEndCall = { answered ->
                        m.endCall(open.id, answered)
                        chats = m.listChats()
                    },
                )
            }
        }
    }
}
