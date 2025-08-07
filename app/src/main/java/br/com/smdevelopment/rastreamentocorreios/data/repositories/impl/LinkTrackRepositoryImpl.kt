package br.com.smdevelopment.rastreamentocorreios.data.repositories.impl

import br.com.smdevelopment.rastreamentocorreios.data.api.CorreiosRapidApiKtor
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.data.mappers.CorreiosRapidApiMapper
import br.com.smdevelopment.rastreamentocorreios.data.room.dao.DeliveryDao
import br.com.smdevelopment.rastreamentocorreios.domain.abstraction.LinkTrackRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LinkTrackRepositoryImpl(
    private val api: CorreiosRapidApiKtor,
    private val linkTrackDao: DeliveryDao,
    private val firebaseAuth: FirebaseAuth,
    private val mapper: CorreiosRapidApiMapper
) : LinkTrackRepository {

    private var isJobRunning = false

    override suspend fun fetchTrackByCode(code: String): Flow<List<TrackingModel>> {
        return withContext(Dispatchers.IO) {
            flow {
                val apiResponse = api.fetchTrackByCode(code = code)
                linkTrackDao.insertNewDelivery(mapper.mapToTrackModel(apiResponse))
                emit(getListFromRoom())
            }
        }
    }


    override suspend fun getAllDeliveries(): Flow<List<TrackingModel>> {
        return flow {
            emit(getListFromRoom())
            updateCache()
        }
    }

    override suspend fun getAllCacheDeliveries(): Flow<List<TrackingModel>> {
        return flow {
            val cachedList = getListFromRoom()
            emit(cachedList)
        }
    }

    override suspend fun updateCache() {
        if (isJobRunning)
            return

        withContext(Dispatchers.IO) {
            try {
                val list = getListFromRoom()
                list.forEach { delivery ->
                    val response = api.fetchTrackByCode(code = delivery.code)
                    linkTrackDao.insertNewDelivery(mapper.mapToTrackModel(response))
                    delay(POOLING_TIME)
                }
            } catch (ex: Exception) {
                throw Exception("Error updating cache")
            } finally {
                isJobRunning = false
            }
        }
    }

    override suspend fun deleteDelivery(delivered: TrackingModel) {
        withContext(Dispatchers.IO) {
            linkTrackDao.deleteDelivery(delivered)
        }
    }

    private fun getListFromRoom(): List<TrackingModel> {
        return linkTrackDao.getAllDeliveries(firebaseAuth.currentUser?.uid.orEmpty())
            ?.sortedWith(compareByDescending { it.events.firstOrNull()?.date })
            ?: emptyList()
    }

    private companion object {
        const val POOLING_TIME = 2500L
    }
}