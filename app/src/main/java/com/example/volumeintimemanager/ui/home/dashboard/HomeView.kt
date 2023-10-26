package com.example.volumeintimemanager.ui.home.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Tune
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
import com.example.volumeintimemanager.R
import com.example.volumeintimemanager.db.Rule
import com.example.volumeintimemanager.sampledata.RulesRepo
import com.example.volumeintimemanager.ui.rules.EditRule
import com.example.volumeintimemanager.utils.RuleUtils

@Composable
fun Home() {
    MaterialTheme {
        Scaffold(
            topBar = { ApplicationBar() },
            floatingActionButton = { AddRuleButton() },
            floatingActionButtonPosition = FabPosition.Center
        ) { _ ->
            LazyColumn() {
                items(
                    items = RulesRepo.getRules(),
                    key = { rule ->
                        rule.id
                    }
                ) {rule ->
                    ExpandableCard(rule = rule)
                }
            }
        }
    }
}

@Composable
private fun RuleRow(rule: Rule) {
    var checked by remember { mutableStateOf(true) }
    val weekNamesFull = remember { RuleUtils.convertWeekdaysShortcutsToFullNames(rule.weekDays) }

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
        ) {// days
            weekNamesFull.forEach { weekDay ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    text = weekDay,
                )
            }
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
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        ) {
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
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
private fun AddRuleButton() {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
private fun ExpandableCard(rule: Rule) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = { expanded = !expanded })
    ) {
        Column() {
            RuleRow(rule)
            if (expanded) {
                EditRule()
            }
        }
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
        RuleRow(RulesRepo.getRules()[0])
    }
}
@Preview
@Composable
private fun ExpendableCardPreview() {
    Surface {
        ExpandableCard(RulesRepo.getRules()[0])
    }
}


@Preview
@Composable
private fun HomePreview() {
    Home()
}