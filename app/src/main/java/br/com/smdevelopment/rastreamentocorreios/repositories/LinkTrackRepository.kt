package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import kotlinx.coroutines.flow.Flow

interface LinkTrackRepository {

    fun fetchTrackByCode(code: String): Flow<TrackingModel>
}