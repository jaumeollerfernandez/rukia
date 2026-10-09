package com.rukia.police.domain.model

import kotlinx.serialization.Serializable
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

/** A point on the map. */
@Serializable
data class Spot(val lat: Double, val lon: Double) {
    /** Great-circle distance to [other], in meters. */
    fun metersTo(other: Spot): Double {
        val dLat = Math.toRadians(other.lat - lat)
        val dLon = Math.toRadians(other.lon - lon)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat)) * cos(Math.toRadians(other.lat)) * sin(dLon / 2).pow(2)
        return 2 * 6_371_000.0 * asin(sqrt(a))
    }
}

/**
 * The case's question, answered on the map: each day the player sends a squad from [station] to search a circle, and
 * it scores how well the circle pins [target]. A circle of [tolerance] meters or less around it scores 100%: solved.
 * [deadline]: case time ("D7 06:30") after which no squad goes out. Null = no limit.
 */
@Serializable
data class CaseQuestion(
    val question: String, val target: Spot, val station: Spot, val tolerance: Double = 500.0, val deadline: String? = null,
    /** More police stations around the map, besides the main [station]. */
    val stations: List<Spot> = emptyList(),
) {
    init { require(tolerance > 0) { "Tolerance must be positive" } }

    /** Every police station, the main one included. */
    val allStations get() = listOf(station) + stations

    /** The station closest to [spot]: the one whose squad goes there. */
    fun nearestStation(spot: Spot) = allStations.minBy { it.metersTo(spot) }

    /** 0 if [target] is outside the circle; otherwise the tighter the circle the higher, 100 at [tolerance] or less. */
    fun accuracy(center: Spot, radius: Double): Int =
        if (center.metersTo(target) > radius) 0 else min(100, (100 * tolerance / radius).roundToInt())
}

/** A squad sent on case day [day] at [at] (epoch millis) to search a circle. Its [accuracy] is told at [readyAt]. */
@Serializable
data class Search(
    val day: Int, val center: Spot, val radius: Double, val at: Long, val accuracy: Int,
    /** The station the squad left from, how long it drives there and how long until the result ([duration]). Searches saved before stations had them keep [DURATION]. */
    val from: Spot? = null, val travel: Long = 0, val duration: Long = DURATION,
) {
    val readyAt get() = at + duration

    /** The share of [duration] spent driving; the rest is spent searching the zone. */
    val driveShare get() = if (travel > 0) travel.toDouble() / duration else 0.4

    companion object {
        const val DURATION = 15 * 60_000L
        private const val MILLIS_PER_METER = 30.0 // cars at 120 km/h
        private const val SWEEP_BASE = 5 * 60_000L

        /** Driving [meters] to the zone, and then searching a zone of [radius] meters: 5 minutes plus one per km of radius. */
        fun timing(meters: Double, radius: Double): Pair<Long, Long> {
            val travel = (meters * MILLIS_PER_METER).toLong()
            return travel to travel + SWEEP_BASE + (radius / 1000 * 60_000).toLong()
        }
    }
}

/** What the solve screen shows: the searches so far, today's case day, the last one ([lastDay], null = no deadline). */
data class SearchBoard(val searches: List<Search>, val today: Int, val lastDay: Int?, val canSearch: Boolean, val solved: Boolean) {
    /** The search still under way, if any. */
    fun pending(now: Long) = searches.lastOrNull()?.takeIf { now < it.readyAt }
}
