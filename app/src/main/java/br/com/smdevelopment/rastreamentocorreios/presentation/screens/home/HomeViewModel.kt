package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusiness
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Resource
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val business: DeliveryBusiness) : ViewModel() {

    private val _state = MutableStateFlow<Resource<DeliveryData>>(Resource.Initial())
    val state: StateFlow<Resource<DeliveryData>>
        get() = _state

    fun fetchDelivery(code: String) {
        _state.value = Resource.Loading()
        viewModelScope.launch {
            try {
                business.fetchDelivery(code = code).collect { delivery ->
                    _state.value = Resource.Success(delivery)
                }
            } catch (exception: Exception) {
                _state.value = Resource.Error(exception.message.toString())
            }
        }
    }
}