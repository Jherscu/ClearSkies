package com.jHerscu.clearskies.domain.repoInterface

import androidx.annotation.StringRes
import androidx.datastore.preferences.core.Preferences
import com.jHerscu.clearskies.R
import kotlinx.coroutines.flow.Flow

enum class TempUnitPref(@StringRes val labelRes: Int) {
    FAHRENHEIT(R.string.degrees_fahrenheit),
    CELSIUS(R.string.degrees_celcius)
}

enum class LockedThemePref(@StringRes val labelRes: Int) {
    LOCK_DARK(R.string.dark_mode),
    LOCK_LIGHT(R.string.light_mode),
    UNLOCKED(R.string.unlocked)
}

enum class HomeDisplayIntervalPref(@StringRes val labelRes: Int) {
    HOURLY(R.string.hourly),
    DAILY(R.string.daily)
}

enum class PreferenceGroup(@StringRes val titleRes: Int) {
    THEME(R.string.theme_pref_group_title),
    GENERAL(R.string.general_pref_group_title),
    HOME(R.string.home_screen)
    // TODO(jherscu): NOTIFICATIONS(R.string.notifications_pref_group_title)
    ;

    companion object {
        val defaultSortOrder = listOf(GENERAL, THEME, HOME)
    }
}

data class TranslatedPrefGroup(
    val translatedTitle: String,
    val prefGroup: PreferenceGroup
)

enum class PreferenceOrderComparator(
    val titleRes: Int,
    val sortingFun: (List<TranslatedPrefGroup>) -> List<PreferenceGroup>
) { // TODO(jherscu): add sorting usage
    DEFAULT(
        R.string.default_sort,
        { _ -> PreferenceGroup.defaultSortOrder }
    ),
    MOST_CLICKED(
        R.string.most_clicked_sort,
        { _ ->
            PreferenceGroup.defaultSortOrder // TODO(jherscu): instument interactions by card to prefs to form sort order
        }
    ),
    ALPHABETICAL( // Use translated strings as alphabetical could change based on language
        R.string.alphabetical_sort,
        { translatedPrefGroup ->
            translatedPrefGroup
                .sortedBy { it.translatedTitle }
                .map { it.prefGroup }
        }
    ),
    REVERSE_ALPHABETICAL(
        R.string.rev_alphabetical_sort,
        { translatedPrefGroup ->
            translatedPrefGroup
                .sortedByDescending { it.translatedTitle }
                .map { it.prefGroup }
        }
    ),
    CUSTOM(
        R.string.custom_sort,
        { _ ->
            PreferenceGroup.defaultSortOrder // TODO(jherscu): launch bottom sheet to select custom order and preserve as default for bottom sheet state in memory
        }
    )
}

data class UserPrefs(
    val twentyFourHourModeOn: Boolean = false,
    val dynamicThemingOn: Boolean = true,
    val tempUnit: TempUnitPref = TempUnitPref.FAHRENHEIT,
    val lockedTheme: LockedThemePref = LockedThemePref.UNLOCKED,
    val homeDisplayInterval: HomeDisplayIntervalPref = HomeDisplayIntervalPref.DAILY,
    val prefOrderComparator: PreferenceOrderComparator = PreferenceOrderComparator.DEFAULT
)

interface PreferencesRepo {

    suspend fun writeToStore(preference: Preferences.Key<Boolean>, option: Boolean)

    suspend fun writeToStore(preference: Preferences.Key<String>, option: String)

    fun readFromStore(): Flow<Preferences>
}
