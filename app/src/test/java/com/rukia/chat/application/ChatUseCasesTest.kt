package com.rukia.chat.application

import com.rukia.chat.domain.model.CallRecord
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.MAX_NAME_LENGTH
import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.chat.domain.port.CharacterRepository
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.ProfileRepository
import com.rukia.chat.domain.port.StoryEngine
import com.rukia.chat.domain.port.StoryLine
import com.rukia.chat.domain.port.StoryStep
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.port.MessageNotifier
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

private class InMemoryChats : ChatRepository {
    val chats = linkedMapOf<String, Chat>()
    override fun all() = chats.values.toList()
    override fun find(id: String) = chats[id]
    override fun save(chat: Chat) { chats[chat.id] = chat }
    override fun delete(id: String) { chats.remove(id) }
    override fun deleteAll() = chats.clear()
}

private fun clockAt(hour: Int, minute: Int): Clock =
    Clock.fixed(LocalDateTime.of(2026, 1, 1, hour, minute).toInstant(ZoneOffset.UTC), ZoneOffset.UTC)

private class RecordingNotifier : MessageNotifier {
    val scheduled = mutableListOf<Message>()
    override fun notifyWhenDelivered(chatId: String, message: Message) { scheduled += message }
}

private val cast = object : CharacterRepository {
    override fun all() = listOf("rukia", "ichigo").associateWith { Character(it, it) }
}

class ChatUseCasesTest {
    private val repo = InMemoryChats()
    private val notifier = RecordingNotifier()

    @Test fun `one-to-one chat is reused, group gets a new id`() {
        val create = CreateChat(repo, cast)
        val solo = create(listOf("rukia"))
        assertEquals("rukia", solo.id)
        assertSame(solo, create(listOf("rukia")))

        val group = create(listOf("rukia", "ichigo"), "Squad")
        assertEquals("Squad", group.title)
        assertEquals(2, repo.all().size)
    }

    @Test fun `unknown or missing participants are rejected`() {
        val create = CreateChat(repo, cast)
        assertFailsWith<IllegalArgumentException> { create(emptyList()) }
        assertFailsWith<IllegalArgumentException> { create(listOf("nobody")) }
    }

    @Test fun `story lines become messages and a choice is sent as the player's reply`() {
        val story = object : StoryEngine {
            override fun advance(chatId: String) =
                StoryStep(listOf(StoryLine(null, "Hey"), StoryLine("ichigo", "Hi")), listOf("A", "B"))
            override fun choose(chatId: String, index: Int) = StoryStep(listOf(StoryLine(null, "You chose $index")), emptyList())
            override fun reset() {}
        }
        val clock = clockAt(9, 5)
        repo.save(Chat("rukia", listOf("rukia")))

        val opened = AdvanceChat(repo, story, notifier, clock)("rukia")
        assertEquals(listOf("rukia" to "Hey", "ichigo" to "Hi"), opened.messages.map { it.from to it.text })
        assertEquals(listOf("A", "B"), opened.choices)
        assertEquals("09:05", opened.messages.first().time)

        val replied = ChooseReply(repo, story, notifier, clock)("rukia", 1)
        assertEquals(listOf("me" to "B", "rukia" to "You chose 1"), replied.messages.drop(2).map { it.from to it.text })
        assertEquals(emptyList(), replied.choices)
        assertEquals(replied, repo.find("rukia"))
        assertFailsWith<IllegalArgumentException> { ChooseReply(repo, story, notifier)("rukia", 0) }
    }

