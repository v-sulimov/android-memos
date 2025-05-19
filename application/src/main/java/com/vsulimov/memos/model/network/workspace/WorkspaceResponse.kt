package com.vsulimov.memos.model.network.workspace

/**
 * Represents the response from the GET /api/v1/workspace/{name} endpoint.
 *
 * @property name The name of the setting. Format: settings/{setting}
 * @property generalSetting The general settings for the workspace.
 * @property storageSetting The storage settings for the workspace.
 * @property memoRelatedSetting The memo-related settings for the workspace.
 */
data class WorkspaceResponse(
    val name: String,
    val generalSetting: GeneralSetting,
    val storageSetting: StorageSetting,
    val memoRelatedSetting: MemoRelatedSetting,
)
