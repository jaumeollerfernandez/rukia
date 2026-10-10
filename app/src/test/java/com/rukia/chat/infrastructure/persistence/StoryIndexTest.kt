package com.rukia.chat.infrastructure.persistence

import java.io.File
import kotlin.test.Test
import kotlin.test.fail

/**
 * Keeps each case's INDICE.md (variables, timeline, who's mentioned where) up to date with its story. If the story
 * changed, the test rewrites the index and fails once: run the tests again and it passes.
 */
class StoryIndexTest {
    @Test fun `every case's index matches its story`() {
        val stale = File("src/main/assets/cases").listFiles { f -> File(f, "story/main.ink").exists() }!!.filter { case ->
            val index = File(case, "INDICE.md")
            // Some .ink files have Windows line ends: compare without carriage returns.
            val fresh = StoryIndex(case).markdown().replace("\r", "")
            (index.takeIf { it.exists() }?.readText()?.replace("\r", "") != fresh).also { if (it) index.writeText(fresh) }
        }
        if (stale.isNotEmpty()) fail("INDICE.md regenerado en ${stale.joinToString { it.name }}. Vuelve a pasar los tests.")
    }
}
