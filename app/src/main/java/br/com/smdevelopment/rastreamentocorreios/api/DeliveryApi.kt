package br.com.smdevelopment.rastreamentocorreios.api

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DeliveryApi {

    @GET("v1/sro-rastro/{code}")
    suspend fun fetchDelivery(@Path("code") code: String): Response<DeliveryResponse>
}