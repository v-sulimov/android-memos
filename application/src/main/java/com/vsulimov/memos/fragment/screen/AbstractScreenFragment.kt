package com.vsulimov.memos.fragment.screen

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment

/**
 * An abstract base Fragment class for screen fragments that use a specified layout.
 * This class provides a template for initializing views, setting click listeners, and applying window insets.
 * Subclasses must implement [findViewsById] and [applyWindowInsets], and may override [setOnClickListeners] as needed.
 *
 * @param contentLayoutId The resource ID of the layout to be inflated for this fragment.
 * @constructor Creates an instance of [AbstractScreenFragment] with the specified layout resource ID.
 */
abstract class AbstractScreenFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId) {

    /**
     * Called after the fragment's view has been created. This method initializes the fragment by:
     * - Finding views using [findViewsById].
     * - Setting click listeners using [setOnClickListeners].
     * - Applying window insets using [applyWindowInsets].
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViewsById(view)
        setOnClickListeners()
        applyWindowInsets()
    }

    /**
     * Abstract method to find and initialize views from the fragment's layout.
     * Subclasses must implement this method to locate views (e.g., using [View.findViewById]).
     *
     * @param view The root view of the fragment's layout.
     */
    abstract fun findViewsById(view: View)

    /**
     * Abstract method to apply window insets to the fragment's views.
     * Subclasses must implement this method to handle system window insets (e.g., status bar, navigation bar).
     */
    abstract fun applyWindowInsets()

    /**
     * Applies default window insets to the specified root layout by adjusting its padding.
     * This method sets a listener to handle system bars and display cutout insets, updating the padding
     * of the [rootLayout] to account for these insets.
     *
     * @param rootLayout The root ViewGroup to which the insets will be applied.
     * @return [WindowInsetsCompat.CONSUMED] to indicate that the insets have been fully handled.
     */
    internal fun applyDefaultInsets(rootLayout: ViewGroup) {
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            view.updatePadding(
                top = insets.top + view.paddingTop,
                bottom = insets.bottom + view.paddingBottom,
                left = insets.left + view.paddingLeft,
                right = insets.right + view.paddingRight
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * Open method to set click listeners for views in the fragment.
     * Subclasses can override this method to define click listener behavior.
     * The default implementation does nothing.
     */
    open fun setOnClickListeners() {}
}
