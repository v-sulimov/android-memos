package com.vsulimov.memos.validator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * Test class for [URLValidator] functionality.
 * Verifies URL normalization and validation for various input cases.
 */
class URLValidatorTest {
    @Test
    fun `test valid URL with no schema adds https`() {
        val result = URLValidator.validateUrl("domain.com")
        assertTrue(result.isSuccess)
        assertEquals("https://domain.com", result.getOrNull())
    }

    @Test
    fun `test valid URL with https schema`() {
        val result = URLValidator.validateUrl("https://domain.com")
        assertTrue(result.isSuccess)
        assertEquals("https://domain.com", result.getOrNull())
    }

    @Test
    fun `test valid URL with subdomains`() {
        val result = URLValidator.validateUrl("subdomain.domain.com")
        assertTrue(result.isSuccess)
        assertEquals("https://subdomain.domain.com", result.getOrNull())
    }

    @Test
    fun `test valid URL with multiple subdomains`() {
        val result = URLValidator.validateUrl("sub.subdomain.domain.com")
        assertTrue(result.isSuccess)
        assertEquals("https://sub.subdomain.domain.com", result.getOrNull())
    }

    @Test
    fun `test invalid URL with http schema`() {
        val exception =
            assertFailsWith<IllegalArgumentException> {
                URLValidator.validateUrl("http://domain.com").getOrThrow()
            }
        assertEquals("Only HTTPS schema is allowed", exception.message)
    }

    @Test
    fun `test invalid URL format with single label`() {
        val exception =
            assertFailsWith<IllegalArgumentException> {
                URLValidator.validateUrl("invalid").getOrThrow()
            }
        assertEquals("Invalid URL format", exception.message)
    }

    @Test
    fun `test invalid URL with incomplete domain`() {
        val exception =
            assertFailsWith<IllegalArgumentException> {
                URLValidator.validateUrl("https://domain").getOrThrow()
            }
        assertEquals("Invalid URL format", exception.message)
    }

    @Test
    fun `test URL with whitespace is trimmed and valid`() {
        val result = URLValidator.validateUrl("  domain.com  ")
        assertTrue(result.isSuccess)
        assertEquals("https://domain.com", result.getOrNull())
    }

    @Test
    fun `test valid URL with memos prefix`() {
        val result = URLValidator.validateUrl("memos.domain.com")
        assertTrue(result.isSuccess)
        assertEquals("https://memos.domain.com", result.getOrNull())
    }

    @Test
    fun `test invalid URL with invalid characters`() {
        val exception =
            assertFailsWith<IllegalArgumentException> {
                URLValidator.validateUrl("domain#.com").getOrThrow()
            }
        assertEquals("Invalid URL format", exception.message)
    }
}
