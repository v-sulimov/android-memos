package com.vsulimov.memos.parser.network.workspace

import com.vsulimov.memos.model.network.workspace.StorageType
import com.vsulimov.memos.parser.network.error.ErrorResponseParser.parseErrorResponse
import com.vsulimov.memos.parser.network.workspace.WorkspaceResponseParser.parseWorkspaceResponse
import org.json.JSONException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WorkspaceResponseParserTest {
    @Test
    fun testParseWorkspaceResponse() {
        val json =
            """
            {
                "name": "settings/example",
                "generalSetting": {
                    "disallowUserRegistration": true,
                    "disallowPasswordAuth": false,
                    "additionalScript": "script",
                    "additionalStyle": "style",
                    "customProfile": {
                        "title": "Title",
                        "description": "Description",
                        "logoUrl": "http://example.com/logo",
                        "locale": "en",
                        "appearance": "light"
                    },
                    "weekStartDayOffset": 1,
                    "disallowChangeUsername": false,
                    "disallowChangeNickname": true
                },
                "storageSetting": {
                    "storageType": "S3",
                    "filepathTemplate": "assets/{timestamp}_{filename}",
                    "uploadSizeLimitMb": 10,
                    "s3Config": {
                        "accessKeyId": "key",
                        "accessKeySecret": "secret",
                        "endpoint": "http://s3.example.com",
                        "region": "us-west-1",
                        "bucket": "mybucket",
                        "usePathStyle": true
                    }
                },
                "memoRelatedSetting": {
                    "disallowPublicVisibility": false,
                    "displayWithUpdateTime": true,
                    "contentLengthLimit": 1000,
                    "enableAutoCompact": true,
                    "enableDoubleClickEdit": false,
                    "enableLinkPreview": true,
                    "enableComment": true,
                    "enableLocation": false,
                    "defaultVisibility": "PRIVATE",
                    "reactions": ["like", "love"],
                    "disableMarkdownShortcuts": false
                }
            }
            """.trimIndent()

        val response = parseWorkspaceResponse(json)

        assertEquals("settings/example", response.name)
        with(response.generalSetting) {
            assertTrue(disallowUserRegistration)
            assertFalse(disallowPasswordAuth)
            assertEquals("script", additionalScript)
            assertEquals("style", additionalStyle)
            with(customProfile) {
                assertEquals("Title", title)
                assertEquals("Description", description)
                assertEquals("http://example.com/logo", logoUrl)
                assertEquals("en", locale)
                assertEquals("light", appearance)
            }
            assertEquals(1, weekStartDayOffset)
            assertFalse(disallowChangeUsername)
            assertTrue(disallowChangeNickname)
        }
        with(response.storageSetting) {
            assertEquals(StorageType.S3, storageType)
            assertEquals("assets/{timestamp}_{filename}", filepathTemplate)
            assertEquals(10L, uploadSizeLimitMb)
            with(s3Config!!) {
                assertEquals("key", accessKeyId)
                assertEquals("secret", accessKeySecret)
                assertEquals("http://s3.example.com", endpoint)
                assertEquals("us-west-1", region)
                assertEquals("mybucket", bucket)
                assertTrue(usePathStyle)
            }
        }
        with(response.memoRelatedSetting) {
            assertFalse(disallowPublicVisibility)
            assertTrue(displayWithUpdateTime)
            assertEquals(1000, contentLengthLimit)
            assertTrue(enableAutoCompact)
            assertFalse(enableDoubleClickEdit)
            assertTrue(enableLinkPreview)
            assertTrue(enableComment)
            assertFalse(enableLocation)
            assertEquals("PRIVATE", defaultVisibility)
            assertEquals(listOf("like", "love"), reactions)
            assertFalse(disableMarkdownShortcuts)
        }
    }

    @Test(expected = JSONException::class)
    fun testParseWorkspaceResponseMissingField() {
        val json =
            """
            {
                "name": "settings/example",
                "generalSetting": {
                    "disallowPasswordAuth": false
                },
                "storageSetting": {
                    "storageType": "S3",
                    "filepathTemplate": "path",
                    "uploadSizeLimitMb": 10
                },
                "memoRelatedSetting": {
                    "disallowPublicVisibility": false,
                    "displayWithUpdateTime": true,
                    "contentLengthLimit": 1000,
                    "enableAutoCompact": true,
                    "enableDoubleClickEdit": false,
                    "enableLinkPreview": true,
                    "enableComment": true,
                    "enableLocation": false,
                    "defaultVisibility": "PRIVATE",
                    "reactions": ["like"],
                    "disableMarkdownShortcuts": false
                }
            }
            """.trimIndent()
        parseWorkspaceResponse(json)
    }

    @Test
    fun testParseErrorResponse() {
        val json =
            """
            {
                "code": 400,
                "message": "Bad Request",
                "details": [
                    {
                        "@type": "type.googleapis.com/google.rpc.ErrorInfo",
                        "reason": "INVALID_INPUT",
                        "domain": "example.com"
                    }
                ]
            }
            """.trimIndent()

        val response = parseErrorResponse(json)

        assertEquals(400, response.code)
        assertEquals("Bad Request", response.message)
        assertEquals(1, response.details.size)
        with(response.details[0]) {
            assertEquals("type.googleapis.com/google.rpc.ErrorInfo", type)
            assertEquals(mapOf("reason" to "INVALID_INPUT", "domain" to "example.com"), additionalProperties)
        }
    }

    @Test(expected = JSONException::class)
    fun testParseErrorResponseMissingField() {
        val json =
            """
            {
                "message": "Bad Request",
                "details": []
            }
            """.trimIndent()
        parseErrorResponse(json)
    }
}
