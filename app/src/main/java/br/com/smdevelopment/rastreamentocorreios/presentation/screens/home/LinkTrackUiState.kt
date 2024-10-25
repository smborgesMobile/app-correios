package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel

data class LinkTrackUiState(
    val trackingInfo: List<TrackingModel> = emptyList(),
    val errorState: Boolean = false,
    val loadingState: Boolean = false,
    val deliveryCode: String = "",
    val buttonEnabled: Boolean = false,
    val isRefreshing: Boolean = false,
    val deliveryName: String = "",
    val displayNameBottomSheet: Boolean = false
)