package com.jHerscu.clearskies.ui.prefs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.data.model.HomeDisplayIntervalPref
import com.jHerscu.clearskies.data.model.LockedThemePref
import com.jHerscu.clearskies.data.model.PreferenceGroup
import com.jHerscu.clearskies.data.model.PreferenceOrderComparator
import com.jHerscu.clearskies.data.model.TempUnitPref
import com.jHerscu.clearskies.data.model.TranslatedPrefGroup
import com.jHerscu.clearskies.ui.components.CarouselComponent
import com.jHerscu.clearskies.ui.components.FloatingMenuButton
import com.jHerscu.clearskies.ui.components.PrimaryElevatedCard
import com.jHerscu.clearskies.ui.prefs.PreferencesViewModel.Intent
import com.jHerscu.clearskies.ui.prefs.components.PreferenceCarouselItem
import com.jHerscu.clearskies.ui.prefs.components.PreferenceRadioComponent
import com.jHerscu.clearskies.ui.prefs.components.PreferenceSwitchComponent
import com.jHerscu.clearskies.ui.prefs.components.RadioComponentData
import com.jHerscu.clearskies.ui.theme.Dimen
import com.jHerscu.clearskies.ui.theme.ON_CARD_TONAL_ELEVATION_DP
import com.jHerscu.clearskies.utils.padding
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreferencesScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: PreferencesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    // Local pref which resets to default when page is left and returned to
    var isColumn by remember { mutableStateOf(true) }
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(viewState.value.userPrefs.prefOrderComparator) {
        this.launch {
            val sorted =
                with(viewState.value) {
                    viewModel.sortPrefGroups(
                        translatedPrefGroups =
                            prefGroups.map {
                                TranslatedPrefGroup(
                                    translatedTitle = context.getString(it.titleRes),
                                    prefGroup = it,
                                )
                            },
                        comparator = userPrefs.prefOrderComparator,
                    )
                }
            viewModel.updatePrefGroupOrder(sorted)
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(bottom = Dimen.MEDIUM.dp),
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Row(
            modifier =
                Modifier.padding(
                    vertical = Dimen.MEDIUM.dp,
                    horizontal = Dimen.STANDARD.dp,
                ),
        ) {
            FloatingMenuButton(
                drawerState = drawerState,
                scope = scope,
            )
            Spacer(modifier = Modifier.weight(1f))
            PreferenceSwitchComponent(
                title = stringResource(id = R.string.group_by_column),
                optionIsSelected = isColumn,
                setOption = { isColumn = it },
                isInColumn = false,
            )
        }
        PrimaryElevatedCard(
            columnModifier = Modifier.padding(Dimen.MEDIUM.dp),
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns =
                    if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                        GridCells.Fixed(2)
                    } else {
                        GridCells.Fixed(1)
                    },
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                val prefGroups = viewState.value.prefGroups
                item(
                    span = {
                        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                            GridItemSpan(2)
                        } else {
                            GridItemSpan(1)
                        }
                    },
                ) {
                    val carouselItems = remember { PreferenceOrderComparator.entries }
                    CarouselComponent(
                        modifier =
                            Modifier
                                .padding(
                                    vertical = Dimen.MEDIUM.dp,
                                    horizontal = Dimen.EXTRA_SMALL.dp,
                                ),
                        count = carouselItems.size,
                        title = {
                            Text(
                                modifier =
                                    Modifier
                                        .align(Alignment.Start)
                                        .padding(
                                            start = Dimen.MEDIUM.dp,
                                            top = Dimen.MEDIUM.dp,
                                        ),
                                text = stringResource(id = R.string.sort_prefs),
                            )
                        },
                    ) { item ->
                        PreferenceCarouselItem(
                            sortMethod = stringResource(id = carouselItems[item].titleRes),
                        ) {
                            viewModel.passIntent(Intent.UpdateSortOrderClick(carouselItems[item].name))
                        }
                    }
                }
                items(
                    items = prefGroups,
                    // TODO(jherscu): Declare localized string as key in case of language pref change?
                    key = { it.name },
                ) { prefGroup ->
                    GroupedPreferencesCard(
                        modifier =
                            Modifier
                                .then(
                                    if (prefGroup != prefGroups.last()) {
                                        Modifier.padding(
                                            bottom = Dimen.STANDARD.dp,
                                            horizontal = Dimen.EXTRA_SMALL.dp,
                                        )
                                    } else {
                                        Modifier.padding(horizontal = Dimen.EXTRA_SMALL.dp)
                                    },
                                ),
                        isColumn = isColumn,
                        title = { Text(text = stringResource(id = prefGroup.titleRes)) },
                    ) {
                        val weightModifier =
                            Modifier.then(
                                if (isColumn) {
                                    Modifier.fillMaxWidth()
                                } else {
                                    Modifier.weight(1f)
                                },
                            )
                        when (prefGroup) {
                            PreferenceGroup.THEME -> {
                                PreferenceSwitchComponent(
                                    isInColumn = isColumn,
                                    modifier = weightModifier,
                                    title = stringResource(id = R.string.dynamic_theming),
                                    optionIsSelected = viewState.value.userPrefs.dynamicThemingOn,
                                    setOption = {
                                        viewModel.passIntent(
                                            Intent.DynamicThemingModeClick(
                                                it,
                                            ),
                                        )
                                    },
                                )
                                Spacer(modifier = Modifier.size(Dimen.MEDIUM.dp))
                                PreferenceRadioComponent(
                                    modifier = weightModifier,
                                    title = stringResource(id = R.string.lock_theme),
                                    options =
                                        LockedThemePref.entries.map {
                                            RadioComponentData(
                                                labelRes = it.labelRes,
                                                name = it.name,
                                            )
                                        }.toImmutableList(),
                                    optionIsSelected = { it == viewState.value.userPrefs.lockedTheme.name },
                                    setOption = { viewModel.passIntent(Intent.LockThemePrefClick(it)) },
                                )
                            }

                            PreferenceGroup.HOME -> {
                                PreferenceRadioComponent(
                                    modifier = weightModifier,
                                    title = stringResource(id = R.string.home_screen_pref_title),
                                    options =
                                        HomeDisplayIntervalPref.entries.map {
                                            RadioComponentData(
                                                labelRes = it.labelRes,
                                                name = it.name,
                                            )
                                        }.toImmutableList(),
                                    optionIsSelected = { it == viewState.value.userPrefs.homeDisplayInterval.name },
                                    setOption = {
                                        viewModel.passIntent(
                                            Intent.HomeDisplayIntervalPrefClick(
                                                it,
                                            ),
                                        )
                                    },
                                )
                                // TODO(jherscu): Determine if spacer with same weight is needed for row config
                            }

                            PreferenceGroup.GENERAL -> {
                                PreferenceRadioComponent(
                                    modifier = weightModifier,
                                    title = stringResource(id = R.string.temp_unit_pref_title),
                                    options =
                                        TempUnitPref.entries.map {
                                            RadioComponentData(
                                                labelRes = it.labelRes,
                                                name = it.name,
                                            )
                                        }.toImmutableList(),
                                    optionIsSelected = { it == viewState.value.userPrefs.tempUnit.name },
                                    setOption = { viewModel.passIntent(Intent.TempUnitPrefClick(it)) },
                                )
                                Spacer(modifier = Modifier.size(Dimen.MEDIUM.dp))
                                PreferenceSwitchComponent(
                                    isInColumn = isColumn,
                                    modifier = weightModifier,
                                    title = stringResource(id = R.string.twenty_four_hour_mode),
                                    optionIsSelected = viewState.value.userPrefs.twentyFourHourModeOn,
                                    setOption = {
                                        viewModel.passIntent(
                                            Intent.TwentyFourHourModeClick(
                                                it,
                                            ),
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    } // TODO(jherscu): Add space for bottom bar tablet/nav
}

@Composable
private fun GroupedPreferencesCard(
    isColumn: Boolean,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val movableContent = remember { movableContentOf(content) }
    ElevatedCard(
        modifier = modifier,
        colors =
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = ON_CARD_TONAL_ELEVATION_DP.dp,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(vertical = Dimen.MEDIUM.dp, horizontal = Dimen.SMALL.dp),
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.titleMedium) { // Set default style
                title()
            }
            Spacer(modifier = Modifier.height(Dimen.MEDIUM.dp))
            if (isColumn) {
                movableContent()
            } else {
                Row {
                    movableContent()
                }
            }
        }
    }
}
