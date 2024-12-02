package com.sborges.core.remoteconfig

interface FeatureToggle {
    fun isFeatureEnabled(key: String): Boolean
}