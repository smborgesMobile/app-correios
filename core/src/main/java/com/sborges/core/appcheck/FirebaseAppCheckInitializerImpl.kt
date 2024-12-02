package com.sborges.core.appcheck

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.initialize

internal class FirebaseAppCheckInitializerImpl(
    private val firebase: Firebase
) : FirebaseAppCheckerInitializer {

    override fun initAppChecker(context: Context) {
        firebase.initialize(context)
        firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }
}