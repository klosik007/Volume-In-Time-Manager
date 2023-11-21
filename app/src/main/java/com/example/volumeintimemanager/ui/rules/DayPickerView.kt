package com.example.volumeintimemanager.ui.rules

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.utils.sampledata.RulesRepo

@Composable
fun DayPicker(rule: Rule) {
    val weekDaysLetters by remember { mutableStateOf(listOf("M", "T", "W", "T", "F", "S", "S")) }
    val weekDaysApplies by remember { mutableStateOf(
        listOf(
            rule.monday, rule.tuesday, rule.wednesday, rule.thursday, rule.friday, rule.saturday, rule.sunday)
        )
    }

    Row() {
        for (idx in weekDaysLetters.indices) {
            Column() {
                DayCircle(weekDayIdx = idx, weekDay = weekDaysLetters[idx], weekDayApply = weekDaysApplies[idx], rule = rule)
            }
        }
    }
}

@Composable
private fun DayCircle(weekDayIdx: Int, weekDay: String, weekDayApply: Boolean, rule: Rule) {
    val isFilled = remember { mutableStateOf(weekDayApply) }

    Box {
        Canvas(
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    isFilled.value = !isFilled.value
                    when (weekDayIdx) {
                        0 -> { rule.monday = !rule.monday }
                        1 -> { rule.tuesday = !rule.tuesday }
                        2 -> { rule.wednesday = !rule.wednesday }
                        3 -> { rule.thursday = !rule.thursday }
                        4 -> { rule.friday = !rule.friday }
                        5 -> { rule.saturday = !rule.saturday }
                        6 -> { rule.sunday = !rule.sunday }
                    }
                },
            onDraw = {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    color = Color.Blue,
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = 40f,
                    style = if (isFilled.value) Fill else Stroke(5f)
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = weekDay,
            color = if (isFilled.value) Color.White else Color.Black,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun DayCirclePreview() {
    Surface {
        DayCircle(0, "M", true, Rule(
            0, true, "", "", false, false,
            false, false, false, false, false, false)
        )
    }
}

@Preview
@Composable
private fun DayPickerPreview() {
    Surface {
        DayPicker(RulesRepo.getRules()[0])
    }
}