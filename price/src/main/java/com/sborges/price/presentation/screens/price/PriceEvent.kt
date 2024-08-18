package com.sborges.price.presentation.screens.price

sealed class PriceEvent {
    data class OnChangeStartCep(val value: String) : PriceEvent()
    data class OnChangeEndCep(val value: String) : PriceEvent()
    data class OnHeightChange(val value: String) : PriceEvent()
    data class OnWidthChange(val value: String) : PriceEvent()
    data class OnLengthChange(val value: String) : PriceEvent()
    data class OnDeepChange(val value: String) : PriceEvent()
    data class OnWeightChange(val value: Double) : PriceEvent()
    data object OnPriceButtonClick : PriceEvent()
}
