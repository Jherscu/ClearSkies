package com.jHerscu.clearskies.ui

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.ui.prefs.PreferencesScreen
import com.jHerscu.clearskies.ui.theme.BASE_TONAL_ELEVATION_DP
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val NAV_DRAWER_TOP_PADDING_DP = 36

enum class Route(@StringRes val title: Int) {
    HOME(R.string.home_screen),
    LOCATIONS(R.string.locations_screen),
    ADD_CITY(R.string.add_city_screen),
    PREFERENCES(R.string.preferences_screen),
    ONBOARDING(R.string.onboarding_screen)
}

data class DrawerItem(
    val icon: ImageVector,
    val screen: Route
)


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ClearSkiesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
    drawerItemsList: ImmutableList<DrawerItem> = persistentListOf(
        DrawerItem(
            Icons.Default.Home,
            Route.HOME
        ),
        DrawerItem(
            Icons.Default.LocationOn,
            Route.LOCATIONS
        ),
        DrawerItem(
            Icons.Default.Edit,
            Route.PREFERENCES
        )
    )
) {
    Surface {
        val windowSizeClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)
        val backStackState = navController.currentBackStackEntryAsState()
        AppNavDrawer(
            drawerState = drawerState,
            drawerItemsList = drawerItemsList,
            backStackState = backStackState,
            scope = scope,
            navController = navController
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = BASE_TONAL_ELEVATION_DP.dp
            ) {
                NavHost(
                    modifier = modifier,
                    navController = navController,
                    startDestination = Route.HOME.name
                ) {
                    composable(Route.HOME.name) {
                        TestScreen(title = "HOME")
                    }
                    composable(Route.LOCATIONS.name) {
                        TestScreen(title = "LOCATIONS") // TODO(jherscu): create to track ime insets
                        // TODO(jherscu): FAB should always be above system bars while rest of layout overlaps
                    }
                    composable(Route.ADD_CITY.name) {
                        TestScreen(title = "ADD CITY") // TODO(jherscu): create to track ime insets
                    }
                    composable(Route.PREFERENCES.name) {
                        PreferencesScreen(
                            drawerState = drawerState,
                            scope = scope,
                            windowSizeClass = windowSizeClass
                        )
                    }
                    composable(Route.ONBOARDING.name) {
                        // TODO(jherscu): decide how to load api keys for both services
                        // likely via some sort of webview experience coupled with drag and drop/copy and paste
                        // possibly after first key is successfully loaded and a test ping is made with a 2xx response,
                        // reload webview with new url for second service signup
                        // Then tooltip exp could possibly be triggered in above standard screens
                    }
                }
            }
        }
    }
}

@Composable
private fun AppNavDrawer(
    drawerState: DrawerState,
    drawerItemsList: ImmutableList<DrawerItem>,
    backStackState: State<NavBackStackEntry?>,
    scope: CoroutineScope,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer( // TODO(jherscu): Disable drawer while onboarding experience is on/ add onboarding logic
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                drawerTonalElevation = BASE_TONAL_ELEVATION_DP.dp
            ) {
                val selectedColor = MaterialTheme.colorScheme.secondaryContainer
                Spacer(Modifier.height(NAV_DRAWER_TOP_PADDING_DP.dp))
                drawerItemsList.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(stringResource(id = item.screen.title)) },
                        selected = item.screen.name == backStackState.value?.destination?.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.screen.name)
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            selectedContainerColor = MaterialTheme.colorScheme.secondary,
                            selectedIconColor = selectedColor,
                            selectedTextColor = selectedColor
                        ),
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        content()
    }
}

@Composable
private fun TestScreen( // TODO(jherscu): Rm test layouts for real impl
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title)
    }
}
