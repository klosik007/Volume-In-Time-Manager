package com.example.volumeintimemanager.home.rules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.volumeintimemanager.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddRule() {
    val daysHintText = stringResource(id = R.string.daysOfWeek_hint)
    val timeFromText = stringResource(id = R.string.timeFrom_hint)
    val timeToText = stringResource(id = R.string.timeTo_hint)

    val daysOfWeek by remember { mutableStateOf(daysHintText) }
    val timeFrom by remember { mutableStateOf(timeFromText) }
    val timeTo by remember { mutableStateOf(timeToText) }

    val soundsStates = stringArrayResource(id = R.array.behaviorSpinner_array)
    var soundsExpanded by remember { mutableStateOf(false) }
    var selectedSoundState by remember { mutableStateOf(soundsStates[0]) }

    MaterialTheme {
        Surface(modifier = Modifier.padding(5.dp)) {
            Column(
                modifier = Modifier.padding(end = 5.dp)
            ) {
                Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 5.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = daysOfWeek,
                        onValueChange = {}
                    )
                }
                Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp)) {
                    // TODO: fill the half of parent
                    OutlinedTextField(value = timeFrom, onValueChange = {})
                }
                Row(modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 24.dp)) {
                    // TODO: fill the half of parent
                    OutlinedTextField(value = timeTo, onValueChange = {})
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
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Column (
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {}) {
                            Text(stringResource(id = R.string.button_addRule))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditRulePreview() {
    AddRule()
}