package com.vsulimov.memos.model.network.error

/**
 * Represents an error response from the server.
 *
 * @property code The error code.
 * @property message The error message.
 * @property details Additional details about the error.
 */
data class ErrorResponse(
    val code: Int,
    val message: String,
    val details: List<ErrorDetail>,
)
