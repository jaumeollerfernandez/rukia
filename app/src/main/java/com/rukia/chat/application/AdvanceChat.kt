package com.rukia.chat.application

import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.MessageNotifier
import com.rukia.chat.domain.port.StoryEngine
import com.rukia.chat.domain.port.StoryStep
import java.time.Clock
import java.time.Instant
import java.time.format.DateTimeFormatter

/** Brings a chat up to date with the story. Call it when the chat is opened. */
class AdvanceChat(
    private val chats: ChatRepository,
    private val story: StoryEngine,
    private val notifier: MessageNotifier,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(chatId: String): Chat {
        val chat = requireNotNull(chats.find(chatId)) { "Chat $chatId not found" }
        return continueChat(chat, story.advance(chatId), clock, chats, notifier)
    }
}

/**
 * Appends the story's new lines (after [before], e.g. the player's reply) and saves the chat. Delayed lines are
 * timestamped in the future; the notifier is told about each of them so the player hears about it while away.
 */
internal fun continueChat(
    chat: Chat,
    step: StoryStep,
    clock: Clock,
    chats: ChatRepository,
    notifier: MessageNotifier,
    before: List<Message> = emptyList(),
): Chat {
    val now = clock.millis()
    var at = now
    val lines = step.lines.flatMap {
        at += it.delaySeconds * 1000L
        val from = it.from ?: chat.defaultSpeaker()
        val time = timeOf(at, clock)
        listOfNotNull(
            Message(from, it.text, time, deliverAt = at),
            if (it.call) Message(from, "", time, CallStatus.RINGING, deliverAt = at, audio = it.callAudio) else null,
        )
    }
    val updated = chat.continuedWith(before + lines, step.choices)
    chats.save(updated)
    lines.filter { it.deliverAt > now }.forEach { notifier.notifyWhenDelivered(chat.id, it) }
    return updated
}

internal fun timeOf(epochMillis: Long, clock: Clock): String =
    Instant.ofEpochMilli(epochMillis).atZone(clock.zone).format(DateTimeFormatter.ofPattern("HH:mm"))
