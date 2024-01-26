package br.com.smdevelopment.rastreamentocorreios.repositories.impl

import android.util.Log
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.api.LinkTrackApi
import br.com.smdevelopment.rastreamentocorreios.entities.TrackingResponse
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventModel
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.room.dao.DeliveryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class LinkTrackRepositoryImpl(
    private val api: LinkTrackApi,
    private val linkTrackDao: DeliveryDao
) : LinkTrackRepository {

    override suspend fun fetchTrackByCode(code: String): Flow<List<TrackingModel>> {
        return withContext(Dispatchers.IO) {
            flow {
                val response = api.fetchTrackByCode(code = code)
                if (response.isSuccessful) {
                    val deliveryResponse = response.body()
                    if (deliveryResponse != null && deliveryResponse.events.isNotEmpty()) {
                        try {
                            linkTrackDao.insertNewDelivery(deliveryResponse.toDeliveryData())
                            emit(getListFromRoom())
                        } catch (e: Exception) {
                            Log.d("LinkTrackRepository", "Error: ${e.message}")
                        }
                    }
                } else {
                    handleBackendException(response)
                }
            }
        }
    }

    override suspend fun getAllDeliveries(): Flow<List<TrackingModel>> {
        return flow {
            emit(getListFromRoom())
        }
    }

    private fun getListFromRoom(): List<TrackingModel> {
        return linkTrackDao.getAllDeliveries()
            ?.sortedWith(compareByDescending { it.events.firstOrNull()?.date })
            ?: emptyList()
    }

    private fun TrackingResponse.toDeliveryData(): TrackingModel {
        return TrackingModel(
            code = this.code,
            host = this.host,
            last = this.last.orEmpty(),
            quantity = this.quantity,
            service = this.service.orEmpty(),
            time = this.time,
            events = this.events.mapIndexed { index, event ->
                EventModel(
                    date = event.date,
                    time = event.time,
                    location = event.location,
                    status = event.status,
                    subStatus = event.subStatus,
                    icon = when {
                        event.status == OBJECT_DONE -> {
                            R.drawable.delivered_icon
                        }

                        index == events.size - 1 -> {
                            R.drawable.package_delivery
                        }

                        else -> {
                            R.drawable.delivered_start_icon
                        }
                    }
                )
            },
            icon = when {
                this.events.first().status == OBJECT_DONE -> {
                    R.drawable.delivered_icon
                }

                else -> {
                    R.drawable.delivered_start_icon
                }
            }
        )
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
        const val OBJECT_DONE = "Objeto entregue ao destinatário"
        const val POSTED = "postado"
    }
}