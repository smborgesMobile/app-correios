package com.sborges.core.push.di

import android.content.Context
import com.sborges.core.push.data.FirebaseMessageInitializer
import com.sborges.core.push.data.FirebaseMessageLocalRepositoryImpl
import com.sborges.core.push.domain.abstraction.FirebaseMessageLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class CoreModulesDI {

    fun loadModules() = module {
        single<FirebaseMessageLocalRepository> {
            FirebaseMessageLocalRepositoryImpl(
                sharedPreferences = androidContext().getSharedPreferences(
                    /* name = */ FIREBASE_MESSAGE_KEY,
                    /* mode = */ Context.MODE_PRIVATE
                )
            )
        }

        single { FirebaseMessageInitializer(get()) }
    }

    private companion object {
        const val FIREBASE_MESSAGE_KEY = "FIREBASE_MESSAGE_KEY"
    }
}