package com.sborges.core.review.manager.data.repository

import android.content.SharedPreferences
import com.sborges.core.review.manager.domain.abstraction.InAppReviewRepository

internal class InAppReviewRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : InAppReviewRepository {

    override fun saveLaunchCount(count: Int) {
        sharedPreferences.edit().putInt(LAUNCH_COUNTER, count).apply()
    }

    override fun getLaunchCount(): Int =
        sharedPreferences.getInt(LAUNCH_COUNTER, 0)

    override fun saveCounterWhenReviewIsShown(count: Int) {
        sharedPreferences.edit().putInt(REVIEW_COUNTER, count).apply()
    }

    override fun getCounterWhenReviewIsShown(): Int =
        sharedPreferences.getInt(REVIEW_COUNTER, 0)

    private companion object {
        const val LAUNCH_COUNTER = "LAUNCH_COUNTER"
        const val REVIEW_COUNTER = "REVIEW_COUNTER"
    }
}