package com.rukia.chat.application

import com.rukia.chat.domain.port.StoryEngine

/** The player sent a squad to search on case day [day]: the story remembers it (ink variable `ultimo_intento`), e.g. to remind them of it. */
class MarkSearchDone(private val story: StoryEngine) {
    operator fun invoke(day: Int) = synchronized(story) { story.setVariable(VARIABLE, day) }

    companion object { const val VARIABLE = "ultimo_intento" }
}
