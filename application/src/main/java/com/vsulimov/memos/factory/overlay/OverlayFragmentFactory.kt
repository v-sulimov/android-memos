package com.vsulimov.memos.factory.overlay

import androidx.fragment.app.DialogFragment
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.navigation.factory.OverlayFragmentFactory

/**
 * A factory class responsible for creating overlay fragments based on the provided [OverlayState].
 *
 * This class implements the [OverlayFragmentFactory] interface to generate [DialogFragment] instances
 * corresponding to specific overlay states. It is used within the navigation system to instantiate
 * overlay UI components dynamically.
 *
 * @see OverlayFragmentFactory
 * @see OverlayState
 * @see DialogFragment
 */
class OverlayFragmentFactory : OverlayFragmentFactory<OverlayState> {
    /**
     * Creates a [DialogFragment] for the specified [OverlayState].
     *
     * This method is responsible for mapping an [OverlayState] to its corresponding [DialogFragment].
     * If the provided overlay state is not recognized, an [IllegalArgumentException] is thrown.
     *
     * @param overlay The [OverlayState] defining the overlay to be created.
     * @return A [DialogFragment] corresponding to the specified overlay state.
     * @throws IllegalArgumentException If the overlay state is unknown or unsupported.
     */
    override fun createOverlayFragment(overlay: OverlayState): DialogFragment =
        throw IllegalArgumentException("Unknown overlay state ${overlay::class.simpleName}")

    /**
     * Retrieves the state type ID for a given [DialogFragment].
     *
     * This method maps a [DialogFragment] to its corresponding state type identifier, which is used
     * to associate the fragment with its [OverlayState]. If the fragment type is not recognized,
     * an [IllegalArgumentException] is thrown.
     *
     * @param dialogFragment The [DialogFragment] for which to retrieve the state type ID.
     * @return A [String] representing the state type ID for the given fragment.
     * @throws IllegalArgumentException If the dialog fragment type is unknown or unsupported.
     */
    override fun getStateTypeIdForOverlay(dialogFragment: DialogFragment): String =
        when (dialogFragment) {
            else -> throw IllegalArgumentException("Unknown overlay type ${dialogFragment::class.simpleName}")
        }
}
