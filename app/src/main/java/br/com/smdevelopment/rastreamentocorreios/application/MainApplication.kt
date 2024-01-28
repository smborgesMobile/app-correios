package br.com.smdevelopment.rastreamentocorreios.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.com.smdevelopment.rastreamentocorreios.di.appModule
import br.com.smdevelopment.rastreamentocorreios.workmanager.NotificationCheckWorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        configureNotification()
        setupNotificationWork()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    private fun setupNotificationWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<NotificationCheckWorkManager>(
            WORKER_TIME,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun configureNotification() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_ID,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "app_notification_chanel"
        const val WORKER_TIME = 2L
        const val WORKER_NAME = "notification_check_worker"
    }
}