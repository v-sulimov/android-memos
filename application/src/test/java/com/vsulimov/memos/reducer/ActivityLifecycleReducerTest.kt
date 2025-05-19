package com.vsulimov.memos.reducer

import com.vsulimov.memos.action.ActivityLifecycleAction
import com.vsulimov.memos.factory.state.ApplicationStateFactory
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.navigation.state.NavigationState
import com.vsulimov.stack.CopyOnWriteStack
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/**
 * Test class for [ActivityLifecycleReducer], ensuring it correctly processes
 * [ActivityLifecycleAction.OnDestroy] actions and updates the [ApplicationState] accordingly.
 */
class ActivityLifecycleReducerTest {
    /**
     * Tests that the [ActivityLifecycleReducer] resets the [ApplicationState] to its initial state
     * when handling an [ActivityLifecycleAction.OnDestroy] with [isFinishing] set to true.
     */
    @Test
    fun `reduceTyped resets state to initial when OnDestroy with isFinishing true`() {
        val initialState = ApplicationStateFactory.createInitialApplicationState()
        val customBackStack = CopyOnWriteStack<ScreenState>(listOf(ScreenState.Onboarding()))
        val customNavigationState =
            NavigationState<ScreenState, OverlayState>(
                screen = ScreenState.PrivacyPolicy(),
                backStack = customBackStack,
                overlay = OverlayState.Dialog.EmptyDialog(),
            )
        val customState = ApplicationState(navigationState = customNavigationState)
        val action = ActivityLifecycleAction.OnDestroy(isFinishing = true)

        val newState = ActivityLifecycleReducer.reduceTyped(action, customState)

        assertEquals(initialState, newState)
    }

    /**
     * Tests that the [ActivityLifecycleReducer] returns the current [ApplicationState] unchanged
     * when handling an [ActivityLifecycleAction.OnDestroy] with [isFinishing] set to false.
     */
    @Test
    fun `reduceTyped returns same state when OnDestroy with isFinishing false`() {
        val customBackStack = CopyOnWriteStack<ScreenState>(listOf(ScreenState.Onboarding()))
        val customNavigationState =
            NavigationState<ScreenState, OverlayState>(
                screen = ScreenState.PrivacyPolicy(),
                backStack = customBackStack,
                overlay = OverlayState.Dialog.EmptyDialog(),
            )
        val customState = ApplicationState(navigationState = customNavigationState)
        val action = ActivityLifecycleAction.OnDestroy(isFinishing = false)

        val newState = ActivityLifecycleReducer.reduceTyped(action, customState)

        assertSame(customState, newState)
    }
}
