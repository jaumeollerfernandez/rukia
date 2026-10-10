package com.rukia.police.domain.model

import kotlinx.serialization.Serializable

/**
 * The case's police/records.json: the records the player can ask the station for. [hours] is how long the paperwork
 * takes on each case day (D1 first): the further into the week, the faster it goes.
 */
@Serializable
data class PoliceRecords(val hours: List<Int> = listOf(12, 10, 8, 6, 4, 2, 1), val records: List<PoliceRecord> = emptyList()) {
    init {
        require(records.map { it.id }.toSet().size == records.size) { "Record ids must be unique" }
    }

    /** Hours of paperwork on case day [day]. */
    fun hoursOn(day: Int) = hours.getOrElse((day - 1).coerceAtLeast(0)) { hours.lastOrNull() ?: 1 }
}

/**
 * A person's or a vehicle's record. Its text is case content, in the case's language. [tone] colors the [badge]
 * (missing, police, clean, complainant, mentioned). [banner] replaces the usual dark header (a missing person's red one).
 * [mugshot] is the plate of a police photo, front and profile, for someone who was once arrested; [missingSince] a case
 * time ("D-1 23:00") the record counts from. Reading it sets the story's `ficha_<id>` to true. [unlockedBy]: the record
 * only shows up once one of these holds, "ficha:<id>" (that record was read) or the name of a true story variable;
 * empty, it's there from the start.
 */
@Serializable
data class PoliceRecord(
    val id: String,
    val name: String,
    val subtitle: String,
    val badge: String,
    val tone: String = "clean",
    val vehicle: Boolean = false,
    val keywords: String = "",
    val reference: String = "",
    val banner: String? = null,
    val bannerNote: String? = null,
    val photo: String? = null,
    val photoCaption: String? = null,
    val mugshot: String? = null,
    val facts: List<String> = emptyList(),
    val missingSince: String? = null,
    val sections: List<RecordSection> = emptyList(),
    val unlockedBy: List<String> = emptyList(),
) {
    fun matches(query: String) = query.isBlank() || query.trim().lowercase().let { q -> q in name.lowercase() || q in keywords.lowercase() }

    /** The story variable set when the player reads it. */
    val flag get() = "ficha_$id"
}

/** One numbered part of a record. Every part is optional: most sections use one or two. */
@Serializable
data class RecordSection(
    val title: String,
    val fields: List<RecordField> = emptyList(),
    val highlight: String? = null,
    val entries: List<RecordEntry> = emptyList(),
    val timeline: List<RecordEvent> = emptyList(),
    val fingerprints: Fingerprints? = null,
    val links: List<RecordLink> = emptyList(),
    val text: String? = null,
    val signature: String? = null,
)

/** A labelled value. [wide] takes the whole row; [mono] is for codes (DNI, plates). */
@Serializable
data class RecordField(val label: String, val value: String, val wide: Boolean = false, val mono: Boolean = false)

/** A police or court entry. [tone] "warn" marks it in amber, else grey. */
@Serializable
data class RecordEntry(val code: String, val date: String, val title: String, val detail: String, val state: String, val tone: String = "warn")

/** Something done in the case. [done] false: still pending. */
@Serializable
data class RecordEvent(val time: String, val text: String, val source: String, val done: Boolean = true)

/** Someone or something connected, with its own badge. [record] is the id of its record, if it has one. */
@Serializable
data class RecordLink(val name: String, val role: String, val badge: String, val tone: String = "clean", val record: String? = null)

/** Ten fingers by Olóriz codes (V whorl, A arch, E/3 loop to one side, 2/I loop to the other), right then left hand. */
@Serializable
data class Fingerprints(val right: String, val left: String, val formula: String, val note: String, val taken: String)

/** A record the player asked for: it arrives at [readyAt] (epoch millis). [read] once they've opened it. */
@Serializable
data class RecordRequest(val record: String, val requestedAt: Long, val readyAt: Long, val read: Boolean = false)

enum class RecordStatus { Available, Pending, Ready, Read }

data class RecordRow(val record: PoliceRecord, val status: RecordStatus, val request: RecordRequest?)

/** The records screen: every record and where its paperwork is. Only one goes through at a time: [pending]. */
data class RecordsBoard(val rows: List<RecordRow>, val pending: RecordRequest?, val hoursNow: Int) {
    val canRequest get() = pending == null
}

/** A record the player opens: [consultedAt] is when it came in ("D3 18:40"), [missingFor] how long since [PoliceRecord.missingSince]. */
data class OpenedRecord(val record: PoliceRecord, val consultedAt: String, val missingFor: Long?)

/** A police photo of someone arrested, from a record the player read. [path] is where a real picture would be, in the case's content. */
data class Mugshot(val record: String, val plate: String, val path: String, val profile: Boolean)
