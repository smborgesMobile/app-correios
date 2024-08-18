package com.sborges.price.presentation.screens.price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sborges.price.domain.useCase.GetPriceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PriceViewModel(
    private val getPricesUseCase: GetPriceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PriceUIState())
    val uiState: StateFlow<PriceUIState> get() = _uiState

    fun getPrices(
        originZipCode: String,
        destinationZipCode: String,
        weight: String,
        height: String,
        width: String,
        length: String
    ) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                loading = false,
                priceEntity = null,
                error = false
            )
            val response = getPricesUseCase(
                originZipCode,
                destinationZipCode,
                weight,
                height,
                width,
                length
            )

            if (response != null) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    priceEntity = response,
                    error = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    priceEntity = null,
                    error = true
                )
            }
        }
    }

}