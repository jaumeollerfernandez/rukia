package com.rukia.phone.domain.model

import com.rukia.chat.domain.model.Chat

/** The next moment the story has something to do: a line arriving, choices expiring or a pause ending. Null if it's waiting for the player. */
fun nextEvent(chats: List<Chat>, now: Long): Long? = chats.flatMap { c ->
    c.messages.map { it.deliverAt } + listOfNotNull(c.choicesExpireAt.takeIf { c.choices.isNotEmpty() && it > 0 }, c.waitUntil)
}.filter { it > now }.minOrNull()
