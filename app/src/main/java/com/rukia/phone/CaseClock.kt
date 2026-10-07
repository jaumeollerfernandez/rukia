package com.rukia.phone

import android.content.Context
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/**
 * The case's own calendar. D1 is the day the case was first opened; "D3 22:15" is 22:15 two days later, and days
 * before D1 (D0, D-1...) are the phone's past. Stories, Gonpi and the police deadline all count from here.
 */
object CaseClock {
    /** Midnight of D1, saved the first time it's asked for so the week keeps its dates. */
    fun start(context: Context, caseId: String, zone: ZoneId = ZoneId.systemDefault()): Long {
        val file = file(context, caseId)
        file.takeIf { it.exists() }?.readText()?.trim()?.toLongOrNull()?.let { return it }
        val start = LocalDate.now(zone).atStartOfDay(zone).toInstant().toEpochMilli()
        file.parentFile?.mkdirs()
        file.writeText("$start")
        return start
    }

    /** False until the case is first opened, and again after a reset. */
    fun isStarted(context: Context, caseId: String) = file(context, caseId).exists()

    /** Starts the week over: the next [start] is today. */
    fun reset(context: Context, caseId: String) {
        file(context, caseId).delete()
    }

    private fun file(context: Context, caseId: String) = File(context.filesDir, "cases/$caseId/clock.txt")
}

private val caseTimeFormat = Regex("""D(-?\d+)\s+(\d{1,2}):(\d{2})""")

/** "D3 22:15" as epoch millis, D1 being the day that starts at [start]. Null if [spec] isn't written like that. */
fun caseTime(spec: String, start: Long, zone: ZoneId): Long? {
    val (day, hour, minute) = caseTimeFormat.matchEntire(spec.trim())?.destructured ?: return null
    return Instant.ofEpochMilli(start).atZone(zone).plusDays(day.toLong() - 1)
        .withHour(hour.toInt()).withMinute(minute.toInt()).toInstant().toEpochMilli()
}

/** Which day of the case [millis] falls on: 1 on D1, 2 on D2... */
fun caseDay(millis: Long, start: Long, zone: ZoneId): Int =
    ChronoUnit.DAYS.between(Instant.ofEpochMilli(start).atZone(zone).toLocalDate(), Instant.ofEpochMilli(millis).atZone(zone).toLocalDate()).toInt() + 1
