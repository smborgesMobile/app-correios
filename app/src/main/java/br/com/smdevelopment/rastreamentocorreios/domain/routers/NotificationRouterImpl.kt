package br.com.smdevelopment.rastreamentocorreios.domain.routers

import br.com.smdevelopment.rastreamentocorreios.presentation.MainActivity
import com.sborges.core.push.router.NotificationHandler

class NotificationRouterImpl : NotificationHandler {

    override fun navigateToActivity(screen: String): Class<*> {
        return MainActivity::class.java
    }
}