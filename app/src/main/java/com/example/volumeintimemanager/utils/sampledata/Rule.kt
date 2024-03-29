package com.example.volumeintimemanager.utils.sampledata

import com.example.volumeintimemanager.domain.model.Rule

object RulesRepo {
    fun getRules(): List<Rule> = rules
}

private val rule1 = Rule(
    id = 1,
    applyRule = true,
    timeFrom = "8:00",
    timeTo = "16:00",
    monday = true,
    wednesday = true,
    friday = true,
    soundOn = true
)

private val rule2 = Rule(
    id = 2,
    applyRule = true,
    timeFrom = "9:00",
    timeTo = "15:00",
    tuesday = true,
    thursday = true,
    saturday = true,
    soundOn = true
)

private val rule3 = Rule(
    id = 3,
    applyRule = true,
    timeFrom = "8:00",
    timeTo = "16:00",
    friday = true,
    sunday = true,
    soundOn = false
)

private val rule4 = Rule(
    id = 4,
    applyRule = true,
    timeFrom = "8:00",
    timeTo = "16:00",
    monday = true,
    tuesday = true,
    wednesday = true,
    thursday = true,
    friday = true,
    saturday = true,
    sunday = true,
    soundOn = false
)

private val rules = listOf(
    rule1,
    rule2,
    rule3,
    rule4
)