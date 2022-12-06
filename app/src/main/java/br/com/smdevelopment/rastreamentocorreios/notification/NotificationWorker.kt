package br.com.smdevelopment.rastreamentocorreios.notification

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.business.NotificationBusiness
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val business: NotificationBusiness,
    private val notificationManager: NotificationManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        var result = Result.success()
        business.checkForUpdate()
            .catch {
                result = Result.failure()
                Log.d(NOTIFICATION_TAG, "Failed to fetch data")
            }
            .collect { deliveryData ->
                notificationManager.createNotification(
                    title = deliveryData.code,
                    description = deliveryData.eventList.firstOrNull()?.description.orEmpty(),
                    location = deliveryData.eventList.firstOrNull()?.formattedDestination.orEmpty()
                )
            }

        return result
    }

    private companion object {
        const val NOTIFICATION_TAG = "NOTIFICATION_TAG"
    }
}