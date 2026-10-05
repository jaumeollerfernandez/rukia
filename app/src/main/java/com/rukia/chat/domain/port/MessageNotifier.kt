package com.rukia.chat.domain.port

import com.rukia.chat.domain.model.Message

/** Tells the player about a message that arrives later, like a phone notification, even if the app is closed by then. */
interface MessageNotifier {
    fun notifyWhenDelivered(chatId: String, message: Message)
}
