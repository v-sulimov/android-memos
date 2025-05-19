package com.vsulimov.memos.validator

/**
 * A utility class for normalizing and validating URLs.
 * If a URL lacks a schema (e.g., "memos.com"), it prepends "https://" and validates the result.
 * If a URL includes a schema, it validates the URL as provided.
 */
object URLValidator {

    /**
     * Normalizes and validates a URL.
     * If the URL does not contain a schema, "https://" is prepended before validation.
     * If the URL contains a schema, it is validated as is.
     *
     * @param url The input URL to normalize and validate.
     * @return `true` if the URL is valid, `false` otherwise.
     */
    fun normalizeAndValidate(url: String): Boolean {
        val normalizedUrl = normalizeUrl(url)
        return isValidUrl(normalizedUrl)
    }

    /**
     * Normalizes a URL by adding "https://" if no schema is present.
     *
     * @param url The input URL to normalize.
     * @return The normalized URL with a schema.
     */
    private fun normalizeUrl(url: String): String {
        val trimmedUrl = url.trim()
        return if (!trimmedUrl.contains("://")) {
            "https://$trimmedUrl"
        } else {
            trimmedUrl
        }
    }

    /**
     * Validates whether a URL is well-formed.
     * Checks for a valid schema (http or https), domain, and optional path.
     *
     * @param url The URL to validate.
     * @return `true` if the URL is valid, `false` otherwise.
     */
    private fun isValidUrl(url: String): Boolean {
        val urlRegex =
            """^(https?://)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+[a-zA-Z0-9/?#][a-zA-Z0-9\-._?=&%#]*$""".toRegex()
        return try {
            urlRegex.matches(url)
        } catch (_: Exception) {
            false
        }
    }
}
