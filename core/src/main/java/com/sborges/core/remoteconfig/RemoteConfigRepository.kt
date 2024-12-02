package com.sborges.core.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigRepository(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) {

    fun initRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = REMOTE_CONFIG_FETCH_TIME
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.fetchAndActivate()
    }

    fun getBoolean(key: String): Boolean =
        firebaseRemoteConfig.getBoolean(key)

    private companion object {
        const val REMOTE_CONFIG_FETCH_TIME = 3600L
    }
}