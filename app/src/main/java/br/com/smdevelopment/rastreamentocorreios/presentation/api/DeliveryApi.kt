package br.com.smdevelopment.rastreamentocorreios.presentation.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DeliveryApi {

    @GET("v1/{code}")
    suspend fun fetchDelivery(@Path("code") code: String)
}