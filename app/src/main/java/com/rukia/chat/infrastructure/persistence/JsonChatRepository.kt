package com.rukia.chat.infrastructure.persistence

import android.content.res.AssetManager
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.port.ChatRepository
import kotlinx.serialization.json.Json
import java.io.File

internal val storyJson = Json { ignoreUnknownKeys = true; prettyPrint = true }

internal fun AssetManager.readText(path: String) = open(path).bufferedReader().use { it.readText() }

/**
 * Story chats ship read-only in [contentRoot]/chats/ (inside assets). Player progress lives in [saveRoot]/chats/<id>.json
 * and wins over the asset copy. Deleting a story chat leaves a <id>.deleted marker, since assets can't be removed.
 */
class JsonChatRepository(private val assets: AssetManager, private val contentRoot: String, saveRoot: File) : ChatRepository {
    private val saveDir = File(saveRoot, "chats").apply { mkdirs() }
    private val storyIds by lazy {
        assets.list("$contentRoot/chats").orEmpty().filter { it.endsWith(".json") }.map { it.removeSuffix(".json") }.toSet()
    }

    override fun all(): List<Chat> {
        val savedIds = saveDir.listFiles().orEmpty().filter { it.extension == "json" }.map { it.nameWithoutExtension }
        return (storyIds + savedIds).mapNotNull(::find)
    }

    override fun find(id: String): Chat? {
        if (marker(id).exists()) return null
        val saved = saved(id)
        val text = when {
            saved.exists() -> saved.readText()
            id in storyIds -> assets.readText("$contentRoot/chats/$id.json")
            else -> return null
        }
        return storyJson.decodeFromString<Chat>(text)
    }

    override fun save(chat: Chat) {
        saved(chat.id).writeText(storyJson.encodeToString(chat))
        marker(chat.id).delete()
    }

    override fun delete(id: String) {
        saved(id).delete()
        if (id in storyIds) marker(id).createNewFile()
    }

    override fun deleteAll() {
        saveDir.listFiles().orEmpty().forEach(File::delete)
    }

    private fun saved(id: String) = File(saveDir, "$id.json")
    private fun marker(id: String) = File(saveDir, "$id.deleted")
}
