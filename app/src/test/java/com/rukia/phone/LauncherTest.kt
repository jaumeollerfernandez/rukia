package com.rukia.phone

import kotlin.test.Test
import kotlin.test.assertEquals

class LauncherTest {
    @Test fun `saved cells are kept, new apps take the first free cell, gone and off-grid entries are dropped`() {
        val saved = mapOf("chat" to 0, "police" to 23, "uninstalled" to 1, "gonpi" to 99)
        assertEquals(mapOf("chat" to 0, "police" to 23, "gonpi" to 1, "multimedia" to 2), arrange(listOf("chat", "police", "gonpi", "multimedia"), saved))
    }
}
