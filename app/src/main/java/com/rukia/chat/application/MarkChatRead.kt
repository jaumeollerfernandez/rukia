package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository

/** The player has seen the first [count] messages of the chat. */
class MarkChatRead(private val chats: ChatRepository) {
    operator fun invoke(chatId: String, count: Int) {
        val chat = chats.find(chatId) ?: return
        val read = chat.readUpTo(count)
        if (read != chat) chats.save(read)
    }
}
