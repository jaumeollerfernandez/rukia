package com.rukia.police.domain.port

import com.rukia.police.domain.model.Dispatch
import com.rukia.police.domain.model.Operations

interface OperationsRepository {
    /** The case's squads and operations; empty if the case has none. */
    fun operations(): Operations
}

/** Squads the player has sent, kept with the save. */
interface DispatchLog {
    fun all(): List<Dispatch>
    fun add(dispatch: Dispatch)
    fun clear()
}

/** Plays the officers' report (an ink knot) in the story, in the chat [channel]. The phone backs it with the chat app. */
fun interface Radio {
    fun report(channel: String, knot: String)
}
