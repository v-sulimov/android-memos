package com.vsulimov.memos.factory.overlay

import androidx.fragment.app.DialogFragment
import org.junit.Test
import org.mockito.kotlin.mock
import kotlin.test.assertFailsWith

/**
 * Test class for [OverlayFragmentFactory].
 *
 * This class contains unit tests to verify the behavior of [OverlayFragmentFactory],
 * ensuring that it correctly creates dialog fragments for given overlay states and retrieves
 * state type identifiers for dialog fragments.
 */
class OverlayFragmentFactoryTest {

    /**
     * Tests that [OverlayFragmentFactory.getStateTypeIdForOverlay] throws an
     * [IllegalArgumentException] when provided with an unknown overlay type.
     */
    @Test
    fun `getStateTypeIdForOverlay with unknown fragment throws IllegalArgumentException`() {
        val factory = OverlayFragmentFactory()
        val fragment: DialogFragment = mock()
        assertFailsWith<IllegalArgumentException> {
            factory.getStateTypeIdForOverlay(fragment)
        }
    }
}
