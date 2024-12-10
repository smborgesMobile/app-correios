package br.com.smdevelopment.rastreamentocorreios.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.data.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.UpdateCacheUseCase
import br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl.GetAllTrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.notification.DeliveryNotificationChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationCheckWorkManager(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val getAllTrackingUseCase: GetAllTrackingUseCase by inject()
    private val getCodeUseCase: TrackingUseCase by inject()
    private val deliveryNotificationChannel: DeliveryNotificationChannel by inject()
    private val updateCacheUseCase: UpdateCacheUseCase by inject()

    override suspend fun doWork(): Result {
        return try {
            getAllTrackingUseCase.getTrackingList().collectLatest { allList ->
                if (allList.isNotEmpty()) {
                    processTrackingCodes(allList)
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun processTrackingCodes(allList: List<TrackingModel>) {
        allList.forEach { cachedData ->
            val notificationSent = checkForStatusChange(cachedData)
            if (notificationSent) {
                updateCacheUseCase.updateCache()
            }
        }
    }

    private suspend fun checkForStatusChange(trackingModel: TrackingModel): Boolean {
        var notificationSent = false
        getCodeUseCase.getTrackingInfo(trackingModel.code).collectLatest { trackingInfo ->
            if (trackingInfo.isNotEmpty()) {
                val filteredList = trackingInfo.firstOrNull { it.code == trackingModel.code }
                if (filteredList?.last != trackingModel.last && trackingModel.isDelivered.not()) {
                    deliveryNotificationChannel.showBasicNotification(
                        title = trackingModel.code,
                        description = applicationContext.getString(R.string.delivered_moving)
                    )
                    notificationSent = true
                    delay(QUERY_DELAY)
                }
            }
        }
        return notificationSent
    }

    private companion object {
        const val QUERY_DELAY = 500L
    }
}