package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveredType
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull

class DeliveryBusinessImpl(
    private val deliveryRepository: DeliveryRepository,
    private val converter: DeliveryConverter
) : DeliveryBusiness {

    //#region --- get data

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

    override suspend fun getAllDeliveries(): Flow<List<DeliveryData>> = flow {
        val deliveries =
            deliveryRepository.fetchDeliveryListFromLocal().sortedBy { it.deliveredType == DeliveredType.DELIVERED }.toMutableList()

        // Notify Screen to change the list
        emit(deliveries)

        // update existing deliveries because api does not support multiples requests.
        deliveries.forEachIndexed { index, oldDelivery ->
            val deliveryData = deliveryRepository.fetchDelivery(oldDelivery.code)
            deliveryData.collect { newDelivery ->
                val convertedDelivery = converter.convert(newDelivery)
                if (oldDelivery != convertedDelivery) {
                    deliveries[index] = convertedDelivery
                    deliveryRepository.insertNewDelivery(convertedDelivery)
                }
            }
        }

        // Notify screen again without updated data
        emit(deliveries)
    }

    override suspend fun getDeliveredList(): Flow<List<DeliveryData>> = flow {
        val localList = deliveryRepository.fetchDeliveryListFromLocal()
        emit(localList.filter { it.deliveredType == DeliveredType.DELIVERED })
    }

    override suspend fun getPendingList(): Flow<List<DeliveryData>> = flow {
        val localList = deliveryRepository.fetchDeliveryListFromLocal()
        emit(localList.filter { it.deliveredType == DeliveredType.IN_PROGRESS })
    }

    //#endregion --- get data

    //#region --- insert data

    override suspend fun insertNewDelivery(delivery: DeliveryData) {
        deliveryRepository.insertNewDelivery(delivery)
    }

    //#endregion --- insert data
}