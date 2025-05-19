package com.vsulimov.memos.state

import androidx.annotation.StringRes
import com.vsulimov.memos.factory.TypeIds.TYPE_ID_SCREEN_CONFIGURE_SERVER
import com.vsulimov.memos.factory.TypeIds.TYPE_ID_SCREEN_ONBOARDING
import com.vsulimov.memos.factory.TypeIds.TYPE_ID_SCREEN_PRIVACY_POLICY
import com.vsulimov.navigation.state.NavigationComponent

/**
 * A sealed class representing the possible states of screens in the application.
 *
 * This class defines the different types of screens that can be displayed in the application. Each
 * subclass represents a specific screen state.
 *
 * @see Onboarding
 */
sealed class ScreenState : NavigationComponent {
    /**
     * Represents the onboarding screen state.
     *
     * This object is used to indicate that the onboarding screen should be displayed in the application.
     */
    data class Onboarding(
        override val typeId: String = TYPE_ID_SCREEN_ONBOARDING,
    ) : ScreenState()

    /**
     * Represents the privacy policy screen state.
     *
     * This object is used to indicate that the privacy policy screen should be displayed in the application.
     */
    data class PrivacyPolicy(
        override val typeId: String = TYPE_ID_SCREEN_PRIVACY_POLICY,
    ) : ScreenState()

    /**
     * Represents the state of the configure server screen in the application.
     *
     * This data class extends [ScreenState] and manages the state of the server configuration screen,
     * including the server URL for network communication, any associated error messages, and the visibility
     * of a loading progress indicator.
     *
     * @property typeId A unique identifier for the screen type, defaults to [TYPE_ID_SCREEN_CONFIGURE_SERVER].
     * @property serverUrl The URL of the server to be configured, defaults to an empty string.
     * @property serverUrlErrorResId The resource ID of an error message related to the server URL, or null if no error exists.
     * @property isLoadingProgressVisible A boolean indicating whether the loading progress indicator is visible, defaults to false.
     */
    data class ConfigureServer(
        override val typeId: String = TYPE_ID_SCREEN_CONFIGURE_SERVER,
        val serverUrl: String = "",
        @StringRes val serverUrlErrorResId: Int? = null,
        val isLoadingProgressVisible: Boolean = false
    ) : ScreenState()
}
