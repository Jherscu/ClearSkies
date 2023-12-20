package com.jHerscu.clearskies.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jHerscu.clearskies.domain.repoInterface.LockedThemePref
import com.jHerscu.clearskies.domain.useCase.preferences.ReadThemePreferencesUseCase
import com.jHerscu.clearskies.domain.useCase.preferences.ThemePreferences
import com.jHerscu.clearskies.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject protected lateinit var readThemePreferences: ReadThemePreferencesUseCase

    private val splashViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Make app edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            WindowCompat.getInsetsController(this.window, this.window.decorView)

        windowInsetsController.isAppearanceLightNavigationBars =
            resources.configuration.uiMode != UI_MODE_NIGHT_YES // TODO(jherscu): determine whether to map to locked theme pref?
        // Set splash screen to obey delay set in its vm
        splashScreen.setKeepOnScreenCondition { splashViewModel.isVisible.value }

        setContent {
            val themePreferences by readThemePreferences()
                .collectAsStateWithLifecycle(initialValue = ThemePreferences())

            AppTheme(
                darkTheme =
                    when (themePreferences.lockedThemePref) {
                        LockedThemePref.UNLOCKED -> isSystemInDarkTheme()
                        LockedThemePref.LOCK_DARK -> true
                        LockedThemePref.LOCK_LIGHT -> false
                    },
                dynamicColor = themePreferences.dynamicTheming,
            ) {
                ClearSkiesApp()
            }
        }
    }
}
