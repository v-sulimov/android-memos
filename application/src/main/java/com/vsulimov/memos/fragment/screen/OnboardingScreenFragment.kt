package com.vsulimov.memos.fragment.screen

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.vsulimov.memos.R
import com.vsulimov.memos.state.ScreenState.PrivacyPolicy
import com.vsulimov.memos.util.dispatch
import com.vsulimov.navigation.action.NavigationAction.NavigateTo

/**
 * A fragment that displays the onboarding screen for the application.
 *
 * This fragment is responsible for presenting the initial onboarding UI, including a title, description,
 * and a "Get Started" button. It extends [AbstractScreenFragment] to leverage common fragment setup logic.
 *
 * @see AbstractScreenFragment
 * @constructor Creates an instance of [OnboardingScreenFragment] with the layout resource ID [R.layout.screen_onboarding].
 */
class OnboardingScreenFragment : AbstractScreenFragment(R.layout.screen_onboarding) {

    /**
     * The root ViewGroup of the onboarding screen layout.
     */
    private lateinit var rootLayout: ViewGroup

    /**
     * Button that triggers the "Get Started" action, navigating to the Privacy Policy screen.
     */
    private lateinit var getStartedButton: Button

    /**
     * Initializes the views for the onboarding screen by locating them in the layout.
     * This method is called during [onViewCreated] to set up [rootLayout] and [getStartedButton].
     *
     * @param view The root view of the fragment's layout.
     */
    override fun findViewsById(view: View) {
        rootLayout = view.findViewById(R.id.root_layout)
        getStartedButton = view.findViewById(R.id.onboarding_get_started_button)
    }

    /**
     * Applies window insets to the onboarding screen's [rootLayout].
     * This method uses [applyDefaultInsets] to adjust padding for system bars and display cutouts.
     */
    override fun applyWindowInsets() {
        applyDefaultInsets(rootLayout)
    }

    /**
     * Sets the click listener for the [getStartedButton].
     * When clicked, the button triggers navigation to the Privacy Policy screen.
     */
    override fun setOnClickListeners() {
        getStartedButton.setOnClickListener {
            dispatch(NavigateTo(PrivacyPolicy()))
        }
    }
}
