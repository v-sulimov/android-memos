package com.vsulimov.memos.parser.network.workspace

import com.vsulimov.memos.model.network.workspace.CustomProfile
import com.vsulimov.memos.model.network.workspace.GeneralSetting
import com.vsulimov.memos.model.network.workspace.MemoRelatedSetting
import com.vsulimov.memos.model.network.workspace.S3Config
import com.vsulimov.memos.model.network.workspace.StorageSetting
import com.vsulimov.memos.model.network.workspace.StorageType
import com.vsulimov.memos.model.network.workspace.WorkspaceResponse
import org.json.JSONObject

object WorkspaceResponseParser {
    /**
     * Parses a JSON string into a [WorkspaceResponse] object.
     *
     * @param json The JSON string to parse.
     * @return The parsed [WorkspaceResponse] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    fun parseWorkspaceResponse(json: String): WorkspaceResponse {
        val jsonObject = JSONObject(json)
        val name = jsonObject.getString("name")
        val generalSetting = parseGeneralSetting(jsonObject.getJSONObject("generalSetting"))
        val storageSetting = parseStorageSetting(jsonObject.getJSONObject("storageSetting"))
        val memoRelatedSetting = parseMemoRelatedSetting(jsonObject.getJSONObject("memoRelatedSetting"))
        return WorkspaceResponse(name, generalSetting, storageSetting, memoRelatedSetting)
    }

    /**
     * Parses a [JSONObject] into a [GeneralSetting] object.
     *
     * @param jsonObject The JSONObject to parse.
     * @return The parsed [GeneralSetting] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    private fun parseGeneralSetting(jsonObject: JSONObject): GeneralSetting {
        val disallowUserRegistration = jsonObject.getBoolean("disallowUserRegistration")
        val disallowPasswordAuth = jsonObject.getBoolean("disallowPasswordAuth")
        val additionalScript = jsonObject.getString("additionalScript")
        val additionalStyle = jsonObject.getString("additionalStyle")
        val customProfile = parseCustomProfile(jsonObject.getJSONObject("customProfile"))
        val weekStartDayOffset = jsonObject.getInt("weekStartDayOffset")
        val disallowChangeUsername = jsonObject.getBoolean("disallowChangeUsername")
        val disallowChangeNickname = jsonObject.getBoolean("disallowChangeNickname")
        return GeneralSetting(
            disallowUserRegistration,
            disallowPasswordAuth,
            additionalScript,
            additionalStyle,
            customProfile,
            weekStartDayOffset,
            disallowChangeUsername,
            disallowChangeNickname,
        )
    }

    /**
     * Parses a [JSONObject] into a [CustomProfile] object.
     *
     * @param jsonObject The JSONObject to parse.
     * @return The parsed [CustomProfile] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    private fun parseCustomProfile(jsonObject: JSONObject): CustomProfile {
        val title = jsonObject.getString("title")
        val description = jsonObject.getString("description")
        val logoUrl = jsonObject.getString("logoUrl")
        val locale = jsonObject.getString("locale")
        val appearance = jsonObject.getString("appearance")
        return CustomProfile(title, description, logoUrl, locale, appearance)
    }

    /**
     * Parses a [JSONObject] into a [StorageSetting] object.
     *
     * @param jsonObject The JSONObject to parse.
     * @return The parsed [StorageSetting] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    private fun parseStorageSetting(jsonObject: JSONObject): StorageSetting {
        val storageTypeStr = jsonObject.getString("storageType")
        val storageType = StorageType.valueOf(storageTypeStr)
        val filepathTemplate = jsonObject.getString("filepathTemplate")
        val uploadSizeLimitMb = jsonObject.getLong("uploadSizeLimitMb")
        val s3ConfigJson = jsonObject.optJSONObject("s3Config")
        val s3Config = s3ConfigJson?.let { parseS3Config(it) }
        return StorageSetting(storageType, filepathTemplate, uploadSizeLimitMb, s3Config)
    }

    /**
     * Parses a [JSONObject] into a [S3Config] object.
     *
     * @param jsonObject The JSONObject to parse.
     * @return The parsed [S3Config] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    private fun parseS3Config(jsonObject: JSONObject): S3Config {
        val accessKeyId = jsonObject.getString("accessKeyId")
        val accessKeySecret = jsonObject.getString("accessKeySecret")
        val endpoint = jsonObject.getString("endpoint")
        val region = jsonObject.getString("region")
        val bucket = jsonObject.getString("bucket")
        val usePathStyle = jsonObject.getBoolean("usePathStyle")
        return S3Config(accessKeyId, accessKeySecret, endpoint, region, bucket, usePathStyle)
    }

    /**
     * Parses a [JSONObject] into a [MemoRelatedSetting] object.
     *
     * @param jsonObject The JSONObject to parse.
     * @return The parsed [MemoRelatedSetting] object.
     * @throws JSONException if the JSON is malformed or missing required fields.
     */
    private fun parseMemoRelatedSetting(jsonObject: JSONObject): MemoRelatedSetting {
        val disallowPublicVisibility = jsonObject.getBoolean("disallowPublicVisibility")
        val displayWithUpdateTime = jsonObject.getBoolean("displayWithUpdateTime")
        val contentLengthLimit = jsonObject.getInt("contentLengthLimit")
        val enableAutoCompact = jsonObject.getBoolean("enableAutoCompact")
        val enableDoubleClickEdit = jsonObject.getBoolean("enableDoubleClickEdit")
        val enableLinkPreview = jsonObject.getBoolean("enableLinkPreview")
        val enableComment = jsonObject.getBoolean("enableComment")
        val enableLocation = jsonObject.getBoolean("enableLocation")
        val defaultVisibility = jsonObject.getString("defaultVisibility")
        val reactionsJson = jsonObject.getJSONArray("reactions")
        val reactions = mutableListOf<String>()
        for (i in 0 until reactionsJson.length()) {
            reactions.add(reactionsJson.getString(i))
        }
        val disableMarkdownShortcuts = jsonObject.getBoolean("disableMarkdownShortcuts")
        return MemoRelatedSetting(
            disallowPublicVisibility,
            displayWithUpdateTime,
            contentLengthLimit,
            enableAutoCompact,
            enableDoubleClickEdit,
            enableLinkPreview,
            enableComment,
            enableLocation,
            defaultVisibility,
            reactions,
            disableMarkdownShortcuts,
        )
    }
}
