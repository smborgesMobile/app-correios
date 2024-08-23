package com.sborges.core.push.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sborges.core.R
import com.sborges.core.push.router.NotificationHandler
import org.koin.android.ext.android.inject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val activityResult: NotificationHandler by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(LOG_TAG, "From: ${remoteMessage.from}")
        remoteMessage.notification?.let {
            sendNotification(it.body)
        }
    }


    private fun sendNotification(messageBody: String?) {
        val intent = Intent(this, activityResult.navigateToActivity(PUSH_ACTIVITY)).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(
            this,
            DEFAULT
        )
            .setContentTitle("FCM Message")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            DEFAULT,
            CHANEL_TITLE,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(LOG_TAG, "Refreshed token: $token")
        super.onNewToken(token)

    }

    private companion object {
        private const val LOG_TAG = "sm.borges"
        private const val PUSH_ACTIVITY = "MainActivity"
        private const val DEFAULT = "default"
        private const val CHANEL_TITLE = "Channel human readable title"
    }
}