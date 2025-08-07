package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.GetAllTrackingUseCase
import com.sborges.core.review.manager.domain.usecase.LaunchCounterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LinkTrackViewModel(
    private val trackingUseCase: TrackingUseCase,
    private val getAllTrackingUseCase: GetAllTrackingUseCase,
    private val appLaunchCounterUseCase: LaunchCounterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LinkTrackUiState())
    val uiState: StateFlow<LinkTrackUiState> get() = _uiState.asStateFlow()

    init {
        onEvent(LinkTrackEvent.FetchAllLinkTrackItems)
    }

    fun onEvent(event: LinkTrackEvent) {
        when (event) {
            is LinkTrackEvent.CodeChanged -> onCodeChange(event.code)
            is LinkTrackEvent.DeleteItem -> deleteItem(event.trackingModel)
            is LinkTrackEvent.FindForCode -> findForCode(event.code)
            is LinkTrackEvent.FetchAllLinkTrackItems -> fetchAllLinkTrackItems()
            is LinkTrackEvent.InAppReviewCheck -> fetchLaunchCounter()
        }
    }

    private fun fetchLaunchCounter() {
        appLaunchCounterUseCase.incrementLaunchCounter()
    }

    private fun onCodeChange(code: String) {
        _uiState.value = _uiState.value.copy(
            errorState = false,
            deliveryCode = code,
            buttonEnabled = code.length == CODE_LENGTH
        )
    }

    private fun fetchAllLinkTrackItems() {
        viewModelScope.launch(Dispatchers.Default) {
            getAllTrackingUseCase.getTrackingList()
                .catch {
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false
                    )
                }
                .collect { result ->
                    _uiState.value = _uiState.value.copy(
                        trackingInfo = result,
                        isRefreshing = false
                    )
                }
        }
    }

    private fun findForCode(code: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = _uiState.value.copy(
                errorState = false,
                loadingState = true
            )
            trackingUseCase.getTrackingInfo(code)
                .catch {
                    Log.d("sm.borges", "Error: $it")
                    _uiState.value = _uiState.value.copy(
                        errorState = true,
                        loadingState = false
                    )
                }
                .collect { result ->
                    onCodeChange(String())
                    _uiState.value = _uiState.value.copy(
                        trackingInfo = result,
                        errorState = false,
                        loadingState = false
                    )
                }
        }
    }

    private fun deleteItem(trackingModel: TrackingModel) {
        viewModelScope.launch {
            val trackingList = _uiState.value.trackingInfo.toMutableList()
            trackingList.remove(trackingModel)
            _uiState.value = _uiState.value.copy(
                trackingInfo = trackingList
            )
            trackingUseCase.deleteTracking(trackingModel)
        }
    }

    private companion object {
        const val CODE_LENGTH = 13
    }
}
