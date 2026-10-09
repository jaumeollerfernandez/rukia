package com.rukia.gonpi.infrastructure.persistence

import com.rukia.gonpi.domain.model.Gonpi
import com.rukia.gonpi.domain.port.GonpiRepository
import com.rukia.gonpi.domain.port.LikeRepository
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json { ignoreUnknownKeys = true }

/** Reads gonpi/gonpi.json from the case's content folder. A case without the file has an empty Gonpi; a broken file fails loudly. */
class JsonGonpiRepository(private val readFile: (path: String) -> String?) : GonpiRepository {
    private val gonpi by lazy { readFile(PATH)?.let(::parse) ?: Gonpi() }

    override fun gonpi() = gonpi

    companion object {
        const val PATH = "gonpi/gonpi.json"
        fun parse(text: String) = json.decodeFromString<Gonpi>(text)
    }
}

/** One post key per line, in the Gonpi app's save folder. */
class FileLikeRepository(private val file: File) : LikeRepository {
    override fun all(): List<String> = if (file.exists()) file.readLines().filter { it.isNotEmpty() } else emptyList()

    override fun save(keys: List<String>) = file.writeText(keys.joinToString("\n"))
}
