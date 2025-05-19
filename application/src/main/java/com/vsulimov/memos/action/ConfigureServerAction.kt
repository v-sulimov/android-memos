package com.vsulimov.memos.action

import androidx.annotation.StringRes
import com.vsulimov.redux.Action

/**
 * A sealed class representing actions related to server configuration.
 * This class extends [Action] and serves as a base for all server configuration-related actions.
 */
sealed class ConfigureServerAction : Action {
    /**
     * A data class representing the action of changing the server URL.
     * This action is used to update the server URL with a new value.
     *
     * @property newUrl The new server URL to be set.
     */
    data class ServerUrlChanged(
        val newUrl: String,
    ) : ConfigureServerAction()

    /**
     * An object representing the action to proceed to the next step in the server configuration process.
     */
    object Next : ConfigureServerAction()

    /**
     * A data class representing the action of setting an error message for the server URL configuration.
     * This action is used to display an error when the server URL is invalid or cannot be set.
     *
     * @property serverUrlErrorResId The resource ID of the error message string to be displayed.
     */
    data class SetServerUrlError(
        @StringRes val serverUrlErrorResId: Int,
    ) : ConfigureServerAction()

    /**
     * An object representing the action to clear any existing error message related to the server URL configuration.
     */
    object ClearServerUrlError : ConfigureServerAction()

    /**
     * A data class representing the action of setting the visibility of a loading progress indicator.
     * This action is used to show or hide the loading progress indicator during server configuration.
     *
     * @property isVisible A boolean indicating whether the loading progress indicator should be visible (true) or hidden (false).
     */
    data class SetLoadingProgressVisibility(
        val isVisible: Boolean
    ) : ConfigureServerAction()
}
