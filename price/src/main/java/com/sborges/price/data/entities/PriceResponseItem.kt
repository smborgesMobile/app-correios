package com.sborges.price.data.entities

import com.google.gson.annotations.SerializedName

data class PriceResponseItem(
    @SerializedName("additional_services")
    val additionalServices: AdditionalServices,
    @SerializedName("company")
    val company: Company,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("delivery_range")
    val deliveryRange: DeliveryRange,
    @SerializedName("delivery_time")
    val deliveryTime: Int,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("has_error")
    val hasError: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("packages")
    val packages: List<Package>,
    @SerializedName("price")
    val price: Double
)