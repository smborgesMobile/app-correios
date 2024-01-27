package br.com.smdevelopment.rastreamentocorreios.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.usecase.InProgressUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InProgressUseCaseImpl(private val repository: LinkTrackRepository) : InProgressUseCase {
    override suspend fun fetchDelivered(): Flow<List<TrackingModel>> {
        return repository.getAllDeliveries().map {
            it.filter { it.isDelivered.not() }
        }
    }
}