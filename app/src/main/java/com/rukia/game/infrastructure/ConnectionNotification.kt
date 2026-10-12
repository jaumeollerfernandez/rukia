package com.rukia.game.infrastructure

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
import com.rukia.game.domain.model.GameCase
import com.rukia.game.domain.port.ConnectionAlarm
import com.rukia.phone.infrastructure.AppLaunch
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.Language

private const val CHANNEL = "case_connection"
private const val CASE = "case"
private const val OWNER = "owner"

/** [ConnectionAlarm] on Android's alarms. One per case, so setting it again replaces it. */
// ponytail: alarms don't survive a reboot; the case still opens on launch, just without the notification.
class AndroidConnectionAlarm(private val context: Context) : ConnectionAlarm {
    override fun set(case: GameCase, at: Long) {
        val alarms = context.getSystemService(AlarmManager::class.java)
        val alarm = PendingIntent.getBroadcast(
            context, "connection-${case.id}".hashCode(),
            Intent(context, ConnectionAlarmReceiver::class.java).putExtra(CASE, case.id).putExtra(OWNER, case.owner),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        if (Build.VERSION.SDK_INT < 31 || alarms.canScheduleExactAlarms()) {
            alarms.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, alarm)
        } else alarms.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, alarm)
    }
}

/** The case's D1 began: "Connection established on <owner>'s phone". Tapping it opens the case. */
class ConnectionAlarmReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission") // checked just below
    override fun onReceive(context: Context, intent: Intent) {
        val app = context.applicationContext
        val caseId = intent.getStringExtra(CASE) ?: return
        if (!CaseClock.isStarted(app, caseId)) return // reset since
        if (Build.VERSION.SDK_INT >= 33 &&
            app.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return
        val res = Language.wrap(app).resources
        val manager = NotificationManagerCompat.from(app)
        manager.createNotificationChannel(
            NotificationChannelCompat.Builder(CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH).setName(res.getString(R.string.connection_channel)).build(),
        )
        val id = "connection-$caseId".hashCode()
        val open = PendingIntent.getActivity(
            app, id, AppLaunch.intent(app, caseId, "home", null),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
        val notification = NotificationCompat.Builder(app, CHANNEL)
            .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            .setContentTitle(res.getString(R.string.case_file, caseId.uppercase()))
            .setContentText(res.getString(R.string.connection_established, intent.getStringExtra(OWNER).orEmpty()))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(open)
            .setAutoCancel(true)
            .build()
        manager.notify(id, notification)
    }
}
