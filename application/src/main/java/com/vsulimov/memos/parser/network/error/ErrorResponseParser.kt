package com.vsulimov.memos.parser.network.error

import com.vsulimov.memos.model.network.error.ErrorDetail
import com.vsulimov.memos.model.network.error.ErrorResponse
import org.json.JSONObject

object ErrorResponseParser {
    /**
     * Parses a JSON string into an [ErrorResponse] object.
     *
     * @param json The JSON string to parse.
     * @return The parsed [ErrorResponse] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    fun parseErrorResponse(json: String): ErrorResponse {
        val jsonObject = JSONObject(json)
        val code = jsonObject.getInt("code")
        val message = jsonObject.getString("message")
        val detailsJson = jsonObject.optJSONArray("details")
        val details = mutableListOf<ErrorDetail>()
        detailsJson?.let {
            for (i in 0 until it.length()) {
                val detailJson = it.getJSONObject(i)
                val type = detailJson.getString("@type")
                val additionalProperties = mutableMapOf<String, Any>()
                val keys = detailJson.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    if (key != "@type") {
                        additionalProperties[key] = detailJson.get(key)
                    }
                }
                details.add(ErrorDetail(type, additionalProperties))
            }
        }
        return ErrorResponse(code, message, details)
    }
}
