package com.vsulimov.memos.reducer

import com.vsulimov.memos.action.ActivityLifecycleAction
import com.vsulimov.memos.factory.state.ApplicationStateFactory
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.navigation.action.NavigationAction
import com.vsulimov.navigation.state.NavigationState
import com.vsulimov.navigation.state.TransitionType
import com.vsulimov.redux.Action
import com.vsulimov.stack.CopyOnWriteStack
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/**
 * Test class for [RootReducer], ensuring it correctly processes various actions
 * and updates the [ApplicationState] as expected.
 */
class RootReducerTest {

    private val rootReducer = RootReducer()

    /**
     * Tests that the [RootReducer] correctly handles a [NavigationAction.NavigateTo] by delegating
     * to the [NavigationReducer] and updating the [ApplicationState]'s [NavigationState] with the new screen
     * and updated back stack.
     */
    @Test
    fun `reduce handles NavigationAction PushScreen by updating navigation state`() {
        val initialState = ApplicationState(
            navigationState = NavigationState(
                screen = ScreenState.Onboarding(),
                backStack = CopyOnWriteStack<ScreenState>(),
                overlay = null
            )
        )

        val action = NavigationAction.NavigateTo(ScreenState.PrivacyPolicy())

        val newState = rootReducer.reduce(action, initialState)

        val expectedBackStack = CopyOnWriteStack<ScreenState>(listOf(ScreenState.Onboarding()))
        val expectedNavigationState = NavigationState<ScreenState, OverlayState>(
            screen = ScreenState.PrivacyPolicy(),
            backStack = expectedBackStack,
            overlay = null,
            transitionType = TransitionType.FORWARD
        )
        assertEquals(expectedNavigationState, newState.navigationState)
        assertEquals(initialState.copy(navigationState = expectedNavigationState), newState)
    }

    /**
     * Tests that the [RootReducer] correctly handles an [ActivityLifecycleAction.OnDestroy] with
     * isFinishing = true by delegating to the [ActivityLifecycleReducer] and resetting the
     * [ApplicationState] to its initial default state.
     */
    @Test
    fun `reduce handles ActivityLifecycleAction OnDestroy with isFinishing true by resetting state`() {
        val initialState = ApplicationState(
            navigationState = NavigationState(
                screen = ScreenState.PrivacyPolicy(),
                backStack = CopyOnWriteStack<ScreenState>().apply { push(ScreenState.Onboarding()) },
                overlay = null
            )
        )
        val action = ActivityLifecycleAction.OnDestroy(isFinishing = true)

        val newState = rootReducer.reduce(action, initialState)

        val expectedState = ApplicationStateFactory.createInitialApplicationState()
        assertEquals(expectedState, newState)
    }

    /**
     * Tests that the [RootReducer] returns the current [ApplicationState] unchanged when processing
     * an action that is neither a [NavigationAction] nor an [ActivityLifecycleAction].
     */
    @Test
    fun `reduce returns unchanged state for unhandled actions`() {
        val initialState = ApplicationState(
            navigationState = NavigationState(
                screen = ScreenState.Onboarding(),
                backStack = CopyOnWriteStack(),
                overlay = null
            )
        )

        val action = object : Action {}

        val newState = rootReducer.reduce(action, initialState)

        assertSame(initialState, newState)
    }
}
