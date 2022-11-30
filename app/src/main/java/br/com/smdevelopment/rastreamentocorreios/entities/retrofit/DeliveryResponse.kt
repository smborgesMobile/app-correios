package br.com.smdevelopment.rastreamentocorreios.entities.retrofit

import com.google.gson.annotations.SerializedName

data class DeliveryResponse(
    @SerializedName("status")
    val status: String
)