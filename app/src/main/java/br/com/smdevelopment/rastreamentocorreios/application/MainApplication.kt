package br.com.smdevelopment.rastreamentocorreios.application

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import br.com.smdevelopment.rastreamentocorreios.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .build()
}