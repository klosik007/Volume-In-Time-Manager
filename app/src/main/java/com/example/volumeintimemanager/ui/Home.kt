package com.example.volumeintimemanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Home() {

}

@Composable
private fun MainButtonsAndSpinner() {
    var checked by remember { mutableStateOf(true) }

    MaterialTheme {
        Scaffold(
            topBar = { ApplicationBar() }
        ) { padding ->
            Row(modifier = Modifier.padding(padding)) {
                Column {
                    Button(onClick = {}) {
                        Text("ADD RULE")
                    }
                }
                Column {
                    Button(onClick = {}) {
                        Text("EDIT RULE")
                    }
                }
                Column {
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        }
                    )
                }
            }

        }
    }
}

@Composable
private fun ApplicationBar() {
    TopAppBar(
        navigationIcon =  {

        },
        title = {},
        backgroundColor = MaterialTheme.colors.primary
    )
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