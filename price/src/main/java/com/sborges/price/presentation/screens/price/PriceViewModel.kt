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

    fun onEvent(event: PriceEvent) {
        val updatedState = when (event) {
            is PriceEvent.OnChangeStartCep -> _uiState.value.copy(startCepValue = event.value)
            is PriceEvent.OnChangeEndCep -> _uiState.value.copy(endCepValue = event.value)
            is PriceEvent.OnDeepChange -> _uiState.value.copy(deepValue = event.value)
            is PriceEvent.OnWidthChange -> _uiState.value.copy(widthValue = event.value)
            is PriceEvent.OnHeightChange -> _uiState.value.copy(heightValue = event.value)
            is PriceEvent.OnWeightChange -> _uiState.value.copy(weightValue = event.value)
            is PriceEvent.OnPriceButtonClick -> {
                fetchPrices()
                return
            }
        }
        _uiState.value = updatedState

        checkForEnabledButton()
    }

    private fun checkForEnabledButton() {
        _uiState.value = _uiState.value.copy(
            isButtonEnabled = _uiState.value.run {
                startCepValue.isNotEmpty() &&
                    endCepValue.isNotEmpty() &&
                    weightValue > 0.0 &&
                    heightValue.isNotEmpty() &&
                    widthValue.isNotEmpty() &&
                    deepValue.isNotEmpty()
            },
            error = null
        )
    }

    private fun fetchPrices() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true,
                priceEntity = null,
                error = null
            )
            try {
                val response = getPricesUseCase(
                    originZipCode = _uiState.value.startCepValue,
                    destinationZipCode = _uiState.value.endCepValue,
                    weight = _uiState.value.weightValue,
                    height = _uiState.value.heightValue,
                    width = _uiState.value.widthValue,
                    deep = _uiState.value.deepValue
                )

                if (response != null) {
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                        priceEntity = response,
                        error = response.errorMessage
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    priceEntity = null,
                    error = null
                )
            }
        }
    }
}
