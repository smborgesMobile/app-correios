package com.sborges.price.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceResponseItem(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("price")
    val price: Double,

    @SerialName("discount")
    val discount: String,

    @SerialName("currency")
    val currency: String,

    @SerialName("delivery_time")
    val deliveryTime: Int,

    @SerialName("delivery_range")
    val deliveryRange: DeliveryRange,

    @SerialName("packages")
    val packages: List<Package>,

    @SerialName("company")
    val company: Company,

    @SerialName("additional_services")
    val additionalServices: AdditionalServices,

    @SerialName("has_error")
    val hasError: Boolean
)