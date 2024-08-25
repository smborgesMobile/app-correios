package com.sborges.core.review.manager

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.sborges.core.review.manager.domain.usecase.LaunchCounterUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ReviewManagerLauncher(
    private val launchCounterUse: LaunchCounterUseCase,
    private val reviewManager: ReviewManager,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
) {

    fun launchInAppReview(activity: Activity) {
        coroutineScope.launch {
            try {
                val reviewInfo = requestReviewFlow()
                val reviewCounter = launchCounterUse.getReviewCount()

                if (launchCounterUse.getLaunchCounter() >= (MIN_LAUNCH_COUNT + reviewCounter)) {
                    launchReviewFlow(activity, reviewInfo)
                    launchCounterUse.incrementReviewCount()
                }
            } catch (e: Exception) {
                Log.e("AppCorreiosTag", "Failed to launch in-app review", e)
            }
        }
    }

    private suspend fun requestReviewFlow(): ReviewInfo =
        withContext(Dispatchers.IO) {
            val task: Task<ReviewInfo> = reviewManager.requestReviewFlow()
            task.await()
        }

    private suspend fun launchReviewFlow(activity: Activity, reviewInfo: ReviewInfo) =
        withContext(Dispatchers.Main) {
            val flowTask = reviewManager.launchReviewFlow(activity, reviewInfo)
            flowTask.await()
        }

    private companion object {
        const val MIN_LAUNCH_COUNT = 3
    }
}

