package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import kotlinx.coroutines.flow.Flow

interface DeliveryBusiness {

    suspend fun fetchDelivery(code: String): Flow<DeliveryData>

}