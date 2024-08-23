package com.sborges.core.push.domain.abstraction

interface FirebaseMessageLocalRepository {
    fun saveToken(token: String)
    fun getToken(): String?
}