package com.rukia.chat.infrastructure.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.phone.AppLaunch

/** WhatsApp-style message notification; tapping it opens the chat in the game. */
object MessageNotifications {
    private const val CHANNEL = "messages"

    @SuppressLint("MissingPermission") // checked just below
    fun show(context: Context, caseId: String, chat: Chat, messages: List<Message>, characters: Map<String, Character>) {
        if (Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return

        val notificationId = "$caseId/${chat.id}".hashCode() // one notification per chat, like WhatsApp
        val manager = NotificationManagerCompat.from(context)
        manager.createNotificationChannel(
            NotificationChannelCompat.Builder(CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH).setName("Messages").build()
        )
        val style = NotificationCompat.MessagingStyle(Person.Builder().setName("You").build())
        if (chat.isGroup) style.setConversationTitle(chat.displayTitle(characters)).setGroupConversation(true)
        for (m in messages) {
            val sender = Person.Builder().setName(characters[m.from]?.name ?: m.from).build()
            style.addMessage(if (m.call != null) "📞 Voice call" else m.text, m.deliverAt, sender)
        }
        val open = PendingIntent.getActivity(
            context, notificationId, AppLaunch.intent(context, caseId, "chat", chat.id),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        val notification = NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setStyle(style)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(open)
            .setAutoCancel(true)
            .build()
        manager.notify(notificationId, notification)
    }
}
