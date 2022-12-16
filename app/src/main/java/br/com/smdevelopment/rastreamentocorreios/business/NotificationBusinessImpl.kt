package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationBusinessImpl @Inject constructor(
    private val repository: DeliveryRepository,
    private val converter: DeliveryConverter
) : NotificationBusiness {

    override suspend fun checkForUpdate(): Flow<DeliveryData> = flow {
        val deliveries = repository.fetchDeliveryListFromLocal()
        repository.fetchDelivery(deliveries.map { it.code }).collect {
            val convertedList = converter.convert(it)
            val difference: List<DeliveryData> = convertedList.minus(deliveries.toSet())
            difference.forEach { delivery -> repository.insertNewDelivery(delivery) }
            val firstDelivery = difference.firstOrNull()
            firstDelivery?.let {
                emit(difference.first())
            }
        }
    }

}