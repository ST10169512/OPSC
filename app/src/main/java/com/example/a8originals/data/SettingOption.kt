package com.example.a8originals.data

data class SettingOption(
    val name: String,
    val isEnabled: Boolean = true // for toggles, or use `value: String` for text options
)

