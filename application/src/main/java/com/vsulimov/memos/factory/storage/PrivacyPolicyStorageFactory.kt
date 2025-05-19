package com.vsulimov.memos.factory.storage

import android.content.Context
import com.vsulimov.memos.storage.PrivacyPolicyStorage

/**
 * A factory object responsible for creating instances of [PrivacyPolicyStorage].
 * This singleton provides a centralized way to instantiate the storage with the required dependencies.
 */
object PrivacyPolicyStorageFactory {
    /**
     * Creates a new instance of [PrivacyPolicyStorage] with a configured [SharedPreferences].
     *
     * @param context The Android [Context] used to initialize the [SharedPreferences] for the storage.
     * @return A configured [PrivacyPolicyStorage] instance.
     */
    fun createPrivacyPolicyStorage(context: Context): PrivacyPolicyStorage {
        val sharedPreferences = SharedPreferencesFactory.createSharedPreferences(context)
        return PrivacyPolicyStorage(sharedPreferences)
    }
}
