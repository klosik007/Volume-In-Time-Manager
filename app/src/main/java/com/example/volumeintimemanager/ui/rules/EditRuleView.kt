package com.example.volumeintimemanager.ui.rules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.volumeintimemanager.R
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.utils.convertToTwoDigitsString
import com.example.volumeintimemanager.utils.sampledata.RulesRepo

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditRule(rule: Rule) {
    var timeFrom by remember { mutableStateOf(rule.timeFrom) }
    var timeTo by remember { mutableStateOf(rule.timeTo) }

    val soundsStates = stringArrayResource(id = R.array.behaviorSpinner_array)
    var soundsExpanded by remember { mutableStateOf(false) }
    var selectedSoundState by remember { mutableStateOf(if (rule.soundOn) soundsStates[1] else soundsStates[0]) }

    var showTimePickerTimeFrom by remember { mutableStateOf(false) }
    var showTimePickerTimeTo  by remember { mutableStateOf(false) }

    val initHourFrom = if (timeFrom.substringBefore(':').isEmpty()) 0 else timeFrom.substringBefore(':').toInt()
    val initMinuteFrom = if (timeFrom.substringAfter(':').isEmpty()) 0 else timeFrom.substringAfter(':').toInt()
    val timePickerStateTimeFrom = rememberTimePickerState(
                initialHour = initHourFrom,
                initialMinute = initMinuteFrom,
                is24Hour = true)

    val initHourTo = if (timeTo.substringBefore(':').isEmpty()) 0 else timeTo.substringBefore(':').toInt()
    val initMinuteTo = if (timeTo.substringAfter(':').isEmpty()) 0 else timeTo.substringAfter(':').toInt()
    val timePickerStateTimeTo = rememberTimePickerState(
        initialHour = initHourTo,
        initialMinute = initMinuteTo,
        is24Hour = true)

    MaterialTheme {
        Surface(modifier = Modifier.padding(5.dp)) {
            Column(
                modifier = Modifier.padding(end = 5.dp)
            ) {
                Row(modifier = Modifier
                                .padding(start = 24.dp, top = 24.dp, end = 24.dp)
                                .align(Alignment.CenterHorizontally))
                {
                   DayPicker(rule)
                }
                Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)) {
                    // TODO: fill the half of parent
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Time From") },
                        readOnly = true,
                        value = timeFrom,
                        onValueChange = { timeFrom = it; rule.timeFrom = timeFrom },
                        trailingIcon = {
                            IconButton(onClick = { showTimePickerTimeFrom = true }) {
                                Icon(
                                    imageVector = Icons.Rounded.AccessTime,
                                    contentDescription = null,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    )
                }
                Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)) {
                    // TODO: fill the half of parent
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Time To") },
                        readOnly = true,
                        value = timeTo,
                        onValueChange = { timeTo = it },
                        trailingIcon = {
                            IconButton(onClick = { showTimePickerTimeTo = true }) {
                                Icon(
                                    imageVector = Icons.Rounded.AccessTime,
                                    contentDescription = null,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    )
                }
                Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 24.dp)) {
                    Column(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = soundsExpanded,
                            onExpandedChange = { soundsExpanded = !soundsExpanded }
                        ) {
                            TextField(
                                value = selectedSoundState,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = soundsExpanded) }
                            )
                            ExposedDropdownMenu(
                                expanded = soundsExpanded,
                                onDismissRequest = { /*TODO*/ }
                            ) {
                                soundsStates.forEach { item ->
                                    DropdownMenuItem(
                                        content = { Text(text = item) },
                                        onClick = {
                                            selectedSoundState = item
                                            soundsExpanded = false
                                            rule.soundOn = selectedSoundState != soundsStates[0]
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                if (showTimePickerTimeFrom || showTimePickerTimeTo) {
                    val selectTimeCaption = if (showTimePickerTimeFrom) "Select Time From" else "Select Time To"

                    TimePickerDialog(
                        title = selectTimeCaption,
                        onCancel = { showTimePickerTimeFrom = false; showTimePickerTimeTo = false },
                        onConfirm = {
                            val pickerTimeFrom = "${timePickerStateTimeFrom.hour.convertToTwoDigitsString()}:${timePickerStateTimeFrom.minute.convertToTwoDigitsString()}"
                            val pickerTimeTo = "${timePickerStateTimeTo.hour.convertToTwoDigitsString()}:${timePickerStateTimeTo.minute.convertToTwoDigitsString()}"

                            if (showTimePickerTimeFrom) {
                                timeFrom = pickerTimeFrom
                                rule.timeFrom = pickerTimeFrom
                            }

                            if (showTimePickerTimeTo) {
                                timeTo = pickerTimeTo
                                rule.timeTo = pickerTimeTo
                            }

                            showTimePickerTimeFrom = false
                            showTimePickerTimeTo = false
                        },
                    ) {
                        if (showTimePickerTimeFrom)
                            TimePicker(state = timePickerStateTimeFrom)
                        else
                            TimePicker(state = timePickerStateTimeTo)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditRulePreview() {
    val rules = RulesRepo.getRules()
    EditRule(rules[0])
}