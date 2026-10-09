package com.rukia.phone.infrastructure

import android.content.Context
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.phone.application.GetHomeLayout
import com.rukia.phone.application.MoveApp
import com.rukia.phone.application.NextStoryEvent
import com.rukia.phone.application.SkipTime
import com.rukia.phone.domain.port.CaseCalendar
import com.rukia.phone.domain.port.StoryTimeline
import com.rukia.phone.infrastructure.persistence.FileHomeLayoutRepository
import java.io.File

/** Composition root of the phone itself (home screen and debug clock) for one case. */
class PhoneModule(context: Context, caseId: String) {
    private val layout = FileHomeLayoutRepository(File(CaseFolders.saves(context, caseId, "phone"), "layout.txt"))
    private val calendar = object : CaseCalendar {
        override fun now() = CaseClock.now(caseId)
        override fun skip(millis: Long) = CaseClock.skip(context, caseId, millis)
    }
    // The story lives in the chat app.
    private val story = object : StoryTimeline {
        override fun advance() = ChatModule.of(context, caseId).advanceAll()
        override fun chats(): List<Chat> = ChatModule.of(context, caseId).listChats()
    }

    val getHomeLayout = GetHomeLayout(layout)
    val moveApp = MoveApp(layout)
    val nextStoryEvent = NextStoryEvent(calendar, story)
    val skipTime = SkipTime(calendar, story)
}
