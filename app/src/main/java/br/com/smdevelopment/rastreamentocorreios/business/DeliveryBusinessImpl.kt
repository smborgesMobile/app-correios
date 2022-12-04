package br.com.smdevelopment.rastreamentocorreios.business

import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.Address
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.repositories.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class DeliveryBusinessImpl @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : DeliveryBusiness {

    //#region --- fetch data

    override suspend fun fetchDelivery(code: String): Flow<DeliveryData> {
        val responseFlow = deliveryRepository.fetchDelivery(code)
        val deliveryFlow: Flow<DeliveryData> = responseFlow.mapNotNull {
            it.toDeliveryData()
        }

        deliveryFlow.collect { delivery ->
            insertNewDelivery(delivery)
        }

        return deliveryFlow
    }

    override suspend fun getAllDeliveries(): List<DeliveryData> {
        return deliveryRepository.fetchDeliveryListFromLocal()
    }

    override suspend fun insertNewDelivery(delivery: DeliveryData) {
        deliveryRepository.insertNewDelivery(delivery)
    }

    //#endregion --- fetch data

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
        DELIVERED_ICON -> R.drawable.delivered_icon
        else -> R.drawable.delivery_icon
    }

    private fun buildLocationDescription(startAddress: Address?, endAddress: Address?) = when (endAddress) {
        null -> DELIVERED_PATTERN.format(startAddress?.buildLocation())
        else -> DESCRIPTION_PATTERN.format(startAddress?.buildLocation(), endAddress.buildLocation())
    }

    //#endregion --- helpers

    private companion object {
        private const val DELIVERED_ICON = "BDE"
        private const val DESCRIPTION_PATTERN = "De %s para %s"
        private const val DELIVERED_PATTERN = "Entregue em %s"
    }
}