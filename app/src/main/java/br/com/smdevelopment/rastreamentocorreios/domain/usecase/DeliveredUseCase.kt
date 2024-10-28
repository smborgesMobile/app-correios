package br.com.smdevelopment.rastreamentocorreios.domain.usecase

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface DeliveredUseCase {

    suspend fun fetchDelivered(): Flow<List<TrackingModel>>

    suspend fun deleteDelivery(model: TrackingModel)
}