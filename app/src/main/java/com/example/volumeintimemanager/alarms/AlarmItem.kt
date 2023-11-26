package com.example.volumeintimemanager.alarms

data class AlarmItem(
    val timeFrom: String,
    val timeTo: String,
    val weekDay: Int,
    val soundsOn: Boolean
)
