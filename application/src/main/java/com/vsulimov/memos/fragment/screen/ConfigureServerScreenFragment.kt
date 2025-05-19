package com.vsulimov.memos.fragment.screen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import com.vsulimov.memos.R
import com.vsulimov.memos.action.ConfigureServerAction.Next
import com.vsulimov.memos.action.ConfigureServerAction.ServerUrlChanged
import com.vsulimov.memos.factory.TypeIds
import com.vsulimov.memos.factory.middleware.ConfigureServerMiddlewareFactory
import com.vsulimov.memos.lens.toConfigureServerErrorResId
import com.vsulimov.memos.lens.toConfigureServerLoadingIndicatorVisibility
import com.vsulimov.memos.lens.toScreenState
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.memos.util.dispatch
import com.vsulimov.memos.util.subscribeToNullableStateChanges
import com.vsulimov.memos.util.subscribeToStateChanges
import com.vsulimov.navigation.action.NavigationAction.GoBack
import com.vsulimov.redux.Middleware

/**
 * A fragment providing a user interface for configuring a server connection.
 *
 * This fragment displays an input field for entering a server URL, a cancel button to navigate back,
 * and a next button to proceed with the configuration. It extends [AbstractScreenFragment] and uses
 * the layout defined in [R.layout.screen_configure_server]. The fragment integrates with the Redux
 * store to dispatch actions such as [ServerUrlChanged] and [Next], and it subscribes to state changes
 * to update the UI based on server URL validation errors.
 *
 * @see AbstractScreenFragment
 * @see ScreenMiddlewaresProvider
 * @see ConfigureServerAction
 */
