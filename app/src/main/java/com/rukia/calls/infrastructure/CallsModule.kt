package com.rukia.calls.infrastructure

import com.rukia.calls.application.CountNewVoicemails
import com.rukia.calls.application.ListCalls
import com.rukia.calls.application.ListenTo
import com.rukia.calls.domain.port.CallLog

/** Composition root of the Calls app for one case. [log] is the phone's call history; the phone wires it to the story. */
class CallsModule(log: CallLog) {
    val listCalls = ListCalls(log)
    val countNewVoicemails = CountNewVoicemails(log)
    val listenTo = ListenTo(log)
}
