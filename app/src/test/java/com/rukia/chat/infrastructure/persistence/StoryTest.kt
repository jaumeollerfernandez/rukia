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

    @Test fun `every police operation plays a report knot in a chat that exists`() {
        for (case in allCases.listFiles()!!) {
            val file = File(case, "police/actions.json").takeIf { it.exists() } ?: continue
            val ops = kotlinx.serialization.json.Json.decodeFromString<com.rukia.police.domain.model.Operations>(file.readText())
            assertTrue(File(case, "chats/${ops.channel}.json").exists(), "${case.name}: no chat '${ops.channel}' for the reports")
            val engine = InkStoryEngine({ File(case, it).readText() }, tempState())
            for (op in ops.operations) assertTrue(engine.jump(ops.channel, op.knot).lines.isNotEmpty(), "${case.name}: operation '${op.id}' has no knot '${op.knot}'")
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

    @Test fun `case-time tags - history keeps its date, the story pauses at a future line, choices expire, dia follows the clock`() {
        val zone = java.time.ZoneOffset.UTC
        val start = java.time.LocalDate.of(2026, 1, 1).atStartOfDay(zone).toInstant().toEpochMilli()
        fun at(day: Int, hour: Int) = java.time.Clock.fixed(java.time.Instant.ofEpochMilli(start + (day - 1) * 86_400_000L + hour * 3_600_000L), zone)
        val timed = """
            VAR dia = 0
            -> DONE
            === laia ===
            Before. #at: D-1 20:00
            Day {dia}. #image: photos/x.jpg
            Later. #at: D2 10:00
            On day {dia}. #caduca: D2 12:00
            * [Ok.]
            * [(sin responder)]
            - -> DONE
        """.trimIndent()
        val file = tempState()
        fun engine(clock: java.time.Clock) = InkStoryEngine({ timed }, file, caseStart = { start }, clock = clock)

        val first = engine(at(1, 9)).advance("laia")
        assertEquals(listOf("Before.", "Day 1.", "Later."), first.lines.map { it.text })
        assertEquals(listOf(start - 28 * 3_600_000L, null, start + 34 * 3_600_000L), first.lines.map { it.at }, "D-1 is two days before D1")
        assertEquals("photos/x.jpg", first.lines[1].image)
        assertEquals(emptyList(), first.choices, "paused at the line of D2")

        val second = engine(at(2, 11)).advance("laia")
        assertEquals(listOf("On day 2."), second.lines.map { it.text })
        assertEquals(listOf("Ok.", "(sin responder)"), second.choices)
        assertEquals(start + 36 * 3_600_000L, second.expiresAt)
    }

    @Test fun `a save from an older story starts over instead of crashing`() {
        engine().advance("rukia") // saved while waiting at rukia's choices
        val rewritten = InkStoryEngine({ "-> DONE\n=== uryu ===\nNew story.\n-> DONE" }, state)
        assertEquals(listOf("New story."), rewritten.advance("uryu").lines.map { it.text })
        assertEquals(StoryStep(emptyList(), emptyList()), rewritten.choose("rukia", 0))
    }
}
