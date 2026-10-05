package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.StoryEngine

/** Wipes the save: conversations, calls and every story choice. The player profile is kept. */
class ResetProgress(private val chats: ChatRepository, private val story: StoryEngine) {
    operator fun invoke() {
        chats.deleteAll()
        story.reset()
    }
}
