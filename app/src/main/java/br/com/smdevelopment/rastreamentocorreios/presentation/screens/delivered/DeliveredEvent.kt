package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel

sealed class DeliveredEvent {
    data class OnDeleteClick(val item: TrackingModel) : DeliveredEvent()
    data object FetchNewItems : DeliveredEvent()
}
