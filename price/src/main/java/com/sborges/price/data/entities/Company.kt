package com.sborges.price.data.entities

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String
)