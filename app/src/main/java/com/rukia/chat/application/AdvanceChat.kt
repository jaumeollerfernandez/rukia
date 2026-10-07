package com.rukia.chat.application

import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.model.SILENT_CHOICE
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.MessageNotifier
import com.rukia.chat.domain.port.StoryEngine
import com.rukia.chat.domain.port.StoryStep
import java.time.Clock
import java.time.Instant
import java.time.format.DateTimeFormatter

/**
 * Brings a chat up to date with the story. Call it when the chat is opened, and every now and then while the case
 * runs: the story waits for timed lines to arrive and for choices to expire.
 */
class AdvanceChat(
    private val chats: ChatRepository,
    private val story: StoryEngine,
    private val notifier: MessageNotifier,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(chatId: String): Chat = synchronized(story) {
        val chat = requireNotNull(chats.find(chatId)) { "Chat $chatId not found" }
        val now = clock.millis()
        val silent = chat.choices.indexOf(SILENT_CHOICE)
        when {
            // What comes next is only written once everything already said has arrived, so it can depend on the moment.
            chat.messages.any { !it.arrivedBy(now) } -> chat
            chat.choices.isEmpty() -> continueChat(chat, story.advance(chatId), clock, chats, notifier)
            // The player let the choices expire: the story takes the silent one, and nothing is sent.
            chat.choicesExpireAt in 1..now && silent >= 0 ->
                continueChat(chat.copy(choices = emptyList()), story.choose(chatId, silent), clock, chats, notifier)
            else -> chat
        }
    }
}

/**
 * Appends the story's new lines (after [before], e.g. the player's reply) and saves the chat. Each line arrives
 * [StoryLine.delaySeconds] after the previous one, or at its [StoryLine.at] time; if [answering] has the speaker of
 * the first line, they reply when they're next online. Lines arriving later are handed to the notifier.
 */
internal fun continueChat(
    chat: Chat,
    step: StoryStep,
    clock: Clock,
    chats: ChatRepository,
    notifier: MessageNotifier,
    before: List<Message> = emptyList(),
    answering: Map<String, Character> = emptyMap(),
): Chat {
    val now = clock.millis()
    var at = (chat.messages + before).maxOfOrNull { it.deliverAt } ?: 0L
    // Lines whose time already passed are the phone's history: Alicia read them before the case began.
    val history = step.lines.takeWhile { it.at != null && it.at < now }.sumOf { if (it.call) 2 else 1 }
    val lines = step.lines.flatMapIndexed { i, line ->
        val from = line.from ?: chat.defaultSpeaker()
        // A time from the script wins; one before the case started keeps its date, as the phone's history.
        at = line.at?.let { maxOf(at, it) } ?: (maxOf(at, now) + line.delaySeconds * 1000L)
        if (i == 0) answering[from]?.let { at = it.nextOnline(at, clock.zone) }
        val time = timeOf(at, clock)
        listOfNotNull(
            Message(from, line.text, time, deliverAt = at, effect = line.effect, image = line.image),
            if (line.call) Message(from, "", time, CallStatus.RINGING, deliverAt = at, audio = line.callAudio) else null,
        )
    }
    val updated = chat.continuedWith(before + lines, step.choices, step.expiresAt ?: 0).let {
        if (history > 0 && chat.readCount >= chat.messages.size) it.readUpTo(chat.messages.size + before.size + history) else it
    }
    chats.save(updated)
    lines.filter { it.deliverAt > now }.forEach { notifier.notifyWhenDelivered(chat.id, it) }
    return updated
}

internal fun timeOf(epochMillis: Long, clock: Clock): String =
    Instant.ofEpochMilli(epochMillis).atZone(clock.zone).format(DateTimeFormatter.ofPattern("HH:mm"))
