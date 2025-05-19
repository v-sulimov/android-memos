package com.vsulimov.memos.lens

import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState

/**
 * Extracts the navigation state from the [ApplicationState].
 *
 * This function retrieves the navigation state, which encapsulates information about the current screen,
 * any active overlay, and the navigation back stack, from the provided [ApplicationState].
 *
 * @return The navigation state contained within the [ApplicationState].
 */
fun ApplicationState.toNavigationState() = navigationState

/**
 * Retrieves the current screen state from the [ApplicationState].
 *
 * This function extracts the current screen state (e.g., [ScreenState.Onboarding] or [ScreenState.ConfigureServer])
 * from the navigation state of the [ApplicationState]. The screen state represents the currently displayed screen.
 *
 * @return The [ScreenState] from the navigation state of the [ApplicationState].
 */
fun ApplicationState.toScreenState() = navigationState.screen

/**
 * Retrieves the current overlay state from the [ApplicationState].
 *
 * This function extracts the overlay state (e.g., [OverlayState.Dialog] or null if no overlay is active) from the
 * navigation state of the [ApplicationState]. The overlay state represents any active dialogs or overlays displayed
 * over the current screen.
 *
 * @return The [OverlayState] from the navigation state of the [ApplicationState], or null if no overlay is present.
 */
fun ApplicationState.toOverlayState() = navigationState.overlay

/**
 * Retrieves the navigation back stack from the [ApplicationState].
 *
 * This function returns the list of screens in the navigation back stack, representing the history of screens the
 * user has navigated through. The back stack is part of the navigation state in the [ApplicationState].
 *
 * @return The list of [ScreenState] instances in the navigation back stack.
 */
fun ApplicationState.toNavigationBackStack() = navigationState.backStack

/**
 * Extracts the server configuration screen state from the [ApplicationState].
 *
 * This function retrieves the current screen state from the [ApplicationState]'s navigation state and casts it to
 * [ScreenState.ConfigureServer]. It is used to access the server configuration screen state, which includes details
 * such as the server URL and any associated error state.
 *
 * @return The [ScreenState.ConfigureServer] representing the current server configuration screen state.
 * @throws ClassCastException if the current screen state is not [ScreenState.ConfigureServer].
 */
fun ApplicationState.toConfigureServerScreenState() = toScreenState() as ScreenState.ConfigureServer

/**
 * Retrieves the server URL from the [ApplicationState]'s server configuration screen state.
 *
 * This function extracts the [ScreenState.ConfigureServer] from the [ApplicationState] using
 * [toConfigureServerScreenState] and returns the server URL stored in that state.
 *
 * @return The server URL string from the [ScreenState.ConfigureServer] state.
 * @throws ClassCastException if the current screen state is not [ScreenState.ConfigureServer].
 */
fun ApplicationState.toConfigureServerUrl() = toConfigureServerScreenState().serverUrl

/**
 * Retrieves the server URL error resource ID from the [ApplicationState]'s server configuration screen state.
 *
 * This function extracts the [ScreenState.ConfigureServer] from the [ApplicationState] using
 * [toConfigureServerScreenState] and returns the error resource ID (if any) associated with the server URL.
 *
 * @return The error resource ID (e.g., [Int] referencing a string resource) from the [ScreenState.ConfigureServer]
 *         state, or null if no error is present.
 * @throws ClassCastException if the current screen state is not [ScreenState.ConfigureServer].
 */
fun ApplicationState.toConfigureServerErrorResId() = toConfigureServerScreenState().serverUrlErrorResId

/**
 * Retrieves the loading progress indicator visibility from the [ApplicationState]'s server configuration screen state.
 *
 * This function extracts the [ScreenState.ConfigureServer] from the [ApplicationState] using
 * [toConfigureServerScreenState] and returns the visibility state of the loading progress indicator.
 *
 * @return A boolean indicating whether the loading progress indicator is visible in the [ScreenState.ConfigureServer] state.
 * @throws ClassCastException if the current screen state is not [ScreenState.ConfigureServer].
 */
fun ApplicationState.toConfigureServerLoadingIndicatorVisibility() = toConfigureServerScreenState().isLoadingProgressVisible
