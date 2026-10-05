package com.rukia.chat.application

import com.rukia.chat.domain.port.ProfileRepository

class GetProfile(private val profiles: ProfileRepository) {
    operator fun invoke() = profiles.get()
}
