package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class DeliveryBusinessImpl @Inject constructor(private val deliveryRepository: DeliveryRepository) : DeliveryBusiness {

    override suspend fun fetchDelivery(code: String): Flow<DeliveryData> {
        val responseFlow = deliveryRepository.fetchDelivery(code)
        val deliveryFlow: Flow<DeliveryData> = responseFlow.mapNotNull {
            it.toDeliveryData()
        }
        return deliveryFlow
    }
}

private fun DeliveryResponse.toDeliveryData() = DeliveryData(
    code = delivery.objectCode.orEmpty(),
    eventList = delivery.eventList,
    type = delivery.type,
    description = delivery.eventList.lastOrNull()?.description.orEmpty()
)