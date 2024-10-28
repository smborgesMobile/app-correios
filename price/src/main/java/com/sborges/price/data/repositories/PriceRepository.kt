package com.sborges.price.data.repositories

import android.util.Log
import com.sborges.price.data.api.PriceApiKtor
import com.sborges.price.data.entities.PriceResponseItem
import com.sborges.price.data.wrapper.ResponseWrapper
import com.sborges.price.domain.abstraction.PriceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PriceRepositoryImpl(private val api: PriceApiKtor) : PriceRepository {

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
                ResponseWrapper.Success(response)

            } catch (e: HttpException) {
                ResponseWrapper.Error(e.message ?: "Network error", e.code())
            } catch (e: Exception) {
                Log.d("sm.borges", "Exception: ${e.message}")
                ResponseWrapper.Error(e.message ?: "Unknown error")
            }
        }
    }
}
