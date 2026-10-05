package com.rukia.chat.infrastructure.persistence

import com.rukia.chat.domain.port.StoryStep
import com.rukia.game.cases
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** Plays the real story files of every case, so a broken .ink fails the build instead of the phone. */
class StoryTest {
    private val allCases = File("src/main/assets/cases")
    private fun tempState() = File(createTempDirectory().toFile(), "state.json")

    @Test fun `every case in the list has a content folder`() {
        for (c in cases) assertTrue(File(allCases, c.id).isDirectory, "no folder assets/cases/${c.id}")
    }

    @Test fun `every case's story compiles and every chat has a story that starts`() {
        for (case in allCases.listFiles()!!) {
            val engine = InkStoryEngine({ File(case, it).readText() }, tempState())
            engine.advance("") // compiles main.ink and its INCLUDEs
            val chatIds = File(case, "chats").listFiles().orEmpty().filter { it.extension == "json" }.map { it.nameWithoutExtension }
            for (id in chatIds) assertTrue(engine.advance(id).lines.isNotEmpty(), "${case.name}: chat '$id' has no opening lines")
        }
    }

    // The engine itself, on a small script that doesn't depend on any case's content.
    private val script = """
        VAR knows = false
        -> DONE
        === rukia ===
        Hi.
        I need to tell you something.
        - (top)
        * [What?] Nothing.
        * [The key?]
            ~ knows = true
            Yes, the key. #delay: 30
        + {knows} [Call me.] Calling you now. #call: audio/hi.m4a
            -> DONE
        - -> top
        === group ===
        Hey. #from: ichigo
        Hi! #from: orihime
        -> DONE
    """.trimIndent()
    private val state = tempState()
    private fun engine() = InkStoryEngine({ script }, state) // new engine = app restarted, state loaded from disk

    @Test fun `choices branch, share variables, read tags and survive a restart`() {
        val first = engine().advance("rukia")
        assertEquals(listOf("Hi.", "I need to tell you something."), first.lines.map { it.text })
        assertEquals(listOf("What?", "The key?"), first.choices)

        val key = engine().choose("rukia", 1)
        assertEquals(30, key.lines.single().delaySeconds)
        assertEquals(listOf("What?", "Call me."), key.choices, "the variable unlocked a choice")
        assertEquals(key.choices, engine().advance("rukia").choices)

        val call = engine().choose("rukia", 1).lines.single()
        assertEquals(true to "audio/hi.m4a", call.call to call.callAudio)

        // Reset puts every chat back at its first lines, also after a restart.
        engine().reset()
        assertEquals(first, engine().advance("rukia"))

        // A 1-to-1 line has no speaker tag; group lines name theirs. A chat with no knot is just empty.
        assertEquals(null, first.lines.first().from)
        assertEquals(listOf("ichigo", "orihime"), engine().advance("group").lines.map { it.from })
        assertTrue(engine().advance("nobody").lines.isEmpty())
    }

    @Test fun `a save from an older story starts over instead of crashing`() {
        engine().advance("rukia") // saved while waiting at rukia's choices
        val rewritten = InkStoryEngine({ "-> DONE\n=== uryu ===\nNew story.\n-> DONE" }, state)
        assertEquals(listOf("New story."), rewritten.advance("uryu").lines.map { it.text })
        assertEquals(StoryStep(emptyList(), emptyList()), rewritten.choose("rukia", 0))
    }
}
