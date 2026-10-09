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
            // An empty file is a save cut short (the app was killed mid-write): start from the story's copy instead of crashing.
            saved.exists() && saved.length() > 0 -> saved.readText()
            id in storyIds -> assets.readText("$contentRoot/chats/$id.json")
            else -> return null
        }
        return storyJson.decodeFromString<Chat>(text)
    }

    // Written beside the chat and renamed over it, so a reader on another thread (the effects layer, the chat list, the
    // background worker) never sees a half-written or empty file: that crashed the app when many chats updated at once.
    @Synchronized override fun save(chat: Chat) {
        val file = saved(chat.id)
        val temp = File(saveDir, "${chat.id}.json.tmp")
        temp.writeText(storyJson.encodeToString(chat))
        if (!temp.renameTo(file)) { file.writeText(temp.readText()); temp.delete() }
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
