package com.rukia.police.application

import com.rukia.police.domain.model.Place
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.domain.port.StoryFacts

class GetCaseQuestion(private val caseFile: CaseFileRepository) {
    operator fun invoke() = caseFile.question()
}

/** The case's places the player already knows of: those with no [Place.unlockedBy], or one of whose variables is true. */
class GetKnownPlaces(private val caseFile: CaseFileRepository, private val facts: StoryFacts) {
    operator fun invoke(): List<Place> = caseFile.question().places.filter { p -> p.unlockedBy.isEmpty() || p.unlockedBy.any(facts::isTrue) }
}
