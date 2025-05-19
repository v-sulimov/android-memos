package com.vsulimov.memos.factory.middleware

import com.vsulimov.memos.middleware.ConfigureServerMiddleware

/**
 * A factory object responsible for creating instances of [ConfigureServerMiddleware].
 * This factory provides a centralized way to instantiate [ConfigureServerMiddleware].
 */
object ConfigureServerMiddlewareFactory {
    /**
     * Creates an instance of [ConfigureServerMiddleware].
     *
     * @return A configured [ConfigureServerMiddleware] instance ready to handle [com.vsulimov.memos.action.ConfigureServerAction] actions.
     */
    fun createConfigureServerMiddleware(): ConfigureServerMiddleware = ConfigureServerMiddleware()
}
