package com.rukia.chat.application

import com.rukia.chat.domain.port.StoryEngine

/** Something happened outside the chats that the story can react to, e.g. `ficha_dani` once the player read Dani's record. */
class SetStoryFlag(private val story: StoryEngine) {
    operator fun invoke(name: String) = synchronized(story) { story.setVariable(name, true) }
}
