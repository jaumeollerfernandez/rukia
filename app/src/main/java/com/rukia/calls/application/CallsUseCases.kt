package com.rukia.calls.application

import com.rukia.calls.domain.model.VoiceCall
import com.rukia.calls.domain.port.CallLog

/** The calls, newest first. */
class ListCalls(private val log: CallLog) {
    operator fun invoke(): List<VoiceCall> = log.calls().sortedByDescending { it.at }
}

/** Voicemails nobody has listened to yet: the Calls app's badge. */
class CountNewVoicemails(private val log: CallLog) {
    operator fun invoke(): Int = log.calls().count { it.voicemail && !it.heard }
}

/** The player plays [call]: it's no longer new. */
class ListenTo(private val log: CallLog) {
    operator fun invoke(call: VoiceCall) {
        if (!call.heard) log.hear(call)
    }
}
