package br.com.smdevelopment.rastreamentocorreios.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.MainActivity
import javax.inject.Inject

class NotificationManager @Inject constructor(private val application: Application) {

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE_DEFAULT)
        val notificationManager: NotificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(title: String, description: String, location: String) {
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            application,
            SCREEN_REQUEST_CODE,
            Intent(application, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.delivered_icon)
            .setContentTitle(title)
            .setContentText("$location - $description")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(application)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private companion object {
        private const val CHANNEL_NAME = "DELIVERY_NOTIFICATION"
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val NOTIFICATION_ID = 12
        private const val SCREEN_REQUEST_CODE = 13
    }

}