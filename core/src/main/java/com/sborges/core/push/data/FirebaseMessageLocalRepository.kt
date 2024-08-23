package com.sborges.core.push.data

import android.content.SharedPreferences
import com.sborges.core.push.domain.abstraction.FirebaseMessageLocalRepository

class FirebaseMessageLocalRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : FirebaseMessageLocalRepository {

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(FIREBASE_TOKEN, token).apply()
    }

    override fun getToken(): String? =
        sharedPreferences.getString(
            FIREBASE_TOKEN,
            null
        )

    private companion object {
        const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
    }
}