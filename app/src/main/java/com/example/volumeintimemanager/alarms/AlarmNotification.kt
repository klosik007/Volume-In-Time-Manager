package com.example.volumeintimemanager.alarms

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.volumeintimemanager.MainActivity
import com.example.volumeintimemanager.R

fun showNotification(
    context: Context?,
    channelId: String,
    channelName: String,
    notificationId: Int,
    contentTitle: String
) {
    val startAppIntent = Intent(context, MainActivity::class.java)
    val startAppPendingIntent = PendingIntent.getActivity(context, 0, startAppIntent, PendingIntent.FLAG_IMMUTABLE)

    val deleteIntent = Intent(context, AlarmNotificationDismissedReceiver::class.java)
    val deletePendingIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, PendingIntent.FLAG_IMMUTABLE)
    context?.let {
        val notification = NotificationCompat.Builder(it, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(contentTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(startAppPendingIntent, true)
            .setDeleteIntent(deletePendingIntent)
            .build()
        val notificationManager = it.getSystemService(NotificationManager::class.java)

        if (notificationManager?.getNotificationChannel(channelId) == null) {
            notificationManager?.createNotificationChannel(NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT))
        }

        notificationManager?.notify(notificationId, notification)
    }
}