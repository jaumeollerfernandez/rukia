package com.rukia.police.infrastructure

import android.content.Context
import com.rukia.police.application.DispatchSquad
import com.rukia.police.application.GetCaseQuestion
import com.rukia.police.application.GetOperations
import com.rukia.police.application.SolveCase
import com.rukia.police.domain.port.Radio
import com.rukia.police.domain.port.SaveFile
import com.rukia.police.infrastructure.persistence.JsonCaseFileRepository
import com.rukia.police.infrastructure.persistence.JsonDispatchLog
import com.rukia.police.infrastructure.persistence.JsonOperationsRepository
import com.rukia.phone.CaseClock
import com.rukia.phone.CaseFolders
import com.rukia.phone.caseTime
import java.io.File
import java.time.ZoneId

/**
 * Composition root of the Police Department app for one case. [save] wipes the rest of the game (chats, clock),
 * [onSolved] tells the story the case was solved, and [radio] plays the officers' reports in it; the phone wires them in.
 */
class PoliceModule(context: Context, caseId: String, save: SaveFile, onSolved: () -> Unit, radio: Radio) {
    private val content = CaseFolders.content(caseId)
    private val assets = context.assets
    private fun read(path: String) = runCatching { assets.open("$content/$path").bufferedReader().use { it.readText() } }.getOrNull()
    private val caseFile = JsonCaseFileRepository { path -> read(path) ?: error("Missing $content/$path") }
    private val operations = JsonOperationsRepository(::read)
    private val dispatches = JsonDispatchLog(File(CaseFolders.saves(context, caseId, "police"), "dispatches.json"))
    private val timeOf = { spec: String -> caseTime(spec, CaseClock.start(context, caseId), ZoneId.systemDefault()) }

    /** Wipes the whole save, the squads sent included. */
    val resetSave = SaveFile {
        dispatches.clear()
        save.reset()
    }

    val getCaseQuestion = GetCaseQuestion(caseFile)
    val solveCase = SolveCase(caseFile, onSolved, resetSave)
    val getOperations = GetOperations(operations, dispatches, timeOf)
    val dispatchSquad = DispatchSquad(operations, dispatches, radio, timeOf)
    /** Whether the case has field operations at all (police/actions.json). */
    val hasOperations = operations.operations().squads.isNotEmpty()
    val operationLabels = operations.operations().operations.associate { it.id to it.label }

    /** When the case closes (epoch millis), from the question's deadline. Null if it never does. */
    val deadline: Long? = caseFile.question().deadline?.let(timeOf)
}
