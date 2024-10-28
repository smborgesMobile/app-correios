package br.com.smdevelopment.rastreamentocorreios.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackingResponse(
    @SerialName("codigo")
    val code: String,
    @SerialName("host")
    val host: String,
    @SerialName("eventos")
    val events: List<Event>,
    @SerialName("time")
    val time: Double,
    @SerialName("quantidade")
    val quantity: Int,
    @SerialName("servico")
    val service: String? = null,
    @SerialName("ultimo")
    val last: String? = null
)

@Serializable
data class Event(
    @SerialName("data")
    val date: String,
    @SerialName("hora")
    val time: String,
    @SerialName("local")
    val location: String,
    @SerialName("status")
    val status: String,
    @SerialName("subStatus")
    val subStatus: List<String>
)