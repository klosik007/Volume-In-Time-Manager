package com.example.volumeintimemanager.ui.home.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.volumeintimemanager.R

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
private fun MainButtonsAndSpinner() {
    var checked by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Button(onClick = {}) {
                Text(stringResource(R.string.button_addRule))
            }
        }
        Column(modifier = Modifier
            .padding(5.dp)
            .weight(1f)) {
            Button(onClick = {}) {
                Text(stringResource(R.string.button_editRule))
            }
        }
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(stringResource(R.string.switch_text))
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
fun RulesData() {

}

@Composable
fun Home() {
    MaterialTheme {
        Scaffold(topBar = { ApplicationBar() }) { _ ->
            Row() {
                MainButtonsAndSpinner()
            }
            Row {
                RulesData()
            }
        }
    }
}

@Preview
@Composable
private fun MainButtonsAndSpinnerPreview() {
    MainButtonsAndSpinner()
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
private fun HomePreview() {
    Home()
}