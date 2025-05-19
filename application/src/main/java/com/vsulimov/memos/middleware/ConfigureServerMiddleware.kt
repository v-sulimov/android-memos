package com.vsulimov.memos.middleware

import com.vsulimov.memos.R
import com.vsulimov.memos.action.ConfigureServerAction
import com.vsulimov.memos.action.ConfigureServerAction.ClearServerUrlError
import com.vsulimov.memos.action.ConfigureServerAction.Next
import com.vsulimov.memos.action.ConfigureServerAction.ServerUrlChanged
import com.vsulimov.memos.action.ConfigureServerAction.SetLoadingProgressVisibility
import com.vsulimov.memos.action.ConfigureServerAction.SetServerUrlError
import com.vsulimov.memos.lens.toConfigureServerErrorResId
import com.vsulimov.memos.lens.toConfigureServerUrl
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.validator.URLValidator
import com.vsulimov.redux.Action
import com.vsulimov.redux.TypedMiddleware

/**
 * Middleware for handling [ConfigureServerAction] actions in the Redux store.
 *
 * This middleware processes actions related to server configuration, such as validating server URLs, managing error states,
 * and controlling the visibility of a loading progress indicator. It uses [URLValidator] to ensure URLs are valid and use HTTPS,
 * dispatching appropriate actions based on validation results and state changes.
 */
class ConfigureServerMiddleware : TypedMiddleware<ConfigureServerAction, ApplicationState>(ConfigureServerAction::class.java) {
    /**
     * Processes [ConfigureServerAction] actions and delegates to specific handlers based on the action type.
     *
     * Handles the following actions:
     * - [Next]: Validates the server URL and triggers loading or error actions.
     * - [ServerUrlChanged]: Clears existing errors if the URL changes and an error exists.
     * - [SetLoadingProgressVisibility]: Updates the visibility of the loading progress indicator.
     * - [ClearServerUrlError]: Clears any existing server URL error.
     * - [SetServerUrlError]: Sets an error message for the server URL configuration.
     *
     * @param action The [ConfigureServerAction] to process.
     * @param state The current [ApplicationState] containing the server URL and error state.
     * @param next The function to pass the action to the next middleware or reducer.
     * @param dispatch The function to dispatch new actions to the Redux store.
     */
    override fun invokeTyped(
        action: ConfigureServerAction,
        state: ApplicationState,
        next: (Action) -> Unit,
        dispatch: (Action) -> Unit,
    ) {
        when (action) {
            is Next -> handleNext(state, next)
            is ServerUrlChanged -> handleServerUrlChanged(state, dispatch, next, action)
            is SetLoadingProgressVisibility -> next(action) // Passes the action to update loading progress visibility
            else -> next(action)
        }
    }

    /**
     * Handles the [Next] action by validating the server URL from the state.
     *
     * If the URL is valid, it dispatches a [SetLoadingProgressVisibility] action to show the loading indicator.
     * If invalid, it passes a [SetServerUrlError] action with an error resource ID to the next middleware.
     *
     * @param state The current [ApplicationState] containing the server URL.
     * @param next The function to pass actions to the next middleware or reducer.
     */
    private fun handleNext(
        state: ApplicationState,
        next: (Action) -> Unit,
    ) {
        val serverUrl = state.toConfigureServerUrl()
        URLValidator.validateUrl(serverUrl).fold(
            onSuccess = { normalizedUrl -> next(SetLoadingProgressVisibility(isVisible = true)) },
            onFailure = { next(SetServerUrlError(R.string.screen_configure_server_url_invalid)) },
        )
    }

    /**
     * Handles the [ServerUrlChanged] action by clearing any existing server URL error if the new URL differs
     * from the current one and an error exists in the state, then passes the action to the next middleware.
     *
     * @param state The current [ApplicationState] containing the server URL and error state.
     * @param dispatch The function to dispatch new actions to the Redux store.
     * @param next The function to pass the action to the next middleware or reducer.
     * @param action The [ServerUrlChanged] action containing the new server URL.
     */
    private fun handleServerUrlChanged(
        state: ApplicationState,
        dispatch: (Action) -> Unit,
        next: (Action) -> Unit,
        action: ServerUrlChanged,
    ) {
        if (state.toConfigureServerErrorResId() != null && action.newUrl != state.toConfigureServerUrl()) {
            dispatch(ClearServerUrlError)
        }
        next(action)
    }
}
