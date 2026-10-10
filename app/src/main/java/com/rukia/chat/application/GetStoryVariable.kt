package com.rukia.chat.application

import com.rukia.chat.domain.port.StoryEngine

/** A story variable as it stands now, e.g. which ending the case reached (`final_caso`). Null if the story doesn't declare it. */
class GetStoryVariable(private val story: StoryEngine) {
    operator fun invoke(name: String): Any? = synchronized(story) { story.variable(name) }
}
