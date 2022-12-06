package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData

interface NotificationBusiness {

    suspend fun checkForUpdate(): DeliveryData?
}