    @Test fun `a call line rings, ending it records the outcome in the call history`() {
        val story = object : StoryEngine {
            override fun advance(chatId: String) = StoryStep(listOf(StoryLine(null, "Calling you", call = true, callAudio = "audio/hi.m4a")), emptyList())
            override fun choose(chatId: String, index: Int) = error("unused")
            override fun reset() {}
        }
        repo.save(Chat("rukia", listOf("rukia")))
        val ringing = AdvanceChat(repo, story, notifier, clockAt(10, 30))("rukia")
        assertEquals(listOf(null, CallStatus.RINGING), ringing.messages.map { it.call })
        assertEquals("audio/hi.m4a", ringing.ringingCall?.audio)
        assertEquals(emptyList(), ListCalls(repo)(), "a ringing call isn't history yet")

        EndCall(repo)("rukia", answered = false)
        assertEquals(listOf(CallRecord("rukia", "rukia", "10:30", CallStatus.DECLINED)), ListCalls(repo)())
        assertNull(repo.find("rukia")!!.ringingCall)
        assertFailsWith<IllegalArgumentException> { EndCall(repo)("rukia", answered = true) }
    }

    @Test fun `delayed lines arrive later and are handed to the notifier`() {
        val story = object : StoryEngine {
            override fun advance(chatId: String) = StoryStep(
                listOf(StoryLine(null, "Wait a moment."), StoryLine(null, "Back!", delaySeconds = 120), StoryLine(null, "So?")),
                listOf("Hi"),
            )
            override fun choose(chatId: String, index: Int) = error("unused")
            override fun reset() {}
        }
        val clock = clockAt(9, 5)
        repo.save(Chat("jaume", listOf("jaume")))
        val chat = AdvanceChat(repo, story, notifier, clock)("jaume")

        assertEquals(listOf("09:05", "09:07", "09:07"), chat.messages.map { it.time })
        assertEquals(1, chat.arrivedCount(clock.millis()), "only the first line is there yet")
        assertEquals(3, chat.arrivedCount(clock.millis() + 120_000))
        assertEquals(listOf("Back!", "So?"), notifier.scheduled.map { it.text })
    }

    @Test fun `unread counts arrived messages from others until the chat is read`() {
        val chat = Chat(
            "rukia", listOf("rukia"),
            messages = listOf(
                Message("rukia", "Hey"),
                Message("me", "Hi"),
                Message("rukia", "Listen"),
                Message("rukia", "Later", deliverAt = 5_000),
            ),
        )
        repo.save(chat)
        assertEquals(2, chat.unreadCount(nowMillis = 0), "own messages and not-yet-arrived ones don't count")
        assertEquals(3, chat.unreadCount(nowMillis = 5_000))

        MarkChatRead(repo)("rukia", 3)
        assertEquals(0, repo.find("rukia")!!.unreadCount(nowMillis = 0))
        assertEquals(1, repo.find("rukia")!!.unreadCount(nowMillis = 5_000))
        MarkChatRead(repo)("rukia", 1) // seeing less later never un-reads
        assertEquals(3, repo.find("rukia")!!.readCount)
    }

    @Test fun `reset wipes chats and restarts the story`() {
        var storyReset = false
        val story = object : StoryEngine {
            override fun advance(chatId: String) = StoryStep(emptyList(), emptyList())
            override fun choose(chatId: String, index: Int) = error("unused")
            override fun reset() { storyReset = true }
        }
        repo.save(Chat("rukia", listOf("rukia")))
        ResetProgress(repo, story)()
        assertEquals(emptyList(), repo.all())
        assertTrue(storyReset)
    }

    @Test fun `profile name is trimmed, capped and never blank`() {
        var stored: PlayerProfile? = null
        val profiles = object : ProfileRepository {
            override fun get() = stored ?: PlayerProfile()
            override fun save(profile: PlayerProfile) { stored = profile }
        }
        val update = UpdateProfile(profiles)
        assertEquals("Ana", update(PlayerProfile(name = "  Ana ")).name)
        assertEquals(MAX_NAME_LENGTH, update(PlayerProfile(name = "x".repeat(40))).name.length)
        assertFailsWith<IllegalArgumentException> { update(PlayerProfile(name = "   ")) }
        assertEquals(MAX_NAME_LENGTH, GetProfile(profiles)().name.length)
    }

    @Test fun `deleting removes the chat`() {
        repo.save(Chat("g", listOf("rukia", "ichigo")))
        DeleteChat(repo)("g")
        assertNull(repo.find("g"))
    }
}
