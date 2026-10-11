package com.rukia.game.application

import com.rukia.game.domain.model.CaseKind
import com.rukia.game.domain.model.CaseRecord
import com.rukia.game.domain.model.GameCase
import com.rukia.game.domain.port.CaseProgress
import com.rukia.game.domain.port.CaseRecords
import com.rukia.game.domain.port.CurrentCaseStore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ConnectingTest {
    private val case = GameCase("c", 1, "C", "")
    private val debug = GameCase("d", 2, "D", "", kind = CaseKind.Debug)
    private var now = 0L
    private var lateStart = false // opened from noon on: D1 is tomorrow
    private val starts = HashMap<String, Long>()
    private val progress = object : CaseProgress {
        override fun isStarted(caseId: String) = caseId in starts
        override fun start(caseId: String) = starts.getOrPut(caseId) { if (lateStart) 100L else 0L }
        override fun now(caseId: String) = now
    }
    private val current = object : CurrentCaseStore {
        var id: String? = null
        override fun get() = id
        override fun set(caseId: String?) { id = caseId }
    }
    private val records = object : CaseRecords {
        override fun get(caseId: String): CaseRecord? = null
        override fun save(caseId: String, record: CaseRecord) {}
    }
    private val connecting = IsConnecting(progress)
    private val play = PlayCase(current, records, ReopenCase({}, records), progress, connecting)
    private val currentCase = GetCurrentCase({ listOf(case, debug) }, current, progress, connecting)

    @Test fun `a case opened in the morning plays at once`() {
        assertTrue(play(case))
        assertEquals("c", currentCase()?.id)
    }

    @Test fun `a case opened from noon on connects until its D1`() {
        lateStart = true
        assertFalse(play(case))
        assertTrue(connecting(case))
        assertNull(currentCase())
        now = 100
        assertFalse(connecting(case))
        assertTrue(play(case))
        assertEquals("c", currentCase()?.id)
    }

    @Test fun `a debug case opens at any time`() {
        lateStart = true
        assertTrue(play(debug))
        assertEquals("d", currentCase()?.id)
    }
}
