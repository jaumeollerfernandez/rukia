package com.rukia.chat.infrastructure.persistence

import com.bladecoder.ink.compiler.Compiler
import com.bladecoder.ink.compiler.IFileHandler
import com.rukia.chat.domain.port.StoryEngine
import com.rukia.chat.domain.port.StoryLine
import com.rukia.chat.domain.port.StoryStep
import com.bladecoder.ink.runtime.Story
import java.io.File

/**
 * Runs the ink story in [STORY_DIR]/main.ink (plus its INCLUDEs). Each chat is the knot named like the chat id,
 * played in its own ink flow so chats keep separate places but share variables. The whole ink state is saved
 * to [stateFile] after every step.
 *
 * Supported line tags: `#from: <characterId>` sets the speaker; `#call` makes the speaker call the player after the line (`#call: audio/x.m4a` plays that clip if answered);
 * `#delay: <seconds>` makes the line arrive that long after the previous one (with a notification if the app is closed).
 */
class InkStoryEngine(private val readFile: (path: String) -> String, private val stateFile: File) : StoryEngine {
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
            // A save from before the .ink files changed can point at knots that no longer exist: start the story over.
            if (stateFile.exists()) runCatching { state.loadJson(stateFile.readText()) }.onFailure { resetState() }
        }
    }

    private val story by lazyStory

    override fun reset() {
        stateFile.delete()
        if (lazyStory.isInitialized()) story.resetState()
    }

    override fun advance(chatId: String): StoryStep {
        if (!story.mainContentContainer.namedContent.containsKey(chatId)) return StoryStep(emptyList(), emptyList())
        story.switchFlow(chatId)
        if (story.state.visitCountAtPathString(chatId) == 0) story.choosePathString(chatId)
        return play()
    }

    override fun choose(chatId: String, index: Int): StoryStep {
        story.switchFlow(chatId)
        // A choice left in a chat saved before the story changed: the reply is sent, but the story has nothing to say.
        if (index !in story.currentChoices.indices) return StoryStep(emptyList(), emptyList())
        story.chooseChoiceIndex(index)
        return play()
    }

    private fun play(): StoryStep {
        val lines = buildList {
            while (story.canContinue()) {
                val text = story.Continue().trim()
                val tags = story.currentTags.associate { it.substringBefore(':').trim() to it.substringAfter(':', "").trim() }
                if (text.isNotEmpty()) {
                    add(StoryLine(tags["from"]?.takeIf { it.isNotEmpty() }, text, "call" in tags, tags["delay"]?.toIntOrNull() ?: 0, tags["call"]?.takeIf { it.isNotEmpty() }))
                }
            }
        }
        stateFile.writeText(story.state.toJson())
        return StoryStep(lines, story.currentChoices.map { it.text })
    }

    companion object { const val STORY_DIR = "story" }
}
