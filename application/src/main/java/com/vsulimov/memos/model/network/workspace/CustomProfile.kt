package com.vsulimov.memos.model.network.workspace

/**
 * Represents the custom profile settings.
 *
 * @property title The title of the profile.
 * @property description The description of the profile.
 * @property logoUrl The URL of the logo.
 * @property locale The locale setting.
 * @property appearance The appearance setting.
 */
data class CustomProfile(
    val title: String,
    val description: String,
    val logoUrl: String,
    val locale: String,
    val appearance: String,
)
