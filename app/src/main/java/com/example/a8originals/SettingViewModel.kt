package com.example.a8originals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a8originals.data.SettingOption
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _settings = MutableLiveData<List<SettingOption>>()
    val settings: StateFlow<List<SettingOption>> get() = _settings as StateFlow<List<SettingOption>>

    init {
        loadSettings()
    }

    private fun loadSettings() {
        // Add settings options as needed
        _settings.value = listOf(
            SettingOption("Enable Notifications", true),
            SettingOption("Dark Mode", false)
            // Add other options
        )
    }

    fun updateSetting(name: String, isEnabled: Boolean) {
        // Update the setting in the list and notify observers
        _settings.value = _settings.value?.map {
            if (it.name == name) it.copy(isEnabled = isEnabled) else it
        }
    }
}
