package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel

data class DeliveredUiState(
    val deliveredList: List<TrackingModel> = emptyList(),
    val emptyState: Boolean = false,
    val isLoading: Boolean = false
)
