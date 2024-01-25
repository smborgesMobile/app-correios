package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusiness
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val business: DeliveryBusiness) : ViewModel() {

    private val _state = MutableStateFlow<Resource<DeliveryData>>(Resource.Initial())
    val state: StateFlow<Resource<DeliveryData>>
        get() = _state

    private val _deliveryState = MutableStateFlow<Resource<List<DeliveryData>>>(Resource.Initial())
    val deliveryState: StateFlow<Resource<List<DeliveryData>>>
        get() = _deliveryState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _showPermission = MutableStateFlow(false)
    val showPermission = _showPermission.asStateFlow()

    init {
        _showPermission.value = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    var resource: Resource<DeliveryData> = Resource.Initial()
        set(value) {
            //Resets view model state when user start typing
            field = value
            _state.value = resource
        }

    //#region --- view model init

    init {
        fetchAllDeliveries()
    }

    //#endregion --- view model init

    //#region --- fetchDelivery

    fun fetchDelivery(code: String) {
        _state.value = Resource.Loading()
        viewModelScope.launch((Dispatchers.IO)) {
            try {
                business.fetchDelivery(code = code).collect { delivery ->
                    _state.value = Resource.Success(delivery)
                }
                fetchAllDeliveries()
            } catch (exception: Exception) {
                _state.value = Resource.Error(exception.message.toString())
            }
        }
    }

    //#endregion --- fetchDelivery

    //#region --- fetchAllDeliveries

    private fun fetchAllDeliveries() {
        _deliveryState.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                business.getAllDeliveries().collect {
                    _deliveryState.value = Resource.Success(it)
                }
            } catch (ex: Exception) {
                _deliveryState.value = Resource.Error(ex.message.orEmpty())
            }
        }
    }

    //#endregion --- fetchAllDeliveries

    //#region --- refresh

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.update { true }
            fetchAllDeliveries()
            _isRefreshing.update { false }
        }
    }

    //#endregion --- refresh
}