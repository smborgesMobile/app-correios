package br.com.smdevelopment.rastreamentocorreios.converters

import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Address
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData

class DeliveryConverter {

    fun convert(deliveryResponse: DeliveryResponse): DeliveryData {
        return deliveryResponse.toDeliveryData()
    }

    //#region --- helpers

    private fun DeliveryResponse.toDeliveryData() = DeliveryData(
        code = delivery.objectCode.orEmpty(),
        eventList = delivery.eventList,
        type = delivery.type,
        description = delivery.eventList.firstOrNull()?.description.orEmpty(),
        destination = buildLocationDescription(
            delivery.eventList.firstOrNull()?.postLocation?.address,
            delivery.eventList.firstOrNull()?.destinationLocation?.address
        ),
        imageRes = getDeliveredIcon(delivery.eventList.firstOrNull()?.code.orEmpty())
    )

    private fun getDeliveredIcon(code: String) = when (code) {
        DELIVERED_CODE -> R.drawable.delivered_icon
        else -> R.drawable.package_delivery
    }

    private fun buildLocationDescription(startAddress: Address?, endAddress: Address?) = when (endAddress) {
        null -> DELIVERED_PATTERN.format(startAddress?.buildLocation())
        else -> DESCRIPTION_PATTERN.format(startAddress?.buildLocation(), endAddress.buildLocation())
    }

    //#endregion --- helpers

    private companion object {
        private const val DELIVERED_CODE = "BDE"
        private const val DESCRIPTION_PATTERN = "De %s para %s"
        private const val DELIVERED_PATTERN = "Entregue em %s"
    }
}