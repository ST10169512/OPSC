package com.example.a8originals

// UserProfileActivity.kt
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.a8originals.data.UserProfile

class UserProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var notificationSwitch: Switch
    private lateinit var languageEditText: EditText
    private lateinit var saveButton: Button

    private val userProfileRepository = UserProfileRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Initialize UI components
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        notificationSwitch = findViewById(R.id.notificationSwitch)
        languageEditText = findViewById(R.id.languageEditText)
        saveButton = findViewById(R.id.saveButton)

        // Load profile data
        loadUserProfile()

        // Save button click listener
        saveButton.setOnClickListener {
            saveUserProfile()
        }
    }

    private fun loadUserProfile() {
        userProfileRepository.getUserProfile { profile ->
            if (profile != null) {
                nameEditText.setText(profile.name)
                emailEditText.setText(profile.email)
                notificationSwitch.isChecked = profile.notificationPreferences
                languageEditText.setText(profile.languagePreference)
            } else {
                Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserProfile() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val notificationPreferences = notificationSwitch.isChecked
        val languagePreference = languageEditText.text.toString()

        val profile = UserProfile(
            userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
            name = name,
            email = email,
            notificationPreferences = notificationPreferences,
            languagePreference = languagePreference
        )

        userProfileRepository.saveUserProfile(profile)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save profile: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
