package com.rukia.chat.infrastructure.notifications

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.port.MessageNotifier
import java.util.concurrent.TimeUnit

/** Schedules a background job for when the message arrives; it runs even if the app was closed. */
class WorkManagerNotifier(private val context: Context, private val caseId: String) : MessageNotifier {
    /** Messages arriving before this (epoch millis) get no notification. */
    @Volatile var quietUntil = 0L

    override fun notifyWhenDelivered(chatId: String, message: Message) {
        if (message.deliverAt <= quietUntil) return
        val delay = (message.deliverAt - System.currentTimeMillis()).coerceAtLeast(0)
        val work = OneTimeWorkRequestBuilder<MessageNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    MessageNotificationWorker.CASE to caseId,
                    MessageNotificationWorker.CHAT to chatId,
                    MessageNotificationWorker.AT to message.deliverAt,
                )
            )
            .build()
        // One job per chat and arrival time: messages arriving together share one notification.
        WorkManager.getInstance(context).enqueueUniqueWork("message-$caseId-$chatId-${message.deliverAt}", ExistingWorkPolicy.REPLACE, work)
    }
}
