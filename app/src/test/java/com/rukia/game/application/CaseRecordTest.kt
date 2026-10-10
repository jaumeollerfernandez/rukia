package com.rukia.game.application

import com.rukia.game.domain.model.Achievement
import com.rukia.game.domain.model.AchievementGroup
import com.rukia.game.domain.model.CaseEnding
import com.rukia.game.domain.model.CaseRecord
import com.rukia.game.domain.model.EndingTier
import com.rukia.game.domain.model.GameCase
import com.rukia.game.domain.model.StoryVariables
import com.rukia.game.domain.port.CaseOutcome
import com.rukia.game.domain.port.CaseRecords
import com.rukia.game.domain.port.CurrentCaseStore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CaseRecordTest {
    private val case = GameCase(
        "c", 1, "C", "",
        endings = listOf(
            CaseEnding(1, "Good", EndingTier.Good, "", "", "D7 06:31"),
            CaseEnding(4, "Bad", EndingTier.Bad, "", "", "D7 06:45"),
        ),
        achievements = listOf(
            Achievement("bus", AchievementGroup.FalseLeads, "Bus", "") { it.isTrue("descarta_bus") },
            Achievement("trust", AchievementGroup.KeyClues, "Trust", "") { it.number("confianza") >= 3 },
        ),
    )
    private var story = mapOf<String, Any>()
    private val current = object : CurrentCaseStore {
        var id: String? = "c"
        override fun get() = id
        override fun set(caseId: String?) { id = caseId }
    }
    private val records = object : CaseRecords {
        val all = HashMap<String, CaseRecord>()
        override fun get(caseId: String) = all[caseId]
        override fun save(caseId: String, record: CaseRecord) { all[caseId] = record }
    }
    private val outcome = object : CaseOutcome {
        override fun storyVariables(caseId: String) = StoryVariables { story[it] }
        override fun squads(caseId: String) = 1 to 2
    }
    private var wiped = 0
    private val close = CloseCase({ listOf(case) }, current, outcome, records)
    private val reopen = ReopenCase({ wiped++ }, records)
    private val report = GetCaseReport({ listOf(case) }, records)

    @Test fun `closing records the ending and keeps what earlier runs found`() {
        story = mapOf("final_caso" to 4, "descarta_bus" to true, "confianza" to 1)
        close("c")
        assertNull(current.id, "a closed case no longer opens on launch")
        val first = report("c")!!
        assertEquals("Bad", first.ending.name)
        assertEquals(1 to 1, first.falseLeads)
        assertEquals(1 to 2, first.record.squadsSent to first.record.squads)
        assertEquals(50, first.percent) // 1 achievement + 1 ending out of 4

        reopen("c")
        assertEquals(1, wiped)
        assertNull(report("c"), "a replayed case is open again")

        story = mapOf("final_caso" to 1, "confianza" to 3)
        close("c")
        val second = report("c")!!
        assertEquals("Good", second.ending.name)
        assertEquals(setOf("bus", "trust"), second.record.achievements)
        assertEquals(setOf(1, 4), second.record.endings)
        assertEquals(0 to 1, second.falseLeads, "false leads count this run only")
        assertEquals(100, second.percent)
    }

    @Test fun `a story without a known ending doesn't close the case`() {
        story = mapOf("final_caso" to 0)
        assertNull(close("c"))
        assertTrue(records.all.isEmpty())
    }
}
