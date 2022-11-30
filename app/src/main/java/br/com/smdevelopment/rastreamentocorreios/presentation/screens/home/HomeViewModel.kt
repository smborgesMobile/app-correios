package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smdevelopment.rastreamentocorreios.presentation.business.DeliveryBusiness
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val business: DeliveryBusiness) : ViewModel() {

    fun fetchDelivery(code: String) {
        viewModelScope.launch {
            try {
                business.fetchDelivery(code = code).collect { delivery ->
                    Log.d("sm.borges", "sucesso: ${delivery}")
                }
            } catch (exception: Exception) {
                Log.d("sm.borges", "Exception: ${code}")
            }
        }
    }
}