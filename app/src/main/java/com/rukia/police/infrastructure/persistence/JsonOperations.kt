package com.rukia.police.infrastructure.persistence

import com.rukia.police.domain.model.Dispatch
import com.rukia.police.domain.model.Operations
import com.rukia.police.domain.port.DispatchLog
import com.rukia.police.domain.port.OperationsRepository
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json { ignoreUnknownKeys = true }

/** Reads police/actions.json from the case's content folder; [readFile] returns null if it isn't there. */
class JsonOperationsRepository(private val readFile: (path: String) -> String?) : OperationsRepository {
    private val operations by lazy { readFile(PATH)?.let { json.decodeFromString<Operations>(it) } ?: Operations() }

    override fun operations() = operations

    companion object { const val PATH = "police/actions.json" }
}

/** One JSON file in the police app's save folder. */
class JsonDispatchLog(private val file: File) : DispatchLog {
    override fun all(): List<Dispatch> = if (file.exists()) json.decodeFromString(file.readText()) else emptyList()

    override fun add(dispatch: Dispatch) = file.writeText(json.encodeToString(all() + dispatch))

    override fun clear() {
        file.delete()
    }
}
