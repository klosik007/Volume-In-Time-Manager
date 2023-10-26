package com.example.volumeintimemanager.ui.rules

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.volumeintimemanager.sampledata.RulesRepo
import com.example.volumeintimemanager.ui.home.dashboard.RuleRow

@Composable
fun DayPicker() {
    val weekDaysLetters by remember { mutableStateOf(listOf("M", "T", "W", "T", "F", "S", "S")) }

    MaterialTheme() {
        Surface {
            Row() {
                weekDaysLetters.forEach {
                    Column(modifier = Modifier.padding(5.dp)) {
                        DayCircle(weekDay = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCircle(weekDay: String) {
    Box {
        Canvas(
            modifier = Modifier.size(40.dp),
            onDraw = {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    color = Color.Blue,
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = 40f,
                    style = Stroke(10F))
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = weekDay, color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun DayCirclePreview() {
    DayCircle("M")
}

@Preview
@Composable
private fun DayPickerPreview() {
    DayPicker()
}