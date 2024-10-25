package br.com.smdevelopment.rastreamentocorreios.mappers

import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.TrackingResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventModel
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import com.google.firebase.auth.FirebaseAuth

class LinkTrackDomainMapper(
    private val firebaseAuth: FirebaseAuth
) {

    fun mapToTrackModel(trackingResponse: TrackingResponse): TrackingModel {
        trackingResponse.run {
            return TrackingModel(
                userId = firebaseAuth.currentUser?.uid.orEmpty(),
                code = this.code,
                host = this.host,
                last = this.last.orEmpty(),
                quantity = this.quantity,
                service = this.service.orEmpty(),
                time = this.time,
                events = this.events.takeIf { it.isNotEmpty() }?.mapIndexed { index, event ->
                    EventModel(
                        date = "${event.date} - ${event.time}",
                        time = event.time,
                        location = event.location,
                        status = event.status.ifBlank { FAILED_TO_LOCATE },
                        subStatus = event.subStatus,
                        icon = when {
                            event.status == OBJECT_DONE -> {
                                R.drawable.delivered_icon
                            }

                            index == events.size - 1 -> {
                                R.drawable.package_delivery
                            }

                            else -> {
                                R.drawable.delivered_start_icon
                            }
                        },
                        isDelivered = event.status == OBJECT_DONE
                    )
                } ?: emptyList(),
                icon = when (this.events.firstOrNull()?.status) {
                    OBJECT_DONE -> {
                        R.drawable.delivered_icon
                    }

                    else -> {
                        R.drawable.delivered_start_icon
                    }
                },
                isDelivered = this.events.firstOrNull()?.status == OBJECT_DONE
            )
        }
    }

    private companion object {
        const val OBJECT_DONE = "Objeto entregue ao destinatário"
        const val FAILED_TO_LOCATE = "Objeto encaminhado"
    }
}