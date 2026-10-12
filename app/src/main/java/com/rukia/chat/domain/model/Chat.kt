package com.rukia.chat.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val PLAYER_ID = "me"

/** The id of the Chats app on the phone. */
const val CHATS_APP = "chat"

/** A choice the player never sees: the story takes it on its own when the choices expire, and nothing is sent. */
const val SILENT_CHOICE = "(sin responder)"

/**
 * Another hidden choice, in a chat's hub of questions: the app takes it when something outside the chats (a photo
 * opened, a record read) may open new questions, so the hub offers them at once. Nothing is sent and nothing is said.
 */
const val REFRESH_CHOICE = "(actualizar)"

/**
 * A call rings, then it's answered (heard live) or goes to voicemail: the caller leaves the same words as a message.
 * There are no missed calls. Saved as "DECLINED" by older versions, which is read back as a voicemail.
 */
enum class CallStatus { RINGING, ANSWERED, @SerialName("DECLINED") VOICEMAIL }

/**
 * A text message, or a voice call when [call] is set (then [text] is empty; [audio] is what the caller says if answered).
 * [deliverAt] (epoch millis) is when it arrives; the chat only shows it from then on. 0 = already there.
 */
@Serializable
data class Message(
    val from: String,
    val text: String,
    val time: String = "",
    val call: CallStatus? = null,
    val deliverAt: Long = 0,
    val audio: String? = null,
    /** Screen effect the phone plays when the message arrives. */
    val effect: String? = null,
    /** Picture shown in the bubble, a path inside the case's media folder. */
    val image: String? = null,
    /** A call: whether the player has heard it, live or from the voicemail. */
    val heard: Boolean = false,
) {
    val fromPlayer get() = from == PLAYER_ID

    fun arrivedBy(nowMillis: Long) = deliverAt <= nowMillis
}

/** One entry of the call history, taken from the call messages of every chat. [at] is when it rang (epoch millis). */
data class CallRecord(val chatId: String, val from: String, val time: String, val status: CallStatus, val audio: String? = null, val at: Long = 0, val heard: Boolean = false)

data class EffectCue(val id: String, val effect: String)

@Serializable
data class Chat(
    val id: String,
    val participants: List<String>,
    val title: String? = null, // only needed for group chats
    val messages: List<Message> = emptyList(),
    /** Replies the player can pick right now. Empty while the story isn't waiting on the player. */
    val choices: List<String> = emptyList(),
    /** How many messages the player has seen; later ones from others are unread. */
    val readCount: Int = 0,
    /** When the [choices] expire (epoch millis) and the story takes [SILENT_CHOICE] on its own. 0 = never. */
    val choicesExpireAt: Long = 0,
) {
    val isGroup get() = participants.size > 1

    /** The choices shown to the player, with their index in [choices]. */
    fun visibleChoices() = choices.withIndex().filter { it.value != SILENT_CHOICE && it.value != REFRESH_CHOICE }

    /** The phone app showing this chat: Chats, unless its one contact lives in another app ([Character.app]). */
    fun app(characters: Map<String, Character>) = characters[participants.singleOrNull()]?.app ?: CHATS_APP

    fun displayTitle(characters: Map<String, Character>) =
        title ?: participants.joinToString { characters[it]?.name ?: it }

    /** Lines without a speaker come from the other person in a 1-to-1 chat. Group lines must name one. */
    fun defaultSpeaker() = participants.first()

    fun continuedWith(newMessages: List<Message>, choices: List<String>, choicesExpireAt: Long = 0) =
        copy(messages = messages + newMessages, choices = choices, choicesExpireAt = choicesExpireAt)

    /** Messages arrive in order, so the ones that have arrived are always the first ones. */
    fun arrivedCount(nowMillis: Long) = messages.indexOfFirst { !it.arrivedBy(nowMillis) }.let { if (it == -1) messages.size else it }

    val ringingCall get() = messages.lastOrNull { it.call == CallStatus.RINGING }

    /** The newest message that has arrived, which the chat list shows and sorts by. */
    fun lastArrived(nowMillis: Long) = messages.take(arrivedCount(nowMillis)).lastOrNull { it.call == null }

    /** Messages from others that have arrived but the player hasn't seen yet. */
    fun unreadCount(nowMillis: Long) = messages.take(arrivedCount(nowMillis)).drop(readCount).count { !it.fromPlayer && it.call == null }

    fun readUpTo(count: Int) = copy(readCount = maxOf(readCount, count.coerceAtMost(messages.size)))

    fun endCall(answered: Boolean): Chat {
        val ringing = requireNotNull(ringingCall) { "No call ringing in $id" }
        val ended = ringing.copy(call = if (answered) CallStatus.ANSWERED else CallStatus.VOICEMAIL, heard = answered)
        return copy(messages = messages.map { if (it === ringing) ended else it })
    }

    /** Effects of the messages that have arrived. [EffectCue.id] is unique per message and per play-through, to remember which ones played. */
    fun arrivedEffects(nowMillis: Long) = messages.withIndex()
        .filter { (_, m) -> m.effect != null && m.arrivedBy(nowMillis) }
        .map { (i, m) -> EffectCue("$id:$i:${m.deliverAt}", m.effect!!) }

    fun callRecords() = messages.filter { it.call != null && it.call != CallStatus.RINGING }
        .map { CallRecord(id, it.from, it.time, it.call!!, it.audio, it.deliverAt, it.heard) }

    /** The player listened to the call that rang at [at]. */
    fun hear(at: Long) = copy(messages = messages.map { if (it.call != null && it.call != CallStatus.RINGING && it.deliverAt == at) it.copy(heard = true) else it })
}
