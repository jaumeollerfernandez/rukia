package com.rukia.chat.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: String,
    val name: String,
    val online: Boolean = false,
    val status: String = "",
    val color: String = "#888888",
    /** Profile photo, a path inside the case's media folder (e.g. "photos/rukia.jpg"). Without it the avatar shows the initial. */
    val photo: String? = null,
)
