package com.rukia.chat.application

import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.chat.domain.port.ProfileRepository

class UpdateProfile(private val profiles: ProfileRepository) {
    operator fun invoke(profile: PlayerProfile): PlayerProfile = profile.also(profiles::save)
}
