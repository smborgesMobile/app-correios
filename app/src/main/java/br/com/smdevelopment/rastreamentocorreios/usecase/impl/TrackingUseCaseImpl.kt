package br.com.smdevelopment.rastreamentocorreios.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import kotlinx.coroutines.flow.Flow

class TrackingUseCaseImpl(
    private val linkTrackRepository: LinkTrackRepository
) : TrackingUseCase {

    override fun getTrackingInfo(code: String): Flow<TrackingModel> {
        return linkTrackRepository.fetchTrackByCode(code)
    }
}
