package com.rukia.chat.domain.model

import kotlinx.serialization.Serializable

const val MAX_NAME_LENGTH = 25

@Serializable
data class PlayerProfile(
    val name: String = "Player",
    val avatarPath: String? = null,
    val darkMode: Boolean = false,
)
