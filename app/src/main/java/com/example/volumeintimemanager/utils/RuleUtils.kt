package com.example.volumeintimemanager.utils

object RuleUtils {

    fun convertWeekdaysShortcutsToFullNames(weekDays: String): MutableList<String> {
        val shortWeekNames  = convertWeekDaysToList(weekDays)
        val fullNames = mutableListOf<String>()

        shortWeekNames.forEach { day ->
            weekDaysShortcut[day]?.let { fullNames.add(it) }
        }

        return fullNames
    }

    private fun convertWeekDaysToList(weekDays: String) = weekDays.split(";")

    private val weekDaysShortcut = hashMapOf(
        "Mon" to "Monday",
        "Tue" to "Tuesday",
        "Wed" to "Wednesday",
        "Thu" to "Thursday",
        "Fri" to "Friday",
        "Sat" to "Saturday",
        "Sun" to "Sunday"
    )
}