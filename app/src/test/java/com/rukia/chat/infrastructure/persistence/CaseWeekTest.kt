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

    private fun playWeek(answer: Boolean): Map<String, Chat> {
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
        val advance = AdvanceChat(chats, story, notifier, clock)
        val reply = ChooseReply(chats, story, characters, notifier, clock)
        val report = com.rukia.chat.application.PlayStoryEvent(chats, story, notifier, clock)
        // What the Police app would send: the farm in the morning, the crater watch at night.
        val dispatches = if (answer) mapOf(start + 5 * 86_400_000L + 9 * 3_600_000L to "envio_mas", start + 5 * 86_400_000L + 22 * 3_600_000L + 30 * 60_000L to "envio_vigilancia") else emptyMap()

        val end = start + 6 * 86_400_000L + 12 * 3_600_000L // D7 12:00
        while (clock.millis < end) {
            dispatches[clock.millis]?.let { report("central", it) }
            for (id in chats.all.keys.toList()) {
                val chat = advance(id)
                val first = chat.visibleChoices().firstOrNull()
                if (answer && first != null && chat.messages.all { it.arrivedBy(clock.millis) }) reply(id, first.index)
            }
            clock.millis += 10 * 60_000L
        }
        return chats.all
    }

    private fun assertEnded(chats: Map<String, Chat>, how: String, ending: String) {
        val dawn = start + 6 * 86_400_000L + 6 * 3_600_000L // D7 06:00
        val laia = chats.getValue("laia")
        assertTrue(laia.messages.any { it.deliverAt >= dawn }, "$how: Laia never got to the ending. Last: ${laia.messages.lastOrNull()?.text}")
        val stuck = chats.values.filter { c -> c.visibleChoices().isNotEmpty() && c.choicesExpireAt == 0L }.map { it.id }
        assertTrue(stuck.isEmpty(), "$how: choices that never expire in $stuck")
        assertTrue(laia.messages.any { ending in it.text }, "$how: expected the ending «$ending», got: ${laia.messages.takeLast(3).map { it.text }}")
    }

    @Test fun `answering every first choice reaches the ending`() = assertEnded(playWeek(answer = true), "answering", ending = "Y Alicia está en el coche patrulla")

    @Test fun `never answering still reaches the ending`() = assertEnded(playWeek(answer = false), "silent", ending = "Hemos llegado tarde.")

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
