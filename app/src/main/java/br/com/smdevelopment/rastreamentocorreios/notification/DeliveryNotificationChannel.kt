package br.com.smdevelopment.rastreamentocorreios.notification


import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.presentation.MainActivity
import kotlin.random.Random

class DeliveryNotificationChannel(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification(title: String, description: String) {
        val resultIntent = Intent(context, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntentWithParentStack(resultIntent)

        // Get the PendingIntent containing the entire back stack
        val resultPendingIntent = stackBuilder.getPendingIntent(
            Random.nextInt(),
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.delivered_icon)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setContentIntent(resultPendingIntent)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "app_notification_chanel"
    }
}