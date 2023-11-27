package com.example.volumeintimemanager.utils

import com.example.volumeintimemanager.alarms.AlarmItem
import com.example.volumeintimemanager.domain.model.Rule

fun createAlarmItemListForWeekDays(rule: Rule): List<AlarmItem> {
    val alarmItems = mutableListOf<AlarmItem>()

    if (rule.monday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 2, rule.soundOn))
    if (rule.tuesday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 3, rule.soundOn))
    if (rule.wednesday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 4, rule.soundOn))
    if (rule.thursday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 5, rule.soundOn))
    if (rule.friday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 6, rule.soundOn))
    if (rule.saturday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 7, rule.soundOn))
    if (rule.sunday)
        alarmItems.add(AlarmItem(rule.timeFrom, rule.timeTo, 1, rule.soundOn))

    return alarmItems.toList()
}