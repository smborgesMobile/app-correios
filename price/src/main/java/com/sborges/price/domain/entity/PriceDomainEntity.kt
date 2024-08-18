package com.sborges.price.domain.entity

data class PriceDomainEntity(
    val type: String = "",
    val price: String = "",
    val deliveryTime: String = "",
    val errorMessage: Int? = null
)