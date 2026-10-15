package com.rukia.police.infrastructure.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rukia.R
import com.rukia.phone.infrastructure.AppLaunch
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.Language
import com.rukia.police.domain.model.RecordStatus
import com.rukia.police.domain.port.ResultAlarm
import com.rukia.police.infrastructure.PoliceModule

private const val CHANNEL = "police_results"
private const val CASE = "case"
private const val AT = "at"

/** [ResultAlarm] on Android's alarms, on time even while the phone sleeps. */
// ponytail: alarms don't survive a reboot; a result due while the phone was off shows in the app, without a notification.
class AndroidResultAlarm(private val context: Context, private val caseId: String) : ResultAlarm {
    override fun set(at: Long) {
        val alarms = context.getSystemService(AlarmManager::class.java)
        val alarm = PendingIntent.getBroadcast(
            context, "police-$caseId-$at".hashCode(),
            Intent(context, ResultAlarmReceiver::class.java).putExtra(CASE, caseId).putExtra(AT, at),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        if (Build.VERSION.SDK_INT < 31 || alarms.canScheduleExactAlarms()) {
            alarms.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, alarm)
        } else alarms.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, alarm)
    }
}

/**
 * A result came in: "<name>'s record has arrived" or the search's accuracy, read from the save at that moment, so
 * nothing shows if the case was reset since. Tapping it opens the Police app.
 */
class ResultAlarmReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission") // checked just below
    override fun onReceive(context: Context, intent: Intent) {
        val app = context.applicationContext
        val caseId = intent.getStringExtra(CASE) ?: return
        val at = intent.getLongExtra(AT, 0)
        if (!CaseClock.isStarted(app, caseId)) return
        if (Build.VERSION.SDK_INT >= 33 &&
            app.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return
        val res = Language.wrap(app).resources
        val m = PoliceModule(app, caseId)
        val record = m.getRecords().rows.find { it.request?.readyAt == at && it.status == RecordStatus.Ready }?.record
        val search = m.getSearches().searches.find { it.readyAt == at }
        val (title, text) = when {
            record != null -> res.getString(R.string.police_records) to res.getString(R.string.notif_record_ready, record.name)
            search == null -> return
            search.accuracy == 0 -> res.getString(R.string.zone_miss_title) to res.getString(R.string.zone_miss_text)
            search.accuracy == 100 -> res.getString(R.string.zone_hit_title, 100) to res.getString(R.string.notif_zone_found)
            else -> res.getString(R.string.zone_hit_title, search.accuracy) to res.getString(R.string.zone_hit_text)
        }
        val manager = NotificationManagerCompat.from(app)
        manager.createNotificationChannel(
            NotificationChannelCompat.Builder(CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH).setName(res.getString(R.string.police)).build(),
        )
        val id = "police-$caseId-$at".hashCode()
        val open = PendingIntent.getActivity(
            app, id, AppLaunch.intent(app, caseId, "police", null),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        val notification = NotificationCompat.Builder(app, CHANNEL)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setWhen(at)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(open)
            .setAutoCancel(true)
            .build()
        manager.notify(id, notification)
    }
}
