package com.vsulimov.memos.application

import android.app.Application
import android.os.Looper
import com.google.android.material.color.DynamicColors
import com.vsulimov.memos.factory.state.ApplicationStateFactory
import com.vsulimov.memos.reducer.RootReducer
import com.vsulimov.memos.state.ApplicationState
import com.vsulimov.redux.Store

/**
 * The main application class for the Memos app, responsible for initializing global resources.
 *
 * This class extends [Application] and sets up the application's state management system, including
 * the creation of the [Store] and initial [ApplicationState]. It also applies dynamic colors to activities
 * if available and provides access to the application instance and store via companion object methods.
 *
 * @see Application
 * @see Store
 * @see ApplicationState
 */
class MemosApplication : Application() {

    /**
     * The application-wide store that manages the [ApplicationState].
     */
    private lateinit var store: Store<ApplicationState>

    /**
     * Returns the application-wide store instance.
     *
     * @return The [Store] managing the [ApplicationState].
     */
    fun getStore() = store

    override fun onCreate() {
        super.onCreate()
        instance = this
        store = createStore()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    /**
     * Creates and configures the application-wide store.
     *
     * This method initializes a [Store] with the initial [ApplicationState] and a check for the main
     * thread. It also adds the [RootReducer] to handle state updates.
     *
     * @return A configured [Store] instance managing the [ApplicationState].
     */
    private fun createStore(): Store<ApplicationState> = Store(
        initialState = ApplicationStateFactory.createInitialApplicationState(),
        isMainThread = { Looper.getMainLooper().isCurrentThread }
    ).apply {
        addReducer(RootReducer())
    }

    companion object {

        /**
         * The singleton instance of the [MemosApplication].
         */
        private lateinit var instance: MemosApplication

        /**
         * Returns the singleton instance of the [MemosApplication].
         *
         * @return The [MemosApplication] instance.
         */
        fun getInstance() = instance
    }
}
