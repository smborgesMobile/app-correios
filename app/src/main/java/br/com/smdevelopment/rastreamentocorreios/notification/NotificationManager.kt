package br.com.smdevelopment.rastreamentocorreios.notification

interface NotificationManager {

    fun createNotification(title: String, description: String, location: String)
}
