package br.com.smdevelopment.rastreamentocorreios.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventModel
import br.com.smdevelopment.rastreamentocorreios.entities.view.TrackingModel
import br.com.smdevelopment.rastreamentocorreios.notification.DeliveryNotificationChannel
import br.com.smdevelopment.rastreamentocorreios.usecase.TrackingUseCase
import br.com.smdevelopment.rastreamentocorreios.usecase.impl.GetAllTrackingUseCase
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
        // Create a map of tracking codes to cached statuses
        val cachedStatuses = allList.associate { it.code to it.events }

        // Process each tracking code
        for ((trackingCode, events) in cachedStatuses) {
            if (checkForStatusChange(trackingCode, events)) {
                break // Exit the loop if a notification has been sent
            }
        }
    }

    private suspend fun checkForStatusChange(
        trackingCode: String,
        events: List<EventModel>
    ): Boolean {
        var notificationSent = false

        getCodeUseCase.getTrackingInfo(trackingCode).collectLatest { trackingInfo ->
            if (trackingInfo.isNotEmpty()) {
                val trackingList: List<EventModel> =
                    trackingInfo.firstOrNull()?.events ?: emptyList()
                val remoteItemStatus = trackingList.firstOrNull()?.status.orEmpty()
                val cacheStatus = events.firstOrNull()?.status.orEmpty()

                // Compare the cached and latest statuses
                if (remoteItemStatus != cacheStatus) {
                    // Show notification if status has changed
                    deliveryNotificationChannel.showBasicNotification(
                        title = trackingCode,
                        description = applicationContext.getString(R.string.delivered_moving)
                    )
                    notificationSent = true
                }
            }
        }

        return notificationSent
    }
}