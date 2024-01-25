package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.converters.DeliveryConverter
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationBusinessImpl(
    private val repository: DeliveryRepository,
    private val converter: DeliveryConverter
) : NotificationBusiness {

    override suspend fun checkForUpdate(): Flow<DeliveryData> = flow {
        val repositories = repository.fetchDeliveryListFromLocal()

        repositories.forEach { delivery ->
            val fetchFlow = repository.fetchDelivery(delivery.code)
            fetchFlow.collect {
                val convertData = converter.convert(it)
                if (convertData != delivery) {
                    repository.insertNewDelivery(convertData)
                    emit(convertData)
                }
            }
        }
    }

}