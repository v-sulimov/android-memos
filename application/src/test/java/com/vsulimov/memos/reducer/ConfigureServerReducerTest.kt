package com.vsulimov.memos.reducer

import com.vsulimov.memos.action.ConfigureServerAction
import com.vsulimov.memos.state.ScreenState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Test class for [ConfigureServerReducer], ensuring it correctly processes
 * [ConfigureServerAction] variants and updates [ScreenState.ConfigureServer] accordingly.
 *
 * This test suite verifies the reducer's handling of:
 * - [ConfigureServerAction.ServerUrlChanged]: Updates the server URL.
 * - [ConfigureServerAction.SetServerUrlError]: Sets the error resource ID.
 * - [ConfigureServerAction.ClearServerUrlError]: Clears the error resource ID.
 * - [ConfigureServerAction.Next]: Leaves the state unchanged.
 * - [ConfigureServerAction.SetLoadingProgressVisibility]: Updates the loading progress visibility.
 */
class ConfigureServerReducerTest {
    /**
     * Tests that the [ConfigureServerReducer] updates the [ScreenState.ConfigureServer] with the new server URL
     * when handling a [ConfigureServerAction.ServerUrlChanged] action.
     */
    @Test
    fun `reduceTyped updates serverUrl when ServerUrlChanged action is received`() {
        val initialState = ScreenState.ConfigureServer(serverUrl = "https://old-url.com", serverUrlErrorResId = null)
        val newUrl = "https://new-url.com"
        val action = ConfigureServerAction.ServerUrlChanged(newUrl)

        val newState = ConfigureServerReducer.reduceTyped(action, initialState)

        assertEquals(newUrl, newState.serverUrl, "Server URL should be updated to the new URL")
        assertNull(newState.serverUrlErrorResId, "Error resource ID should remain null")
        assertEquals(ScreenState.ConfigureServer(serverUrl = newUrl, serverUrlErrorResId = null), newState)
    }

    /**
     * Tests that the [ConfigureServerReducer] sets the error resource ID
     * when handling a [ConfigureServerAction.SetServerUrlError] action.
     */
    @Test
    fun `reduceTyped sets serverUrlErrorResId when SetServerUrlError action is received`() {
        val initialState = ScreenState.ConfigureServer(serverUrl = "https://memos.com", serverUrlErrorResId = null)
        val errorResId = 1234
        val action = ConfigureServerAction.SetServerUrlError(serverUrlErrorResId = errorResId)

        val newState = ConfigureServerReducer.reduceTyped(action, initialState)

        assertEquals(initialState.serverUrl, newState.serverUrl, "Server URL should remain unchanged")
        assertEquals(errorResId, newState.serverUrlErrorResId, "Error resource ID should be set")
        assertEquals(
            ScreenState.ConfigureServer(serverUrl = initialState.serverUrl, serverUrlErrorResId = errorResId),
            newState,
        )
    }

    /**
     * Tests that the [ConfigureServerReducer] clears the error resource ID
     * when handling a [ConfigureServerAction.ClearServerUrlError] action.
     */
    @Test
    fun `reduceTyped clears serverUrlErrorResId when ClearServerUrlError action is received`() {
        val initialState = ScreenState.ConfigureServer(serverUrl = "https://memos.com", serverUrlErrorResId = 1234)
        val action = ConfigureServerAction.ClearServerUrlError

        val newState = ConfigureServerReducer.reduceTyped(action, initialState)

        assertEquals(initialState.serverUrl, newState.serverUrl, "Server URL should remain unchanged")
        assertNull(newState.serverUrlErrorResId, "Error resource ID should be cleared")
        assertEquals(
            ScreenState.ConfigureServer(serverUrl = initialState.serverUrl, serverUrlErrorResId = null),
            newState,
        )
    }

    /**
     * Tests that the [ConfigureServerReducer] does not modify the state
     * when handling a [ConfigureServerAction.Next] action.
     */
    @Test
    fun `reduceTyped does not modify state when Next action is received`() {
        val initialState = ScreenState.ConfigureServer(serverUrl = "https://memos.com", serverUrlErrorResId = 1234)
        val action = ConfigureServerAction.Next

        val newState = ConfigureServerReducer.reduceTyped(action, initialState)

        assertEquals(initialState, newState, "State should remain unchanged for Next action")
    }

    /**
     * Tests that the [ConfigureServerReducer] updates the loading progress visibility
     * when handling a [ConfigureServerAction.SetLoadingProgressVisibility] action.
     */
    @Test
    fun `reduceTyped updates isLoadingProgressVisible when SetLoadingProgressVisibility action is received`() {
        val initialState = ScreenState.ConfigureServer(
            serverUrl = "https://memos.com",
            serverUrlErrorResId = 1234,
            isLoadingProgressVisible = false
        )
        val action = ConfigureServerAction.SetLoadingProgressVisibility(isVisible = true)

        val newState = ConfigureServerReducer.reduceTyped(action, initialState)

        assertEquals(initialState.serverUrl, newState.serverUrl, "Server URL should remain unchanged")
        assertEquals(initialState.serverUrlErrorResId, newState.serverUrlErrorResId, "Error resource ID should remain unchanged")
        assertEquals(true, newState.isLoadingProgressVisible, "Loading progress visibility should be updated to true")
        assertEquals(
            ScreenState.ConfigureServer(
                serverUrl = initialState.serverUrl,
                serverUrlErrorResId = initialState.serverUrlErrorResId,
                isLoadingProgressVisible = true
            ),
            newState,
        )
    }
}
