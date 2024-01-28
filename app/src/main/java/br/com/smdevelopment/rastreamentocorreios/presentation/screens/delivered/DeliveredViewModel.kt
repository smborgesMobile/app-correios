package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.usecase.DeliveredUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DeliveredViewModel(
    private val deliveredUseCase: DeliveredUseCase
) : ViewModel() {

    private val _deliveredList = MutableStateFlow<List<TrackingModel>>(emptyList())
    val deliveredList: StateFlow<List<TrackingModel>> get() = _deliveredList

    private val _emptyState = MutableStateFlow(false)
    val emptyState: StateFlow<Boolean> get() = _emptyState

    fun getDeliveredList() {
        viewModelScope.launch(Dispatchers.Default) {
            deliveredUseCase.fetchDelivered()
                .catch {
                    _emptyState.value = true
                }
                .collect {
                    _deliveredList.value = it
                    _emptyState.value = it.isEmpty()
                }
        }
    }
}