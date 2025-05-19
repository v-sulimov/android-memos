package com.vsulimov.memos.model.network.error

/**
 * Represents additional details about an error.
 *
 * @property type The type URL of the error detail.
 * @property additionalProperties Any additional properties.
 */
data class ErrorDetail(
    val type: String,
    val additionalProperties: Map<String, Any>,
)
