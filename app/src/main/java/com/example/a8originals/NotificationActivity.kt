package com.example.a8originals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.activity.viewModels

class NotificationActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification) // Make sure to create this layout

        titleTextView = findViewById(R.id.titleTextView)
        messageTextView = findViewById(R.id.messageTextView)

        // Get the extras from the intent
        val title = intent.getStringExtra("notificationTitle")
        val message = intent.getStringExtra("notificationMessage")

        // Set the title and message to the TextViews
        titleTextView.text = title ?: "No Title"
        messageTextView.text = message ?: "No Message"
    }
}
