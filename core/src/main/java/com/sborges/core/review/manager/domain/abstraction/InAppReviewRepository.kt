package com.sborges.core.review.manager.domain.abstraction

/**
 * Repository interface for managing in-app review related data.
 */
interface InAppReviewRepository {

    /**
     * Saves the current app launch count.
     *
     * @param count The number of times the app has been launched.
     */
    fun saveLaunchCount(count: Int)

    /**
     * Retrieves the saved app launch count.
     *
     * @return The number of times the app has been launched.
     */
    fun getLaunchCount(): Int

    /**
     * Saves the launch count at the moment the in-app review was shown.
     *
     * @param count The launch count when the in-app review was shown.
     */
    fun saveCounterWhenReviewIsShown(count: Int)

    /**
     * Retrieves the launch count at the moment the in-app review was shown.
     *
     * @return The launch count when the in-app review was shown.
     */
    fun getCounterWhenReviewIsShown(): Int
}
