package com.vsulimov.memos.fragment.screen

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.vsulimov.memos.R
import com.vsulimov.memos.action.PrivacyPolicyAction.PrivacyPolicyAccepted
import com.vsulimov.memos.factory.TypeIds
import com.vsulimov.memos.factory.middleware.PrivacyPolicyMiddlewareFactory
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.util.dispatch
import com.vsulimov.navigation.action.NavigationAction.GoBack
import com.vsulimov.redux.Middleware

/**
 * A fragment that displays the privacy policy screen, allowing users to agree or disagree with the policy.
 *
 * This fragment extends [AbstractScreenFragment] to present the privacy policy UI, including a root layout
 * and buttons for agreeing or disagreeing with the policy. It implements [ScreenMiddlewaresProvider] to
 * provide screen-specific middlewares for state management and handles user interactions through click listeners.
 *
 * @see AbstractScreenFragment
 * @see ScreenMiddlewaresProvider
 * @constructor Creates an instance of [PrivacyPolicyScreenFragment] with the layout resource ID [R.layout.screen_privacy_policy].
 */
class PrivacyPolicyScreenFragment :
    AbstractScreenFragment(R.layout.screen_privacy_policy),
    ScreenMiddlewaresProvider {
    /**
     * The root [LinearLayout] of the privacy policy screen layout.
     */
    private lateinit var rootLayout: LinearLayout

    /**
     * Button for users to disagree with the privacy policy, triggering a navigation back action.
     */
    private lateinit var disagreeButton: Button

    /**
     * Button for users to agree with the privacy policy, triggering navigation to the server configuration screen.
     */
    private lateinit var agreeButton: Button

    /**
     * Initializes the views for the privacy policy screen by locating them in the layout.
     * Sets up [rootLayout], [disagreeButton], and [agreeButton] using their respective IDs from the layout.
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
     * The [disagreeButton] dispatches a [GoBack] action to navigate back, while the [agreeButton]
     * dispatches a [PrivacyPolicyAccepted] action to navigate to the server configuration screen.
     */
    override fun setOnClickListeners() {
        disagreeButton.setOnClickListener { dispatch(GoBack) }
        agreeButton.setOnClickListener { dispatch(PrivacyPolicyAccepted) }
    }

    /**
     * Applies window insets to the privacy policy screen's [rootLayout].
     * Uses [applyDefaultInsets] to adjust padding for system bars and display cutouts.
     */
    override fun applyWindowInsets() {
        applyDefaultInsets(rootLayout)
    }

    /**
     * Provides a unique identifier for the privacy policy screen.
     *
     * @return A string representing the screen's unique tag, defined as [TypeIds.TYPE_ID_SCREEN_PRIVACY_POLICY].
     */
    override fun provideTag(): String = TypeIds.TYPE_ID_SCREEN_PRIVACY_POLICY

    /**
     * Provides the list of middlewares for the privacy policy screen.
     *
     * @return A list containing a single middleware created by [PrivacyPolicyMiddlewareFactory.createPrivacyPolicyMiddleware]
     *         using the fragment's context.
     */
    override fun provideScreenMiddlewares(): List<Middleware<ApplicationState>> =
        listOf(PrivacyPolicyMiddlewareFactory.createPrivacyPolicyMiddleware(requireContext()))
}
