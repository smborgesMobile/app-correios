package br.com.smdevelopment.rastreamentocorreios.data.repositories.impl

import br.com.smdevelopment.rastreamentocorreios.data.api.LinkTrackApiKtor
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.data.mappers.LinkTrackDomainMapper
import br.com.smdevelopment.rastreamentocorreios.data.repositories.LinkTrackRepository
import br.com.smdevelopment.rastreamentocorreios.data.room.dao.DeliveryDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LinkTrackRepositoryImpl(
    private val api: LinkTrackApiKtor,
    private val linkTrackDao: DeliveryDao,
    private val firebaseAuth: FirebaseAuth,
    private val linkTrackMapper: LinkTrackDomainMapper
) : LinkTrackRepository {

    private var isJobRunning = false

    override suspend fun fetchTrackByCode(code: String): Flow<List<TrackingModel>> {
        return withContext(Dispatchers.IO) {
            flow {
                val apiResponse = api.fetchTrackByCode(code = code)
                linkTrackDao.insertNewDelivery(linkTrackMapper.mapToTrackModel(apiResponse))
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
                    linkTrackDao.insertNewDelivery(linkTrackMapper.mapToTrackModel(response))
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