package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository

/**
 * The player listened to a call (a voicemail, or one answered) from the call history: it's no longer new, and the
 * story learns it through `llamada_<chatId>_escuchada` ([setFlag]), if it declares one.
 */
class HearCall(private val chats: ChatRepository, private val setFlag: (String) -> Unit = {}) {
    operator fun invoke(chatId: String, at: Long) {
        chats.find(chatId)?.let { chats.save(it.hear(at)) }
        setFlag("llamada_${chatId}_escuchada")
    }
}
