package com.vsulimov.memos.action

import com.vsulimov.redux.Action

/**
 * A sealed class representing actions related to the privacy policy in the application.
 * This class serves as a base for specific privacy policy actions and extends the [Action] interface.
 */
sealed class PrivacyPolicyAction : Action {
    /**
     * An action indicating that the user has accepted the privacy policy.
     * This object can be dispatched to update the application state when the user
     * confirms their acceptance of the privacy policy.
     */
    data object PrivacyPolicyAccepted : PrivacyPolicyAction()
}
