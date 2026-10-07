package com.rukia.chat.application

import com.rukia.chat.domain.port.StoryEngine

/** The player solved the case in the Police app: the story can react to it (ink variable `caso_resuelto`). */
class MarkCaseSolved(private val story: StoryEngine) {
    operator fun invoke() = synchronized(story) { story.setVariable(VARIABLE, true) }

    companion object { const val VARIABLE = "caso_resuelto" }
}
