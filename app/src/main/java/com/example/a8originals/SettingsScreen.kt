package com.example.a8originals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    // Collect settings using collectAsState, with an empty list as the initial state
    val settings by viewModel.settings.collectAsState(initial = emptyList())

    LazyColumn {
        items(settings) { setting ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = setting.name, modifier = Modifier.weight(1f))
                Switch(
                    checked = setting.isEnabled,
                    onCheckedChange = { isChecked ->
                        viewModel.updateSetting(setting.name, isChecked)
                    }
                )
            }
        }
    }
}

