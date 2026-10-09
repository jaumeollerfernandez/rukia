package com.rukia.chat.infrastructure

import android.content.Context
import com.rukia.chat.application.*
import com.rukia.chat.infrastructure.notifications.WorkManagerNotifier
import com.rukia.chat.infrastructure.persistence.AssetCharacterRepository
import com.rukia.chat.infrastructure.persistence.InkStoryEngine
import com.rukia.chat.infrastructure.persistence.JsonChatRepository
import com.rukia.chat.infrastructure.persistence.JsonProfileRepository
import com.rukia.chat.infrastructure.persistence.readText
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.CaseFolders
import com.rukia.phone.domain.model.isDebugCase
import java.io.File

/**
 * Composition root of the chat app for one case: the only place that knows which adapters back the ports.
 * Get it with [of]: there is one per case, so the screens, the story clock and the notification jobs share the same story state.
 */
class ChatModule private constructor(context: Context, caseId: String) {
    // Content and saves both live in the case's own folders, so cases never mix.
    private val content = CaseFolders.content(caseId)
    private val clock = CaseClock.clock(caseId)
    private val saveRoot = CaseFolders.saves(context, caseId, "chat")
    private val chatRepo = JsonChatRepository(context.assets, content, saveRoot)
    private val characterRepo = AssetCharacterRepository(context.assets, content)
    private val profileRepo = JsonProfileRepository(saveRoot)
    private val story = InkStoryEngine(
        { context.assets.readText("$content/$it") }, File(saveRoot, "story-state.json"),
        caseStart = { CaseClock.start(context, caseId) }, clock = clock,
    )
    // A debug case runs ahead of real time, so it never notifies: its messages arrive while the tester watches.
    private val notifier = WorkManagerNotifier(context, caseId).apply { if (isDebugCase(caseId)) quietUntil = Long.MAX_VALUE }
    private val isStarted = { CaseClock.isStarted(context, caseId) }

    val listChats = ListChats(chatRepo)
    val advanceChat = AdvanceChat(chatRepo, story, notifier, clock)
    val chooseReply = ChooseReply(chatRepo, story, characterRepo, notifier, clock)
    val createChat = CreateChat(chatRepo, characterRepo)
    val markChatRead = MarkChatRead(chatRepo)
    val endCall = EndCall(chatRepo)
    val resetProgress = ResetProgress(chatRepo, story)
    val markCaseSolved = MarkCaseSolved(story)
    val playStoryEvent = PlayStoryEvent(chatRepo, story, notifier, clock)
    val listCalls = ListCalls(chatRepo)
    val listArrivedEffects = ListArrivedEffects(chatRepo, clock)
    val deleteChat = DeleteChat(chatRepo)
    val getProfile = GetProfile(profileRepo)
    val updateProfile = UpdateProfile(profileRepo)
    val characters = GetCharacters(characterRepo)()

    init {
        // A save made with other .ink files can't be resumed reliably: ink may lose its place, crash or repeat lines.
        // So when the story has changed since the case was saved, the case starts over, as with "Reset chats".
        val storyDir = "$content/${InkStoryEngine.STORY_DIR}"
        val version = context.assets.list(storyDir).orEmpty().filter { it.endsWith(".ink") }.sorted()
            .joinToString("\n") { context.assets.readText("$storyDir/$it") }.hashCode().toString()
        val savedVersion = File(saveRoot, "story-version.txt")
        val hasStory = File(saveRoot, "story-state.json").exists()
        if (hasStory && savedVersion.takeIf { it.exists() }?.readText() != version) {
            resetProgress()
            CaseClock.reset(context, caseId)
        }
        savedVersion.writeText(version)
    }

    /** Plays every story chat's new lines: timed ones that are due, expired choices, and new chats' openings. */
    fun advanceAll() {
        // The first time the case is opened every chat's opening arrives at once: no burst of notifications for those.
        // ponytail: in-memory window, lost if the process dies within it; the case is on screen then anyway.
        if (!isStarted()) notifier.quietUntil = maxOf(notifier.quietUntil, System.currentTimeMillis() + OPENING_QUIET_MILLIS)
        listChats().forEach { advanceChat(it.id) }
    }

    companion object {
        private val modules = HashMap<String, ChatModule>()
        private const val OPENING_QUIET_MILLIS = 5 * 60_000L

        fun of(context: Context, caseId: String): ChatModule = synchronized(modules) {
            modules.getOrPut(caseId) { ChatModule(context.applicationContext, caseId) }
        }
    }
}
