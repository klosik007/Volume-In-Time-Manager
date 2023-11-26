package com.example.volumeintimemanager.alarms

interface AlarmScheduler {
    fun turnOn(alarmItem: AlarmItem)
    fun turnOff(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}