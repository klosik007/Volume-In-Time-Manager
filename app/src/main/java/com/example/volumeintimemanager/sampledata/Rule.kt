package com.example.volumeintimemanager.sampledata

import com.example.volumeintimemanager.db.Rule

object RulesRepo {
    fun getRules(): List<Rule> = rules
}

private val rule1 = Rule(
    1,
    "8:00",
    "16:00",
    "Mon;Wed;Fri",
    true
)

private val rule2 = Rule(
    2,
    "9:00",
    "15:00",
    "Tue;Thu;Sat",
    true
)

private val rule3 = Rule(
    3,
    "8:00",
    "16:00",
    "Fri;Sun",
    false
)

private val rules = listOf(
    rule1,
    rule2,
    rule3
)