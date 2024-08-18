package com.sborges.price.data.entities

import com.google.gson.annotations.SerializedName

data class DeliveryRange(
    @SerializedName("max")
    val max: Int,
    @SerializedName("min")
    val min: Int
)