package com.example.a8originals
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Log that we've entered the method
        Log.d("MyFirebaseMsgService", "onMessageReceived called")

        // Log the full message received
        Log.d("MyFirebaseMsgService", "From: ${remoteMessage.from}")

        // Check if there is a notification payload
        remoteMessage.notification?.let {
            Log.d("MyFirebaseMsgService", "Message Notification Title: ${it.title}")
            Log.d("MyFirebaseMsgService", "Message Notification Body: ${it.body}")
            sendNotification(it.title, it.body)
        }

        // Check if there is data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("MyFirebaseMsgService", "Message Data Payload: ${remoteMessage.data}")
        }
    }


    private fun sendNotification(title: String?, messageBody: String?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "default_channel"
        val channelName = "Default Channel"

        // Create a Notification Channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Create an intent to open the NotificationActivity when the notification is clicked
        val intent = Intent(this, NotificationActivity::class.java).apply {
            putExtra("notificationTitle", title)
            putExtra("notificationMessage", messageBody)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setSmallIcon(R.drawable.ic_notification) // Replace with your app's notification icon
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set the intent to open the NotificationActivity

        notificationManager.notify(0, notificationBuilder.build())
    }

}
