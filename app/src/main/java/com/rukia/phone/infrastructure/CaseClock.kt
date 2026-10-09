package com.rukia.phone.infrastructure

import com.rukia.phone.domain.model.isDebugCase
import android.content.Context
import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

/**
 * The case's own calendar. D1 is the day the case was first opened; "D3 22:15" is 22:15 two days later, and days
 * before D1 (D0, D-1...) are the phone's past. Stories, Gonpi and the police deadline all count from here.
 * Its "now" is real time, except in a debug case, which runs [skipped] ahead.
 */
object CaseClock {
    /** Opening a case from this hour on starts its D1 the next day. */
    const val LATE_START_HOUR = 14

    private var filesDir: File? = null
    private val skips = ConcurrentHashMap<String, Long>()

    /** Lets [now] find the debug cases' saved skips; the game calls it once when it starts. */
    fun init(context: Context) { filesDir = context.filesDir }

    /**
     * Midnight of D1, saved the first time it's asked for so the week keeps its dates. Opened in the morning, D1 is
     * today; from [LATE_START_HOUR] on it's tomorrow, so the day's timed lines don't all arrive at once. That evening
     * is D0: stories can tell (`dia` is 0) and introduce the case before it starts.
     */
    fun start(context: Context, caseId: String, zone: ZoneId = ZoneId.systemDefault()): Long {
        val file = file(context, caseId)
        file.takeIf { it.exists() }?.readText()?.trim()?.toLongOrNull()?.let { return it }
        val now = Instant.ofEpochMilli(now(caseId)).atZone(zone)
        val day = if (now.hour >= LATE_START_HOUR) now.toLocalDate().plusDays(1) else now.toLocalDate()
        val start = day.atStartOfDay(zone).toInstant().toEpochMilli()
        file.parentFile?.mkdirs()
        file.writeText("$start")
        return start
    }

    /** False until the case is first opened, and again after a reset. */
    fun isStarted(context: Context, caseId: String) = file(context, caseId).exists()

    /** Starts the week over: the next [start] is today, and a debug case's clock is back to real time. */
    fun reset(context: Context, caseId: String) {
        file(context, caseId).delete()
        skipFile(context.filesDir, caseId).delete()
        skips.remove(caseId)
    }

    /** The case's current time, epoch millis. */
    fun now(caseId: String) = System.currentTimeMillis() + skipped(caseId)

    /** [now] as a [Clock], for the use cases. */
    fun clock(caseId: String): Clock = object : Clock() {
        override fun getZone(): ZoneId = ZoneId.systemDefault()
        override fun withZone(zone: ZoneId) = this
        override fun millis() = now(caseId)
        override fun instant(): Instant = Instant.ofEpochMilli(millis())
    }

    /** How far a debug case's clock runs ahead of real time, in millis. Always 0 for a real case. */
    fun skipped(caseId: String): Long {
        if (!isDebugCase(caseId)) return 0
        val dir = filesDir ?: return 0
        return skips.getOrPut(caseId) { skipFile(dir, caseId).takeIf { it.exists() }?.readText()?.trim()?.toLongOrNull() ?: 0 }
    }

    /** Moves a debug case's clock [millis] ahead. Only forward: the story can't take back what it already said. */
    fun skip(context: Context, caseId: String, millis: Long) {
        require(isDebugCase(caseId)) { "Only debug cases can move their clock" }
        val total = skipped(caseId) + millis.coerceAtLeast(0)
        skips[caseId] = total
        skipFile(context.filesDir, caseId).apply { parentFile?.mkdirs() }.writeText("$total")
    }

    private fun file(context: Context, caseId: String) = File(context.filesDir, "cases/$caseId/clock.txt")
    private fun skipFile(filesDir: File, caseId: String) = File(filesDir, "cases/$caseId/skip.txt")
}
