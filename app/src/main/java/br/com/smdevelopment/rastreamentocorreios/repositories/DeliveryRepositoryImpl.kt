package br.com.smdevelopment.rastreamentocorreios.repositories

import br.com.smdevelopment.rastreamentocorreios.api.DeliveryApi
import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(private val api: DeliveryApi) : DeliveryRepository {

    override suspend fun fetchDelivery(code: String): Flow<DeliveryResponse> {
        return flow {
            val response = api.fetchDelivery(code)

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

    private fun handleBackendException(response: Response<DeliveryResponse>) {
        if (response.code() == CODE_NOT_FOUND) {
            throw CodeNotFoundException(code = response.code(), errorMessage = response.message())
        } else {
            throw DeliveryErrorException(code = response.code(), errorMessage = response.message())
        }
    }

    private companion object {
        const val CODE_NOT_FOUND = 422
    }
}

class DeliveryErrorException(val code: Int, val errorMessage: String) : Exception()

class CodeNotFoundException(val code: Int, val errorMessage: String) : Exception()