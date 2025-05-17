package com.vsulimov.memos.factory.screen

import androidx.fragment.app.Fragment
import com.vsulimov.memos.factory.TypeIds.TYPE_ID_SCREEN_ONBOARDING
import com.vsulimov.memos.factory.TypeIds.TYPE_ID_SCREEN_PRIVACY_POLICY
import com.vsulimov.memos.fragment.screen.OnboardingScreenFragment
import com.vsulimov.memos.fragment.screen.PrivacyPolicyScreenFragment
import com.vsulimov.memos.state.ScreenState
import org.junit.Test
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * Test class for [ScreenFragmentFactory].
 *
 * This class contains unit tests to verify the behavior of [ScreenFragmentFactory],
 * ensuring that it correctly creates fragments for given screen states and retrieves
 * state type identifiers for fragments.
 */
class ScreenFragmentFactoryTest {

    /**
     * Tests that [ScreenFragmentFactory.createScreenFragment] returns an instance of
     * [OnboardingScreenFragment] when provided with [ScreenState.Onboarding].
     */
    @Test
    fun `createScreenFragment with Onboarding state returns OnboardingScreenFragment`() {
        val factory = ScreenFragmentFactory()
        val fragment = factory.createScreenFragment(ScreenState.Onboarding())
        assertTrue(fragment is OnboardingScreenFragment)
    }

    /**
     * Tests that [ScreenFragmentFactory.createScreenFragment] returns an instance of
     * [PrivacyPolicyScreenFragment] when provided with [ScreenState.PrivacyPolicy].
     */
    @Test
    fun `createScreenFragment with PrivacyPolicy state returns PrivacyPolicyScreenFragment`() {
        val factory = ScreenFragmentFactory()
        val fragment = factory.createScreenFragment(ScreenState.PrivacyPolicy())
        assertTrue(fragment is PrivacyPolicyScreenFragment)
    }

    /**
     * Tests that [ScreenFragmentFactory.getStateTypeIdForScreen] returns the correct
     * state type identifier, [TYPE_ID_SCREEN_ONBOARDING], for [OnboardingScreenFragment].
     */
    @Test
    fun `getStateTypeIdForScreen with OnboardingScreenFragment returns TYPE_ID_SCREEN_ONBOARDING`() {
        val factory = ScreenFragmentFactory()
        val fragment: OnboardingScreenFragment = mock()
        val typeId = factory.getStateTypeIdForScreen(fragment)
        assertEquals(TYPE_ID_SCREEN_ONBOARDING, typeId)
    }

    /**
     * Tests that [ScreenFragmentFactory.getStateTypeIdForScreen] returns the correct
     * state type identifier, [TYPE_ID_SCREEN_PRIVACY_POLICY], for [PrivacyPolicyScreenFragment].
     */
    @Test
    fun `getStateTypeIdForScreen with PrivacyPolicyScreenFragment returns TYPE_ID_SCREEN_PRIVACY_POLICY`() {
        val factory = ScreenFragmentFactory()
        val fragment: PrivacyPolicyScreenFragment = mock()
        val typeId = factory.getStateTypeIdForScreen(fragment)
        assertEquals(TYPE_ID_SCREEN_PRIVACY_POLICY, typeId)
    }

    /**
     * Tests that [ScreenFragmentFactory.getStateTypeIdForScreen] throws an
     * [IllegalArgumentException] when provided with an unknown fragment type.
     */
    @Test
    fun `getStateTypeIdForScreen with unknown fragment throws IllegalArgumentException`() {
        val factory = ScreenFragmentFactory()
        val fragment: Fragment = mock()
        assertFailsWith<IllegalArgumentException> {
            factory.getStateTypeIdForScreen(fragment)
        }
    }
}
