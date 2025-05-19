package com.vsulimov.memos.middleware

import com.vsulimov.memos.action.OnboardingAction
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.memos.storage.PrivacyPolicyStorage
import com.vsulimov.navigation.action.NavigationAction.NavigateTo
import com.vsulimov.redux.Action
import com.vsulimov.redux.TypedMiddleware

/**
 * A middleware that handles the [OnboardingAction.GetStarted] action during the onboarding process.
 * This middleware checks the privacy policy acceptance status and navigates to the appropriate screen
 * based on whether the privacy policy has been accepted.
 *
 * @param privacyPolicyStorage The storage mechanism used to check if the privacy policy has been accepted.
 * @constructor Creates an instance of [OnboardingMiddleware] with the provided [PrivacyPolicyStorage].
 */
class OnboardingMiddleware(
    private val privacyPolicyStorage: PrivacyPolicyStorage,
) : TypedMiddleware<OnboardingAction.GetStarted, ApplicationState>(OnboardingAction.GetStarted::class.java) {
    /**
     * Processes the [OnboardingAction.GetStarted] action by determining the next screen to navigate to.
     * If the privacy policy is accepted, it navigates to the server configuration screen; otherwise,
     * it navigates to the privacy policy screen.
     *
     * @param action The [OnboardingAction.GetStarted] action to process.
     * @param state The current [ApplicationState] of the application.
     * @param next A function to pass the next [Action] to the middleware chain.
     * @param dispatch A function to dispatch an [Action] to the application's state management system.
     */
    override fun invokeTyped(
        action: OnboardingAction.GetStarted,
        state: ApplicationState,
        next: (Action) -> Unit,
        dispatch: (Action) -> Unit,
    ) {
        val targetScreen =
            if (privacyPolicyStorage.getPrivacyPolicyAccepted()) {
                ScreenState.ConfigureServer()
            } else {
                ScreenState.PrivacyPolicy()
            }
        next(NavigateTo(targetScreen))
    }
}
