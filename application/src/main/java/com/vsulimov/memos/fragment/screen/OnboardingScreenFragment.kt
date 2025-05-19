package com.vsulimov.memos.fragment.screen

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.vsulimov.memos.R
import com.vsulimov.memos.action.OnboardingAction.GetStarted
import com.vsulimov.memos.factory.TypeIds
import com.vsulimov.memos.factory.middleware.OnboardingMiddlewareFactory
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.util.dispatch
import com.vsulimov.redux.Middleware

/**
 * A fragment that displays the onboarding screen for the application.
 *
 * This fragment presents the initial onboarding UI, including a title, description, and a "Get Started" button.
 * It extends [AbstractScreenFragment] to leverage common fragment setup logic and implements
 * [ScreenMiddlewaresProvider] to provide screen-specific middlewares for state management.
 *
 * @see AbstractScreenFragment
 * @see ScreenMiddlewaresProvider
 * @constructor Creates an instance of [OnboardingScreenFragment] with the layout resource ID [R.layout.screen_onboarding].
 */
class OnboardingScreenFragment :
    AbstractScreenFragment(R.layout.screen_onboarding),
    ScreenMiddlewaresProvider {
    /**
     * The root [ViewGroup] of the onboarding screen layout.
     */
    private lateinit var rootLayout: ViewGroup

    /**
     * The button that triggers the "Get Started" action, navigating to the Privacy Policy screen.
     */
    private lateinit var getStartedButton: Button

    /**
     * Initializes the views for the onboarding screen by locating them in the layout.
     * Sets up [rootLayout] and [getStartedButton] using their respective IDs from the layout.
     *
     * @param view The root view of the fragment's layout.
     */
    override fun findViewsById(view: View) {
        rootLayout = view.findViewById(R.id.root_layout)
        getStartedButton = view.findViewById(R.id.onboarding_get_started_button)
    }

    /**
     * Applies window insets to the onboarding screen's [rootLayout].
     * Uses [applyDefaultInsets] to adjust padding for system bars and display cutouts.
     */
    override fun applyWindowInsets() {
        applyDefaultInsets(rootLayout)
    }

    /**
     * Sets the click listener for the [getStartedButton].
     * When clicked, the button dispatches a [GetStarted] action to trigger navigation to the Privacy Policy screen.
     */
    override fun setOnClickListeners() {
        getStartedButton.setOnClickListener {
            dispatch(GetStarted)
        }
    }

    /**
     * Provides a unique identifier for the onboarding screen.
     *
     * @return A string representing the screen's unique tag, defined as [TypeIds.TYPE_ID_SCREEN_ONBOARDING].
     */
    override fun provideTag(): String = TypeIds.TYPE_ID_SCREEN_ONBOARDING

    /**
     * Provides the list of middlewares for the onboarding screen.
     *
     * @return A list containing a single middleware created by [OnboardingMiddlewareFactory.createOnboardingMiddleware]
     *         using the fragment's context.
     */
    override fun provideScreenMiddlewares(): List<Middleware<ApplicationState>> =
        listOf(OnboardingMiddlewareFactory.createOnboardingMiddleware(requireContext()))
}
