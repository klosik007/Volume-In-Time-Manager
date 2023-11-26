package com.example.volumeintimemanager.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.Calendar

class AlarmSchedulerImpl(private val context: Context): AlarmScheduler {

    override fun turnOn(alarmItem: AlarmItem) {
        val timeFromCalendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.DAY_OF_WEEK, alarmItem.weekDay)
            set(Calendar.HOUR_OF_DAY, alarmItem.timeFrom.substringBefore(':').toInt())
            set(Calendar.MINUTE, alarmItem.timeFrom.substringAfter(':').toInt())
        }

        if (alarmItem.soundsOn)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeFromCalendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, turnOnPendingIntent)
        else
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeFromCalendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, turnOffPendingIntent)

        // TODO: show toolbar notification
        Toast.makeText(context, "Sounds on from ${alarmItem.timeFrom}", Toast.LENGTH_LONG).show()
    }

    override fun turnOff(alarmItem: AlarmItem) {
        val timeToCalendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.DAY_OF_WEEK, alarmItem.weekDay)
            set(Calendar.HOUR_OF_DAY, alarmItem.timeTo.substringBefore(':').toInt())
            set(Calendar.MINUTE, alarmItem.timeTo.substringAfter(':').toInt())
        }

        if (alarmItem.soundsOn)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeToCalendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, turnOffPendingIntent)
        else
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeToCalendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, turnOnPendingIntent)

        // TODO: show toolbar notification
        Toast.makeText(context, "Sounds off from ${alarmItem.timeFrom}", Toast.LENGTH_LONG).show()
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(turnOnPendingIntent)
        alarmManager.cancel(turnOffPendingIntent)
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val turnOnIntent = Intent(context, SoundOnReceiver::class.java)
    private val turnOnPendingIntent = PendingIntent.getBroadcast(context, 0, turnOnIntent, PendingIntent.FLAG_IMMUTABLE)
    private val turnOffIntent = Intent(context, SoundOffReceiver::class.java)
    private val turnOffPendingIntent = PendingIntent.getBroadcast(context, 0, turnOffIntent, PendingIntent.FLAG_IMMUTABLE)
}