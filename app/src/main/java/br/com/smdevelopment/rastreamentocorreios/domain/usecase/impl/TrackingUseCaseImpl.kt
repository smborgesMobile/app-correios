package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.TrackingUseCase
import kotlinx.coroutines.flow.Flow

class TrackingUseCaseImpl(
    private val linkTrackRepository: LinkTrackRepository
) : TrackingUseCase {

    override suspend fun getTrackingInfo(code: String): Flow<List<TrackingModel>> {
        return linkTrackRepository.fetchTrackByCode(code)
    }

    override suspend fun deleteTracking(trackingModel: TrackingModel) {
        linkTrackRepository.deleteDelivery(trackingModel)
    }
}
