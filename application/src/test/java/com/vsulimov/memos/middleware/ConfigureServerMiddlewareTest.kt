package com.vsulimov.memos.middleware

import com.vsulimov.memos.ApplicationStateTestFactory
import com.vsulimov.memos.R
import com.vsulimov.memos.action.ConfigureServerAction
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.memos.validator.URLValidator
import com.vsulimov.redux.Action
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the [ConfigureServerMiddleware] class using the kotlin.test framework.
 *
 * This test suite verifies the behavior of [ConfigureServerMiddleware] when processing [ConfigureServerAction] actions,
 * including [ConfigureServerAction.Next], [ConfigureServerAction.ServerUrlChanged],
 * [ConfigureServerAction.SetLoadingProgressVisibility], [ConfigureServerAction.ClearServerUrlError],
 * and [ConfigureServerAction.SetServerUrlError]. It ensures the middleware correctly validates URLs using [URLValidator],
 * manages server URL error states, controls loading progress visibility, and dispatches or passes actions appropriately.
 *
 * @see ConfigureServerMiddleware
 * @see ConfigureServerAction
 * @see URLValidator
 */
class ConfigureServerMiddlewareTest {
    private lateinit var urlValidator: URLValidator
    private lateinit var nextAction: MutableList<Action>
    private lateinit var dispatchAction: MutableList<Action>
    private lateinit var middleware: ConfigureServerMiddleware

    /**
     * Sets up the test environment before each test.
     *
     * Initializes empty lists for [nextAction] and [dispatchAction], creates a mock [URLValidator],
     * and instantiates a new [ConfigureServerMiddleware].
     */
    @BeforeTest
    fun setUp() {
        urlValidator = mock()
        nextAction = mutableListOf()
        dispatchAction = mutableListOf()
        middleware = ConfigureServerMiddleware()
    }

    /**
     * Tests that unhandled actions are passed to the next middleware without dispatching.
     *
     * Verifies that a [ConfigureServerAction.ClearServerUrlError] action is passed to the next middleware
     * and no actions are dispatched.
     */
    @Test
    fun `should pass unhandled action to next middleware`() {
        val state = ApplicationStateTestFactory.createApplicationState()
        val action = ConfigureServerAction.ClearServerUrlError

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(listOf(action), nextAction, "Action should be passed to next middleware")
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }

    /**
     * Tests that an invalid URL triggers a SetServerUrlError action.
     *
     * Verifies that when a [ConfigureServerAction.Next] action is processed with an invalid URL,
     * the middleware calls [URLValidator.validateUrl], receives a failure, and passes a
     * [ConfigureServerAction.SetServerUrlError] action to the next middleware.
     */
    @Test
    fun `should pass SetServerUrlError to next middleware when URL is invalid`() {
        val invalidUrl = "invalid-url"
        val state =
            ApplicationStateTestFactory.createApplicationState(
                ScreenState.ConfigureServer(serverUrl = invalidUrl),
            )
        val action = ConfigureServerAction.Next
        whenever(urlValidator.validateUrl(invalidUrl)).thenReturn(Result.failure(Exception("Invalid URL")))

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(
            listOf(ConfigureServerAction.SetServerUrlError(R.string.screen_configure_server_url_invalid)),
            nextAction,
            "SetServerUrlError should be passed to next middleware",
        )
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }

    /**
     * Tests that a valid URL triggers a SetLoadingProgressVisibility action.
     *
     * Verifies that when a [ConfigureServerAction.Next] action is processed with a valid URL,
     * the middleware calls [URLValidator.validateUrl], receives a success, and passes a
     * [ConfigureServerAction.SetLoadingProgressVisibility] action with `isVisible = true` to the next middleware.
     */
    @Test
    fun `should pass SetLoadingProgressVisibility to next middleware when URL is valid`() {
        val validUrl = "https://test-url.com"
        val normalizedUrl = "https://test-url.com/"
        val state =
            ApplicationStateTestFactory.createApplicationState(
                ScreenState.ConfigureServer(serverUrl = validUrl),
            )
        val action = ConfigureServerAction.Next
        whenever(urlValidator.validateUrl(validUrl)).thenReturn(Result.success(normalizedUrl))

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(
            listOf(ConfigureServerAction.SetLoadingProgressVisibility(isVisible = true)),
            nextAction,
            "SetLoadingProgressVisibility should be passed to next middleware",
        )
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }

