package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.data.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.GetTrackingListUseCase
import kotlinx.coroutines.flow.Flow

class GetAllTrackingUseCase(
    private val repository: LinkTrackRepository
) : GetTrackingListUseCase {

    override suspend fun getTrackingList(): Flow<List<TrackingModel>> {
        return repository.getAllDeliveries()
    }

    override suspend fun getCacheList(): Flow<List<TrackingModel>> {
        return repository.getAllCacheDeliveries()
    }
}