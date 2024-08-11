package br.com.smdevelopment.rastreamentocorreios.presentation.screens.pending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.usecase.InProgressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PendingScreenViewModel(private val useCase: InProgressUseCase) : ViewModel() {

    private val _inProgressList = MutableStateFlow<List<TrackingModel>>(emptyList())
    val inProgressList: StateFlow<List<TrackingModel>> get() = _inProgressList

    private val _emptyState = MutableStateFlow(false)
    val emptyState: StateFlow<Boolean> get() = _emptyState

    fun getDeliveredList() {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.fetchDelivered()
                .catch {
                    _emptyState.value = true
                }
                .collect {
                    _inProgressList.value = it
                    _emptyState.value = it.isEmpty()
                }
        }
    }

    fun deleteItem(item: TrackingModel) {
        val newItemList = _inProgressList.value.toMutableList()
        newItemList.remove(item)

        viewModelScope.launch {
            _inProgressList.value = newItemList
            useCase.deleteItem(item)
        }
    }
}