package com.rukia.chat.application

import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.StoryEngine

/**
 * The player picks up or declines the ringing call. The story learns it through the ink variable
 * `llamada_<chatId>_contestada`, if it declares one, so a caller can ring again only if nobody picked up.
 */
class EndCall(private val chats: ChatRepository, private val story: StoryEngine? = null) {
    operator fun invoke(chatId: String, answered: Boolean): Chat {
        val chat = requireNotNull(chats.find(chatId)) { "Chat $chatId not found" }
        return chat.endCall(answered).also(chats::save).also {
            story?.let { s -> synchronized(s) { s.setVariable("llamada_${chatId}_contestada", answered) } }
        }
    }
}
