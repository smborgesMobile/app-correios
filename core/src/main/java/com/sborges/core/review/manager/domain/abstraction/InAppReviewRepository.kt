package com.sborges.core.review.manager.domain.abstraction

interface InAppReviewRepository {

    fun saveLaunchCount(count: Int)

    fun getLaunchCount(): Int

    fun saveCounterWhenReviewIsShown(count: Int)

    fun getCounterWhenReviewIsShown(): Int
}