package br.com.smdevelopment.rastreamentocorreios.converters

import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Address
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Event
import br.com.smdevelopment.rastreamentocorreios.entities.view.AddressData
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventData
import br.com.smdevelopment.rastreamentocorreios.entities.view.LocationData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DeliveryConverter {

    fun convert(deliveryResponse: DeliveryResponse): DeliveryData {
        return deliveryResponse.toDeliveryData()
    }

    //#region --- helpers

    private fun DeliveryResponse.toDeliveryData() = DeliveryData(
        code = delivery.objectCode.orEmpty(),
        eventList = delivery.eventList.toDeliveryDataList(),
        type = delivery.type,
        description = delivery.eventList.firstOrNull()?.description.orEmpty(),
        destination = buildLocationDescription(
            delivery.eventList.firstOrNull()?.postLocation?.address,
            delivery.eventList.firstOrNull()?.destinationLocation?.address
        ),
        imageRes = getDeliveredIcon(delivery.eventList.firstOrNull()?.code.orEmpty())
    )

    private fun List<Event>.toDeliveryDataList() = map {
        EventData(
            code = it.code,
            description = it.description,
            date = formatDate(it.date),
            postLocation = LocationData(
                AddressData(
                    it.postLocation?.address?.city.orEmpty(),
                    it.postLocation?.address?.uf.orEmpty(),
                )
            ),
            destinationLocation = LocationData(
                AddressData(
                    it.destinationLocation?.address?.city.orEmpty(),
                    it.destinationLocation?.address?.uf.orEmpty(),
                )
            ),
            iconUrl = getDeliveredIcon(it.code),
            formattedDestination = buildLocationDescription(it.postLocation?.address, it.destinationLocation?.address)
        )
    }

    private fun formatDate(date: String): String {
        val localDateTime = LocalDateTime.parse(date)
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
        return formatter.format(localDateTime)
    }

    private fun getDeliveredIcon(code: String) = when (code) {
        DELIVERED_CODE -> R.drawable.delivered_icon
        DELIVERED_START_CODE -> R.drawable.delivered_start_icon
        else -> R.drawable.package_delivery
    }

    private fun buildLocationDescription(startAddress: Address?, endAddress: Address?) = when (endAddress) {
        null -> DELIVERED_PATTERN.format(startAddress?.buildLocation())
        else -> DESCRIPTION_PATTERN.format(startAddress?.buildLocation(), endAddress.buildLocation())
    }

    //#endregion --- helpers

    private companion object {
        private const val DELIVERED_CODE = "BDE"
        private const val DELIVERED_START_CODE = "PO"
        private const val DESCRIPTION_PATTERN = "De %s para %s"
        private const val DELIVERED_PATTERN = "Objeto em  %s"
        private const val DATE_PATTERN = "dd/MM/yyyy - HH:mm:ss"
    }
}