package com.rukia.chat.infrastructure.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.phone.AppLaunch

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
        if (AppLaunch.foreground && VisibleChat.caseId == caseId && VisibleChat.id == chatId) return Result.success()

        val module = ChatModule(applicationContext, caseId)
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
