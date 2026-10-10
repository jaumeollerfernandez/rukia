package com.rukia.chat.infrastructure.persistence

import com.rukia.chat.application.AdvanceChat
import com.rukia.chat.application.ChooseReply
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.port.CharacterRepository
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.MessageNotifier
import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.io.path.createTempDirectory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Plays the real week of case ruk-93429049 on a fake clock, ten minutes at a time, as the phone's story clock does:
 * once answering every first choice and sending police where the case is solved, once never answering nor sending anyone.
 * Both must reach the D7 ending without the story getting stuck, and land on the endings they deserve.
 */
class CaseWeekTest {
    private val case = File("src/main/assets/cases/ruk-93429049")
    private val zone = ZoneOffset.UTC
    private val start = LocalDate.of(2026, 3, 10).atStartOfDay(zone).toInstant().toEpochMilli()

    private class FakeClock(var millis: Long) : Clock() {
        override fun getZone(): ZoneId = ZoneOffset.UTC
        override fun withZone(zone: ZoneId?) = this
        override fun instant(): Instant = Instant.ofEpochMilli(millis)
    }

    /** [preset]: story variables set at the start, as if the player had got them. [sent]: police dispatches by case time, instead of the defaults. */
    private fun playWeek(answer: Boolean, random: kotlin.random.Random? = null, preset: Map<String, Boolean> = emptyMap(), sent: Map<Long, String>? = null): Map<String, Chat> {
        val clock = FakeClock(start + 9 * 3_600_000L) // opened on D1 at 09:00
        val chats = object : ChatRepository {
            val all = case.resolve("chats").listFiles()!!.map { storyJson.decodeFromString<Chat>(it.readText()) }.associateBy { it.id }.toMutableMap()
            override fun all() = all.values.toList()
            override fun find(id: String) = all[id]
            override fun save(chat: Chat) { all[chat.id] = chat }
            override fun delete(id: String) { all.remove(id) }
            override fun deleteAll() = all.clear()
        }
        val characters = object : CharacterRepository {
            val all = storyJson.decodeFromString<List<Character>>(case.resolve("characters.json").readText()).associateBy { it.id }
            override fun all() = all
        }
        // The player opens every contact in the Contacts tab too, as CreateChat does: a 1-to-1 chat named like the contact.
        characters.all.values.filter { !it.hidden && it.id !in chats.all }.forEach { chats.all[it.id] = Chat(it.id, listOf(it.id)) }
        val notifier = object : MessageNotifier { override fun notifyWhenDelivered(chatId: String, message: com.rukia.chat.domain.model.Message) {} }
        val story = InkStoryEngine({ case.resolve(it).readText() }, File(createTempDirectory().toFile(), "state.json"), { start }, clock)
        lastStory = story
        preset.forEach { (name, value) -> story.setVariable(name, value) }
        val advance = AdvanceChat(chats, story, notifier, clock)
        val reply = ChooseReply(chats, story, characters, notifier, clock)
        val report = com.rukia.chat.application.PlayStoryEvent(chats, story, notifier, clock)
        // What the Police app would send: the farm in the morning, the crater watch at night.
        val knots = listOf("envio_mas", "envio_masias", "envio_capsec", "envio_residencia", "envio_estacion", "envio_pol", "envio_girona", "envio_barcelona", "envio_dani", "envio_crater")
        val dispatches = sent ?: when {
            random != null -> mapOf(
                start + 5 * 86_400_000L + 9 * 3_600_000L to knots.random(random),
                start + 5 * 86_400_000L + 22 * 3_600_000L + 30 * 60_000L to (knots + "envio_vigilancia").random(random),
            )
            answer -> mapOf(start + 5 * 86_400_000L + 9 * 3_600_000L to "envio_mas", start + 5 * 86_400_000L + 22 * 3_600_000L + 30 * 60_000L to "envio_vigilancia")
            else -> emptyMap()
        }

        val end = start + 6 * 86_400_000L + 12 * 3_600_000L // D7 12:00
        while (clock.millis < end) {
            dispatches[clock.millis]?.let { report("central", it) }
            for (id in chats.all.keys.toList()) {
                val chat = advance(id)
                val choices = chat.visibleChoices()
                val first = if (random != null) choices.randomOrNull(random)?.takeIf { random.nextInt(4) > 0 } else choices.firstOrNull()
                if (answer && first != null && chat.messages.all { it.arrivedBy(clock.millis) }) reply(id, first.index)
            }
            clock.millis += 10 * 60_000L
        }
        return chats.all
    }

    /** The story of the last [playWeek], to read how it ended. */
    private lateinit var lastStory: InkStoryEngine

    /** Every ending's last line closes the case (the game then opens the report), with `final_caso` set. */
    private fun assertClosed(chats: Map<String, Chat>, how: String, ending: Int? = null) {
        assertEquals("case_closed", chats.getValue("laia").messages.lastOrNull()?.effect, "$how: Laia's last line doesn't close the case")
        val final = lastStory.variable("final_caso") as Int
        assertTrue(final in 1..6, "$how: final_caso is $final")
        ending?.let { assertEquals(it, final, "$how: wrong ending") }
    }

