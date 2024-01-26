package br.com.smdevelopment.rastreamentocorreios.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.usecase.GetTrackingListUseCase
import kotlinx.coroutines.flow.Flow

class GetAllTrackingUseCase(
    private val repository: LinkTrackRepository
) : GetTrackingListUseCase {
    override suspend fun getTrackingList(): Flow<List<TrackingModel>> {
        return repository.getAllDeliveries()
    }
}