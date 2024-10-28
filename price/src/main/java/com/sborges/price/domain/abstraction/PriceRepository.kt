package com.sborges.price.domain.abstraction

import com.sborges.price.data.entities.PriceResponseItem
import com.sborges.price.data.wrapper.ResponseWrapper

interface PriceRepository {

    suspend fun getPrices(
        originZipCode: String,
        destinationZipCode: String,
        weight: Double,
        height: String,
        width: String,
        length: String
    ): ResponseWrapper<List<PriceResponseItem>>
}