package com.rukia.police.domain.model

import kotlinx.serialization.Serializable

/** Officers the player can send once, between the case times [from] and [until] ("D6 08:00"). */
@Serializable
data class Squad(val id: String, val label: String, val from: String, val until: String)

/**
 * Somewhere the player can send a squad: [type] is "patrol" or "inspect". Sending one plays the ink knot [knot],
 * whose lines are the officers' report. [squads] limits it to some squads; empty = any.
 */
@Serializable
data class Operation(val id: String, val label: String, val knot: String, val type: String = "inspect", val squads: List<String> = emptyList()) {
    fun allows(squad: Squad) = squads.isEmpty() || squad.id in squads
}

/** The case's police/actions.json. [channel] is the chat (a chat id) where the officers' reports arrive. */
@Serializable
data class Operations(val channel: String = "central", val squads: List<Squad> = emptyList(), val operations: List<Operation> = emptyList()) {
    init {
        val squadIds = squads.map { it.id }.toSet()
        require(squadIds.size == squads.size) { "Squad ids must be unique" }
        require(operations.map { it.id }.toSet().size == operations.size) { "Operation ids must be unique" }
        operations.flatMap { it.squads }.firstOrNull { it !in squadIds }?.let { error("Unknown squad '$it'") }
    }
}

/** A squad the player sent somewhere, at [at] (epoch millis). */
@Serializable
data class Dispatch(val operation: String, val squad: String, val at: Long)

/** What the Police app shows: the squad that can go out now (and until when), where it can go, and what was sent so far. */
data class OperationsBoard(val squad: Squad?, val squadUntil: Long?, val operations: List<Operation>, val dispatched: List<Dispatch>)
