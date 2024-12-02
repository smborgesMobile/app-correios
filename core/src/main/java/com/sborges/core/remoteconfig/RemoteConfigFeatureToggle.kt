package com.sborges.core.remoteconfig

class RemoteConfigFeatureToggle(
    private val remoteConfigRepository: RemoteConfigRepository
) : FeatureToggle {

    override fun isFeatureEnabled(key: String): Boolean {
        return remoteConfigRepository.getBoolean(key)
    }
}