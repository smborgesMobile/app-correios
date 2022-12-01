package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {

    suspend fun fetchDelivery(code: String): Flow<DeliveryResponse>

}