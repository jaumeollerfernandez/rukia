package com.rukia.chat.infrastructure.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.phone.AppLaunch
import com.rukia.phone.CaseClock

/** Chat currently on screen (and its case), so a message arriving there doesn't also pop a notification. */
object VisibleChat {
    @Volatile var caseId: String? = null
    @Volatile var id: String? = null
}

/** Runs when a delayed message arrives and shows it as a notification, unless the player is looking at that chat. */
class MessageNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val caseId = inputData.getString(CASE) ?: return Result.success()
        val chatId = inputData.getString(CHAT) ?: return Result.success()
        val at = inputData.getLong(AT, 0)
        // A job left over from before a reset: the new play-through starts when the player opens the case, not here.
        if (!CaseClock.isStarted(applicationContext, caseId)) return Result.success()
        val module = ChatModule.of(applicationContext, caseId)
        // Keep the story going while the game is closed: what follows this message gets written and scheduled now.
        // ponytail: only wakes on message arrivals; choices that expire with nothing else due wait until the next one or until the case is opened.
        module.advanceAll()
        if (AppLaunch.foreground && VisibleChat.caseId == caseId && VisibleChat.id == chatId) return Result.success()

        val chat = module.listChats().find { it.id == chatId } ?: return Result.success()
        // Nothing left if the save was reset or the chat deleted in the meantime.
        val arrived = chat.messages.filter { it.deliverAt == at && !it.fromPlayer }
        if (arrived.isNotEmpty()) MessageNotifications.show(applicationContext, caseId, chat, arrived, module.characters)
        return Result.success()
    }

    companion object {
        const val CASE = "case"
        const val CHAT = "chat"
        const val AT = "at"
    }
}
