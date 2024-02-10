package com.jHerscu.clearskies.domain.preferences

import androidx.datastore.preferences.core.Preferences
import com.jHerscu.clearskies.data.model.LockedThemePref
import com.jHerscu.clearskies.data.repo.PreferencesRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadThemePreferencesUseCase
    @Inject
    constructor(
        private val prefsRepo: PreferencesRepo,
    ) {
        operator fun invoke() =
            prefsRepo
                .readFromStore()
                .map { mapPreferences(it) }

        private fun mapPreferences(prefs: Preferences): ThemePreferences {
            return ThemePreferences(
                dynamicTheming = prefs[PreferencesRepo.DYNAMIC_THEMING_KEY] ?: true,
                lockedThemePref =
                    LockedThemePref.valueOf(
                        prefs[PreferencesRepo.LOCKED_THEME_KEY] ?: LockedThemePref.UNLOCKED.name,
                    ),
            )
        }
    }

data class ThemePreferences(
    val dynamicTheming: Boolean = true,
    val lockedThemePref: LockedThemePref = LockedThemePref.UNLOCKED,
)
