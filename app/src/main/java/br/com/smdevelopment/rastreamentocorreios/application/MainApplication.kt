package br.com.smdevelopment.rastreamentocorreios.application

import android.app.Application
import android.util.Log.DEBUG
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.com.smdevelopment.rastreamentocorreios.notification.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        setupNotificationWork()
    }

    //#region --- configure workers

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(DEBUG)
            .build()

    private fun setupNotificationWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<NotificationWorker>(MIN_WORKER_TIME, TimeUnit.MINUTES).build()
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
}