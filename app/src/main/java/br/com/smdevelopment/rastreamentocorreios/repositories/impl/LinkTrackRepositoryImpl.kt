package br.com.smdevelopment.rastreamentocorreios.repositories.impl

import br.com.smdevelopment.rastreamentocorreios.api.LinkTrackApi
import br.com.smdevelopment.rastreamentocorreios.entities.TrackingResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventModel
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class LinkTrackRepositoryImpl(
    private val api: LinkTrackApi
) : LinkTrackRepository {

    override fun fetchTrackByCode(code: String): Flow<TrackingModel> {
        return flow {
            val response = api.fetchTrackByCode(code = code)

            if (response.isSuccessful) {
                val deliveryData = response.body()
                if (deliveryData != null) {
                    emit(deliveryData)
                }
            } else {
                handleBackendException(response)
            }
        }.map { response ->
            TrackingModel(
                code = response.code,
                host = response.host,
                last = response.last.orEmpty(),
                quantity = response.quantity,
                service = response.service.orEmpty(),
                time = response.time,
                events = response.events.map { event ->
                    EventModel(
                        date = event.date,
                        time = event.time,
                        location = event.location,
                        status = event.status,
                        subStatus = event.subStatus
                    )
                }
            )
        }
    }

    private fun handleBackendException(response: Response<TrackingResponse>) {
        if (response.code() == CODE_NOT_FOUND) {
            throw CodeNotFoundException(code = response.code())
        } else {
            throw DeliveryErrorException(code = response.code())
        }
    }

    class DeliveryErrorException(val code: Int) : Exception()

    class CodeNotFoundException(val code: Int) : Exception()

    private companion object {
        const val CODE_NOT_FOUND = 422
    }
}