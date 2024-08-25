package br.com.smdevelopment.rastreamentocorreios.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import br.com.smdevelopment.rastreamentocorreios.di.appModule
import br.com.smdevelopment.rastreamentocorreios.workmanager.NotificationCheckWorkManager
import br.com.smdevelopment.rastreamentocorreios.workmanager.UpdateCacheWorker
import com.sborges.core.di.CoreModulesDI
import com.sborges.core.push.data.FirebaseMessageInitializer
import com.sborges.price.di.priceModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApplication : Application(), Configuration.Provider, KoinComponent {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        configureNotification()
        setupNotificationWork()
        setupUpdateCache()
        setupFirebaseMessage()
    }

    private fun setupFirebaseMessage() {
        val firebaseInitializer: FirebaseMessageInitializer = get()
        firebaseInitializer.initFirebaseMessage()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule + priceModules + CoreModulesDI().loadModules())
        }
    }

    private fun setupNotificationWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<NotificationCheckWorkManager>(
            UPDATE_WORKER,
            TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun setupUpdateCache() {
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<UpdateCacheWorker>()
                .build()

        WorkManager
            .getInstance(this)
            .enqueue(uploadWorkRequest)
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
        const val UPDATE_WORKER = 1L
        const val WORKER_NAME = "notification_check_worker"
    }
}