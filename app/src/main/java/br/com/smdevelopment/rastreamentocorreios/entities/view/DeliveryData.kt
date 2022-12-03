package br.com.smdevelopment.rastreamentocorreios.entities.view

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Event

data class DeliveryData(
    val code: String,
    val eventList: List<Event>,
    val type: String? = null,
    val description: String,
    val destination: String,
    val imageRes: Int
)