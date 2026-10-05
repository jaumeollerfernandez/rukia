package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository

class ListChats(private val chats: ChatRepository) {
    operator fun invoke() = chats.all()
}
