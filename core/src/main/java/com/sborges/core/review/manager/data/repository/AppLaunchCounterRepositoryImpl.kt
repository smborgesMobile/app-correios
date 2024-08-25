package com.sborges.core.review.manager.data.repository

import android.content.SharedPreferences
import com.sborges.core.review.manager.domain.abstraction.AppLaunchCounterRepository

internal class AppLaunchCounterRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AppLaunchCounterRepository {

    override fun saveLaunchCount(count: Int) {
        sharedPreferences.edit().putInt(LAUNCH_COUNTER, count).apply()
    }

    override fun getLaunchCount(): Int =
        sharedPreferences.getInt(LAUNCH_COUNTER, 0)

    private companion object {
        const val LAUNCH_COUNTER = "LAUNCH_COUNTER"
    }
}