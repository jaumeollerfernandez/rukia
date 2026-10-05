package com.rukia.chat.domain.port

import com.rukia.chat.domain.model.PlayerProfile

interface ProfileRepository {
    fun get(): PlayerProfile
    fun save(profile: PlayerProfile)
}
