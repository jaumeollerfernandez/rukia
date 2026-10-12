package com.rukia.calls.domain.port

import com.rukia.calls.domain.model.VoiceCall

/** The phone's calls, which the story makes. The phone backs it with the chat app, where the story lives. */
interface CallLog {
    /** Every call that has ended, answered or gone to voicemail. */
    fun calls(): List<VoiceCall>

    /** The player listened to [call]. */
    fun hear(call: VoiceCall)
}
