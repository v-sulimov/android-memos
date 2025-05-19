package com.vsulimov.memos.fragment.screen

import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.redux.Middleware

/**
 * Provides middleware configuration for a specific screen in the application.
 * Implementations of this interface define a unique tag for the screen and
 * a list of middlewares that handle state transformations for the screen.
 */
interface ScreenMiddlewaresProvider {
    /**
     * Returns a unique identifier for the screen.
     *
     * @return A string representing the screen's unique tag.
     */
    fun provideTag(): String

    /**
     * Provides a list of middlewares that process the application state for the screen.
     *
     * @return A list of [Middleware] instances handling [ApplicationState].
     */
    fun provideScreenMiddlewares(): List<Middleware<ApplicationState>>
}
