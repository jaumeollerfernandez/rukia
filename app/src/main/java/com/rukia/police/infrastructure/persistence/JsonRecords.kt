package com.rukia.police.infrastructure.persistence

import com.rukia.police.domain.model.PoliceRecords
import com.rukia.police.domain.model.RecordRequest
import com.rukia.police.domain.port.RecordLog
import com.rukia.police.domain.port.RecordsRepository
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json { ignoreUnknownKeys = true }

/** Reads police/records.json from the case's content folder; [readFile] returns null if it isn't there. */
class JsonRecordsRepository(private val readFile: (path: String) -> String?) : RecordsRepository {
    private val records by lazy { readFile(PATH)?.let { json.decodeFromString<PoliceRecords>(it) } ?: PoliceRecords() }

    override fun records() = records

    companion object { const val PATH = "police/records.json" }
}

/** One JSON file in the police app's save folder. */
class JsonRecordLog(private val file: File) : RecordLog {
    override fun all(): List<RecordRequest> = if (file.exists()) json.decodeFromString(file.readText()) else emptyList()

    override fun save(requests: List<RecordRequest>) = file.writeText(json.encodeToString(requests))

    override fun clear() {
        file.delete()
    }
}
