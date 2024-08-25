package com.sborges.core.review.manager.domain.abstraction

interface AppLaunchCounterRepository {

    fun saveLaunchCount(count: Int)

    fun getLaunchCount(): Int
}