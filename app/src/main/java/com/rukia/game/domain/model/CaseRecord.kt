package com.rukia.game.domain.model

import kotlinx.serialization.Serializable

/** How an ending went: it colors the closed case file and its report. */
enum class EndingTier { Good, Partial, Bad }

/**
 * One way a case can end, numbered as the story's `final_caso` variable sets it. [stamp] is stamped on the closed
 * case file, [quote] is the story line the report quotes and [closedAt] the case time it closes at ("D7 06:31").
 */
class CaseEnding(val number: Int, val name: String, val tier: EndingTier, val stamp: String, val quote: String, val closedAt: String)

/** The kinds of achievement, which the report lists apart. */
enum class AchievementGroup { KeyClues, FalseLeads, Decisions }

/** Something to find out or do in a case, [unlocked] by how the story's variables stand when it ends. */
class Achievement(val id: String, val group: AchievementGroup, val title: String, val description: String, val unlocked: (StoryVariables) -> Boolean)

/** A case's story variables, as its ending left them. */
fun interface StoryVariables {
    operator fun get(name: String): Any?

    /** A true flag, or a count above zero. */
    fun isTrue(name: String) = when (val value = get(name)) {
        is Boolean -> value
        is Int -> value > 0
        else -> false
    }

    fun number(name: String) = get(name) as? Int ?: 0
}

/**
 * How a case went, kept apart from its save so playing it again doesn't lose it. [ending] is how it last closed, null
 * while it's being played again; [run] are that run's achievement ids and [squadsSent] its police squads (out of [squads]).
 * [achievements] and [endings] gather every run's.
 */
@Serializable
data class CaseRecord(
    val ending: Int? = null,
    val run: Set<String> = emptySet(),
    val squadsSent: Int = 0,
    val squads: Int = 0,
    val achievements: Set<String> = emptySet(),
    val endings: Set<Int> = emptySet(),
) {
    val closed get() = ending != null
}

/** A closed case's report: its [record] read against the [case]'s endings and achievements. */
class CaseReport(val case: GameCase, val record: CaseRecord) {
    val ending = case.endings.first { it.number == record.ending }
    val achievements = record.achievements.size
    val endings = record.endings.size

    /** Share of every achievement and ending found so far, 0..100. */
    val percent = 100 * (achievements + endings) / (case.achievements.size + case.endings.size).coerceAtLeast(1)

    /** False leads ruled out in the last run, and how many the case has. */
    val falseLeads = case.achievements.filter { it.group == AchievementGroup.FalseLeads }.let { all -> all.count { it.id in record.run } to all.size }
}
