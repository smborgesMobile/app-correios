package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class DeliveryBusinessImpl @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
    private val converter: DeliveryConverter
) : DeliveryBusiness {

    //#region --- fetch data

    override suspend fun fetchDelivery(code: String): Flow<DeliveryData> {
        val responseFlow = deliveryRepository.fetchDelivery(code)
        val deliveryFlow: Flow<DeliveryData> = responseFlow.mapNotNull {
            converter.convert(it)
        }

        deliveryFlow.collect { delivery ->
            insertNewDelivery(delivery)
        }

        return deliveryFlow
    }

    override suspend fun getAllDeliveries(): List<DeliveryData> {
        val deliveries = deliveryRepository.fetchDeliveryListFromLocal()

        // update existing deliveries because api does not support multiples requests.
        deliveries.forEach { oldDelivery ->
            val deliveryData = deliveryRepository.fetchDelivery(oldDelivery.code)
            deliveryData.collect { newDelivery ->
                val convertedDelivery = converter.convert(newDelivery)
                if (oldDelivery != convertedDelivery)
                    deliveryRepository.insertNewDelivery(convertedDelivery)
            }
        }

        return deliveryRepository.fetchDeliveryListFromLocal().sortedBy { it.eventList[0].code == DELIVERED_CODE }
    }

    override suspend fun insertNewDelivery(delivery: DeliveryData) {
        deliveryRepository.insertNewDelivery(delivery)
    }

    //#endregion --- fetch data

    private companion object {
        private const val DELIVERED_CODE = "BDE"
    }
}