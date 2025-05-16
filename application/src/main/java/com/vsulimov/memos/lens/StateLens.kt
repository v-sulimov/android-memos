package com.vsulimov.memos.lens

import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState

/**
 * Converts the [ApplicationState] to its navigation state.
 *
 * This function extracts the navigation state, which contains information about the current screen,
 * overlay, and back stack, from the [ApplicationState].
 *
 * @return The navigation state contained within the [ApplicationState].
 */
fun ApplicationState.toNavigationState() = navigationState

/**
 * Converts the [ApplicationState] to its current screen state.
 *
 * This function retrieves the current screen state (e.g., [ScreenState.Onboarding])
 * from the navigation state of the [ApplicationState].
 *
 * @return The screen state from the navigation state of the [ApplicationState].
 */
fun ApplicationState.toScreenState() = navigationState.screen

/**
 * Converts the [ApplicationState] to its current overlay state.
 *
 * This function extracts the current overlay state (e.g., [OverlayState.Dialog]) from the navigation state
 * of the [ApplicationState]. The overlay state represents any active dialogs or overlays displayed on the screen.
 *
 * @return The overlay state from the navigation state of the [ApplicationState].
 */
fun ApplicationState.toOverlayState() = navigationState.overlay

/**
 * Retrieves the navigation back stack from the [ApplicationState].
 *
 * This function returns the list of screens in the navigation back stack, which represents the history
 * of screens the user has navigated through.
 *
 * @return The list of screens in the navigation back stack.
 */
fun ApplicationState.toNavigationBackStack() = navigationState.backStack
