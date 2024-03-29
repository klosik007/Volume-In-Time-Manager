package com.example.volumeintimemanager.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.volumeintimemanager.R
import com.example.volumeintimemanager.alarms.AlarmScheduler
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.utils.sampledata.RulesRepo
import com.example.volumeintimemanager.ui.rules.EditRule
import com.example.volumeintimemanager.utils.createAlarmItemListForWeekDays

@Composable
fun Home(viewModel: HomeViewModel = hiltViewModel(), alarmScheduler: AlarmScheduler? = null) {
    val rules by viewModel.rules.collectAsState(initial = emptyList())
    
    MaterialTheme {
        Scaffold(
            topBar = { ApplicationBar() },
            floatingActionButton = { AddRuleButton(openDialog = { viewModel.openDialog() }) },
            floatingActionButtonPosition = FabPosition.Center
        ) { _ ->
            LazyColumn {
                items(
                    items = rules,
                    key = { rule ->
                        rule.id
                    }
                ) {rule ->
                    ExpandableCard(viewModel, rule = rule, alarmScheduler)
                }
            }

            AddRuleDialog(
                openDialog = viewModel.openDialog,
                closeDialog = { viewModel.closeDialog() },
                addRule = { rule -> viewModel.addRule(rule) },
                alarmScheduler
            )
        }
    }
}
@Composable
private fun WeekDayText(weekDay: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        text = weekDay,
    )
}

@Composable
private fun RuleRow(viewModel: HomeViewModel? = hiltViewModel(), rule: Rule, alarmScheduler: AlarmScheduler? = null) {
    var checked by remember { mutableStateOf(rule.applyRule) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (rule.monday)
                WeekDayText(weekDay = stringResource(id = R.string.monday))
            if (rule.tuesday)
                WeekDayText(weekDay = stringResource(id = R.string.tuesday))
            if (rule.wednesday)
                WeekDayText(weekDay = stringResource(id = R.string.wednesday))
            if (rule.thursday)
                WeekDayText(weekDay = stringResource(id = R.string.thursday))
            if (rule.friday)
                WeekDayText(weekDay = stringResource(id = R.string.friday))
            if (rule.saturday)
                WeekDayText(weekDay = stringResource(id = R.string.saturday))
            if (rule.sunday)
                WeekDayText(weekDay = stringResource(id = R.string.sunday))
        }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {//hours
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                text = "${rule.timeFrom} \n-\n${rule.timeTo}"
            )
        }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f)
                .fillMaxWidth()
                .align(Alignment.Bottom),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = null,
                onClick = {
                    val alarmItems = createAlarmItemListForWeekDays(rule)
                    for (alarm in alarmItems) {
                        alarmScheduler?.cancel(alarm)
                    }

                    viewModel?.deleteRule(rule)
                }
            )
            {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    rule.applyRule = checked
                    viewModel?.updateRule(rule)

                    val alarmItems = createAlarmItemListForWeekDays(rule)

                    if (checked) {
                        for (alarm in alarmItems) {
                            alarmScheduler?.schedule(alarm)
                        }
                    }
                    else {
                        for (alarm in alarmItems) {
                            alarmScheduler?.cancel(alarm)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ExpandableCard(viewModel: HomeViewModel? = hiltViewModel(), rule: Rule, alarmScheduler: AlarmScheduler? = null) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { expanded = !expanded })
    ) {
        Column {
            RuleRow(viewModel, rule, alarmScheduler)
            if (expanded) {
                EditRule(rule)
            } else {
                viewModel?.updateRule(rule)
            }
        }
    }
}

@Composable
private fun ApplicationBar() {
    TopAppBar(
        navigationIcon =  {
            Icon(
                imageVector = Icons.Rounded.Tune,
                contentDescription = null,
                modifier = Modifier.padding(5.dp)
            )
        },
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
private fun AddRuleButton(openDialog: () -> Unit) {
    FloatingActionButton(onClick = { openDialog() }) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
private fun AddRuleDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    addRule: (rule: Rule) -> Unit,
    alarmScheduler: AlarmScheduler?
) {
    if (openDialog) {
        val rule by remember {
            mutableStateOf(
                Rule(
                    0, true, timeFrom = "", timeTo = "", monday = false, tuesday = false,
                    wednesday = false, thursday = false, friday = false, saturday = false, sunday = false, soundOn = false)
            )
        }

        AlertDialog(
            onDismissRequest = { closeDialog() },
            text = { EditRule(rule = rule) },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                        addRule(rule)

                        val alarmItems = createAlarmItemListForWeekDays(rule)
                        for (alarm in alarmItems) {
                            alarmScheduler?.schedule(alarm)
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_addRule)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun ApplicationBarPreview() {
    Surface {
        ApplicationBar()
    }
}

@Preview
@Composable
private fun RuleRowPreview() {
    Surface {
        RuleRow(null, rule = RulesRepo.getRules()[0])
    }
}
@Preview
@Composable
private fun ExpendableCardPreview() {
    Surface {
        ExpandableCard(null, rule = RulesRepo.getRules()[0])
    }
}

@Preview
@Composable
private fun HomePreview() {
    Home()
}