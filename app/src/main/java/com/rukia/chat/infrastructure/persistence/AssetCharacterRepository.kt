package com.rukia.chat.infrastructure.persistence

import android.content.res.AssetManager
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.port.CharacterRepository

/** Reads [contentRoot]/characters.json from assets. */
class AssetCharacterRepository(private val assets: AssetManager, private val contentRoot: String) : CharacterRepository {
    private val characters by lazy {
        storyJson.decodeFromString<List<Character>>(assets.readText("$contentRoot/characters.json")).associateBy { it.id }
    }

    override fun all() = characters
}
