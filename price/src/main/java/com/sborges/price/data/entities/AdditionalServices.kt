package com.sborges.price.data.entities

import com.google.gson.annotations.SerializedName

data class AdditionalServices(
    @SerializedName("own_hand")
    val ownHand: Boolean,
    @SerializedName("receipt")
    val receipt: Boolean
)