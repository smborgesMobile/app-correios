package com.sborges.core.review.manager.domain.usecase

import com.sborges.core.review.manager.domain.abstraction.InAppReviewRepository

class LaunchCounterUseCase(
    private val launchCounterRepository: InAppReviewRepository
) {

    fun incrementLaunchCounter() {
        val currentLaunchCount = launchCounterRepository.getLaunchCount()
        launchCounterRepository.saveLaunchCount(currentLaunchCount + 1)
    }

    fun getLaunchCounter(): Int =
        launchCounterRepository.getLaunchCount()

    fun incrementReviewCount() {
        val currentReviewCount = launchCounterRepository.getCounterWhenReviewIsShown()
        launchCounterRepository.saveCounterWhenReviewIsShown(currentReviewCount + 20)
    }

    fun getReviewCount(): Int =
        launchCounterRepository.getCounterWhenReviewIsShown()

}