class ConfigureServerScreenFragment :
    AbstractScreenFragment(R.layout.screen_configure_server),
    ScreenMiddlewaresProvider {
    /**
     * The root layout of the fragment, containing all UI elements.
     */
    private lateinit var rootLayout: LinearLayout

    /**
     * The input layout wrapping the server URL input field, used to display validation errors.
     */
    private lateinit var serverUrlInputLayout: TextInputLayout

    /**
     * The input field where the user enters the server URL.
     */
    private lateinit var serverUrlEditText: EditText

    /**
     * The button that triggers navigation back to the previous screen.
     */
    private lateinit var cancelButton: Button

    /**
     * The button that proceeds to the next step in the server configuration process.
     */
    private lateinit var nextButton: Button

    /**
     * The progress indicator displayed during server configuration processing.
     */
    private lateinit var loadingIndicator: CircularProgressIndicator

    /**
     * Initializes the fragment's view after creation, setting up UI interactions and state subscriptions.
     *
     * This method binds UI elements, sets up focus and text change listeners, requests focus for the input field,
     * and subscribes to state changes to update the UI based on server URL validation errors.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved instance state, if any, from previous configurations.
     */
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        addFocusChangeListeners()
        requestFocus()
        addTextChangedListeners()
        subscribeToNullableStateChanges(
            viewLifecycleOwner = viewLifecycleOwner,
            isExpectedState = { it.toScreenState() is ScreenState.ConfigureServer },
            lens = { it.toConfigureServerErrorResId() },
            onStateChange = ::handleServerErrorResIdChange,
        )
        subscribeToStateChanges(
            viewLifecycleOwner = viewLifecycleOwner,
            isExpectedState = { it.toScreenState() is ScreenState.ConfigureServer },
            lens = { it.toConfigureServerLoadingIndicatorVisibility() },
            onStateChange = ::handleLoadingIndicatorVisibilityChange,
        )
    }

    /**
     * Handles state changes related to server URL validation errors.
     *
     * Updates the [serverUrlInputLayout] to display an error message if a validation error resource ID
     * is present in the state, or clears the error if none exists.
     *
     * @param errorTextResId The resource ID of the error message, or null if no error exists.
     */
    private fun handleServerErrorResIdChange(
        @StringRes errorTextResId: Int?,
    ) {
        val errorText = errorTextResId?.let { getString(it) }
        serverUrlInputLayout.error = errorText
    }

    /**
     * Handles state changes related to the visibility of the loading progress indicator.
     *
     * Updates the UI by enabling or disabling the [serverUrlInputLayout] and [cancelButton], toggling
     * the visibility of the [nextButton], and showing or hiding the [loadingIndicator] based on the
     * provided visibility state.
     *
     * @param isVisible A boolean indicating whether the loading progress indicator should be visible.
     */
    private fun handleLoadingIndicatorVisibilityChange(isVisible: Boolean) {
        serverUrlInputLayout.isEnabled = !isVisible
        cancelButton.isEnabled = !isVisible
        nextButton.visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
        loadingIndicator.isVisible = isVisible
    }

    /**
     * Initializes the fragment's views by binding them to their corresponding UI elements.
     *
     * Locates and assigns the [rootLayout], [serverUrlInputLayout], [serverUrlEditText], [cancelButton],
     * and [nextButton] using their respective IDs from the layout defined in [R.layout.screen_configure_server].
     *
     * @param view The root view of the fragment, used to find UI elements by ID.
     */
    override fun findViewsById(view: View) {
        rootLayout = view.findViewById(R.id.root_layout)
        serverUrlInputLayout = view.findViewById(R.id.configure_server_url_input_layout)
        serverUrlEditText = view.findViewById(R.id.configure_server_url)
        cancelButton = view.findViewById(R.id.configure_server_cancel_button)
        nextButton = view.findViewById(R.id.configure_server_next_button)
        loadingIndicator = view.findViewById(R.id.configure_server_loading_indicator)
    }

    /**
     * Applies window insets to ensure proper padding for system UI elements.
     *
     * Adjusts the padding of the [rootLayout] to account for system bars, display cutouts, and the
     * input method editor (IME) such as the on-screen keyboard.
     */
    override fun applyWindowInsets() {
        applyDefaultInsets(
            rootLayout = rootLayout,
            typeMask = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
        )
    }

    /**
     * Sets up click listeners for interactive UI elements.
     *
     * Configures the [cancelButton] to dispatch a [GoBack] action to navigate back and the [nextButton]
     * to dispatch a [Next] action to proceed with server configuration. Calls the parent class's
     * [setOnClickListeners] to ensure inherited listeners are applied.
     */
    override fun setOnClickListeners() {
        super.setOnClickListeners()
        cancelButton.setOnClickListener { dispatch(GoBack) }
        nextButton.setOnClickListener { dispatch(Next) }
    }

    /**
     * Provides the unique tag for this fragment.
     *
     * @return The string identifier [TypeIds.TYPE_ID_SCREEN_CONFIGURE_SERVER] for this fragment.
     */
    override fun provideTag(): String = TypeIds.TYPE_ID_SCREEN_CONFIGURE_SERVER

    /**
     * Provides the middleware for handling server configuration actions.
     *
     * @return A list containing a single [ConfigureServerMiddlewareFactory.createConfigureServerMiddleware]
     *         middleware for processing [ConfigureServerAction] actions.
     */
    override fun provideScreenMiddlewares(): List<Middleware<ApplicationState>> =
        listOf(ConfigureServerMiddlewareFactory.createConfigureServerMiddleware())

    /**
     * Sets up focus change listeners for the server URL input field.
     *
     * Attaches an [OnFocusChangeListener] to the [serverUrlEditText] to show the keyboard when the field
     * gains focus and hide it when focus is lost.
     */
    private fun addFocusChangeListeners() {
        serverUrlEditText.onFocusChangeListener =
            object : View.OnFocusChangeListener {
                override fun onFocusChange(
                    view: View,
                    hasFocus: Boolean,
                ) {
                    if (hasFocus) {
                        showKeyboard(view)
                    } else {
                        hideKeyboard(view)
                    }
                }
            }
    }

    /**
     * Requests focus for the server URL input field.
     *
     * Programmatically sets focus to the [serverUrlEditText] to prompt the user to enter the server URL
     * and trigger the display of the on-screen keyboard.
     */
    private fun requestFocus() {
        serverUrlEditText.requestFocus()
    }

    /**
     * Sets up a text change listener for the server URL input field.
     *
     * Attaches a listener to the [serverUrlEditText] that dispatches a [ServerUrlChanged] action whenever
     * the text changes, passing the new server URL as a string.
     */
    private fun addTextChangedListeners() {
        serverUrlEditText.doOnTextChanged { text: CharSequence?, _: Int, _: Int, _: Int ->
            dispatch(ServerUrlChanged(newUrl = text.toString()))
        }
    }
}
