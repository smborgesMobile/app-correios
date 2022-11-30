package br.com.smdevelopment.rastreamentocorreios.presentation.repositories

import br.com.smdevelopment.rastreamentocorreios.entities.retrofit.DeliveryResponse
import br.com.smdevelopment.rastreamentocorreios.presentation.api.DeliveryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(private val api: DeliveryApi) : DeliveryRepository {

    override suspend fun fetchDelivery(code: String): Flow<DeliveryResponse> {
        return flow {
            val response = api.fetchDelivery(code)

            if (response.isSuccessful) {
                response.body()
            } else {
                throw DeliveryErrorException(code = response.code(), errorMessage = response.message())
            }
        }
    }

}

class DeliveryErrorException(val code: Int, val errorMessage: String) : Exception()