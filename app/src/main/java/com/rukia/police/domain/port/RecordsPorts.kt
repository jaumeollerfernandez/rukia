package com.rukia.police.domain.port

import com.rukia.police.domain.model.PoliceRecords
import com.rukia.police.domain.model.RecordRequest

interface RecordsRepository {
    /** The case's records; none if the case has none. */
    fun records(): PoliceRecords
}

/** The records the player asked for, kept with the save. */
interface RecordLog {
    fun all(): List<RecordRequest>
    fun save(requests: List<RecordRequest>)
    fun clear()
}

/** Sets a true/false story variable, e.g. `ficha_dani` once the player has read Dani's record. The phone backs it with the chat app. */
fun interface StoryFlags {
    fun set(name: String)
}

/** Reads a true/false story variable, e.g. whether a conversation gave the plate of a car. The phone backs it with the chat app. */
fun interface StoryFacts {
    fun isTrue(name: String): Boolean
}
