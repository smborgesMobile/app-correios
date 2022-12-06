package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import javax.inject.Inject

class NotificationBusinessImpl @Inject constructor(private val repository: DeliveryRepository) : NotificationBusiness {

    override suspend fun checkForUpdate(): DeliveryData? {
        return null
    }

}