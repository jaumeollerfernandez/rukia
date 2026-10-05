package com.rukia.chat.infrastructure.persistence

import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.chat.domain.port.ProfileRepository
import java.io.File

class JsonProfileRepository(saveRoot: File) : ProfileRepository {
    private val file = File(saveRoot, "profile.json")

    override fun get(): PlayerProfile =
        if (file.exists()) storyJson.decodeFromString(file.readText()) else PlayerProfile()

    override fun save(profile: PlayerProfile) = file.writeText(storyJson.encodeToString(profile))
}
