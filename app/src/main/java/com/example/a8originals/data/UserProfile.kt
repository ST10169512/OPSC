package com.example.a8originals.data

// UserProfile.kt
data class UserProfile(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val notificationPreferences: Boolean = true,
    val languagePreference: String = "en"
)

