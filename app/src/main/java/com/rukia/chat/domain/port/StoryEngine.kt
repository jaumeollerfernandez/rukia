package com.rukia.chat.domain.port

/**
 * One line of story text. [from] is null when the script didn't name a speaker. [call]: the speaker calls the player
 * right after it, saying [callAudio] (a media path) if answered. [delaySeconds]: the line arrives this long after the previous one, in real time.
 */
data class StoryLine(val from: String?, val text: String, val call: Boolean = false, val delaySeconds: Int = 0, val callAudio: String? = null)

data class StoryStep(val lines: List<StoryLine>, val choices: List<String>)

/** Runs the branching script. Each chat has its own place in the story; story variables are shared by all chats. */
interface StoryEngine {
    /** Plays the chat's story until it waits for a choice or ends. Calling it again while waiting returns no lines. */
    fun advance(chatId: String): StoryStep

    /** Picks one of the current choices and plays on from there. */
    fun choose(chatId: String, index: Int): StoryStep

    /** Starts the whole story over: every chat back to its beginning, all variables reset. */
    fun reset()
}
