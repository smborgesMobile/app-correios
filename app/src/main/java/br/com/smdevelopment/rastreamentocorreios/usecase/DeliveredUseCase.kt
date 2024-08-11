package br.com.smdevelopment.rastreamentocorreios.usecase

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface DeliveredUseCase {

    suspend fun fetchDelivered(): Flow<List<TrackingModel>>

    suspend fun deleteDelivery(model: TrackingModel)
}