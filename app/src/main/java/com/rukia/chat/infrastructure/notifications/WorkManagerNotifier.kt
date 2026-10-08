package com.rukia.chat.infrastructure.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rukia.chat.domain.model.Message
import com.rukia.chat.domain.port.MessageNotifier
import java.util.concurrent.TimeUnit

/**
 * Notifies a message when it arrives, even if the app was closed. An alarm wakes the phone on time, which a background
 * job alone doesn't: Android holds those back while the phone sleeps. The job stays as a backup, since alarms don't
 * survive a reboot; whichever runs first cancels the other.
 */
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
        WorkManager.getInstance(context).enqueueUniqueWork(workName(caseId, chatId, message.deliverAt), ExistingWorkPolicy.REPLACE, work)

        val alarms = context.getSystemService(AlarmManager::class.java)
        val alarm = alarm(context, caseId, chatId, message.deliverAt)
        // Without the exact alarm permission Android may run it a few minutes late, still far sooner than the job.
        if (Build.VERSION.SDK_INT < 31 || alarms.canScheduleExactAlarms()) {
            alarms.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, message.deliverAt, alarm)
        } else alarms.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, message.deliverAt, alarm)
    }

    companion object {
        fun workName(caseId: String, chatId: String, at: Long) = "message-$caseId-$chatId-$at"

        /** The same alarm each time for the same message, so scheduling it again replaces it. */
        fun alarm(context: Context, caseId: String, chatId: String, at: Long): PendingIntent = PendingIntent.getBroadcast(
            context, workName(caseId, chatId, at).hashCode(),
            Intent(context, MessageAlarmReceiver::class.java)
                .putExtra(MessageNotificationWorker.CASE, caseId)
                .putExtra(MessageNotificationWorker.CHAT, chatId)
                .putExtra(MessageNotificationWorker.AT, at),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }
}
