package com.vsulimov.memos.middleware

import com.vsulimov.memos.action.PrivacyPolicyAction
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.ScreenState.ConfigureServer
import com.vsulimov.memos.storage.PrivacyPolicyStorage
import com.vsulimov.navigation.action.NavigationAction
import com.vsulimov.redux.Action
import com.vsulimov.redux.TypedMiddleware

/**
 * A middleware that handles [PrivacyPolicyAction.PrivacyPolicyAccepted] action.
 * This middleware updates the privacy policy acceptance status in the provided storage and triggers
 * navigation to the server configuration screen upon acceptance.
 *
 * @param storage The [PrivacyPolicyStorage] instance used to persist the privacy policy acceptance status.
 * @constructor Creates an instance of [PrivacyPolicyMiddleware] with the specified storage.
 */
class PrivacyPolicyMiddleware(
    private val storage: PrivacyPolicyStorage,
) : TypedMiddleware<PrivacyPolicyAction.PrivacyPolicyAccepted, ApplicationState>(PrivacyPolicyAction.PrivacyPolicyAccepted::class.java) {
    /**
     * Processes a [PrivacyPolicyAction.PrivacyPolicyAccepted] action by updating the storage and
     * dispatching a navigation action.
     * This method saves the privacy policy acceptance status as `true` in the [storage] and
     * proceeds to dispatch a [NavigationAction.NavigateTo] action to navigate to the server
     * configuration screen.
     *
     * @param action The [PrivacyPolicyAction.PrivacyPolicyAccepted] action to process.
     * @param state The current [ApplicationState] of the application.
     * @param next The function to pass the action to the next middleware or reducer in the chain.
     * @param dispatch The function to dispatch new actions to the Redux store.
     */
    override fun invokeTyped(
        action: PrivacyPolicyAction.PrivacyPolicyAccepted,
        state: ApplicationState,
        next: (Action) -> Unit,
        dispatch: (Action) -> Unit,
    ) {
        storage.setPrivacyPolicyAccepted(isAccepted = true)
        next(
            NavigationAction.NavigateTo(
                newScreen = ConfigureServer(),
                addCurrentScreenToBackStack = false,
            ),
        )
    }
}
