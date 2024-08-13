package br.com.smdevelopment.rastreamentocorreios.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.R
import br.com.smdevelopment.rastreamentocorreios.entities.view.EventModel
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
            // Use Flow's terminal operator to handle the flows in a more controlled manner.
            getAllTrackingUseCase.getTrackingList().collectLatest { allList ->
                if (allList.isNotEmpty()) {
                    // Create a map of tracking codes to cached statuses
                    val cachedStatuses = allList.associate {
                        it.code to it.events
                    }

                    var notificationSent = false

                    // Process each tracking code
                    cachedStatuses.forEach { (trackingCode, events) ->
                        if (!notificationSent) {
                            // Get tracking info for the tracking code
                            getCodeUseCase.getTrackingInfo(trackingCode)
                                .collectLatest { trackingInfo ->
                                    if (trackingInfo.isNotEmpty()) {
                                        val trackingList: List<EventModel> =
                                            trackingInfo.firstOrNull()?.events ?: emptyList()
                                        // Compare the cached and latest statuses
                                        if (events != trackingList) {
                                            // Show notification if status has changed
                                            deliveryNotificationChannel.showBasicNotification(
                                                title = trackingCode,
                                                description = applicationContext.getString(R.string.delivered_moving)
                                            )
                                            notificationSent = true
                                        }
                                    }
                                }
                        }

                        // Exit the loop if a notification has been sent
                        if (notificationSent) return@forEach
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            // Log the exception for debugging purposes
            e.printStackTrace()
            Result.failure()
        }
    }


}