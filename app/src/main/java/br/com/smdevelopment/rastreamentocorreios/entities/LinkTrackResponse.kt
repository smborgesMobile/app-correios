package br.com.smdevelopment.rastreamentocorreios.entities

import com.google.gson.annotations.SerializedName

data class TrackingResponse(
    @SerializedName("codigo")
    val code: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("eventos")
    val events: List<Event>,
    @SerializedName("time")
    val time: Double,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("service")
    val service: String? = null,
    @SerializedName("last")
    val last: String?= null
)

data class Event(
    @SerializedName("data")
    val date: String,
    @SerializedName("hora")
    val time: String,
    @SerializedName("local")
    val location: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subStatus")
    val subStatus: List<String>
)