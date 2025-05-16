package com.vsulimov.memos

import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.navigation.state.NavigationState
import com.vsulimov.stack.CopyOnWriteStack

/**
 * Factory object for creating [com.vsulimov.memos.state.ApplicationState] instances for testing purposes.
 * Provides a convenient way to construct [com.vsulimov.memos.state.ApplicationState] with customizable navigation state.
 */
object ApplicationStateTestFactory {

    /**
     * Creates an [com.vsulimov.memos.state.ApplicationState] with the specified navigation state.
     *
     * @param screenState The current screen state, defaults to [com.vsulimov.memos.state.ScreenState.Onboarding].
     * @param backStack The navigation back stack, defaults to an empty [com.vsulimov.stack.CopyOnWriteStack].
     * @param overlayState The overlay state, can be null for no overlay, defaults to null.
     * @return An [com.vsulimov.memos.state.ApplicationState] instance with the configured [com.vsulimov.navigation.state.NavigationState].
     */
    fun createApplicationState(
        screenState: ScreenState = ScreenState.Onboarding(),
        backStack: CopyOnWriteStack<ScreenState> = CopyOnWriteStack(),
        overlayState: OverlayState? = OverlayState.Dialog.EmptyDialog()
    ) = ApplicationState(
        navigationState = NavigationState<ScreenState, OverlayState>(
            screen = screenState,
            backStack = backStack,
            overlay = overlayState
        )
    )
}
