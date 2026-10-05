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
import com.rukia.phone.CaseFolders
import java.io.File

/** Composition root of the chat app for one case: the only place that knows which adapters back the ports. */
class ChatModule(context: Context, caseId: String) {
    // Content and saves both live in the case's own folders, so cases never mix.
    private val content = CaseFolders.content(caseId)
    private val saveRoot = CaseFolders.saves(context, caseId, "chat")
    private val chatRepo = JsonChatRepository(context.assets, content, saveRoot)
    private val characterRepo = AssetCharacterRepository(context.assets, content)
    private val profileRepo = JsonProfileRepository(saveRoot)
    private val story = InkStoryEngine({ context.assets.readText("$content/$it") }, File(saveRoot, "story-state.json"))
    private val notifier = WorkManagerNotifier(context, caseId)

    val avatars = AvatarImporter(context.contentResolver, saveRoot)
    val listChats = ListChats(chatRepo)
    val advanceChat = AdvanceChat(chatRepo, story, notifier)
    val chooseReply = ChooseReply(chatRepo, story, notifier)
    val createChat = CreateChat(chatRepo, characterRepo)
    val markChatRead = MarkChatRead(chatRepo)
    val endCall = EndCall(chatRepo)
    val resetProgress = ResetProgress(chatRepo, story)
    val listCalls = ListCalls(chatRepo)
    val deleteChat = DeleteChat(chatRepo)
    val getProfile = GetProfile(profileRepo)
    val updateProfile = UpdateProfile(profileRepo)
    val characters = GetCharacters(characterRepo)()

    /** Plays every story chat's new lines, starting delayed ones (e.g. a group created a minute in) counting from now. */
    fun advanceAll() = listChats().forEach { advanceChat(it.id) }
}
