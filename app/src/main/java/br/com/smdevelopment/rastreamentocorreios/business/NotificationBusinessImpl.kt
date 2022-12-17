package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationBusinessImpl @Inject constructor(private val repository: DeliveryRepository) : NotificationBusiness {

    override suspend fun checkForUpdate(): Flow<DeliveryData> = flow {
        val deliveries = repository.fetchDeliveryListFromLocal()
        repository.fetchDelivery(deliveries.map { it.code }).collect { newList ->
            onReceiveNewDelivery(newList, deliveries)
        }
    }

    private suspend fun FlowCollector<DeliveryData>.onReceiveNewDelivery(newList: List<DeliveryData>, deliveries: List<DeliveryData>) {
        val difference: List<DeliveryData> = newList.minus(deliveries.toSet())
        difference.forEach { delivery -> repository.insertNewDelivery(delivery) }
        val firstDelivery = difference.firstOrNull()
        firstDelivery?.let {
            emit(difference.first())
        }
    }

}