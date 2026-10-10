package com.rukia.police.domain.port

import com.rukia.police.domain.model.Spot

/** Looks a place name up on the map. Blocking: call it off the main thread. */
fun interface PlaceFinder {
    /** The places [query] may mean, best match first; none if nothing matches or the lookup fails. */
    fun find(query: String): List<Spot>
}
