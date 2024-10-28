package com.sborges.price.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalServices(
    @SerialName("own_hand")
    val ownHand: Boolean,

    @SerialName("receipt")
    val receipt: Boolean
)