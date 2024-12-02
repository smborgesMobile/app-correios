package com.sborges.core.di

import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.sborges.core.appcheck.FirebaseAppCheckInitializerImpl
import com.sborges.core.appcheck.FirebaseAppCheckerInitializer
import com.sborges.core.push.data.FirebaseMessageInitializer
import com.sborges.core.push.data.FirebaseMessageLocalRepositoryImpl
import com.sborges.core.push.domain.abstraction.FirebaseMessageLocalRepository
import com.sborges.core.remoteconfig.FeatureToggle
import com.sborges.core.remoteconfig.RemoteConfigFeatureToggle
import com.sborges.core.remoteconfig.RemoteConfigRepository
import com.sborges.core.review.manager.ReviewManagerLauncher
import com.sborges.core.review.manager.data.repository.InAppReviewRepositoryImpl
import com.sborges.core.review.manager.domain.abstraction.InAppReviewRepository
import com.sborges.core.review.manager.domain.usecase.LaunchCounterUseCase
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

        // Review Manager
        single {
            ReviewManagerLauncher(
                reviewManager = ReviewManagerFactory.create(androidContext()),
                launchCounterUse = get()
            )
        }

        // Launch Counter
        single<InAppReviewRepository> {
            InAppReviewRepositoryImpl(
                sharedPreferences = androidContext().getSharedPreferences(
                    /* name = */ APP_LAUNCH_COUNTER_PREFERENCES,
                    /* mode = */ Context.MODE_PRIVATE
                )
            )
        }

        // Use cases
        single { LaunchCounterUseCase(get()) }

        // Firebase app check
        factory<FirebaseAppCheckerInitializer> {
            FirebaseAppCheckInitializerImpl(
                Firebase
            )
        }

        factory { RemoteConfigRepository(Firebase.remoteConfig) }
        factory<FeatureToggle> { RemoteConfigFeatureToggle(get()) }
    }

    private companion object {
        const val FIREBASE_MESSAGE_KEY = "FIREBASE_MESSAGE_KEY"
        const val APP_LAUNCH_COUNTER_PREFERENCES = "APP_LAUNCH_COUNTER_PREFERENCES"
    }
}