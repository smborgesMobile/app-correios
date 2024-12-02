package com.sborges.price.data.entities

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PriceResponseItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("discount")
    val discount: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("delivery_time")
    val deliveryTime: Int,

    @SerializedName("delivery_range")
    val deliveryRange: DeliveryRange,

    @SerializedName("packages")
    val packages: List<Package>,

    @SerializedName("company")
    val company: Company,

    @SerializedName("additional_services")
    val additionalServices: AdditionalServices,

    @SerializedName("has_error")
    val hasError: Boolean
)