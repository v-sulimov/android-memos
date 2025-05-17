package com.vsulimov.memos.fragment.screen

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.vsulimov.memos.R
import com.vsulimov.memos.util.dispatch
import com.vsulimov.navigation.action.NavigationAction.GoBack

/**
 * A fragment displaying the privacy policy screen, allowing users to agree or disagree with the policy.
 *
 * This fragment extends [AbstractScreenFragment] to present the privacy policy UI, including a root layout
 * and buttons for agreeing or disagreeing with the policy. It handles navigation and user interactions
 * through click listeners.
 *
 * @see AbstractScreenFragment
 * @constructor Creates an instance of [PrivacyPolicyScreenFragment] with the layout resource ID [R.layout.screen_privacy_policy].
 */
class PrivacyPolicyScreenFragment : AbstractScreenFragment(R.layout.screen_privacy_policy) {

    /**
     * The root LinearLayout of the privacy policy screen layout.
     */
    private lateinit var rootLayout: LinearLayout

    /**
     * Button for users to disagree with the privacy policy, triggering a navigation back action.
     */
    private lateinit var disagreeButton: Button

    /**
     * Button for users to agree with the privacy policy. The action is not yet implemented.
     */
    private lateinit var agreeButton: Button

    /**
     * Initializes the views for the privacy policy screen by locating them in the layout.
     * This method is called during [onViewCreated] to set up [rootLayout], [disagreeButton], and [agreeButton].
     *
     * @param view The root view of the fragment's layout.
     */
    override fun findViewsById(view: View) {
        rootLayout = view.findViewById(R.id.root_layout)
        disagreeButton = view.findViewById(R.id.privacy_policy_disagree_button)
        agreeButton = view.findViewById(R.id.privacy_policy_agree_button)
    }

    /**
     * Sets click listeners for the [disagreeButton] and [agreeButton].
     * The [disagreeButton] navigates back, while the [agreeButton] action is currently unimplemented.
     */
    override fun setOnClickListeners() {
        disagreeButton.setOnClickListener { dispatch(GoBack) }
        agreeButton.setOnClickListener { TODO("Not implemented yet.") }
    }

    /**
     * Applies window insets to the privacy policy screen's [rootLayout].
     * This method uses [applyDefaultInsets] to adjust padding for system bars and display cutouts.
     */
    override fun applyWindowInsets() {
        applyDefaultInsets(rootLayout)
    }
}
