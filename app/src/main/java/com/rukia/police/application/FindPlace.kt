package com.rukia.police.application

import com.rukia.police.domain.model.Spot
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.domain.port.PlaceFinder

/** Where [invoke]'s query is. Many names repeat across Catalonia («Sant Salvador»): the one closest to the case's station wins. */
class FindPlace(private val finder: PlaceFinder, private val caseFile: CaseFileRepository) {
    operator fun invoke(query: String): Spot? = query.trim().takeIf { it.isNotEmpty() }?.let(finder::find)
        ?.minByOrNull { it.metersTo(caseFile.question().station) }
}
