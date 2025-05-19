package com.vsulimov.memos.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vsulimov.memos.R
import com.vsulimov.memos.action.ActivityLifecycleAction.OnDestroy
import com.vsulimov.memos.factory.overlay.OverlayFragmentFactory
import com.vsulimov.memos.factory.screen.ScreenFragmentFactory
import com.vsulimov.memos.lens.toNavigationState
import com.vsulimov.memos.middleware.NavigationMiddleware
import com.vsulimov.memos.state.OverlayState
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.memos.util.dispatch
import com.vsulimov.memos.util.getStateFlow
import com.vsulimov.navigation.NavigationController
import kotlinx.coroutines.flow.map

/**
 * The main entry point of the application, responsible for initializing and managing navigation.
 *
 * This activity sets up the primary UI container and configures
 * the navigation controller to handle screen and overlay transitions.
 *
 * @see NavigationController
 * @see NavigationMiddleware
 * @see ScreenState
 * @see OverlayState
 */
class MainActivity : AppCompatActivity() {
    /**
     * Controller that manages navigation state, screen transitions, and overlay displays.
     */
    private lateinit var navigationController: NavigationController<ScreenState, OverlayState>

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNavigationController()
        navigationController.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispatch(OnDestroy(isFinishing))
    }

    /**
     * Creates and configures the navigation controller.
     *
     * Initializes the [navigationController] with dependencies required for navigation, including
     * a dispatch function, navigation state flow, fragment factories for screens and overlays,
     * and the container ID. This sets up the controller to manage screen and overlay transitions
     * within the activity.
     */
    private fun createNavigationController() {
        val screenFactory = ScreenFragmentFactory()
        val overlayFactory = OverlayFragmentFactory()
        navigationController =
            NavigationController(
                dispatchFunction = { action -> dispatch(action) },
                navigationStateFlow = getStateFlow().map { it.toNavigationState() },
                activity = this,
                screenFragmentFactory = screenFactory,
                overlayFragmentFactory = overlayFactory,
                containerId = R.id.container,
            )
    }
}
