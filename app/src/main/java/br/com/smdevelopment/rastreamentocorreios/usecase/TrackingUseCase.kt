package br.com.smdevelopment.rastreamentocorreios.usecase

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface TrackingUseCase {
    fun getTrackingInfo(code: String): Flow<TrackingModel>
}
