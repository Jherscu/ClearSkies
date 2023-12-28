package com.jHerscu.clearskies.domain.preferences

import androidx.datastore.preferences.core.Preferences
import com.jHerscu.clearskies.data.model.HomeDisplayIntervalPref
import com.jHerscu.clearskies.data.model.LockedThemePref
import com.jHerscu.clearskies.data.model.PreferenceOrderComparator
import com.jHerscu.clearskies.data.model.TempUnitPref
import com.jHerscu.clearskies.data.model.UserPrefs
import com.jHerscu.clearskies.data.repo.PreferencesRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadUserPrefsUseCase
    @Inject
    constructor(
        private val prefsRepo: PreferencesRepo,
    ) {
        operator fun invoke() =
            prefsRepo
                .readFromStore()
                .map { mapPreferences(it) }

        private fun mapPreferences(prefs: Preferences): UserPrefs {
            return UserPrefs(
                twentyFourHourModeOn = prefs[PreferencesRepo.TWENTY_FOUR_HOUR_MODE_KEY] ?: false,
                dynamicThemingOn = prefs[PreferencesRepo.DYNAMIC_THEMING_KEY] ?: true,
                tempUnit =
                    TempUnitPref.valueOf(
                        prefs[PreferencesRepo.TEMP_UNIT_KEY] ?: TempUnitPref.FAHRENHEIT.name,
                    ),
                lockedTheme =
                    LockedThemePref.valueOf(
                        prefs[PreferencesRepo.LOCKED_THEME_KEY] ?: LockedThemePref.UNLOCKED.name,
                    ),
                homeDisplayInterval =
                    HomeDisplayIntervalPref.valueOf(
                        prefs[PreferencesRepo.HOME_DISPLAY_INTERVAL_KEY] ?: HomeDisplayIntervalPref.DAILY.name,
                    ),
                prefOrderComparator =
                    PreferenceOrderComparator.valueOf(
                        prefs[PreferencesRepo.PREF_ORDER_COMPARATOR_KEY] ?: PreferenceOrderComparator.DEFAULT.name,
                    ),
            )
        }
    }
