package com.rukia.chat.application

import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.port.CharacterRepository
import com.rukia.chat.domain.port.ChatRepository
import java.util.UUID

class CreateChat(private val chats: ChatRepository, private val characters: CharacterRepository) {
    /** A 1-to-1 chat reuses the character's id, so opening it twice returns the existing chat. */
    operator fun invoke(participants: List<String>, title: String? = null): Chat {
        require(participants.isNotEmpty()) { "A chat needs at least one participant" }
        val unknown = participants - characters.all().keys
        require(unknown.isEmpty()) { "Unknown characters: $unknown" }

        val id = participants.singleOrNull() ?: "group-${UUID.randomUUID()}"
        return chats.find(id) ?: Chat(id, participants.distinct(), title?.takeIf { it.isNotBlank() })
            .also(chats::save)
    }
}
