package com.rukia.phone.domain.port

import com.rukia.chat.domain.model.Chat

/** Where each app sits on the home screen, kept with the case's save. */
interface HomeLayoutRepository {
    fun load(): Map<String, Int>
    fun save(cells: Map<String, Int>)
}

/** The case's clock: its current time, and moving it ahead (only a debug case can). */
interface CaseCalendar {
    fun now(): Long
    fun skip(millis: Long)
}

/** The case's story as the phone sees it: every chat, and playing whatever is due. The phone backs it with the chat app. */
interface StoryTimeline {
    fun advance()
    fun chats(): List<Chat>
}
