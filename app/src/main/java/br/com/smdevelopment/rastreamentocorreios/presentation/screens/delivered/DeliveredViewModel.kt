package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.usecase.DeliveredUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DeliveredViewModel(
    private val deliveredUseCase: DeliveredUseCase
) : ViewModel() {

    // UI state holding all relevant properties
    private val _uiState = MutableStateFlow(DeliveredUiState())
    val uiState: StateFlow<DeliveredUiState> get() = _uiState

    fun onEvent(event: DeliveredEvent) {
        when (event) {
            is DeliveredEvent.OnDeleteClick -> {
                deleteDelivery(event)
            }
            is DeliveredEvent.FetchNewItems -> {
                getDeliveredList()
            }
        }
    }

    private fun getDeliveredList() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            deliveredUseCase.fetchDelivered()
                .catch {
                    _uiState.value = _uiState.value.copy(
                        emptyState = true,
                        isLoading = false
                    )
                }
                .collect { result ->
                    _uiState.value = _uiState.value.copy(
                        deliveredList = result,
                        emptyState = result.isEmpty(),
                        isLoading = false
                    )
                }
        }
    }

    private fun deleteDelivery(event: DeliveredEvent.OnDeleteClick) {
        viewModelScope.launch {
            val deliveryList = _uiState.value.deliveredList.toMutableList()
            deliveryList.remove(event.item)

            // Update UI state
            _uiState.value = _uiState.value.copy(
                deliveredList = deliveryList,
                emptyState = deliveryList.isEmpty()
            )

            // Perform delete operation
            deliveredUseCase.deleteDelivery(event.item)
        }
    }
}
