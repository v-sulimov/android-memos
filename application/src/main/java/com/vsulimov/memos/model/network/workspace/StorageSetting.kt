package com.vsulimov.memos.model.network.workspace

/**
 * Represents the storage settings for the workspace.
 *
 * @property storageType The type of storage.
 * @property filepathTemplate The template for file paths.
 * @property uploadSizeLimitMb The maximum upload size in megabytes.
 * @property s3Config The S3 configuration, if applicable.
 */
data class StorageSetting(
    val storageType: StorageType,
    val filepathTemplate: String,
    val uploadSizeLimitMb: Long,
    val s3Config: S3Config?,
)
