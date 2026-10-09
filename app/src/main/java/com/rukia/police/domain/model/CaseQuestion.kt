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
data class CaseQuestion(val question: String, val target: Spot, val station: Spot, val tolerance: Double = 500.0, val deadline: String? = null) {
    init { require(tolerance > 0) { "Tolerance must be positive" } }

    /** 0 if [target] is outside the circle; otherwise the tighter the circle the higher, 100 at [tolerance] or less. */
    fun accuracy(center: Spot, radius: Double): Int =
        if (center.metersTo(target) > radius) 0 else min(100, (100 * tolerance / radius).roundToInt())
}

/** A squad sent on case day [day] at [at] (epoch millis) to search a circle. Its [accuracy] is told at [readyAt]. */
@Serializable
data class Search(val day: Int, val center: Spot, val radius: Double, val at: Long, val accuracy: Int) {
    val readyAt get() = at + DURATION

    companion object { const val DURATION = 15 * 60_000L }
}

/** What the solve screen shows: the searches so far, today's case day, the last one ([lastDay], null = no deadline). */
data class SearchBoard(val searches: List<Search>, val today: Int, val lastDay: Int?, val canSearch: Boolean, val solved: Boolean) {
    /** The search still under way, if any. */
    fun pending(now: Long) = searches.lastOrNull()?.takeIf { now < it.readyAt }
}
