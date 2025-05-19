package com.vsulimov.memos.validator

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test class for [URLValidator] functionality.
 * Verifies URL normalization and validation for various input cases.
 */
class URLValidatorTest {

    /**
     * Tests normalization and validation of a URL without a schema.
     * Expects "https://" to be prepended and the URL to be valid.
     */
    @Test
    fun `test URL without schema is normalized and valid`() {
        val result = URLValidator.normalizeAndValidate("memos.com")
        assertTrue(result, "URL 'memos.com' should be valid after adding https://")
    }

    /**
     * Tests validation of a URL with an https schema.
     * Expects the URL to be valid without modification.
     */
    @Test
    fun `test URL with https schema is valid`() {
        val result = URLValidator.normalizeAndValidate("https://memos.com")
        assertTrue(result, "URL 'https://memos.com' should be valid")
    }

    /**
     * Tests validation of a URL with an http schema.
     * Expects the URL to be valid without modification.
     */
    @Test
    fun `test URL with http schema is valid`() {
        val result = URLValidator.normalizeAndValidate("http://memos.com")
        assertTrue(result, "URL 'http://memos.com' should be valid")
    }

    /**
     * Tests validation of a URL with a subdomain and path.
     * Expects the URL to be valid.
     */
    @Test
    fun `test URL with subdomain and path is valid`() {
        val result = URLValidator.normalizeAndValidate("https://sub.memos.com/path")
        assertTrue(result, "URL 'https://sub.memos.com/path' should be valid")
    }

    /**
     * Tests validation of a URL with query parameters.
     * Expects the URL to be valid.
     */
    @Test
    fun `test URL with query parameters is valid`() {
        val result = URLValidator.normalizeAndValidate("https://memos.com?query=1")
        assertTrue(result, "URL 'https://memos.com?query=1' should be valid")
    }

    /**
     * Tests normalization and validation of a URL with whitespace.
     * Expects whitespace to be trimmed and the URL to be valid.
     */
    @Test
    fun `test URL with whitespace is normalized and valid`() {
        val result = URLValidator.normalizeAndValidate("  memos.com  ")
        assertTrue(result, "URL '  memos.com  ' should be valid after trimming and adding https://")
    }

    /**
     * Tests validation of an empty URL.
     * Expects the URL to be invalid.
     */
    @Test
    fun `test empty URL is invalid`() {
        val result = URLValidator.normalizeAndValidate("")
        assertFalse(result, "Empty URL should be invalid")
    }

    /**
     * Tests validation of a URL with an unsupported schema (ftp).
     * Expects the URL to be invalid.
     */
    @Test
    fun `test URL with unsupported schema is invalid`() {
        val result = URLValidator.normalizeAndValidate("ftp://memos.com")
        assertFalse(result, "URL 'ftp://memos.com' should be invalid")
    }

    /**
     * Tests validation of a malformed URL.
     * Expects the URL to be invalid.
     */
    @Test
    fun `test malformed URL is invalid`() {
        val result = URLValidator.normalizeAndValidate("https://memos..com")
        assertFalse(result, "URL 'https://memos..com' should be invalid")
    }

    /**
     * Tests validation of a URL with only schema.
     * Expects the URL to be invalid.
     */
    @Test
    fun `test URL with only schema is invalid`() {
        val result = URLValidator.normalizeAndValidate("https://")
        assertFalse(result, "URL 'https://' should be invalid")
    }
}
