package com.rukia.police.infrastructure.persistence

import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Search
import com.rukia.police.domain.port.CaseFileRepository
import com.rukia.police.domain.port.SearchLog
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json { ignoreUnknownKeys = true }

/** Reads police/case.json from the case's content folder. */
class JsonCaseFileRepository(private val readFile: (path: String) -> String) : CaseFileRepository {
    private val question by lazy { json.decodeFromString<CaseQuestion>(readFile(PATH)) }

    override fun question() = question

    companion object { const val PATH = "police/case.json" }
}

/** One JSON file in the police app's save folder. */
class JsonSearchLog(private val file: File) : SearchLog {
    override fun all(): List<Search> = if (file.exists()) json.decodeFromString(file.readText()) else emptyList()

    override fun add(search: Search) = file.writeText(json.encodeToString(all() + search))

    override fun clear() {
        file.delete()
    }
}
