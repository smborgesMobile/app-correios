package com.sborges.price.presentation.screens.price

import com.sborges.price.domain.entity.PriceDomainEntity

data class PriceUIState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val priceEntity: PriceDomainEntity? = null
)