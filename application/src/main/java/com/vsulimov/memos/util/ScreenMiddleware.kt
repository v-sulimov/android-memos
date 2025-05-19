package com.vsulimov.memos.util

import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.redux.Middleware

/**
 * Manages screen-specific middlewares for the Redux architecture in Android.
 *
 * This object allows adding and removing middlewares associated with specific screens,
 * identified by a unique tag. It maintains a mapping of screen tags to their respective
 * middlewares and ensures that these middlewares are added to or removed from the global
 * middleware chain as screens are added or removed.
 */
object ScreenMiddleware {
    private val screenMiddlewares = mutableMapOf<String, List<Middleware<ApplicationState>>>()

    /**
     * Adds a list of middlewares for a specific screen identified by the given tag.
     *
     * The middlewares are stored in a map with the tag as the key and are also added
     * to the global middleware chain.
     *
     * @param tag A unique identifier for the screen.
     * @param middlewares The list of middlewares to associate with the screen.
     */
    fun addScreenMiddlewares(
        tag: String,
        middlewares: List<Middleware<ApplicationState>>,
    ) {
        screenMiddlewares.put(key = tag, value = middlewares)
        addMiddlewares(middlewares)
    }

    /**
     * Removes the middlewares associated with the specified screen tag.
     *
     * If the tag exists, its associated middlewares are removed from the global middleware
     * chain and the map entry is deleted. If the tag does not exist, [NoSuchElementException] is thrown.
     *
     * @param tag The unique identifier of the screen whose middlewares are to be removed.
     */
    fun removeScreenMiddlewares(tag: String) {
        val middlewares = screenMiddlewares.getValue(key = tag)
        removeMiddlewares(middlewares)
        screenMiddlewares.remove(key = tag)
    }
}
