package com.rukia.police.domain.model

import kotlinx.serialization.Serializable

/** One picture the player can pick. [image] is a path inside the case's media folder; without it a colored tile is shown. */
@Serializable
data class Suspect(val id: String, val label: String, val image: String? = null, val color: String = "#5C6BC0")

@Serializable
data class CaseQuestion(val question: String, val answer: String, val options: List<Suspect>) {
    init {
        require(options.map { it.id }.toSet().size == options.size) { "Option ids must be unique" }
        require(options.any { it.id == answer }) { "Answer '$answer' is not one of the options" }
    }
}

enum class Verdict { SOLVED, FAILED }
