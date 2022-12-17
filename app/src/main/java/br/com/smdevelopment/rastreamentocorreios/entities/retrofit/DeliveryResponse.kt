package br.com.smdevelopment.rastreamentocorreios.entities.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DeliveryResponse(
    @SerializedName("data")
    val deliveryList: List<Delivery>
)

data class Delivery(
    @SerializedName("codObjeto")
    val objectCode: String? = null,
    @SerializedName("eventos")
    val eventList: List<Event> = emptyList(),
    @SerializedName("modalidade")
    val type: String? = null,
)

@Parcelize
data class Event(
    @SerializedName("codigo")
    val code: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("dtHrCriado")
    val date: String,
    @SerializedName("unidade")
    val postLocation: Location? = null,
    @SerializedName("unidadeDestino")
    val destinationLocation: Location? = null,
    @SerializedName("urlIcone")
    val iconUrl: String
) : Parcelable

@Parcelize
data class Location(
    @SerializedName("endereco")
    val address: Address
) : Parcelable

@Parcelize
data class Address(
    @SerializedName("cidade")
    val city: String,
    @SerializedName("uf")
    val uf: String
) : Parcelable {
    fun buildLocation() =
        "$city - $uf"
}