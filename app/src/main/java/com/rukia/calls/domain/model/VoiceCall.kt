package com.rukia.calls.domain.model

/**
 * A call to the phone, from the call history: who called ([caller], and [about] who they are, [color] for their
 * picture), when ([at], epoch millis), and what they said ([audio], a media path). Answered, the player heard it live;
 * otherwise it went to [voicemail]. [heard] once the player has listened to it.
 */
data class VoiceCall(
    val chatId: String, val caller: String, val about: String, val color: String,
    val at: Long, val audio: String?, val voicemail: Boolean, val heard: Boolean,
)
