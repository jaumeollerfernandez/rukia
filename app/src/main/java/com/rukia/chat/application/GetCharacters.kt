package com.rukia.chat.application

import com.rukia.chat.domain.port.CharacterRepository

class GetCharacters(private val characters: CharacterRepository) {
    operator fun invoke() = characters.all()
}
