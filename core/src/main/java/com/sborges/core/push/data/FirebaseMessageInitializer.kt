package com.sborges.core.push.data

import com.google.firebase.messaging.FirebaseMessaging
import com.sborges.core.push.domain.abstraction.FirebaseMessageLocalRepository

class FirebaseMessageInitializer(
    private val repository: FirebaseMessageLocalRepository
) {

    fun initFirebaseMessage() =
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            repository.saveToken(it.toString())
        }
}