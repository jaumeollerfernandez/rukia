package com.rukia.police.infrastructure.persistence

import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.port.CaseFileRepository
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

/** Reads police/case.json from the case's content folder. */
class JsonCaseFileRepository(private val readFile: (path: String) -> String) : CaseFileRepository {
    private val question by lazy { json.decodeFromString<CaseQuestion>(readFile(PATH)) }

    override fun question() = question

    companion object { const val PATH = "police/case.json" }
}
