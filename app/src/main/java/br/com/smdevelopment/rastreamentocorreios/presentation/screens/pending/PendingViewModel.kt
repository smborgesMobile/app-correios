package br.com.smdevelopment.rastreamentocorreios.presentation.screens.pending

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.business.DeliveryBusiness
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PendingViewModel(private val business: DeliveryBusiness) : ViewModel() {

    private val _deliveredList = mutableStateListOf<DeliveryData>()
    val deliveredList: List<DeliveryData>
        get() = _deliveredList

    fun getPendingDeliveries() {
        viewModelScope.launch(Dispatchers.IO) {
            val businessFlow = business.getPendingList()
            businessFlow.catch {
                _deliveredList.addAll(emptyList())
            }.collect {
                _deliveredList.clear()
                _deliveredList.addAll(it)
            }
        }
    }
}