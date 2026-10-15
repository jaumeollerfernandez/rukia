package com.rukia.chat.infrastructure.notifications

import com.rukia.R
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
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.phone.infrastructure.AppLaunch
import com.rukia.phone.infrastructure.Language
import com.rukia.phone.infrastructure.media.mediaBitmap

/** WhatsApp-style message notification; tapping it opens the chat in the game, in the app it lives in. */
object MessageNotifications {
    private const val CHANNEL = "messages"

    @SuppressLint("MissingPermission") // checked just below
    fun show(context: Context, caseId: String, chat: Chat, messages: List<Message>, characters: Map<String, Character>) {
        val res = Language.wrap(context).resources
        if (Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return

        val notificationId = "$caseId/${chat.id}".hashCode() // one notification per chat, like WhatsApp
        val manager = NotificationManagerCompat.from(context)
        manager.createNotificationChannel(
            NotificationChannelCompat.Builder(CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH).setName(res.getString(R.string.notif_channel)).build()
        )
        val style = NotificationCompat.MessagingStyle(Person.Builder().setName(res.getString(R.string.you)).build())
        if (chat.isGroup) style.setConversationTitle(chat.displayTitle(characters)).setGroupConversation(true)
        // The sender's photo, as WhatsApp shows it; the system crops it round.
        val photos = messages.map { it.from }.distinct().associateWith { id -> characters[id]?.photo?.let { mediaBitmap(context, caseId, it) } }
        val people = photos.mapValues { (id, photo) ->
            Person.Builder().setKey(id).setName(characters[id]?.name ?: id).setIcon(photo?.let(IconCompat::createWithBitmap)).build()
        }
        for (m in messages) style.addMessage(if (m.call != null) res.getString(R.string.notif_call) else m.text, m.deliverAt, people[m.from])
        val last = messages.last().from
        val open = PendingIntent.getActivity(
            context, notificationId, AppLaunch.intent(context, caseId, chat.app(characters), chat.id),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        // A conversation shortcut makes Android 11+ show the sender's photo in place of the app icon, as WhatsApp does.
        val shortcutId = "$caseId/${chat.id}"
        ShortcutManagerCompat.pushDynamicShortcut(
            context,
            ShortcutInfoCompat.Builder(context, shortcutId)
                .setShortLabel(chat.displayTitle(characters))
                .setIcon(people.getValue(last).icon ?: IconCompat.createWithResource(context, android.R.drawable.stat_notify_chat))
                .setPerson(people.getValue(last))
                .setLongLived(true)
                .setIntent(AppLaunch.intent(context, caseId, chat.app(characters), chat.id))
                .build(),
        )
        val notification = NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setStyle(style)
            .setShortcutId(shortcutId)
            .setLargeIcon(photos[last]) // older Androids: the photo beside the text
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(open)
            .setAutoCancel(true)
            .build()
        manager.notify(notificationId, notification)
    }
}
