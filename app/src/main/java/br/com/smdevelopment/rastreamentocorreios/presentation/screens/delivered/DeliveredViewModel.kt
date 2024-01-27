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

    fun getDeliveredList() {
        viewModelScope.launch(Dispatchers.Default) {
            deliveredUseCase.fetchDelivered()
                .catch {
                    _deliveredList.value = emptyList()
                }
                .collect {
                    _deliveredList.value = it
                }
        }
    }
}