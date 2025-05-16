package com.vsulimov.memos.state

import com.vsulimov.memos.factory.TypeIds.TYPE_ID_SCREEN_ONBOARDING
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
    data class Onboarding(override val typeId: String = TYPE_ID_SCREEN_ONBOARDING) : ScreenState()
}
