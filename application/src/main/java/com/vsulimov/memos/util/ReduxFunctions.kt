package com.vsulimov.memos.util

import com.vsulimov.memos.application.MemosApplication
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.redux.Action
import com.vsulimov.redux.Middleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Dispatches an action to the application-wide store.
 *
 * @param action The [Action] to dispatch to the store.
 */
fun dispatch(action: Action) = MemosApplication.getInstance().getStore().dispatch(action)

/**
 * Retrieves the state flow from the application-wide store.
 *
 * @return The [StateFlow] emitting the current [ApplicationState].
 */
fun getStateFlow() = MemosApplication.getInstance().getStore().stateFlow

/**
 * Subscribes to state changes in the application-wide store, applying a lens to extract a specific part of the state.
 *
 * This function launches a coroutine that collects distinct changes to the state, as transformed by the provided lens,
 * and invokes the [onStateChange] callback with the updated state.
 *
 * @param scope The [CoroutineScope] in which to launch the coroutine for collecting state changes.
 * @param lens A function that extracts a specific part of the [ApplicationState] to observe.
 * @param onStateChange A callback invoked with the updated state whenever the extracted state changes.
 * @param S The type of the state extracted by the lens.
 */
fun <S> subscribeToStateChanges(
    scope: CoroutineScope,
    lens: (ApplicationState) -> S,
    onStateChange: (S) -> Unit
) {
    scope.launch {
        getStateFlow()
            .map { lens(it) }
            .distinctUntilChanged()
            .collect { onStateChange(it) }
    }
}

/**
 * Adds a middleware to the application-wide store.
 *
 * @param middleware The [Middleware] to add to the store.
 */
fun addMiddleware(middleware: Middleware<ApplicationState>) =
    MemosApplication.getInstance().getStore().addMiddleware(middleware)

/**
 * Removes a middleware from the application-wide store.
 *
 * @param middleware The [Middleware] to remove from the store.
 */
fun removeMiddleware(middleware: Middleware<ApplicationState>) =
    MemosApplication.getInstance().getStore().removeMiddleware(middleware)
