package com.vsulimov.memos.middleware

import android.content.SharedPreferences
import com.vsulimov.memos.ApplicationStateTestFactory
import com.vsulimov.memos.action.PrivacyPolicyAction
import com.vsulimov.memos.state.ScreenState.ConfigureServer
import com.vsulimov.memos.storage.PrivacyPolicyStorage
import com.vsulimov.navigation.action.NavigationAction
import com.vsulimov.redux.Action
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the [PrivacyPolicyMiddleware] class using the kotlin.test framework.
 *
 * This test suite verifies the behavior of [PrivacyPolicyMiddleware] when processing
 * [PrivacyPolicyAction.PrivacyPolicyAccepted] actions. It ensures the middleware correctly updates
 * the [PrivacyPolicyStorage] and passes the appropriate navigation action to the next middleware.
 *
 * @see PrivacyPolicyMiddleware
 * @see PrivacyPolicyAction.PrivacyPolicyAccepted
 */
class PrivacyPolicyMiddlewareTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var storage: PrivacyPolicyStorage
    private lateinit var nextAction: MutableList<Action>
    private lateinit var dispatchAction: MutableList<Action>
    private lateinit var middleware: PrivacyPolicyMiddleware

    private var isPrivacyPolicyAccepted = false

    /**
     * Sets up the test environment before each test.
     *
     * Initializes the [nextAction] and [dispatchAction] lists, creates a mock [SharedPreferences]
     * and its [Editor] to simulate storage behavior, and creates a new [PrivacyPolicyMiddleware]
     * instance with a [PrivacyPolicyStorage] using the mocked [SharedPreferences].
     */
    @BeforeTest
    fun setUp() {
        sharedPreferences = mock()
        editor = mock()

        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putBoolean(any(), any())).then {
            isPrivacyPolicyAccepted = it.getArgument<Boolean>(1)
            editor
        }
        whenever(sharedPreferences.getBoolean(any(), any())).thenReturn(isPrivacyPolicyAccepted)

        storage = PrivacyPolicyStorage(sharedPreferences)
        nextAction = mutableListOf()
        dispatchAction = mutableListOf()
        middleware = PrivacyPolicyMiddleware(storage)
    }

    /**
     * Tests that the middleware updates the storage and navigates to the server configuration screen.
     *
     * Verifies that when a [PrivacyPolicyAction.PrivacyPolicyAccepted] action is processed,
     * the [PrivacyPolicyStorage] is updated to mark the privacy policy as accepted by calling
     * [SharedPreferences.Editor.putBoolean] with the correct key and value, and a
     * [NavigationAction.NavigateTo] action with [ConfigureServer] is passed to the next middleware.
     */
    @Test
    fun `should update storage and navigate to ConfigureServer on privacy policy acceptance`() {
        val state = ApplicationStateTestFactory.createApplicationState()
        val action = PrivacyPolicyAction.PrivacyPolicyAccepted

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        verify(sharedPreferences).edit()
        verify(editor).putBoolean("com.vsulimov.memos.privacy_policy_accepted", true)
        verify(editor).apply()
        assertEquals<List<Action>>(
            listOf(NavigationAction.NavigateTo(newScreen = ConfigureServer(), addCurrentScreenToBackStack = false)),
            nextAction,
            "NavigationAction.NavigateTo(ConfigureServer()) should be passed to next middleware",
        )
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }
}
