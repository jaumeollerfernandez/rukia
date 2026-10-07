package com.rukia.chat.infrastructure

import android.content.Context
import com.rukia.chat.application.*
import com.rukia.chat.infrastructure.notifications.WorkManagerNotifier
import com.rukia.chat.infrastructure.persistence.AssetCharacterRepository
import com.rukia.chat.infrastructure.persistence.AvatarImporter
import com.rukia.chat.infrastructure.persistence.InkStoryEngine
import com.rukia.chat.infrastructure.persistence.JsonChatRepository
import com.rukia.chat.infrastructure.persistence.JsonProfileRepository
import com.rukia.chat.infrastructure.persistence.readText
import com.rukia.phone.CaseClock
import com.rukia.phone.CaseFolders
import java.io.File

/**
 * Composition root of the chat app for one case: the only place that knows which adapters back the ports.
 * Get it with [of]: there is one per case, so the screens, the story clock and the notification jobs share the same story state.
 */
class ChatModule private constructor(context: Context, caseId: String) {
    // Content and saves both live in the case's own folders, so cases never mix.
    private val content = CaseFolders.content(caseId)
    private val saveRoot = CaseFolders.saves(context, caseId, "chat")
    private val chatRepo = JsonChatRepository(context.assets, content, saveRoot)
    private val characterRepo = AssetCharacterRepository(context.assets, content)
    private val profileRepo = JsonProfileRepository(saveRoot)
    private val story = InkStoryEngine(
        { context.assets.readText("$content/$it") }, File(saveRoot, "story-state.json"),
        caseStart = { CaseClock.start(context, caseId) },
    )
    private val notifier = WorkManagerNotifier(context, caseId)

    val avatars = AvatarImporter(context.contentResolver, saveRoot)
    val listChats = ListChats(chatRepo)
    val advanceChat = AdvanceChat(chatRepo, story, notifier)
    val chooseReply = ChooseReply(chatRepo, story, characterRepo, notifier)
    val createChat = CreateChat(chatRepo, characterRepo)
    val markChatRead = MarkChatRead(chatRepo)
    val endCall = EndCall(chatRepo)
    val resetProgress = ResetProgress(chatRepo, story)
    val markCaseSolved = MarkCaseSolved(story)
    val playStoryEvent = PlayStoryEvent(chatRepo, story, notifier)
    val listCalls = ListCalls(chatRepo)
    val listArrivedEffects = ListArrivedEffects(chatRepo)
    val deleteChat = DeleteChat(chatRepo)
    val getProfile = GetProfile(profileRepo)
    val updateProfile = UpdateProfile(profileRepo)
    val characters = GetCharacters(characterRepo)()

    /** Plays every story chat's new lines: timed ones that are due, expired choices, and new chats' openings. */
    fun advanceAll() = listChats().forEach { advanceChat(it.id) }

    companion object {
        private val modules = HashMap<String, ChatModule>()

        fun of(context: Context, caseId: String): ChatModule = synchronized(modules) {
            modules.getOrPut(caseId) { ChatModule(context.applicationContext, caseId) }
        }
    }
}
