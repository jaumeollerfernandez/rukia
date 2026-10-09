package com.rukia.police.domain.port

import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Search

interface CaseFileRepository {
    fun question(): CaseQuestion
}

/** The squads sent to search the map, kept with the save. */
interface SearchLog {
    fun all(): List<Search>
    fun add(search: Search)
    fun clear()
}
