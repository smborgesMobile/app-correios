package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.api.DeliveryApi
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.DeliveryData
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val api: DeliveryApi,
    private val deliveryDao: DeliveryDao
) : DeliveryRepository {

    override suspend fun fetchDelivery(codeList: List<String>): Flow<DeliveryResponse> {
        return flow {
            val response = api.fetchDelivery(codeList.joinToString())

            if (response.isSuccessful) {
                val deliveryData = response.body()
                if (deliveryData != null) {
                    emit(deliveryData)
                }
            } else {
                handleBackendException(response)
            }
        }
    }

    override suspend fun fetchDeliveryListFromLocal(): List<DeliveryData> {
        return deliveryDao.getAllDeliveries() ?: throw Exception()
    }

    override suspend fun insertNewDelivery(delivery: DeliveryData) {
        deliveryDao.insertNewDelivery(delivery)
    }

    private fun handleBackendException(response: Response<DeliveryResponse>) {
        if (response.code() == CODE_NOT_FOUND) {
            throw CodeNotFoundException(code = response.code())
        } else {
            throw DeliveryErrorException(code = response.code())
        }
    }

    private companion object {
        const val CODE_NOT_FOUND = 422
    }
}

class DeliveryErrorException(val code: Int) : Exception()

class CodeNotFoundException(val code: Int) : Exception()