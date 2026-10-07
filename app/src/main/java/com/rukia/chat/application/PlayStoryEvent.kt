package com.rukia.chat.application

import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.MessageNotifier
import com.rukia.chat.domain.port.StoryEngine
import java.time.Clock

/** Something outside the chats happened (e.g. police officers were sent somewhere): its ink [knot] plays in [chatId]. */
class PlayStoryEvent(
    private val chats: ChatRepository,
    private val story: StoryEngine,
    private val notifier: MessageNotifier,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(chatId: String, knot: String): Chat = synchronized(story) {
        val chat = requireNotNull(chats.find(chatId)) { "Chat $chatId not found" }
        continueChat(chat, story.jump(chatId, knot), clock, chats, notifier)
    }
}
