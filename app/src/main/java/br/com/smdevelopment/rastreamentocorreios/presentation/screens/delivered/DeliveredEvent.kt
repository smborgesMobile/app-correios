package br.com.smdevelopment.rastreamentocorreios.presentation.screens.delivered

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel

sealed class DeliveredEvent {
    data class OnDeleteClick(val item: TrackingModel) : DeliveredEvent()
}
