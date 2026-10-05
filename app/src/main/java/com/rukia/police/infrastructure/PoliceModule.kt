package com.rukia.police.infrastructure

import android.content.Context
import com.rukia.police.application.GetCaseQuestion
import com.rukia.police.application.SolveCase
import com.rukia.police.domain.port.SaveFile
import com.rukia.police.infrastructure.persistence.JsonCaseFileRepository
import com.rukia.phone.CaseFolders

/** Composition root of the Police Department app for one case. */
class PoliceModule(context: Context, caseId: String, save: SaveFile) {
    private val content = CaseFolders.content(caseId)
    private val caseFile = JsonCaseFileRepository { path -> context.assets.open("$content/$path").bufferedReader().use { it.readText() } }

    val getCaseQuestion = GetCaseQuestion(caseFile)
    val solveCase = SolveCase(caseFile, save)
}
