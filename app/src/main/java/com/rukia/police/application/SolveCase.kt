package com.rukia.police.application

import com.rukia.police.domain.model.Verdict
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.domain.port.SaveFile

/** The player's single attempt. A wrong answer ends the game and erases the save; a right one is told to the story through [onSolved]. */
class SolveCase(private val caseFile: CaseFileRepository, private val onSolved: () -> Unit = {}, private val save: SaveFile) {
    operator fun invoke(optionId: String): Verdict {
        val question = caseFile.question()
        require(question.options.any { it.id == optionId }) { "Unknown option $optionId" }
        if (optionId == question.answer) return Verdict.SOLVED.also { onSolved() }
        save.reset()
        return Verdict.FAILED
    }
}
