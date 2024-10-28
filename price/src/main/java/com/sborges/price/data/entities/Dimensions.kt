package com.sborges.price.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dimensions(
    @SerialName("height")
    val height: String,

    @SerialName("length")
    val length: String,

    @SerialName("width")
    val width: String
)