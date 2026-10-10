package com.rukia.chat.infrastructure.persistence

import com.bladecoder.ink.compiler.Compiler
import com.bladecoder.ink.compiler.IFileHandler
import com.rukia.chat.domain.port.StoryEngine
import com.rukia.chat.domain.port.StoryLine
import com.rukia.chat.domain.port.StoryStep
import com.rukia.phone.domain.model.caseDay
import com.rukia.phone.domain.model.caseTime
import com.bladecoder.ink.runtime.Error
import com.bladecoder.ink.runtime.Story
import com.bladecoder.ink.runtime.StoryException
import java.io.File
import java.time.Clock
import java.time.Instant

/**
 * Runs the ink story in [STORY_DIR]/main.ink (plus its INCLUDEs). Each chat is the knot named like the chat id,
 * played in its own ink flow so chats keep separate places but share variables. The whole ink state is saved
 * to [stateFile] after every step. [caseStart] is midnight of the case's D1, for the case-time tags.
 *
 * Supported line tags: `#from: <characterId>` sets the speaker; `#call` makes the speaker call the player after the line (`#call: audio/x.m4a` plays that clip if answered);
 * `#delay: <seconds>` makes the line arrive that long after the previous one (with a notification if the app is closed);
 * `#at: D3 22:15` makes it arrive at that case time instead, and the story pauses there until then;
 * `#caduca: D3 23:00` on the line before some choices makes them expire then (the silent choice is taken);
 * `#effect: <name>` plays a phone screen effect when the line arrives; `#image: <media path>` shows a picture in the bubble.
 * Before each step the story's `dia` and `hora` variables, if it declares them, are set to the current case day and hour.
 */
class InkStoryEngine(
    private val readFile: (path: String) -> String,
    private val stateFile: File,
    private val caseStart: () -> Long = { 0L },
    private val clock: Clock = Clock.systemDefaultZone(),
) : StoryEngine {
    // ponytail: compiles on first use on the calling thread; precompile to JSON at build time if the story gets big enough to stall.
    private val lazyStory = lazy {
        val options = Compiler.Options().apply {
            countAllVisits = true
            fileHandler = object : IFileHandler {
                override fun resolveInkFilename(includeName: String) = "$STORY_DIR/$includeName"
                override fun loadInkFileContents(fullFilename: String) = readFile(fullFilename)
            }
        }
        Compiler(readFile("$STORY_DIR/main.ink"), options).compile().apply {
            // A save from before the .ink files changed can point inside a knot that has moved: ink resumes from the
            // nearest place and warns. That mustn't crash the game; only real errors stop the story.
            onError = Error.ErrorHandler { message, type ->
                if (type == Error.ErrorType.Error) throw StoryException(message)
                System.err.println("ink: $message")
            }
            // A save from before the .ink files changed can point at knots that no longer exist: start the story over.
            if (stateFile.exists()) runCatching { state.loadJson(stateFile.readText()) }.onFailure { resetState() }
        }
    }

    private val story by lazyStory

    @Synchronized override fun reset() {
        stateFile.delete()
        if (lazyStory.isInitialized()) story.resetState()
    }

    @Synchronized override fun advance(chatId: String): StoryStep {
        if (!story.mainContentContainer.namedContent.containsKey(chatId)) return StoryStep(emptyList(), emptyList())
        story.switchFlow(chatId)
        if (story.state.visitCountAtPathString(chatId) == 0) story.choosePathString(chatId)
        return play()
    }

    @Synchronized override fun choose(chatId: String, index: Int): StoryStep {
        story.switchFlow(chatId)
        // A choice left in a chat saved before the story changed: the reply is sent, but the story has nothing to say.
        if (index !in story.currentChoices.indices) return StoryStep(emptyList(), emptyList())
        story.chooseChoiceIndex(index)
        return play(changed = true)
    }

    @Synchronized override fun jump(chatId: String, knot: String): StoryStep {
        if (!story.mainContentContainer.namedContent.containsKey(knot)) return StoryStep(emptyList(), emptyList())
        story.switchFlow(chatId)
        story.choosePathString(knot)
        return play(changed = true)
    }

    @Synchronized override fun setVariable(name: String, value: Boolean) = setAny(name, value)

    @Synchronized override fun setVariable(name: String, value: Int) = setAny(name, value)

    @Synchronized override fun variable(name: String): Any? = story.variablesState[name]

    private fun setAny(name: String, value: Any) {
        if (story.variablesState[name] == null) return
        story.variablesState[name] = value
        stateFile.writeText(story.state.toJson())
    }

    private fun play(changed: Boolean = false): StoryStep {
        val now = clock.millis()
        val start = caseStart()
        setClockVariables(now, start)
        var expiresAt: Long? = null
        val lines = buildList {
            while (story.canContinue()) {
                val text = story.Continue().trim()
                val tags = story.currentTags.associate { it.substringBefore(':').trim() to it.substringAfter(':', "").trim() }
                val at = tags["at"]?.let { caseTime(it, start, clock.zone) }
                tags["caduca"]?.let { expiresAt = caseTime(it, start, clock.zone) }
                if (text.isNotEmpty()) {
                    add(StoryLine(
                        tags["from"]?.takeIf { it.isNotEmpty() }, text, "call" in tags, tags["delay"]?.toIntOrNull() ?: 0,
                        tags["call"]?.takeIf { it.isNotEmpty() }, tags["effect"]?.takeIf { it.isNotEmpty() },
                        // A time already gone during the case just means "now"; one before the case is history and keeps its date.
                        at = at?.takeIf { it > now || it < start },
                        image = tags["image"]?.takeIf { it.isNotEmpty() },
                    ))
                }
                // The rest is written once this line has arrived, so its conditions see the story as it is then.
                if (at != null && at > now) break
            }
        }
        // Chats with nothing new are advanced every few seconds: only save when the story moved.
        if (changed || lines.isNotEmpty()) stateFile.writeText(story.state.toJson())
        val choices = if (story.canContinue()) emptyList() else story.currentChoices.map { it.text }
        return StoryStep(lines, choices, expiresAt.takeIf { choices.isNotEmpty() })
    }

    /** `dia` and `hora`, if the story declares them: the case day (1 = D1) and the hour, so a chat can answer differently each day. */
    private fun setClockVariables(now: Long, start: Long) {
        val vars = story.variablesState
        if (vars["dia"] != null) vars["dia"] = caseDay(now, start, clock.zone)
        if (vars["hora"] != null) vars["hora"] = Instant.ofEpochMilli(now).atZone(clock.zone).hour
    }

    companion object { const val STORY_DIR = "story" }
}
