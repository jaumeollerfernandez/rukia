package com.rukia.gonpi.infrastructure.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rukia.R
import com.rukia.gonpi.domain.model.Account
import com.rukia.gonpi.domain.port.PostAlarm
import com.rukia.gonpi.infrastructure.GonpiModule
import com.rukia.gonpi.infrastructure.ui.GonpiPink
import com.rukia.phone.infrastructure.AppLaunch
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.Language

private const val CHANNEL = "gonpi"
private const val CASE = "case"
private const val AUTHOR = "author"
private const val AT = "at"
/** Old news by then: the player sees the post in the feed instead. */
private const val STALE_MILLIS = 30 * 60_000L

/**
 * [PostAlarm] on Android's alarms, on time even while the phone sleeps. The same post gets the same alarm, so setting
 * them again just replaces them.
 */
// ponytail: alarms don't survive a reboot; posts due while the phone was off get no notification until the case is opened again.
class AndroidPostAlarm(private val context: Context, private val caseId: String) : PostAlarm {
    override fun set(author: String, at: Long) {
        val alarms = context.getSystemService(AlarmManager::class.java)
        val alarm = PendingIntent.getBroadcast(
            context, "gonpi-$caseId-$author-$at".hashCode(),
            Intent(context, GonpiAlarmReceiver::class.java).putExtra(CASE, caseId).putExtra(AUTHOR, author).putExtra(AT, at),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        if (Build.VERSION.SDK_INT < 31 || alarms.canScheduleExactAlarms()) {
            alarms.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, alarm)
        } else alarms.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, alarm)
    }
}

/** A post's alarm went off: "<user> posted recently", unless it's stale or no longer in the story. Tapping it opens Gonpi. */
class GonpiAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val app = context.applicationContext
        val caseId = intent.getStringExtra(CASE) ?: return
        val author = intent.getStringExtra(AUTHOR) ?: return
        val at = intent.getLongExtra(AT, 0)
        if (System.currentTimeMillis() - at > STALE_MILLIS || !CaseClock.isStarted(app, caseId)) return
        GonpiModule(app, caseId).findNewPost(author, at)?.let { notify(app, caseId, it, at) }
    }
}

@SuppressLint("MissingPermission") // checked just below
private fun notify(context: Context, caseId: String, author: Account, at: Long) {
    if (Build.VERSION.SDK_INT >= 33 &&
        context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
    ) return
    val res = Language.wrap(context).resources
    val manager = NotificationManagerCompat.from(context)
    manager.createNotificationChannel(NotificationChannelCompat.Builder(CHANNEL, NotificationManagerCompat.IMPORTANCE_DEFAULT).setName("Gonpi").build())
    val id = "gonpi-$caseId-${author.id}-$at".hashCode()
    val open = PendingIntent.getActivity(
        context, id, AppLaunch.intent(context, caseId, "gonpi", null),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
    )
    val notification = NotificationCompat.Builder(context, CHANNEL)
        .setSmallIcon(R.drawable.ic_notif_gonpi)
        .setColor(GonpiPink.toArgb())
        .setContentTitle("Gonpi")
        .setContentText(res.getString(R.string.gonpi_notif_posted, author.username))
        .setWhen(at)
        .setContentIntent(open)
        .setAutoCancel(true)
        .build()
    manager.notify(id, notification)
}
