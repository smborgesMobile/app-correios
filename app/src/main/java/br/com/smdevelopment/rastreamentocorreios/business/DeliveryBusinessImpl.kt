package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveredType
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeliveryBusinessImpl @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : DeliveryBusiness {

    //#region --- get data

    override suspend fun fetchDelivery(code: String): Flow<List<DeliveryData>> {
        val deliveryCodes = deliveryRepository.fetchDeliveryListFromLocal().map {
            it.code
        }.toMutableList()
        deliveryCodes.add(code)

        val responseFlow = deliveryRepository.fetchDelivery(deliveryCodes)
        responseFlow.collect { delivery ->
            insertNewDelivery(delivery)
        }

        return responseFlow
    }

    override suspend fun getAllDeliveries(): Flow<List<DeliveryData>> = flow {
        val deliveries = deliveryRepository.fetchDeliveryListFromLocal()
            .sortedBy { it.deliveredType == DeliveredType.DELIVERED }
        emit(deliveries)

        // update existing deliveries because api does not support multiples requests.
        val mapCodeList: List<String> = deliveries.map {
            it.code
        }

        // update dependencies
        val response = deliveryRepository.fetchDelivery(mapCodeList)
        response.collect { delivery ->
            insertNewDelivery(delivery)
        }

        val updateList = deliveryRepository.fetchDeliveryListFromLocal()
            .sortedBy { it.deliveredType == DeliveredType.DELIVERED }

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

    override suspend fun insertNewDelivery(delivery: List<DeliveryData>) {
        delivery.forEach {
            deliveryRepository.insertNewDelivery(it)
        }
    }

    //#endregion --- insert data
}