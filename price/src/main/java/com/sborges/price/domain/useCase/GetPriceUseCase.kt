package com.sborges.price.domain.useCase

import com.sborges.price.data.entities.PriceResponseItem
import com.sborges.price.data.retrofit.ResponseWrapper
import com.sborges.price.domain.abstraction.PriceRepository
import com.sborges.price.domain.entity.PriceDomainEntity
import java.text.NumberFormat
import java.util.Locale

class GetPriceUseCase(private val priceRepository: PriceRepository) {

    suspend operator fun invoke(
        originZipCode: String,
        destinationZipCode: String,
        weight: String,
        height: String,
        width: String,
        length: String
    ): PriceDomainEntity? {
        return try {
            val response = priceRepository.getPrices(
                originZipCode,
                destinationZipCode,
                weight,
                height,
                width,
                length
            )

            when (response) {
                is ResponseWrapper.Success -> response.data.toDomain()
                is ResponseWrapper.Error -> null
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun List<PriceResponseItem>.toDomain(): PriceDomainEntity {
        val firstItem = this.first()
        return PriceDomainEntity(
            type = firstItem.name,
            price = formatPriceToReal(firstItem.price, firstItem.currency),
            deliveryTime = formatDeliveryTime(firstItem.deliveryTime),
        )
    }

    private fun formatPriceToReal(price: Double, currencySymbol: String): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        numberFormat.currency = java.util.Currency.getInstance("BRL")
        return numberFormat.format(price).replace("R$", currencySymbol)
    }

    private fun formatDeliveryTime(deliveryTime: Int) =
        "$deliveryTime Dias"
}