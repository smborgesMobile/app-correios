package br.com.smdevelopment.rastreamentocorreios.domain.usecase

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface InProgressUseCase {

    suspend fun fetchDelivered(): Flow<List<TrackingModel>>

    suspend fun deleteItem(item: TrackingModel)
}