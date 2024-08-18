package com.sborges.price.data.entities

import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("dimensions")
    val dimensions: Dimensions,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("insurance_value")
    val insuranceValue: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("weight")
    val weight: String
)