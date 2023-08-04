package com.jHerscu.clearskies.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.jHerscu.clearskies.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Make app edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            WindowCompat.getInsetsController(this.window, this.window.decorView)

        windowInsetsController.isAppearanceLightNavigationBars =
            resources.configuration.uiMode != UI_MODE_NIGHT_YES
        // Set splash screen to obey delay set in its vm
        splashScreen.setKeepOnScreenCondition { splashViewModel.isVisible.value }

        setContent {
            AppTheme(
                dynamicColor = true // TODO(jherscu): Add pref for user to toggle on/off Dynamic Theming in app
            ) {
                ClearSkiesApp()
            }
        }
    }
}