    private fun assertEnded(chats: Map<String, Chat>, how: String, ending: String) {
        val dawn = start + 6 * 86_400_000L + 6 * 3_600_000L // D7 06:00
        val laia = chats.getValue("laia")
        assertTrue(laia.messages.any { it.deliverAt >= dawn }, "$how: Laia never got to the ending. Last: ${laia.messages.lastOrNull()?.text}")
        val stuck = chats.values.filter { c -> c.visibleChoices().isNotEmpty() && c.choicesExpireAt == 0L }.map { it.id }
        assertTrue(stuck.isEmpty(), "$how: choices that never expire in $stuck")
        assertTrue(laia.messages.any { ending in it.text }, "$how: expected the ending «$ending», got: ${laia.messages.takeLast(3).map { it.text }}")
    }

    @Test fun `answering every first choice reaches the ending`() = playWeek(answer = true).let {
        assertEnded(it, "answering", ending = "Y Alicia está en el coche patrulla")
        assertClosed(it, "answering", ending = 1)
    }

    @Test fun `never answering still reaches the ending`() = playWeek(answer = false).let {
        assertEnded(it, "silent", ending = "Hemos llegado tarde.")
        assertClosed(it, "silent", ending = 4)
    }

    private val night = start + 5 * 86_400_000L + 22 * 3_600_000L + 30 * 60_000L // D6 22:30

    // Dani's debt (PISTAS.md): with the family off her trail and nobody rescuing her, Dani and the network take her.
    @Test fun `nobody stops Dani so they take her during the blackout`() = playWeek(answer = false, preset = mapOf("pol_calla" to true)).let {
        assertEnded(it, "taken", ending = "Y Dani Rius tampoco.")
        assertTrue(it.getValue("laia").messages.any { m -> "La moto es de Dani Rius" in m.text }, "Laia tells of the handover at 22:05")
        assertClosed(it, "taken", ending = 6)
    }

    @Test fun `stopping Dani buys the night squad time to save her`() =
        playWeek(answer = false, preset = mapOf("pol_calla" to true, "dani_frenado" to true), sent = mapOf(night to "envio_mas")).let {
            assertEnded(it, "stopped", ending = "Alicia está a salvo. Está conmigo.")
            assertClosed(it, "stopped", ending = 3)
        }

    @Test fun `stopping Dani without saving her only delays it to dawn`() =
        playWeek(answer = false, preset = mapOf("pol_calla" to true, "dani_frenado" to true), sent = mapOf(night to "envio_vigilancia")).let {
            assertEnded(it, "delayed", ending = "Montse no deja de preguntar por Alicia.")
            assertTrue(it.getValue("laia").messages.any { m -> "04:20" in m.text }, "the handover comes at dawn")
            assertClosed(it, "delayed", ending = 5)
        }

    // With full trust Alicia phones the exact place (D5 night): Laia gets her out at once, and her statement covers the crater.
    @Test fun `Alicia's call saves her from the family and Dani and stops the ceremony`() =
        playWeek(answer = false, preset = mapOf("alicia_llamo" to true)).let {
            assertEnded(it, "called", ending = "Y Alicia lo ha visto amanecer desde la ventana")
            assertClosed(it, "called", ending = 1)
        }

    /** Whatever the player picks and wherever they send the squads, no story step throws and the week reaches D7. */
    @Test fun `random playthroughs never crash and reach D7`() {
        for (seed in 1..25) {
            val chats = playWeek(answer = true, random = kotlin.random.Random(seed))
            val dawn = start + 6 * 86_400_000L + 6 * 3_600_000L
            assertTrue(chats.getValue("laia").messages.any { it.deliverAt >= dawn }, "seed $seed: Laia never got to D7")
            assertClosed(chats, "seed $seed")
        }
    }

    @Test fun `a contact's questions expire at midnight and the next day brings new ones`() {
        val clock = FakeClock(start + 10 * 3_600_000L) // D1 10:00
        val story = InkStoryEngine({ case.resolve(it).readText() }, File(createTempDirectory().toFile(), "state.json"), { start }, clock)
        val d1 = story.advance("oriol")
        assertEquals(start + 86_400_000L, d1.expiresAt, "open until midnight")
        assertTrue(d1.choices.none { "bracons" in it }, "the D2 question isn't there yet: ${d1.choices}")
        clock.millis += 86_400_000L
        val d2 = story.choose("oriol", d1.choices.indexOf("(sin responder)"))
        assertTrue(d2.lines.isEmpty() && d2.choices.any { "bracons" in it }, "D2 brings its question: ${d2.choices}")
        assertEquals(start + 2 * 86_400_000L, d2.expiresAt)
    }
}
