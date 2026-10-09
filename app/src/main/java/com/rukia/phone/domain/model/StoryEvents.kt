package com.rukia.phone.domain.model

import com.rukia.chat.domain.model.Chat

/** The next moment the story has something to do: a line arriving or choices expiring. Null if it's waiting for the player. */
fun nextEvent(chats: List<Chat>, now: Long): Long? = chats.flatMap { c ->
    c.messages.map { it.deliverAt } + listOfNotNull(c.choicesExpireAt.takeIf { c.choices.isNotEmpty() && it > 0 })
}.filter { it > now }.minOrNull()
