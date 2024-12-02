package com.sborges.core.remoteconfig.abstractions

interface FeatureToggle {
    fun isFeatureEnabled(key: String): Boolean
}