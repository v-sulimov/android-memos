package com.vsulimov.memos.model.network.workspace

/**
 * Represents the memo-related settings for the workspace.
 *
 * @property disallowPublicVisibility Whether public visibility is disallowed for memos.
 * @property displayWithUpdateTime Whether to display memos with update time.
 * @property contentLengthLimit The limit of content length in bytes.
 * @property enableAutoCompact Whether to enable auto compact for large content.
 * @property enableDoubleClickEdit Whether to enable editing on double click.
 * @property enableLinkPreview Whether to enable link previews.
 * @property enableComment Whether to enable comments.
 * @property enableLocation Whether to enable setting location for memos.
 * @property defaultVisibility The default visibility for memos.
 * @property reactions The list of available reactions.
 * @property disableMarkdownShortcuts Whether to disable markdown shortcuts.
 */
data class MemoRelatedSetting(
    val disallowPublicVisibility: Boolean,
    val displayWithUpdateTime: Boolean,
    val contentLengthLimit: Int,
    val enableAutoCompact: Boolean,
    val enableDoubleClickEdit: Boolean,
    val enableLinkPreview: Boolean,
    val enableComment: Boolean,
    val enableLocation: Boolean,
    val defaultVisibility: String,
    val reactions: List<String>,
    val disableMarkdownShortcuts: Boolean,
)
