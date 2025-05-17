package com.vsulimov.memos.lens

import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.navigation.state.NavigationState
import com.vsulimov.stack.CopyOnWriteStack
import org.junit.Test
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

/**
 * Test class for extension functions of [ApplicationState].
 *
 * This class contains unit tests to verify that the extension functions correctly extract
 * the navigation state, screen state, overlay state, and back stack from [ApplicationState].
 */
class StateLensTest {

    /**
     * Tests that [ApplicationState.toNavigationState] returns the navigationState from ApplicationState.
     */
    @Test
    fun `toNavigationState returns the navigationState from ApplicationState`() {
        val navigationState: NavigationState<ScreenState, OverlayState> = mock()
        val applicationState = ApplicationState(navigationState)
        assertEquals(navigationState, applicationState.toNavigationState())
    }

    /**
     * Tests that [ApplicationState.toScreenState] returns the screen from navigationState.
     */
    @Test
    fun `toScreenState returns the screen from navigationState`() {
        val screenState: ScreenState = mock()
        val navigationState: NavigationState<ScreenState, OverlayState> = mock {
            on { screen }.thenReturn(screenState)
        }
        val applicationState = ApplicationState(navigationState)
        assertEquals(screenState, applicationState.toScreenState())
    }

    /**
     * Tests that [ApplicationState.toOverlayState] returns the overlay from navigationState.
     */
    @Test
    fun `toOverlayState returns the overlay from navigationState`() {
        val overlayState: OverlayState = mock()
        val navigationState: NavigationState<ScreenState, OverlayState> = mock {
            on { overlay }.thenReturn(overlayState)
        }
        val applicationState = ApplicationState(navigationState)
        assertEquals(overlayState, applicationState.toOverlayState())
    }

    /**
     * Tests that [ApplicationState.toNavigationBackStack] returns the backStack from navigationState.
     */
    @Test
    fun `toNavigationBackStack returns the backStack from navigationState`() {
        val backStackState: CopyOnWriteStack<ScreenState> = mock()
        val navigationState: NavigationState<ScreenState, OverlayState> = mock {
            on { backStack }.thenReturn(backStackState)
        }
        val applicationState = ApplicationState(navigationState)
        assertEquals(backStackState, applicationState.toNavigationBackStack())
    }
}
