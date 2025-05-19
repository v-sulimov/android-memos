package com.vsulimov.memos.state

import com.vsulimov.navigation.state.NavigationComponent

/**
 * A sealed class representing the possible states of overlays in the application.
 *
 * This class defines the different types of overlay UI components that can be displayed, such as dialogs
 * or bottom sheets.
 *
 * @see Dialog
 * @see BottomSheet
 */
sealed class OverlayState : NavigationComponent {
    /**
     * Represents a dialog overlay state.
     *
     * This object is used to indicate that a dialog should be displayed as an overlay in the application.
     */
    sealed class Dialog : OverlayState() {
        /**
         * Represents an empty dialog state. For testing purposes only.
         */
        data class EmptyDialog(
            override val typeId: String = "Empty Dialog",
        ) : Dialog()
    }

    /**
     * Represents the state of a bottom sheet overlay in the application.
     *
     * A [BottomSheet] is a type of [OverlayState] used to manage the display of bottom sheet UI components.
     * This sealed class defines the possible states a bottom sheet can have, ensuring type safety and
     * enabling exhaustive handling of all bottom sheet states in when expressions.
     *
     * Bottom sheets are typically used to present temporary, modal content that slides up from the bottom
     * of the screen, such as forms, menus, or informational panels.
     */
    sealed class BottomSheet : OverlayState()
}