    /**
     * Tests that changing the URL with an existing error dispatches ClearServerUrlError.
     *
     * Verifies that when a [ConfigureServerAction.ServerUrlChanged] action is processed with a new URL
     * and an error exists in the state, the middleware dispatches [ConfigureServerAction.ClearServerUrlError]
     * and passes the original action to the next middleware.
     */
    @Test
    fun `should dispatch ClearServerUrlError and pass ServerUrlChanged when error exists and URL changes`() {
        val oldUrl = "http://old-url.com"
        val newUrl = "http://new-url.com"
        val state =
            ApplicationStateTestFactory.createApplicationState(
                screenState =
                    ScreenState.ConfigureServer(
                        serverUrl = oldUrl,
                        serverUrlErrorResId = R.string.screen_configure_server_url_invalid,
                    ),
            )
        val action = ConfigureServerAction.ServerUrlChanged(newUrl)

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(listOf(action), nextAction, "ServerUrlChanged should be passed to next middleware")
        assertEquals<List<Action>>(
            listOf(ConfigureServerAction.ClearServerUrlError),
            dispatchAction,
            "ClearServerUrlError should be dispatched",
        )
    }

    /**
     * Tests that no ClearServerUrlError is dispatched when no error exists.
     *
     * Verifies that when a [ConfigureServerAction.ServerUrlChanged] action is processed and no error
     * exists in the state, the middleware only passes the action to the next middleware.
     */
    @Test
    fun `should not dispatch ClearServerUrlError when no error exists`() {
        val url = "http://test-url.com"
        val state =
            ApplicationStateTestFactory.createApplicationState(
                screenState = ScreenState.ConfigureServer(serverUrl = url),
            )
        val action = ConfigureServerAction.ServerUrlChanged(url)

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(listOf(action), nextAction, "ServerUrlChanged should be passed to next middleware")
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }

    /**
     * Tests that no ClearServerUrlError is dispatched when the URL is unchanged.
     *
     * Verifies that when a [ConfigureServerAction.ServerUrlChanged] action is processed with the same URL
     * as in the state, even if an error exists, the middleware only passes the action to the next middleware.
     */
    @Test
    fun `should not dispatch ClearServerUrlError when URL is unchanged`() {
        val url = "http://test-url.com"
        val state =
            ApplicationStateTestFactory.createApplicationState(
                screenState =
                    ScreenState.ConfigureServer(
                        serverUrl = url,
                        serverUrlErrorResId = R.string.screen_configure_server_url_invalid,
                    ),
            )
        val action = ConfigureServerAction.ServerUrlChanged(url)

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(listOf(action), nextAction, "ServerUrlChanged should be passed to next middleware")
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }

    /**
     * Tests that SetLoadingProgressVisibility is passed to the next middleware.
     *
     * Verifies that when a [ConfigureServerAction.SetLoadingProgressVisibility] action is processed,
     * the middleware passes the action to the next middleware without dispatching additional actions.
     */
    @Test
    fun `should pass SetLoadingProgressVisibility to next middleware`() {
        val state = ApplicationStateTestFactory.createApplicationState()
        val action = ConfigureServerAction.SetLoadingProgressVisibility(isVisible = true)

        middleware.invokeTyped(
            action = action,
            state = state,
            next = { nextAction.add(it) },
            dispatch = { dispatchAction.add(it) },
        )

        assertEquals<List<Action>>(listOf(action), nextAction, "SetLoadingProgressVisibility should be passed to next middleware")
        assertEquals(emptyList(), dispatchAction, "No actions should be dispatched")
    }
}
