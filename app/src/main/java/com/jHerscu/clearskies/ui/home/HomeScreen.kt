package com.jHerscu.clearskies.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jHerscu.clearskies.ui.components.FloatingMenuButton
import com.jHerscu.clearskies.ui.components.WeatherItem
import com.jHerscu.clearskies.ui.theme.Dimen
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope

// TODO(jherscu): Create
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
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
        }

        val testItems =
            persistentListOf(
                WeatherItem(
                    timeMillis = 1724634000000L,
                    iconUrl = "",
                    humidity = 36.5f,
                    lowTemp = 23.0f,
                    highTemp = 46.2f,
                ),
                WeatherItem(
                    timeMillis = 1724637600000L,
                    iconUrl = "",
                    humidity = 36.5f,
                    lowTemp = 23.0f,
                    highTemp = 46.2f,
                ),
                WeatherItem(
                    timeMillis = 1724641200000L,
                    iconUrl = "",
                    humidity = 36.5f,
                    lowTemp = 23.0f,
                    highTemp = 46.2f,
                ),
                WeatherItem(
                    timeMillis = 1724644800000L,
                    iconUrl = "",
                    humidity = 36.5f,
                    lowTemp = 23.0f,
                    highTemp = 46.2f,
                ),
            )

        LazyRow(
            modifier = Modifier,
        ) {
            // TODO(jherscu): Add contentType if a new type needs adding
            itemsIndexed(
                items = testItems,
                key = { _, item: WeatherItem -> item.timeMillis },
            ) { index, item: WeatherItem ->
                // TODO(jherscu): Implement items
                Box(
                    modifier =
                        Modifier
                            // TODO(jherscu): implement curve once created using trig to determine offset from center
                            .graphicsLayer {
                                // TODO(jherscu): scale down as the item moves further left/right and up as it approaches center
                                scaleX = 1f
                                scaleY = 1f
                            }
                            .drawBehind {
                                drawCircle(
                                    color = Color.Black,
                                    radius = 150.dp.toPx(),
                                )
                            },
                )
            }
        }
    }
}
