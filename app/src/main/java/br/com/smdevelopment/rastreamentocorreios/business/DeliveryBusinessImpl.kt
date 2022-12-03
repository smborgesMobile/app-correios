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

    override suspend fun fetchDelivery(code: String): Flow<DeliveryData> {
        val responseFlow = deliveryRepository.fetchDelivery(code)
        val deliveryFlow: Flow<DeliveryData> = responseFlow.mapNotNull {
            it.toDeliveryData()
        }

        return deliveryFlow
    }

    private fun DeliveryResponse.toDeliveryData() = DeliveryData(
        code = delivery.objectCode.orEmpty(),
        eventList = delivery.eventList,
        type = delivery.type,
        description = delivery.eventList.firstOrNull()?.description.orEmpty(),
        destination = buildLocationDescription(
            delivery.eventList.firstOrNull()?.postLocation?.address,
            delivery.eventList.firstOrNull()?.destinationLocation?.address
        ),
        imageRes = R.drawable.package_delivery
    )

    private fun buildLocationDescription(startAddress: Address?, endAddress: Address?): String {
        return if (startAddress != null && endAddress != null)
            DESCRIPTION_PATTERN.format(startAddress.buildLocation(), endAddress.buildLocation())
        else String()
    }

    private companion object {
        private const val DESCRIPTION_PATTERN = "De %s para %s"
    }
}