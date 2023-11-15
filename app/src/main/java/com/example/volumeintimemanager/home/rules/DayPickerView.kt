package com.example.volumeintimemanager.home.rules

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
import com.example.volumeintimemanager.db.Rule
import com.example.volumeintimemanager.sampledata.RulesRepo

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
            Column(modifier = Modifier.padding(5.dp)) {
                DayCircle(weekDayIdx = idx, weekDay = weekDaysLetters[idx], weekDayApply = weekDaysApplies[idx])
            }
        }
    }
}

@Composable
private fun DayCircle(weekDayIdx: Int, weekDay: String, weekDayApply: Boolean) {
    val isFilled = remember { mutableStateOf(weekDayApply) }

    Box {
        Canvas(
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    isFilled.value = !isFilled.value
                    // TODO: operations on DB: modify week days list depending on selected day - from separate method
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
        DayCircle(0, "M", true)
    }
}

@Preview
@Composable
private fun DayPickerPreview() {
    Surface {
        DayPicker(RulesRepo.getRules()[0])
    }
}