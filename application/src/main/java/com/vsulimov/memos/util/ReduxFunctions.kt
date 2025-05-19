package com.vsulimov.memos.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.vsulimov.memos.application.MemosApplication
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.redux.Action
import com.vsulimov.redux.Middleware
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Dispatches an action to the application-wide Redux store.
 *
 * This utility function retrieves the Redux store from [MemosApplication] and dispatches the provided
 * action to it, facilitating interaction with the application's state management system.
 *
 * @param action The [Action] to dispatch to the store for processing by reducers or middleware.
 */
fun dispatch(action: Action) = MemosApplication.getInstance().getStore().dispatch(action)

/**
 * Retrieves the state flow from the application-wide Redux store.
 *
 * This utility function provides access to the store's [StateFlow], which emits the current
 * [ApplicationState] whenever it changes, enabling reactive state observation.
 *
 * @return The [StateFlow][ApplicationState] emitting the current application state.
 */
fun getStateFlow() = MemosApplication.getInstance().getStore().stateFlow

/**
 * Subscribes to changes in the application-wide Redux store's state, filtered and transformed by a lens.
 *
 * This function launches a coroutine in the provided [viewLifecycleOwner]'s lifecycle scope to collect
 * distinct state changes. It filters the state using [isExpectedState], applies the [lens] to extract
 * a specific part of the [ApplicationState], and invokes the [onStateChange] callback with the transformed
 * state. The subscription is active only when the lifecycle is in the [Lifecycle.State.STARTED] state,
 * ensuring proper lifecycle awareness.
 *
 * @param viewLifecycleOwner The [LifecycleOwner] whose lifecycle determines when state collection occurs.
 * @param isExpectedState A predicate to filter states, ensuring only relevant states are processed.
 * @param lens A function that extracts a specific part of the [ApplicationState] to observe.
 * @param onStateChange A callback invoked with the extracted state (or null) whenever it changes.
 * @param S The type of the state extracted by the lens.
 */
fun <S> subscribeToNullableStateChanges(
    viewLifecycleOwner: LifecycleOwner,
    isExpectedState: (ApplicationState) -> Boolean,
    lens: (ApplicationState) -> S?,
    onStateChange: (S?) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        getStateFlow()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .filter { isExpectedState(it) }
            .map { lens(it) }
            .distinctUntilChanged()
            .collect { onStateChange(it) }
    }
}

/**
 * Subscribes to changes in the application-wide Redux store's state, filtered and transformed by a lens.
 *
 * This function launches a coroutine in the provided [viewLifecycleOwner]'s lifecycle scope to collect
 * distinct state changes. It filters the state using [isExpectedState], applies the [lens] to extract
 * a specific non-null part of the [ApplicationState], and invokes the [onStateChange] callback with the
 * transformed state. The subscription is active only when the lifecycle is in the [Lifecycle.State.STARTED]
 * state, ensuring proper lifecycle awareness. Unlike [subscribeToNullableStateChanges], this function assumes
 * the [lens] always returns a non-null value.
 *
 * @param viewLifecycleOwner The [LifecycleOwner] whose lifecycle determines when state collection occurs.
 * @param isExpectedState A predicate to filter states, ensuring only relevant states are processed.
 * @param lens A function that extracts a specific non-null part of the [ApplicationState] to observe.
 * @param onStateChange A callback invoked with the extracted non-null state whenever it changes.
 * @param S The type of the state extracted by the lens.
 */
fun <S> subscribeToStateChanges(
    viewLifecycleOwner: LifecycleOwner,
    isExpectedState: (ApplicationState) -> Boolean,
    lens: (ApplicationState) -> S,
    onStateChange: (S) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        getStateFlow()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .filter { isExpectedState(it) }
            .map { lens(it) }
            .distinctUntilChanged()
            .collect { onStateChange(it) }
    }
}

/**
 * Adds a middleware to the application-wide Redux store.
 *
 * This utility function retrieves the Redux store from [MemosApplication] and adds the provided
 * [middleware] to it, enabling custom processing of actions before they reach the reducers.
 *
 * @param middleware The [Middleware] to add to the store for action processing.
 */
fun addMiddleware(middleware: Middleware<ApplicationState>) = MemosApplication.getInstance().getStore().addMiddleware(middleware)

/**
 * Adds a list of middlewares to the application-wide Redux store.
 *
 * This utility function iterates through the provided list of [middlewares] and adds each one to the
 * store using [addMiddleware], facilitating bulk middleware registration.
 *
 * @param middlewares The list of [Middleware][ApplicationState] instances to add to the store.
 */
fun addMiddlewares(middlewares: List<Middleware<ApplicationState>>) {
    middlewares.forEach { middleware -> addMiddleware(middleware) }
}

/**
 * Removes a middleware from the application-wide Redux store.
 *
 * This utility function retrieves the Redux store from [MemosApplication] and removes the specified
 * [middleware], stopping it from processing further actions.
 *
 * @param middleware The [Middleware] to remove from the store.
 */
fun removeMiddleware(middleware: Middleware<ApplicationState>) = MemosApplication.getInstance().getStore().removeMiddleware(middleware)

/**
 * Removes a list of middlewares from the application-wide Redux store.
 *
 * This utility function iterates through the provided list of [middlewares] and removes each one from
 * the store using [removeMiddleware], facilitating bulk middleware removal.
 *
 * @param middlewares The list of [Middleware][ApplicationState] instances to remove from the store.
 */
fun removeMiddlewares(middlewares: List<Middleware<ApplicationState>>) {
    middlewares.forEach { middleware -> removeMiddleware(middleware) }
}
