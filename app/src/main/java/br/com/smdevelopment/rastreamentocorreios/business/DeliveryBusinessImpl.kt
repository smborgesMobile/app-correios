package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveredType
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class DeliveryBusinessImpl @Inject constructor(
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
        val deliveries = deliveryRepository.fetchDeliveryListFromLocal().sortedBy { it.deliveredType == DeliveredType.DELIVERED }
        emit(deliveries)

        // update existing deliveries because api does not support multiples requests.
        deliveries.forEach { oldDelivery ->
            val deliveryData = deliveryRepository.fetchDelivery(oldDelivery.code)
            deliveryData.collect { newDelivery ->
                val convertedDelivery = converter.convert(newDelivery)
                if (oldDelivery != convertedDelivery)
                    deliveryRepository.insertNewDelivery(convertedDelivery)
            }
        }

        val updateList = deliveryRepository.fetchDeliveryListFromLocal().sortedBy { it.deliveredType == DeliveredType.DELIVERED }
        if (updateList.isNotEmpty()) emit(updateList)
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