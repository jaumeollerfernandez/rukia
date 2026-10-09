package com.rukia.phone.application

import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.phone.domain.port.CaseCalendar
import com.rukia.phone.domain.port.StoryTimeline
import kotlin.test.Test
import kotlin.test.assertEquals

class SkipTimeTest {
    @Test fun `skipping stops at every story event on the way, then lands on the target`() {
        var now = 0L
        val stops = mutableListOf<Long>()
        val calendar = object : CaseCalendar {
            override fun now() = now
            override fun skip(millis: Long) { now += millis }
        }
        val story = object : StoryTimeline {
            override fun advance() { stops += now }
            override fun chats() = listOf(Chat("a", listOf("x"), messages = listOf(Message("x", "hi", deliverAt = 30), Message("x", "later", deliverAt = 500))))
        }
        assertEquals(30L, NextStoryEvent(calendar, story)())
        SkipTime(calendar, story)(100)
        assertEquals(100L, now)
        assertEquals(listOf(0L, 30L, 100L), stops, "the story is played at the start, at the line arriving at 30, and at the target")
    }
}
