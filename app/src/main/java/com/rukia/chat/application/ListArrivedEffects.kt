package com.rukia.chat.application

import com.rukia.chat.domain.port.ChatRepository
import java.time.Clock

/** Screen effects of every message that has arrived so far, across all chats. The phone plays the ones it hasn't yet. */
class ListArrivedEffects(private val chats: ChatRepository, private val clock: Clock = Clock.systemDefaultZone()) {
    operator fun invoke() = chats.all().flatMap { it.arrivedEffects(clock.millis()) }
}
