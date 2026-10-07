package com.rukia.chat.domain.model

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

@Serializable
data class Character(
    val id: String,
    val name: String,
    val online: Boolean = false,
    val status: String = "",
    val color: String = "#888888",
    /** Profile photo, a path inside the case's media folder (e.g. "photos/rukia.jpg"). Without it the avatar shows the initial. */
    val photo: String? = null,
    /** Not listed in Contacts, e.g. an unknown number that writes first. Their chat still works. */
    val hidden: Boolean = false,
    /**
     * When they're online, e.g. "08:00-15:00,20:00-23:00"; a range can cross midnight ("23:00-01:00"). Without it,
     * [online] says whether they always are. A reply to the player outside these hours waits until the next range starts.
     */
    val hours: String? = null,
) {
    private fun ranges() = hours.orEmpty().split(',').mapNotNull { range ->
        val (from, to) = range.split('-').map { it.trim() }.takeIf { it.size == 2 } ?: return@mapNotNull null
        LocalTime.parse(from) to LocalTime.parse(to)
    }

    fun onlineAt(time: LocalTime): Boolean {
        if (hours == null) return online
        return ranges().any { (from, to) -> if (from <= to) time >= from && time < to else time >= from || time < to }
    }

    fun onlineAt(millis: Long, zone: ZoneId = ZoneId.systemDefault()) = onlineAt(Instant.ofEpochMilli(millis).atZone(zone).toLocalTime())

    /** [millis] if they're online then, otherwise when their next range starts. */
    fun nextOnline(millis: Long, zone: ZoneId): Long {
        val now = Instant.ofEpochMilli(millis).atZone(zone)
        if (hours == null || onlineAt(now.toLocalTime())) return millis
        return ranges().minOfOrNull { (from, _) ->
            val today = now.with(from).withSecond(0).withNano(0)
            (if (today.isAfter(now)) today else today.plusDays(1)).toInstant().toEpochMilli()
        } ?: millis
    }
}
