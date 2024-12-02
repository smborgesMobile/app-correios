package com.sborges.core.remoteconfig

import com.sborges.core.remoteconfig.abstractions.FeatureToggle

class RemoteConfigFeatureToggle(
    private val remoteConfigRepository: RemoteConfigRepository
) : FeatureToggle {

    override fun isFeatureEnabled(key: String): Boolean {
        return remoteConfigRepository.getBoolean(key)
    }
}