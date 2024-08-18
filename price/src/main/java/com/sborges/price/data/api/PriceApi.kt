package com.sborges.price.data.api

import com.sborges.price.data.entities.PriceResponseItem
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PriceApi {

    @FormUrlEncoded
    @POST("index.php")
    suspend fun getPrices(
        @Field("cepOrigem") originZipCode: String,
        @Field("cepDestino") destinationZipCode: String,
        @Field("peso") weight: Double,
        @Field("altura") height: String,
        @Field("largura") width: String,
        @Field("comprimento") length: String
    ): Response<List<PriceResponseItem>>
}