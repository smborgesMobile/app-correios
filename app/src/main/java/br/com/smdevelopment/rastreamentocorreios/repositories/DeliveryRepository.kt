package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {

    suspend fun fetchDelivery(codeList: List<String>): Flow<DeliveryResponse>

    suspend fun fetchDeliveryListFromLocal(): List<DeliveryData>

    suspend fun insertNewDelivery(delivery: DeliveryData)

}