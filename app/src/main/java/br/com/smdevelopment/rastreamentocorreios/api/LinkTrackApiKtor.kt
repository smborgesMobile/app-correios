package br.com.smdevelopment.rastreamentocorreios.api

import br.com.smdevelopment.rastreamentocorreios.BuildConfig
import br.com.smdevelopment.rastreamentocorreios.entities.TrackingResponse

interface LinkTrackApiKtor {

    suspend fun fetchTrackByCode(
        user: String = BuildConfig.USER,
        token: String = BuildConfig.TOKEN,
        code: String
    ): TrackingResponse
}