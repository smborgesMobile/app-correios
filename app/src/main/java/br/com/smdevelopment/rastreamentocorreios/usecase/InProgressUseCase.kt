package br.com.smdevelopment.rastreamentocorreios.usecase

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface InProgressUseCase {
    suspend fun fetchDelivered(): Flow<List<TrackingModel>>

    suspend fun deleteItem(item: TrackingModel)
}