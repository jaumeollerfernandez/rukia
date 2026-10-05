package com.rukia.chat.domain.port

import com.rukia.chat.domain.model.Character

interface CharacterRepository {
    fun all(): Map<String, Character>
}
