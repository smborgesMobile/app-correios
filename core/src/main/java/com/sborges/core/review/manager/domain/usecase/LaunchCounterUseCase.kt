package com.sborges.core.review.manager.domain.usecase

import com.sborges.core.review.manager.domain.abstraction.AppLaunchCounterRepository

class LaunchCounterUseCase(
    private val launchCounterRepository: AppLaunchCounterRepository
) {

    fun incrementLaunchCounter() {
        val currentLaunchCount = launchCounterRepository.getLaunchCount()
        launchCounterRepository.saveLaunchCount(currentLaunchCount + 1)
    }

    fun getLaunchCounter(): Int =
        launchCounterRepository.getLaunchCount()

}