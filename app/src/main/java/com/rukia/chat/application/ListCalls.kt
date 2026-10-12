package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository

/** Call history across all chats, newest first. */
class ListCalls(private val chats: ChatRepository) {
    operator fun invoke() = chats.all().flatMap { it.callRecords() }.sortedByDescending { it.at }
}
