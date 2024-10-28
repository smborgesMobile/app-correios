package com.sborges.price.data.api

import com.sborges.price.data.entities.PriceResponseItem

interface PriceApiKtor {

    suspend fun getPrices(
        originZipCode: String,
        destinationZipCode: String,
        weight: Double,
        height: String,
        width: String,
        length: String
    ): List<PriceResponseItem>
}