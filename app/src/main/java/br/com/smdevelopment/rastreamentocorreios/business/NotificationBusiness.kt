package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import kotlinx.coroutines.flow.Flow

interface NotificationBusiness {

    suspend fun checkForUpdate(): Flow<DeliveryData>
}