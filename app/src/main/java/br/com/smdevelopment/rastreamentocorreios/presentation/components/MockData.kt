package br.com.smdevelopment.rastreamentocorreios.presentation.components

import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventModel
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel

object MockData {

    fun getMockTrackingModel(): TrackingModel {
        return TrackingModel(
            code = "AB123456789CD",
            userId = "user_123",
            host = "https://correios.com.br",
            events = getMockEventList(),
            time = System.currentTimeMillis().toDouble(),
            quantity = 3,
            service = "SEDEX",
            last = "Delivered",
            icon = R.drawable.delivered_start_icon,
            isDelivered = true
        )
    }

    private fun getMockEventList(): List<EventModel> {
        return listOf(
            EventModel(
                date = "2024-08-01",
                time = "10:30 AM",
                location = "São Paulo, SP",
                status = "Delivered",
                subStatus = listOf("Left at the recipient's address"),
                icon = 0x1F4E6, // Example Unicode for a package icon 📦
                isDelivered = true
            ),
            EventModel(
                date = "2024-07-31",
                time = "02:00 PM",
                location = "Rio de Janeiro, RJ",
                status = "Out for Delivery",
                subStatus = listOf("In transit to the recipient's address"),
                icon = 0x1F69A, // Example Unicode for a delivery truck 🚚
                isDelivered = false
            ),
            EventModel(
                date = "2024-07-30",
                time = "09:15 AM",
                location = "Curitiba, PR",
                status = "In Transit",
                subStatus = listOf("Processed at facility", "Left origin facility"),
                icon = 0x1F6A2, // Example Unicode for a ship 🚢
                isDelivered = false
            )
        )
    }

    fun getMockTrackingList(): List<TrackingModel> {
        return listOf(
            getMockTrackingModel(),
            getMockTrackingModel().copy(
                code = "EF987654321GH",
                userId = "user_456",
                service = "PAC",
                isDelivered = false
            )
        )
    }
}
