package br.com.smdevelopment.rastreamentocorreios.usecase

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface GetTrackingListUseCase {
    suspend fun getTrackingList(): Flow<List<TrackingModel>>
}