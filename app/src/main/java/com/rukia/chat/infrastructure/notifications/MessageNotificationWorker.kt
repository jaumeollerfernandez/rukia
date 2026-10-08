package com.rukia.chat.infrastructure.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.phone.AppLaunch
import com.rukia.phone.CaseClock
import kotlin.concurrent.thread

/** Chat currently on screen (and its case), so a message arriving there doesn't also pop a notification. */
object VisibleChat {
    @Volatile var caseId: String? = null
    @Volatile var id: String? = null
}

/** A notification this late is old news: the player sees the message in the chat instead of a burst of them. */
private const val STALE_MILLIS = 30 * 60_000L

/** A message arrived: moves the story on and shows it as a notification, unless the player is looking at that chat. */
private fun messageArrived(context: Context, caseId: String, chatId: String, at: Long) {
    // A job left over from before a reset: the new play-through starts when the player opens the case, not here.
    if (!CaseClock.isStarted(context, caseId)) return
    val module = ChatModule.of(context, caseId)
    // Keep the story going while the game is closed: what follows this message gets written and scheduled now.
    // ponytail: only wakes on message arrivals; choices that expire with nothing else due wait until the next one or until the case is opened.
    module.advanceAll()
    if (AppLaunch.foreground && VisibleChat.caseId == caseId && VisibleChat.id == chatId) return
    // Late, e.g. after a reboot or once the game is opened again: no pile of old notifications.
    val late = System.currentTimeMillis() - at
    if (late > STALE_MILLIS || (AppLaunch.foreground && late > 60_000)) return

    val chat = module.listChats().find { it.id == chatId } ?: return
    // Nothing left if the save was reset or the chat deleted in the meantime.
    val arrived = chat.messages.filter { it.deliverAt == at && !it.fromPlayer }
    if (arrived.isNotEmpty()) MessageNotifications.show(context, caseId, chat, arrived, module.characters)
}

/** The alarm set for a message, on time even while the phone sleeps. */
class MessageAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val caseId = intent.getStringExtra(MessageNotificationWorker.CASE) ?: return
        val chatId = intent.getStringExtra(MessageNotificationWorker.CHAT) ?: return
        val at = intent.getLongExtra(MessageNotificationWorker.AT, 0)
        val app = context.applicationContext
        WorkManager.getInstance(app).cancelUniqueWork(WorkManagerNotifier.workName(caseId, chatId, at))
        // Loading the story can take a moment: off the main thread, keeping the app awake until it's done.
        val pending = goAsync()
        thread {
            try { messageArrived(app, caseId, chatId, at) } finally { pending.finish() }
        }
    }
}

/** Backup for the alarm, which a reboot clears. */
class MessageNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val caseId = inputData.getString(CASE) ?: return Result.success()
        val chatId = inputData.getString(CHAT) ?: return Result.success()
        val at = inputData.getLong(AT, 0)
        val context = applicationContext
        context.getSystemService(android.app.AlarmManager::class.java).cancel(WorkManagerNotifier.alarm(context, caseId, chatId, at))
        messageArrived(context, caseId, chatId, at)
        return Result.success()
    }

    companion object {
        const val CASE = "case"
        const val CHAT = "chat"
        const val AT = "at"
    }
}
