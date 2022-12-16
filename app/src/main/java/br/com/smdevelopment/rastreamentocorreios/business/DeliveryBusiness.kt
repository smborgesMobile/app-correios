package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import kotlinx.coroutines.flow.Flow

interface DeliveryBusiness {

    suspend fun fetchDelivery(code: String): Flow<List<DeliveryData>>

    suspend fun getAllDeliveries(): Flow<List<DeliveryData>>

    suspend fun insertNewDelivery(delivery: List<DeliveryData>)

    suspend fun getDeliveredList(): Flow<List<DeliveryData>>

    suspend fun getPendingList(): Flow<List<DeliveryData>>

}