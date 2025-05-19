package com.vsulimov.memos.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Looper
import com.google.android.material.color.DynamicColors
import com.vsulimov.memos.activity.ActivityLifecycleCallbacks
import com.vsulimov.memos.factory.middleware.NavigationMiddlewareFactory
import com.vsulimov.memos.factory.state.ApplicationStateFactory
import com.vsulimov.memos.reducer.RootReducer
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.redux.Store
import java.lang.ref.WeakReference

/**
 * The main application class for the Memos app, responsible for initializing and managing global resources.
 *
 * This class extends [Application] and implements [ActivityLifecycleCallbacks] to monitor activity lifecycle events.
 * It initializes the application-wide [Store] for managing [ApplicationState], applies dynamic colors to activities
 * if supported, and provides access to the application instance and store through companion object methods.
 *
 * @see Application
 * @see ActivityLifecycleCallbacks
 * @see Store
 * @see ApplicationState
 */
class MemosApplication : Application(), ActivityLifecycleCallbacks {

    /**
     * A weak reference to the currently active activity.
     * Used to track the current activity for navigation middleware purposes.
     */
    private lateinit var currentActivity: WeakReference<Activity>

    /**
     * The application-wide store that manages the [ApplicationState].
     * This store handles state updates and middleware for the application.
     */
    private lateinit var store: Store<ApplicationState>

    /**
     * Retrieves the application-wide store instance.
     *
     * @return The [Store] instance managing the [ApplicationState].
     */
    fun getStore() = store

    /**
     * Called when the application is created.
     * Initializes the singleton instance, registers activity lifecycle callbacks,
     * sets up the application-wide store, and applies dynamic colors if available.
     */
    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(this)
        store = createStore()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    /**
     * Called when an activity is created.
     * Updates the [currentActivity] reference to the newly created activity.
     *
     * @param activity The activity being created.
     * @param savedInstanceState The bundle containing the activity's previously saved state, or null if none exists.
     */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityCreated(activity, savedInstanceState)
        currentActivity = WeakReference(activity)
    }

    /**
     * Creates and configures the application-wide store.
     *
     * Initializes a [Store] with the initial [ApplicationState], a main thread check,
     * and adds the [NavigationMiddleware] and [RootReducer] for state management.
     *
     * @return A configured [Store] instance managing the [ApplicationState].
     */
    private fun createStore(): Store<ApplicationState> = Store(
        initialState = ApplicationStateFactory.createInitialApplicationState(),
        isMainThread = { Looper.getMainLooper().isCurrentThread }
    ).apply {
        addMiddleware(NavigationMiddlewareFactory.createNavigationMiddleware { currentActivity.get()?.finish() })
        addReducer(RootReducer())
    }

    companion object {

        /**
         * The singleton instance of the [MemosApplication].
         * Provides global access to the application instance.
         */
        private lateinit var instance: MemosApplication

        /**
         * Retrieves the singleton instance of the [MemosApplication].
         *
         * @return The [MemosApplication] instance.
         */
        fun getInstance() = instance
    }
}
