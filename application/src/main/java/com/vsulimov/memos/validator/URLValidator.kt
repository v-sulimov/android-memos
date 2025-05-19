package com.vsulimov.memos.validator

/**
 * A utility class for validating URLs according to specific rules for server URLs.
 * The validator ensures that URLs use the HTTPS schema and follow valid domain patterns
 * (e.g., domain.com, subdomain.domain.com).
 */
object URLValidator {
    // Regex for validating URLs with valid domain patterns, requiring at least two segments
    private val URL_REGEX =
        Regex(
            "^https://([a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$",
        )

    /**
     * Validates a URL and ensures it uses HTTPS and follows valid domain patterns.
     * - If no schema is provided, `https://` is added and validated.
     * - If `http://` is used, validation fails with an error.
     * - If `https://` is provided, the URL is validated against domain rules.
     *
     * @param url The URL string to validate.
     * @return A [Result] containing the valid URL (with `https://` if added) or an [IllegalArgumentException] if invalid.
     */
    fun validateUrl(url: String): Result<String> {
        // Trim whitespace and normalize input
        val trimmedUrl = url.trim()

        // Case 1: No schema provided, add https:// and validate
        if (!trimmedUrl.startsWith("http://") && !trimmedUrl.startsWith("https://")) {
            val httpsUrl = "https://$trimmedUrl"
            return if (URL_REGEX.matches(httpsUrl)) {
                Result.success(httpsUrl)
            } else {
                Result.failure(IllegalArgumentException("Invalid URL format"))
            }
        }

        // Case 2: HTTP schema provided
        if (trimmedUrl.startsWith("http://")) {
            return Result.failure(IllegalArgumentException("Only HTTPS schema is allowed"))
        }

        // Case 3: HTTPS schema provided, validate
        if (trimmedUrl.startsWith("https://")) {
            return if (URL_REGEX.matches(trimmedUrl)) {
                Result.success(trimmedUrl)
            } else {
                Result.failure(IllegalArgumentException("Invalid URL format"))
            }
        }

        // Fallback for any unexpected cases
        return Result.failure(IllegalArgumentException("Invalid URL format"))
    }
}
