package com.example.volumeintimemanager

import android.app.Application
import com.example.volumeintimemanager.alarms.AlarmSchedulerImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VolumeInTimeManager: Application() {

    lateinit var alarmScheduler: AlarmSchedulerImpl

    override fun onCreate() {
        super.onCreate()
        alarmScheduler = AlarmSchedulerImpl(this)
    }
}