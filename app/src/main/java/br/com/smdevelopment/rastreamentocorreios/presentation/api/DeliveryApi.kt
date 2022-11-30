package br.com.smdevelopment.rastreamentocorreios.presentation.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DeliveryApi {

    @GET("sro-rastro/{code}")
    suspend fun fetchDelivery(@Path("code") code: String)
}