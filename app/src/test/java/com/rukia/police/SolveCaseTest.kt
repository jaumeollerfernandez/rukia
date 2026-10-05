package com.rukia.police

import com.rukia.police.application.SolveCase
import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Suspect
import com.rukia.police.domain.model.Verdict
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.infrastructure.persistence.JsonCaseFileRepository
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SolveCaseTest {
    private val question = CaseQuestion("Who?", "b", listOf(Suspect("a", "A"), Suspect("b", "B")))
    private val caseFile = object : CaseFileRepository { override fun question() = question }

    @Test fun `right answer solves the case and keeps the save`() {
        var resets = 0
        assertEquals(Verdict.SOLVED, SolveCase(caseFile) { resets++ }("b"))
        assertEquals(0, resets)
    }

    @Test fun `wrong answer is game over and erases the save`() {
        var resets = 0
        assertEquals(Verdict.FAILED, SolveCase(caseFile) { resets++ }("a"))
        assertEquals(1, resets)
        assertFailsWith<IllegalArgumentException> { SolveCase(caseFile) {}("nobody") }
    }

    @Test fun `the answer must be one of the options`() {
        assertFailsWith<IllegalArgumentException> { CaseQuestion("Who?", "z", listOf(Suspect("a", "A"))) }
    }

    @Test fun `every case's question file loads and is consistent`() {
        for (case in File("src/main/assets/cases").listFiles()!!) {
            val q = JsonCaseFileRepository { File(case, it).readText() }.question()
            assertTrue(q.options.size >= 2, "${case.name}: needs at least two options")
        }
    }
}
