package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface LinkTrackRepository {

    suspend fun fetchTrackByCode(code: String): Flow<List<TrackingModel>>

    suspend fun getAllDeliveries(): Flow<List<TrackingModel>>

    suspend fun updateCache()
}