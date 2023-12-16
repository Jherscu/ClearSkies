package com.jHerscu.clearskies.ui.prefs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jHerscu.clearskies.ui.theme.ON_CARD_TONAL_ELEVATION_DP
import com.jHerscu.clearskies.ui.theme.Dimen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PreferenceSwitchComponent(
    title: String,
    isInColumn: Boolean,
    optionIsSelected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    setOption: (Boolean) -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ON_CARD_TONAL_ELEVATION_DP.dp
        )
    ) {
        FlowRow(
            modifier = Modifier
                .padding(vertical = Dimen.MEDIUM.dp, horizontal = Dimen.SMALL.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Start
        ) {
            val spacerModifier = Modifier.then(
                if (isInColumn) {
                    Modifier.weight(1F)
                } else {
                    Modifier.width(Dimen.STANDARD.dp)
                }
            )
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = spacerModifier)
            Switch(
                modifier = Modifier,
                enabled = enabled,
                checked = optionIsSelected,
                onCheckedChange = { setOption(it) }
            )
        }
    }
}
