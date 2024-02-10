package com.jHerscu.clearskies.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jHerscu.clearskies.ui.theme.CARD_TONAL_ELEVATION_DP
import com.jHerscu.clearskies.ui.theme.Dimen
import com.jHerscu.clearskies.utils.padding

@Composable
fun PrimaryElevatedCard(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ElevatedCard(
        modifier =
            modifier
                .fillMaxHeight()
                .padding(
                    horizontal = Dimen.STANDARD.dp,
                    top = Dimen.SMALL.dp,
                ),
        colors =
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = CARD_TONAL_ELEVATION_DP.dp,
            ),
    ) {
        Column(
            modifier = columnModifier,
        ) {
            content()
        }
    }
}
