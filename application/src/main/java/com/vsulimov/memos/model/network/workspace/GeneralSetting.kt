package com.vsulimov.memos.model.network.workspace

/**
 * Represents the general settings for the workspace.
 *
 * @property disallowUserRegistration Whether user registration is disallowed.
 * @property disallowPasswordAuth Whether password authentication is disallowed.
 * @property additionalScript The additional script.
 * @property additionalStyle The additional style.
 * @property customProfile The custom profile settings.
 * @property weekStartDayOffset The week start day offset from Sunday (0-6).
 * @property disallowChangeUsername Whether changing username is disallowed.
 * @property disallowChangeNickname Whether changing nickname is disallowed.
 */
data class GeneralSetting(
    val disallowUserRegistration: Boolean,
    val disallowPasswordAuth: Boolean,
    val additionalScript: String,
    val additionalStyle: String,
    val customProfile: CustomProfile,
    val weekStartDayOffset: Int,
    val disallowChangeUsername: Boolean,
    val disallowChangeNickname: Boolean,
)
