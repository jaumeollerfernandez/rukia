package com.rukia.chat.application

import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.port.ChatRepository

/** The player picks up or declines the ringing call. For now both just hang up. */
class EndCall(private val chats: ChatRepository) {
    operator fun invoke(chatId: String, answered: Boolean): Chat {
        val chat = requireNotNull(chats.find(chatId)) { "Chat $chatId not found" }
        return chat.endCall(answered).also(chats::save)
    }
}
