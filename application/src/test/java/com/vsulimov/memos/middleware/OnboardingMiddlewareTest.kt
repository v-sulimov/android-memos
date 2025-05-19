package com.vsulimov.memos.middleware

import com.vsulimov.memos.ApplicationStateTestFactory
import com.vsulimov.memos.action.OnboardingAction
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.memos.storage.PrivacyPolicyStorage
import com.vsulimov.navigation.action.NavigationAction.NavigateTo
import com.vsulimov.redux.Action
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the [OnboardingMiddleware] class using the kotlin.test framework.
 *
 * This test suite verifies the behavior of [OnboardingMiddleware] when processing [OnboardingAction.GetStarted]
 * actions. It ensures the middleware navigates to the correct screen based on the privacy policy acceptance
 * status stored in [PrivacyPolicyStorage].
 *
 * @see OnboardingMiddleware
 * @see OnboardingAction.GetStarted
 */
class OnboardingMiddlewareTest {
    private lateinit var privacyPolicyStorage: PrivacyPolicyStorage
    private lateinit var nextAction: MutableList<Action>
    private lateinit var dispatchAction: MutableList<Action>
    private lateinit var middleware: OnboardingMiddleware

    /**
     * Sets up the test environment before each test.
     *
     * Initializes the [nextAction] and [dispatchAction] lists, creates a mock [PrivacyPolicyStorage],
     * and creates a new [OnboardingMiddleware] instance with the mock storage.
     */
    @BeforeTest
    fun setUp() {
        nextAction = mutableListOf()
        dispatchAction = mutableListOf()
        privacyPolicyStorage = mock()
        middleware = OnboardingMiddleware(privacyPolicyStorage)
    }

    /**
     * Tests that the middleware navigates to the ConfigureServer screen when the privacy policy is accepted.
     *
     * Verifies that when a [OnboardingAction.GetStarted] action is processed and the privacy policy is accepted,
     * the middleware passes a [NavigateTo] action with [ScreenState.ConfigureServer] to the next middleware.
     */
    @Test
    fun `should navigate to ConfigureServer when privacy policy is accepted`() {
        whenever(privacyPolicyStorage.getPrivacyPolicyAccepted()).thenReturn(true)
        val state = ApplicationStateTestFactory.createApplicationState()
        val action = OnboardingAction.GetStarted

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(
            listOf(NavigateTo(ScreenState.ConfigureServer())),
            nextAction,
            "Should navigate to ConfigureServer screen",
        )
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }

    /**
     * Tests that the middleware navigates to the PrivacyPolicy screen when the privacy policy is not accepted.
     *
     * Verifies that when a [OnboardingAction.GetStarted] action is processed and the privacy policy is not accepted,
     * the middleware passes a [NavigateTo] action with [ScreenState.PrivacyPolicy] to the next middleware.
     */
    @Test
    fun `should navigate to PrivacyPolicy when privacy policy is not accepted`() {
        whenever(privacyPolicyStorage.getPrivacyPolicyAccepted()).thenReturn(false)
        val state = ApplicationStateTestFactory.createApplicationState()
        val action = OnboardingAction.GetStarted

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(
            listOf(NavigateTo(ScreenState.PrivacyPolicy())),
            nextAction,
            "Should navigate to PrivacyPolicy screen",
        )
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }
}
