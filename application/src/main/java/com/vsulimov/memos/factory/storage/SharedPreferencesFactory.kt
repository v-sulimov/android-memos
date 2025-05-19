package com.vsulimov.memos.factory.storage

import android.content.Context
import android.content.SharedPreferences

/**
 * A factory object responsible for creating [SharedPreferences] instance.
 * Provides a centralized way to initialize shared preferences with consistent configurations.
 */
object SharedPreferencesFactory {
    /**
     * The name of the [SharedPreferences] file used to store data.
     */
    private const val SHARED_PREFERENCES_NAME = "com.vsulimov.memos.PREFERENCES"

    /**
     * Creates a [SharedPreferences] instance for storing data.
     *
     * @param context The Android context used to access the shared preferences.
     * @return A [SharedPreferences] instance configured for private access to stored data.
     */
    fun createSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
}
