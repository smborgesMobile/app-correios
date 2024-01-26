package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.GetAllTrackingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LinkTrackViewModel(
    private val trackingUseCase: TrackingUseCase,
    private val getAllTrackingUseCase: GetAllTrackingUseCase
) : ViewModel() {

    private val _trackingInfo = MutableStateFlow<List<TrackingModel>>(emptyList())
    val trackingInfo: StateFlow<List<TrackingModel>> get() = _trackingInfo

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    init {
        viewModelScope.launch(Dispatchers.Default) {
            getAllTrackingUseCase.getTrackingList()
                .catch { error ->
                    _errorState.value = error.message
                }
                .collect { result ->
                    _trackingInfo.value = result
                }
        }
    }

    fun findForCode(code: String) {
        viewModelScope.launch(Dispatchers.Default) {
            trackingUseCase.getTrackingInfo(code)
                .catch { error ->
                    _errorState.value = error.message
                }
                .collect { result ->
                    _trackingInfo.value = result
                }
        }
    }
}