package br.com.smdevelopment.rastreamentocorreios.domain.usecase

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface GetTrackingListUseCase {

    suspend fun getTrackingList(): Flow<List<TrackingModel>>

    suspend fun getCacheList(): Flow<List<TrackingModel>>
}