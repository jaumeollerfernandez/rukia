package com.rukia.chat.application

import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.model.PLAYER_ID
import com.rukia.chat.domain.port.CharacterRepository
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.MessageNotifier
import com.rukia.chat.domain.port.StoryEngine
import java.time.Clock

/** The player picks one of the offered replies; it's sent as their message and the story continues. Whoever answers does so when they're online. */
class ChooseReply(
    private val chats: ChatRepository,
    private val story: StoryEngine,
    private val characters: CharacterRepository,
    private val notifier: MessageNotifier,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(chatId: String, index: Int): Chat = synchronized(story) {
        val chat = requireNotNull(chats.find(chatId)) { "Chat $chatId not found" }
        val text = requireNotNull(chat.choices.getOrNull(index)) { "No choice $index in $chatId" }
        val now = clock.millis()
        val reply = Message(PLAYER_ID, text, timeOf(now, clock), deliverAt = now)
        continueChat(
            chat.copy(choices = emptyList()), story.choose(chatId, index), clock, chats, notifier,
            before = listOf(reply), answering = characters.all(),
        )
    }
}
