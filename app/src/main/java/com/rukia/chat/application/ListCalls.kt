package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository

/** Call history across all chats, newest first. */
class ListCalls(private val chats: ChatRepository) {
    // ponytail: sorts by "HH:mm", so calls from before midnight sort below later ones; store a full timestamp if sessions cross days.
    operator fun invoke() = chats.all().flatMap { it.callRecords() }.sortedByDescending { it.time }
}
