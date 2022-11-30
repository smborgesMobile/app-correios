package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import androidx.lifecycle.ViewModel
import br.com.smdevelopment.rastreamentocorreios.presentation.business.DeliveryBusiness
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DeliveryBusiness) : ViewModel() {

}