package com.sborges.price.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryRange(
    @SerialName("max")
    val max: Int,

    @SerialName("min")
    val min: Int
)