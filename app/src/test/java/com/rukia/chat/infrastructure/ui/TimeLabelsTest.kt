package com.rukia.chat.infrastructure.ui

import com.rukia.chat.domain.model.Message
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeLabelsTest {
    private fun at(day: Int, hour: Int) = LocalDateTime.of(2026, 3, day, hour, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    private val now = at(12, 15) // a Thursday

    @Test fun `the chat list says the time today and the day otherwise`() {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH) // weekdays follow the interface language
        assertEquals("09:00", listTime(Message("a", "x", "09:00", deliverAt = at(12, 9)), now))
        assertEquals("Yesterday", listTime(Message("a", "x", "22:33", deliverAt = at(11, 22)), now))
        assertEquals("Monday", listTime(Message("a", "x", "10:00", deliverAt = at(9, 10)), now))
        assertEquals("1 Mar 2026", listTime(Message("a", "x", "10:00", deliverAt = at(1, 10)), now))
        assertEquals("10:00", listTime(Message("a", "x", "10:00"), now), "no timestamp: the saved time")
    }
}
