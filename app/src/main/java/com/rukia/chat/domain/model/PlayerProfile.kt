package com.rukia.chat.domain.model

import kotlinx.serialization.Serializable

/** The player's settings. The phone's name and photo belong to the case: its `me` character. */
@Serializable
data class PlayerProfile(
    val darkMode: Boolean = false,
)
