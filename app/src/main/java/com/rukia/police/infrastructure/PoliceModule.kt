package com.rukia.police.infrastructure

import android.content.Context
import com.rukia.police.application.DispatchSquad
import com.rukia.police.application.FindPlace
import com.rukia.police.application.GetCaseQuestion
import com.rukia.police.infrastructure.geocoding.NominatimPlaceFinder
import com.rukia.police.application.GetOperations
import com.rukia.police.application.GetSearches
import com.rukia.police.application.SearchZone
import com.rukia.police.domain.port.Radio
import com.rukia.police.domain.port.SaveFile
import com.rukia.police.infrastructure.persistence.JsonCaseFileRepository
import com.rukia.police.infrastructure.persistence.JsonDispatchLog
import com.rukia.police.infrastructure.persistence.JsonOperationsRepository
import com.rukia.police.infrastructure.persistence.JsonSearchLog
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.CaseFolders
import com.rukia.phone.domain.model.caseDay
import com.rukia.phone.domain.model.caseTime
import java.io.File
import java.time.ZoneId

/**
 * Composition root of the Police Department app for one case. [save] wipes the rest of the game (chats, clock),
 * and [radio] plays the officers' reports in the story; the phone wires them in.
 */
class PoliceModule(context: Context, caseId: String, save: SaveFile, radio: Radio) {
    private val content = CaseFolders.content(caseId)
    private val assets = context.assets
    private fun read(path: String) = runCatching { assets.open("$content/$path").bufferedReader().use { it.readText() } }.getOrNull()
    private val caseFile = JsonCaseFileRepository { path -> read(path) ?: error("Missing $content/$path") }
    private val operations = JsonOperationsRepository(::read)
    private val dispatches = JsonDispatchLog(File(CaseFolders.saves(context, caseId, "police"), "dispatches.json"))
    private val searches = JsonSearchLog(File(CaseFolders.saves(context, caseId, "police"), "searches.json"))
    private val timeOf = { spec: String -> caseTime(spec, CaseClock.start(context, caseId), ZoneId.systemDefault()) }
    private val dayOf = { millis: Long -> caseDay(millis, CaseClock.start(context, caseId), ZoneId.systemDefault()) }

    /** Wipes the whole save, the squads sent included. */
    val resetSave = SaveFile {
        dispatches.clear()
        searches.clear()
        save.reset()
    }

    /** When the case closes (epoch millis), from the question's deadline. Null if it never does. */
    val deadline: Long? = caseFile.question().deadline?.let(timeOf)

    val getCaseQuestion = GetCaseQuestion(caseFile)
    val findPlace = FindPlace(NominatimPlaceFinder(context.packageName))
    val getSearches = GetSearches(searches, dayOf, deadline, CaseClock.clock(caseId))
    val searchZone = SearchZone(caseFile, searches, getSearches, dayOf, CaseClock.clock(caseId))
    val getOperations = GetOperations(operations, dispatches, timeOf, CaseClock.clock(caseId))
    val dispatchSquad = DispatchSquad(operations, dispatches, radio, timeOf, CaseClock.clock(caseId))
    /** Whether the case has field operations at all (police/actions.json). */
    val hasOperations = operations.operations().squads.isNotEmpty()
    val squadCount = operations.operations().squads.size
    val operationLabels = operations.operations().operations.associate { it.id to it.label }
}
