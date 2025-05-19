package com.vsulimov.memos.factory.state

import com.vsulimov.memos.state.ScreenState
import org.junit.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Test class for [ApplicationStateFactory].
 *
 * This class contains unit tests to verify that [ApplicationStateFactory] correctly creates
 * the initial [ApplicationState] with the expected [NavigationState].
 */
class ApplicationStateFactoryTest {
    /**
     * Tests that [ApplicationStateFactory.createInitialApplicationState] returns an [ApplicationState]
     * with a [NavigationState] that has the onboarding screen, an empty back stack, and no overlay.
     */
    @Test
    fun `createInitialApplicationState returns ApplicationState with correct initial NavigationState`() {
        val applicationState = ApplicationStateFactory.createInitialApplicationState()
        val navigationState = applicationState.navigationState

        assertTrue(navigationState.screen is ScreenState.Onboarding)
        assertTrue(navigationState.backStack.isEmpty())
        assertNull(navigationState.overlay)
    }
}
