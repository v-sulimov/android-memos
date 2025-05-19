package com.vsulimov.memos.storage

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * A class responsible for storing and retrieving the user's privacy policy acceptance status.
 * It uses [SharedPreferences] to persistently store the acceptance state.
 *
 * @param sharedPreferences The [SharedPreferences] instance used for storing privacy policy data.
 */
class PrivacyPolicyStorage(
    private val sharedPreferences: SharedPreferences,
) {
    /**
     * Saves the user's privacy policy acceptance status.
     *
     * @param isAccepted A boolean indicating whether the user has accepted the privacy policy.
     */
    fun setPrivacyPolicyAccepted(isAccepted: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_PRIVACY_POLICY_ACCEPTED, isAccepted)
        }
    }

    /**
     * Retrieves the user's privacy policy acceptance status.
     *
     * @return A boolean indicating whether the user has accepted the privacy policy.
     *         Returns [DEFAULT_PRIVACY_POLICY_ACCEPTED] if no value is set.
     */
    fun getPrivacyPolicyAccepted(): Boolean = sharedPreferences.getBoolean(KEY_PRIVACY_POLICY_ACCEPTED, DEFAULT_PRIVACY_POLICY_ACCEPTED)

    /**
     * Companion object containing constants used for storing privacy policy data.
     */
    companion object {
        /**
         * The key used to store the privacy policy acceptance status in [SharedPreferences].
         */
        private const val KEY_PRIVACY_POLICY_ACCEPTED = "com.vsulimov.memos.privacy_policy_accepted"

        /**
         * The default value for the privacy policy acceptance status, used when no value is stored.
         */
        private const val DEFAULT_PRIVACY_POLICY_ACCEPTED = false
    }
}
