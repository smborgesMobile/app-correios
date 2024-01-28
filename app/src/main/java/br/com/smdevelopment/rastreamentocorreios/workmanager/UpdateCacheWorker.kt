package br.com.smdevelopment.rastreamentocorreios.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.smdevelopment.rastreamentocorreios.usecase.UpdateCacheUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateCacheWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val updateCacheUseCase: UpdateCacheUseCase by inject()

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            updateCacheUseCase.updateCache()
        }
        return Result.success()
    }
}