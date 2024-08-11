package br.com.smdevelopment.rastreamentocorreios.usecase

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.presentation.screens.home.LinkTrackViewModel
import kotlinx.coroutines.flow.Flow

interface InProgressUseCase {
    suspend fun fetchDelivered(): Flow<List<TrackingModel>>

    suspend fun deleteItem(item: TrackingModel)
}