package com.rukia.chat.application

import com.rukia.chat.domain.model.MAX_NAME_LENGTH
import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.chat.domain.port.ProfileRepository

class UpdateProfile(private val profiles: ProfileRepository) {
    operator fun invoke(profile: PlayerProfile): PlayerProfile {
        val name = profile.name.trim()
        require(name.isNotEmpty()) { "Name is empty" }
        return profile.copy(name = name.take(MAX_NAME_LENGTH)).also(profiles::save)
    }
}
