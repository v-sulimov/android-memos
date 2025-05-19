package com.vsulimov.memos.storage

import android.content.SharedPreferences
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the [PrivacyPolicyStorage] class.
 */
class PrivacyPolicyStorageTest {
    private val sharedPreferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()
    private val storage = PrivacyPolicyStorage(sharedPreferences)

    /**
     * Tests that [PrivacyPolicyStorage.setPrivacyPolicyAccepted] correctly saves the acceptance status
     * to [SharedPreferences] with the expected key and value.
     */
    @Test
    fun `setPrivacyPolicyAccepted saves boolean to SharedPreferences`() {
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putBoolean(any(), any())).thenReturn(editor)

        storage.setPrivacyPolicyAccepted(true)

        verify(editor).putBoolean("com.vsulimov.memos.privacy_policy_accepted", true)
        verify(editor).apply()
    }

    /**
     * Tests that [PrivacyPolicyStorage.getPrivacyPolicyAccepted] retrieves the correct boolean value
     * from [SharedPreferences] using the expected key.
     */
    @Test
    fun `getPrivacyPolicyAccepted returns value from SharedPreferences`() {
        whenever(sharedPreferences.getBoolean(eq("com.vsulimov.memos.privacy_policy_accepted"), any())).thenReturn(true)

        val result = storage.getPrivacyPolicyAccepted()

        assertEquals(true, result)
        verify(sharedPreferences).getBoolean("com.vsulimov.memos.privacy_policy_accepted", false)
    }

    /**
     * Tests that [PrivacyPolicyStorage.getPrivacyPolicyAccepted] returns the default value
     * when no value is stored in [SharedPreferences].
     */
    @Test
    fun `getPrivacyPolicyAccepted returns default value when not set`() {
        whenever(
            sharedPreferences.getBoolean(
                eq("com.vsulimov.memos.privacy_policy_accepted"),
                any(),
            ),
        ).thenReturn(false)

        val result = storage.getPrivacyPolicyAccepted()

        assertEquals(false, result)
        verify(sharedPreferences).getBoolean("com.vsulimov.memos.privacy_policy_accepted", false)
    }
}
