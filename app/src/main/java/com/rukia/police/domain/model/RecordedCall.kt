package com.rukia.police.domain.model

/** A call the player answered, kept as evidence: who called, when, and the voice clip ([audio], a media path) to replay. */
data class RecordedCall(val caller: String, val time: String, val audio: String)
