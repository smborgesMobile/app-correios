package com.sborges.price.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Package(
    @SerialName("dimensions")
    val dimensions: Dimensions,

    @SerialName("discount")
    val discount: String,

    @SerialName("format")
    val format: String,

    @SerialName("insurance_value")
    val insuranceValue: Double,

    @SerialName("price")
    val price: Double,

    @SerialName("weight")
    val weight: String
)