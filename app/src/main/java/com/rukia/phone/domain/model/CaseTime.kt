package com.rukia.phone.domain.model

import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/** A debug copy of a case ("debug-<id>"): the same content, its own save, and a clock the tester can move ahead. */
const val DEBUG_CASE_PREFIX = "debug-"

fun isDebugCase(caseId: String) = caseId.startsWith(DEBUG_CASE_PREFIX)

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

/** [millis] as a case time, "D3 18:40": the opposite of [caseTime]. */
fun caseTimeLabel(millis: Long, start: Long, zone: ZoneId): String {
    val time = Instant.ofEpochMilli(millis).atZone(zone)
    return "D${caseDay(millis, start, zone)} %02d:%02d".format(time.hour, time.minute)
}
