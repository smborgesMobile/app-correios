package com.sborges.core.push.router

interface NotificationHandler {
    fun navigateToActivity(screen: String): Class<*>
}