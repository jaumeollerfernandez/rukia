package com.rukia.chat.domain.port

/**
 * One line of story text. [from] is null when the script didn't name a speaker. [call]: the speaker calls the player
 * right after it, saying [callAudio] (a media path) if answered. [delaySeconds]: the line arrives this long after the previous one, in real time.
 */
data class StoryLine(
    val from: String?, val text: String, val call: Boolean = false, val delaySeconds: Int = 0, val callAudio: String? = null,
    /** A screen effect the phone plays when the line arrives (see com.rukia.phone.effects). */
    val effect: String? = null,
    /** When the line arrives (epoch millis), set by the script instead of a delay. Before the case started = the phone's history. */
    val at: Long? = null,
    /** A picture shown in the bubble, a path inside the case's media folder. */
    val image: String? = null,
)

/** [expiresAt]: if the player hasn't chosen by then (epoch millis), the story takes the silent choice on its own. */
data class StoryStep(val lines: List<StoryLine>, val choices: List<String>, val expiresAt: Long? = null)

/** Runs the branching script. Each chat has its own place in the story; story variables are shared by all chats. */
interface StoryEngine {
    /** Plays the chat's story until it waits for a choice, a line that hasn't arrived yet, or ends. Calling it again while waiting on a choice returns no lines. */
    fun advance(chatId: String): StoryStep

    /** Picks one of the current choices and plays on from there. */
    fun choose(chatId: String, index: Int): StoryStep

    /** Starts the whole story over: every chat back to its beginning, all variables reset. */
    fun reset()

    /** Sets a story variable from outside the chats (e.g. the case was solved). Does nothing if the story doesn't declare it. */
    fun setVariable(name: String, value: Boolean) {}

    /** Same for a number variable (e.g. the last case day the player tried to solve it). */
    fun setVariable(name: String, value: Int) {}

    /** A story variable's value (Boolean, Int, String...), read from outside the chats (e.g. how the case ended). Null if the story doesn't declare it. */
    fun variable(name: String): Any? = null

    /** Plays [knot] in the chat's place in the story, from the outside (e.g. a police report). No lines if there's no such knot. */
    fun jump(chatId: String, knot: String): StoryStep = StoryStep(emptyList(), emptyList())
}
