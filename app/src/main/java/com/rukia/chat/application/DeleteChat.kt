package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository

class DeleteChat(private val chats: ChatRepository) {
    operator fun invoke(chatId: String) = chats.delete(chatId)
}
