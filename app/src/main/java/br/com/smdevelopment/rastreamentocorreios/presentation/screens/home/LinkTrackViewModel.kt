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

    private val _errorState = MutableStateFlow(false)
    val errorState: StateFlow<Boolean> get() = _errorState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _deliveryCode = MutableStateFlow(String())
    val deliveryCode: StateFlow<String> get() = _deliveryCode

    val isRefreshing = MutableStateFlow(false)

    init {
        fetchAllLinkTrackItems()
    }

    fun onCodeChange(code: String) {
        _errorState.value = false
        _deliveryCode.value = code
    }

    fun fetchAllLinkTrackItems() {
        viewModelScope.launch(Dispatchers.Default) {
            getAllTrackingUseCase.getTrackingList()
                .collect { result ->
                    _trackingInfo.value = result
                }
        }
    }

    fun findForCode(code: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _errorState.value = false
            _loadingState.value = true
            trackingUseCase.getTrackingInfo(code)
                .catch {
                    _deliveryCode.value = String()
                    _errorState.value = true
                }
                .collect { result ->
                    _trackingInfo.value = result
                    _errorState.value = false
                }

            _loadingState.value = false
        }
    }
}