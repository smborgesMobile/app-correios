package com.sborges.price.domain.abstraction

import com.sborges.price.data.entities.PriceResponseItem
import com.sborges.price.data.retrofit.ResponseWrapper

interface PriceRepository {

    suspend fun getPrices(
        originZipCode: String,
        destinationZipCode: String,
        weight: String,
        height: String,
        width: String,
        length: String
    ): ResponseWrapper<List<PriceResponseItem>>
}