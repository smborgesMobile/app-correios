package br.com.smdevelopment.rastreamentocorreios.entities.retrofit

import com.google.gson.annotations.SerializedName

data class DeliveryResponse(
    @SerializedName("data")
    val delivery: Delivery
)

data class Delivery(
    @SerializedName("codObjeto")
    val objectCode: String? = null,
    @SerializedName("eventos")
    val eventList: List<Event> = emptyList(),
    @SerializedName("modalidade")
    val type: String? = null,
)

data class Event(
    @SerializedName("codigo")
    val code: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("dtHrCriado")
    val date: String,
    @SerializedName("unidade")
    val postLocation: Location,
    @SerializedName("unidadeDestino")
    val destinationLocation: Location,
    @SerializedName("urlIcone")
    val iconUrl: String
)

data class Location(
    @SerializedName("endereco")
    val address: Address
)

data class Address(
    @SerializedName("cidade")
    val city: String,
    @SerializedName("uf")
    val uf: String
) {
    fun buildLocation() =
        "$city - $uf"
}