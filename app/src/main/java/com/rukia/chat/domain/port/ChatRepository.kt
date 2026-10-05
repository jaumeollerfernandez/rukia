package com.rukia.chat.domain.port

import com.rukia.chat.domain.model.Chat

interface ChatRepository {
    fun all(): List<Chat>
    fun find(id: String): Chat?
    fun save(chat: Chat)
    fun delete(id: String)
    /** Forgets all player progress; story chats go back to their original state. */
    fun deleteAll()
}
