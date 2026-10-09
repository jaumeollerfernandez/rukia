package com.rukia.phone.infrastructure.persistence

import com.rukia.phone.domain.port.HomeLayoutRepository
import java.io.File

/** One "appId=cell" per line, in the phone's save folder. */
class FileHomeLayoutRepository(private val file: File) : HomeLayoutRepository {
    override fun load(): Map<String, Int> = runCatching { file.readLines() }.getOrDefault(emptyList())
        .mapNotNull { line -> line.substringAfter('=').toIntOrNull()?.let { line.substringBefore('=') to it } }.toMap()

    override fun save(cells: Map<String, Int>) {
        runCatching { file.writeText(cells.entries.joinToString("\n") { "${it.key}=${it.value}" }) }
    }
}
