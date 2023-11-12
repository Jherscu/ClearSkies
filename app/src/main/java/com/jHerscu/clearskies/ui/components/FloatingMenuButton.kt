package com.jHerscu.clearskies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jHerscu.clearskies.R
import com.jHerscu.clearskies.ui.theme.CARD_TONAL_ELEVATION_DP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FloatingMenuButton(
    drawerState: DrawerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        text = {
            Text(
                text = stringResource(id = R.string.menu),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        icon = {
            Image(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary)
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = CARD_TONAL_ELEVATION_DP.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        expanded = true,
        onClick = {
            scope.launch {
                drawerState.open()
            }
        }
    )
}
