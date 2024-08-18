package com.sborges.price.data.repositories

import com.sborges.price.data.api.PriceApi
import com.sborges.price.data.entities.PriceResponseItem
import com.sborges.price.data.retrofit.ResponseWrapper
import com.sborges.price.domain.abstraction.PriceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PriceRepositoryImpl(private val api: PriceApi) : PriceRepository {

    override suspend fun getPrices(
        originZipCode: String,
        destinationZipCode: String,
        weight: Double,
        height: String,
        width: String,
        length: String
    ): ResponseWrapper<List<PriceResponseItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPrices(
                    originZipCode = originZipCode,
                    destinationZipCode = destinationZipCode,
                    weight = weight,
                    height = height,
                    width = width,
                    length = length
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        ResponseWrapper.Success(it)
                    } ?: ResponseWrapper.Error("Empty response body", response.code())
                } else {
                    ResponseWrapper.Error(response.message(), response.code())
                }
            } catch (e: HttpException) {
                ResponseWrapper.Error(e.message ?: "Network error", e.code())
            } catch (e: Exception) {
                ResponseWrapper.Error(e.message ?: "Unknown error")
            }
        }
    }
}
