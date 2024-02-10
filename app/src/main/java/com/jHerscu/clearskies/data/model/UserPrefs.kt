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
    val sortingFun: (List<TranslatedPrefGroup>) -> List<PreferenceGroup>,
) {
    // TODO(jherscu): add sorting usage
    DEFAULT(
        R.string.default_sort,
        { _ -> PreferenceGroup.defaultSortOrder },
    ),
    MOST_CLICKED(
        R.string.most_clicked_sort,
        { _ ->
            PreferenceGroup.defaultSortOrder // TODO(jherscu): instument interactions by card to prefs to form sort order
        },
    ),

    // Use translated strings as alphabetical could change based on language
    ALPHABETICAL(
        R.string.alphabetical_sort,
        { translatedPrefGroup ->
            translatedPrefGroup
                .sortedBy { it.translatedTitle }
                .map { it.prefGroup }
        },
    ),
    REVERSE_ALPHABETICAL(
        R.string.rev_alphabetical_sort,
        { translatedPrefGroup ->
            translatedPrefGroup
                .sortedByDescending { it.translatedTitle }
                .map { it.prefGroup }
        },
    ),
    CUSTOM(
        R.string.custom_sort,
        { _ ->
            // TODO(jherscu): launch bottom sheet to select custom order and preserve as default for bottom sheet state in memory
            PreferenceGroup.defaultSortOrder
        },
    ),
}
