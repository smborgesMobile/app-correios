package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.room.LocalDeliveryData
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {

    suspend fun fetchDelivery(code: String): Flow<DeliveryResponse>

    suspend fun fetchDeliveryListFromLocal(): Flow<List<LocalDeliveryData>>

    suspend fun insertNewDelivery(delivery: LocalDeliveryData)

}