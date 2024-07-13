package com.jHerscu.clearskies.data.model

import androidx.annotation.StringRes
import com.jHerscu.clearskies.R

data class UserPrefs(
    val twentyFourHourModeOn: Boolean = false,
    val dynamicThemingOn: Boolean = true,
    val tempUnit: TempUnitPref = TempUnitPref.FAHRENHEIT,
    val lockedTheme: LockedThemePref = LockedThemePref.UNLOCKED,
    val homeDisplayInterval: HomeDisplayIntervalPref = HomeDisplayIntervalPref.DAILY,
    val prefOrderComparator: PreferenceOrderComparator = PreferenceOrderComparator.DEFAULT,
)

enum class TempUnitPref(
    @StringRes val labelRes: Int,
) {
    FAHRENHEIT(R.string.degrees_fahrenheit),
    CELSIUS(R.string.degrees_celcius),
}

enum class LockedThemePref(
    @StringRes val labelRes: Int,
) {
    LOCK_DARK(R.string.dark_mode),
    LOCK_LIGHT(R.string.light_mode),
    UNLOCKED(R.string.unlocked),
}

enum class HomeDisplayIntervalPref(
    @StringRes val labelRes: Int,
) {
    HOURLY(R.string.hourly),
    DAILY(R.string.daily),
}

enum class PreferenceOrderComparator(
    val titleRes: Int,
) {
    DEFAULT(R.string.default_sort),
    MOST_CLICKED(R.string.most_clicked_sort),
    ALPHABETICAL(R.string.alphabetical_sort),
    REVERSE_ALPHABETICAL(R.string.rev_alphabetical_sort),
    CUSTOM(R.string.custom_sort),
    ;

    fun sortPreferences(translatedPrefGroups: List<TranslatedPrefGroup>): List<PreferenceGroup> {
        return when (this) {
            DEFAULT -> PreferenceGroup.defaultSortOrder
            MOST_CLICKED -> {
                // TODO(jherscu): instument interactions by card to prefs to form sort order
                PreferenceGroup.defaultSortOrder
            }

            ALPHABETICAL -> {
                // Use translated strings as alphabetical could change based on language
                translatedPrefGroups
                    .sortedBy { it.translatedTitle }
                    .map { it.prefGroup }
            }

            REVERSE_ALPHABETICAL -> {
                translatedPrefGroups
                    .sortedByDescending { it.translatedTitle }
                    .map { it.prefGroup }
            }

            CUSTOM -> {
                // TODO(jherscu): launch bottom sheet to select custom order and preserve as default for bottom sheet state in memory
                PreferenceGroup.defaultSortOrder
            }
        }
    }
}
