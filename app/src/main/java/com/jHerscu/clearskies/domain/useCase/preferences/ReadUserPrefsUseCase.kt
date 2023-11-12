package com.jHerscu.clearskies.domain.useCase.preferences

import androidx.datastore.preferences.core.Preferences
import com.jHerscu.clearskies.data.repo.PreferencesRepoImpl
import com.jHerscu.clearskies.domain.repoInterface.HomeDisplayIntervalPref
import com.jHerscu.clearskies.domain.repoInterface.LockedThemePref
import com.jHerscu.clearskies.domain.repoInterface.PreferenceOrderComparator
import com.jHerscu.clearskies.domain.repoInterface.TempUnitPref
import com.jHerscu.clearskies.domain.repoInterface.UserPrefs
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadUserPrefsUseCase @Inject constructor(
    private val prefsRepo: PreferencesRepoImpl
) {
    operator fun invoke() = prefsRepo
        .readFromStore()
        .map { mapPreferences(it) }

    private fun mapPreferences(prefs: Preferences): UserPrefs {
        return UserPrefs(
            twentyFourHourModeOn = prefs[PreferencesRepoImpl.TWENTY_FOUR_HOUR_MODE_KEY] ?: false,
            dynamicThemingOn = prefs[PreferencesRepoImpl.DYNAMIC_THEMING_KEY] ?: true,
            tempUnit = TempUnitPref.valueOf(
                prefs[PreferencesRepoImpl.TEMP_UNIT_KEY] ?: TempUnitPref.FAHRENHEIT.name
            ),
            lockedTheme = LockedThemePref.valueOf(
                prefs[PreferencesRepoImpl.LOCKED_THEME_KEY] ?: LockedThemePref.UNLOCKED.name
            ),
            homeDisplayInterval = HomeDisplayIntervalPref.valueOf(
                prefs[PreferencesRepoImpl.HOME_DISPLAY_INTERVAL_KEY] ?: HomeDisplayIntervalPref.DAILY.name
            ),
            prefOrderComparator = PreferenceOrderComparator.valueOf(
                prefs[PreferencesRepoImpl.PREF_ORDER_COMPARATOR_KEY] ?: PreferenceOrderComparator.DEFAULT.name
            )
        )
    }
}
