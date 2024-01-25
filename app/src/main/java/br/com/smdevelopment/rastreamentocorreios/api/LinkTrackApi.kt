package br.com.smdevelopment.rastreamentocorreios.api

import br.com.smdevelopment.rastreamentocorreios.BuildConfig
import br.com.smdevelopment.rastreamentocorreios.entities.TrackingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LinkTrackApi {

    @GET("track/json")
    suspend fun fetchTrackByCode(
        @Query("user") user: String = BuildConfig.USER,
        @Query("token") token: String = BuildConfig.TOKEN,
        @Query("codigo") code: String
    ): Response<TrackingResponse>
}
