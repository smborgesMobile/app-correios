package com.sborges.price.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val picture: String
)

