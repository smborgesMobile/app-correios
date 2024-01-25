package br.com.smdevelopment.rastreamentocorreios.entities.view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackingModel(
    val code: String,
    val host: String,
    val events: List<EventModel>,
    val time: Double,
    val quantity: Int,
    val service: String,
    val last: String
): Parcelable

@Parcelize
data class EventModel(
    val date: String,
    val time: String,
    val location: String,
    val status: String,
    val subStatus: List<String>
): Parcelable
