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

    fun getDeliveredList() {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.fetchDelivered()
                .catch {
                    _inProgressList.value = emptyList()
                }
                .collect {
                    _inProgressList.value = it
                }
        }
    }
}