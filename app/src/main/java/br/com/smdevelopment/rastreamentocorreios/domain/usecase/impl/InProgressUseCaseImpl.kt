package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.InProgressUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InProgressUseCaseImpl(private val repository: LinkTrackRepository) : InProgressUseCase {
    override suspend fun fetchDelivered(): Flow<List<TrackingModel>> {
        return repository.getAllCacheDeliveries().map { it ->
            it.filter { it.isDelivered.not() }
        }
    }

    override suspend fun deleteItem(item: TrackingModel) {
        repository.deleteDelivery(item)
    }
}