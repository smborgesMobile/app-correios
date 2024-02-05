package br.com.smdevelopment.rastreamentocorreios.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
    @SerializedName("servico")
    val service: String? = null,
    @SerializedName("ultimo")
    val last: String? = null
) : Parcelable

@Parcelize
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
) : Parcelable