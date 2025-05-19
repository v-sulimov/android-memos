package com.vsulimov.memos.fragment.screen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.vsulimov.memos.R
import com.vsulimov.memos.action.ConfigureServerAction.Next
import com.vsulimov.memos.action.ConfigureServerAction.ServerUrlChanged
import com.vsulimov.memos.util.dispatch
import com.vsulimov.navigation.action.NavigationAction.GoBack

/**
 * A fragment that provides a user interface for configuring a server connection.
 * This fragment displays a screen with an input field for the server URL, a cancel button,
 * and a next button. It extends [AbstractScreenFragment] and uses the layout defined in
 * [R.layout.screen_configure_server].
 */
class ConfigureServerScreenFragment : AbstractScreenFragment(R.layout.screen_configure_server) {

    /**
     * The root layout of the fragment, containing all UI elements.
     */
    private lateinit var rootLayout: LinearLayout

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFocusChangeListeners()
        requestFocus()
        addTextChangedListeners()
    }

    /**
     * Initializes the fragment's views by binding them to their corresponding UI elements.
     * This method locates and assigns the [rootLayout], [serverUrlEditText], [cancelButton],
     * and [nextButton] using their respective IDs from the layout.
     *
     * @param view The root view of the fragment, used to find UI elements by ID.
     */
    override fun findViewsById(view: View) {
        rootLayout = view.findViewById(R.id.root_layout)
        serverUrlEditText = view.findViewById(R.id.configure_server_url)
        cancelButton = view.findViewById(R.id.configure_server_cancel_button)
        nextButton = view.findViewById(R.id.configure_server_next_button)
    }

    /**
     * Applies window insets to the fragment's root layout to ensure proper padding.
     * This method adjusts the [rootLayout]'s padding to account for system bars, display cutouts,
     * and the input method editor (IME) such as the on-screen keyboard.
     */
    override fun applyWindowInsets() {
        applyDefaultInsets(
            rootLayout = rootLayout,
            typeMask = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime()
        )
    }

    /**
     * Sets up click listeners for the fragment's interactive UI elements.
     * This method configures the [cancelButton] to dispatch a [GoBack] action and the
     * [nextButton] for future implementation of the next step in the configuration process.
     * It also calls the parent class's [setOnClickListeners] to ensure inherited listeners are set.
     */
    override fun setOnClickListeners() {
        super.setOnClickListeners()
        cancelButton.setOnClickListener { dispatch(GoBack) }
        nextButton.setOnClickListener { dispatch(Next) }
    }

    /**
     * Adds a focus change listener to the server URL input field.
     *
     * This function sets up an [OnFocusChangeListener] for the [serverUrlEditText] to manage keyboard visibility.
     * When the input field gains focus, the keyboard is shown. When it loses focus, the keyboard is hidden.
     */
    private fun addFocusChangeListeners() {
        serverUrlEditText.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
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
     * This function programmatically sets focus to the [serverUrlEditText], typically to prompt the user to start
     * entering the server URL and to trigger the display of the keyboard.
     */
    private fun requestFocus() {
        serverUrlEditText.requestFocus()
    }

    /**
     * Adds a text change listener to the server URL input field.
     *
     * This function sets up a text change listener for the [serverUrlEditText] to dispatch a [ServerUrlChanged]
     * action whenever the text in the input field changes. The action includes the new server URL as a string.
     */
    private fun addTextChangedListeners() {
        serverUrlEditText.doOnTextChanged { text: CharSequence?, _: Int, _: Int, _: Int ->
            dispatch(ServerUrlChanged(newUrl = text.toString()))
        }
    }
}
