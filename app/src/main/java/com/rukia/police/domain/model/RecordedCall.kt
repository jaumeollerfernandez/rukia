package com.rukia.police.domain.model

/**
 * A call the player answered, kept as evidence: who called ([from] says who they are), when ([at], epoch millis, and
 * [time], "21:30"), and the voice clip ([audio], a media path) to replay.
 */
data class RecordedCall(
    val caller: String, val time: String, val audio: String, val from: String = "", val at: Long = 0,
    /** Its chat, and whether the player has heard it (live or from the voicemail). */
    val chatId: String = "", val heard: Boolean = false,
)
