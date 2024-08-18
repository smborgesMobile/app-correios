package com.sborges.price.presentation.screens.price

import com.sborges.price.domain.entity.PriceDomainEntity

data class PriceUIState(
    val loading: Boolean = false,
    val error: Int? = null,
    val priceEntity: PriceDomainEntity? = null,
    val startCepValue: String = "",
    val endCepValue: String = "",
    val weightValue: Double = 0.3,
    val heightValue: String = "",
    val widthValue: String = "",
    val deepValue: String = "",
    val isButtonEnabled: Boolean = false
)