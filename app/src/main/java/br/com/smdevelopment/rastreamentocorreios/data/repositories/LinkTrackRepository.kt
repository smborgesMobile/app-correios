package br.com.smdevelopment.rastreamentocorreios.data.repositories

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface LinkTrackRepository {

    suspend fun fetchTrackByCode(code: String): Flow<List<TrackingModel>>

    suspend fun getAllDeliveries(): Flow<List<TrackingModel>>

    suspend fun getAllCacheDeliveries(): Flow<List<TrackingModel>>

    suspend fun updateCache()

    suspend fun deleteDelivery(delivered: TrackingModel)
}