package com.vsulimov.memos.factory.middleware

import com.vsulimov.memos.middleware.NavigationMiddleware

/**
 * Factory object for creating instances of [NavigationMiddleware].
 * Provides a convenient way to instantiate navigation middleware with a specified activity-finishing action.
 */
object NavigationMiddlewareFactory {
    /**
     * Creates a new instance of [NavigationMiddleware] with the provided activity-finishing action.
     *
     * @param finishActivity A lambda function that defines the action to finish an activity.
     * @return A new [NavigationMiddleware] instance configured with the provided [finishActivity] action.
     */
    fun createNavigationMiddleware(finishActivity: () -> Unit): NavigationMiddleware = NavigationMiddleware(finishActivity)
}
