package com.jHerscu.clearskies.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jHerscu.clearskies.R

enum class ClearSkiesScreen(@StringRes val title: Int) {
    HOME(R.string.home_screen)
}

@Composable
fun ClearSkiesApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ClearSkiesScreen.HOME.name // TODO(jherscu): define enum with destinations
    ) {
        composable(ClearSkiesScreen.HOME.name) {

        }
    }
}
