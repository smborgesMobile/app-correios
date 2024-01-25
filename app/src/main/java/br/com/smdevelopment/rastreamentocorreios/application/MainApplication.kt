package br.com.smdevelopment.rastreamentocorreios.application

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.com.smdevelopment.rastreamentocorreios.di.appModule
import br.com.smdevelopment.rastreamentocorreios.notification.NotificationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        setupNotificationWork()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    //#region --- configure workers

    private fun setupNotificationWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            MIN_WORKER_TIME,
            TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    //#endregion --- configure workers

    private companion object {
        private const val WORKER_NAME = "WORKER_NAME"
        private const val MIN_WORKER_TIME: Long = 30
    }

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()
}