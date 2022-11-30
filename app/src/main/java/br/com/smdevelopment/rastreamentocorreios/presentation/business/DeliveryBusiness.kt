package br.com.smdevelopment.rastreamentocorreios.presentation.business

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import kotlinx.coroutines.flow.Flow

interface DeliveryBusiness {

    suspend fun fetchDelivery(code: String): Flow<DeliveryResponse>

}