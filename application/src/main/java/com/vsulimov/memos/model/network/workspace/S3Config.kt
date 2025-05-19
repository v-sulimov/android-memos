package com.vsulimov.memos.model.network.workspace

/**
 * Represents the S3 configuration.
 *
 * @property accessKeyId The access key ID for S3.
 * @property accessKeySecret The access key secret for S3.
 * @property endpoint The S3 endpoint.
 * @property region The S3 region.
 * @property bucket The S3 bucket name.
 * @property usePathStyle Whether to use path-style access.
 */
data class S3Config(
    val accessKeyId: String,
    val accessKeySecret: String,
    val endpoint: String,
    val region: String,
    val bucket: String,
    val usePathStyle: Boolean,
)
