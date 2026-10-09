package com.rukia.phone.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class HomeLayoutTest {
    @Test fun `saved cells are kept, new apps take the first free cell, gone and off-grid entries are dropped`() {
        val saved = mapOf("chat" to 0, "police" to 23, "uninstalled" to 1, "gonpi" to 99)
        assertEquals(mapOf("chat" to 0, "police" to 23, "gonpi" to 1, "multimedia" to 2), HomeLayout.arrange(listOf("chat", "police", "gonpi", "multimedia"), saved))
    }

    @Test fun `moving an app onto another swaps them, onto an empty cell just moves it`() {
        val cells = mapOf("chat" to 0, "gonpi" to 1)
        assertEquals(mapOf("chat" to 1, "gonpi" to 0), HomeLayout.move(cells, "chat", 1))
        assertEquals(mapOf("chat" to 7, "gonpi" to 1), HomeLayout.move(cells, "chat", 7))
    }
}
