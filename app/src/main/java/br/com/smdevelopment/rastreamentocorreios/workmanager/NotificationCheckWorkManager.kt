package br.com.smdevelopment.rastreamentocorreios.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.R
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
            getAllTrackingUseCase.getTrackingList().collectLatest { allList ->
                allList.firstOrNull()?.code?.let { trackingCode ->
                    getCodeUseCase.getTrackingInfo(trackingCode).collectLatest { trackingInfo ->
                        val shouldShowNotification =
                            allList.firstOrNull()?.events?.firstOrNull()?.status !=
                                    trackingInfo.firstOrNull()?.events?.firstOrNull()?.status

                        if (shouldShowNotification) {
                            deliveryNotificationChannel.showBasicNotification(
                                title = trackingCode,
                                description = applicationContext.getString(R.string.delivered_moving)
                            )
                        }
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}