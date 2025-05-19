package com.vsulimov.memos.action

import com.vsulimov.redux.Action

/**
 * A sealed class representing actions related to the onboarding process.
 * This class extends [Action] and serves as a base for all onboarding-related actions.
 */
sealed class OnboardingAction : Action {
    /**
     * A data object representing the action to initiate the onboarding process.
     * This action is typically used to trigger the start of the onboarding flow.
     */
    data object GetStarted : OnboardingAction()
}
