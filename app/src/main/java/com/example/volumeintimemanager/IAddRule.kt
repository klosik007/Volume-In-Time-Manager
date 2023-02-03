package com.example.volumeintimemanager

import android.view.View

interface IAddRule {
    fun checkDigit(number: Int): String?
    fun dayPicker(view: View?)
    fun replaceDaysIDsWithDaysNames(daysIds: String?): String?
    fun addRuleButton(view: View?)
    fun loadSpinnerItems()
}