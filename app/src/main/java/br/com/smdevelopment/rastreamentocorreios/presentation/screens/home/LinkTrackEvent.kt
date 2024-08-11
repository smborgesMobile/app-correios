package br.com.smdevelopment.rastreamentocorreios.presentation.screens.home

import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel

sealed class LinkTrackEvent {
    data class CodeChanged(val code: String) : LinkTrackEvent()
    data class DeleteItem(val trackingModel: TrackingModel) : LinkTrackEvent()
    data class FindForCode(val code: String) : LinkTrackEvent()
    object FetchAllLinkTrackItems : LinkTrackEvent()
}