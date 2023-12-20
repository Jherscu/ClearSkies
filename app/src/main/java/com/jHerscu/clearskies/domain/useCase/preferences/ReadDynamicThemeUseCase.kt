package com.jHerscu.clearskies.domain.useCase.preferences

import androidx.datastore.preferences.core.Preferences
import com.jHerscu.clearskies.data.repo.PreferencesRepoImpl
import com.jHerscu.clearskies.domain.repoInterface.LockedThemePref
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadThemePreferencesUseCase
    @Inject
    constructor(
        private val prefsRepo: PreferencesRepoImpl,
    ) {
        operator fun invoke() =
            prefsRepo
                .readFromStore()
                .map { mapPreferences(it) }

        private fun mapPreferences(prefs: Preferences): ThemePreferences {
            return ThemePreferences(
                dynamicTheming = prefs[PreferencesRepoImpl.DYNAMIC_THEMING_KEY] ?: true,
                lockedThemePref =
                    LockedThemePref.valueOf(
                        prefs[PreferencesRepoImpl.LOCKED_THEME_KEY] ?: LockedThemePref.UNLOCKED.name,
                    ),
            )
        }
    }

data class ThemePreferences(
    val dynamicTheming: Boolean = true,
    val lockedThemePref: LockedThemePref = LockedThemePref.UNLOCKED,
)
