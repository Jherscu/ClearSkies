package com.jHerscu.clearskies.data.model

import androidx.annotation.StringRes
import com.jHerscu.clearskies.R

enum class PreferenceGroup(
    @StringRes val titleRes: Int,
) {
    THEME(R.string.theme_pref_group_title),
    GENERAL(R.string.general_pref_group_title),
    HOME(R.string.home_screen),
    // TODO(jherscu): NOTIFICATIONS(R.string.notifications_pref_group_title)
    ;

    companion object {
        val defaultSortOrder = listOf(GENERAL, THEME, HOME)
    }
}

data class TranslatedPrefGroup(
    val translatedTitle: String,
    val prefGroup: PreferenceGroup,
)
