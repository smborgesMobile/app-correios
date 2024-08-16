package br.com.smdevelopment.rastreamentocorreios.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.notification.DeliveryNotificationChannel
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.GetAllTrackingUseCase
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

    override suspend fun doWork(): Result {
        return try {
            getAllTrackingUseCase.getCacheList().collectLatest { allList ->
                if (allList.isNotEmpty()) {
                    processTrackingCodes(allList)
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace() // Consider using a logging framework
            Result.failure()
        }
    }

    private suspend fun processTrackingCodes(allList: List<TrackingModel>) {
        allList.forEach { cachedData ->
            checkForStatusChange(cachedData)
        }
    }

    private suspend fun checkForStatusChange(trackingModel: TrackingModel): Boolean {
        var notificationSent = false

        getCodeUseCase.getTrackingInfo(trackingModel.code).collectLatest { trackingInfo ->
            if (trackingInfo.isNotEmpty()) {
                val filteredList = trackingInfo.firstOrNull { it.code == trackingModel.code }
                // Compare the cached and latest statuses
                if (filteredList?.last != trackingModel.last) {
                    // Show notification if status has changed
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