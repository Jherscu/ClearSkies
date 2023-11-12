package com.jHerscu.clearskies.ui.prefs.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jHerscu.clearskies.ui.theme.ON_CARD_TONAL_ELEVATION_DP
import com.jHerscu.clearskies.ui.theme.Padding
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PreferenceRadioComponent(
    title: String,
    options: ImmutableList<RadioComponentData>,
    optionIsSelected: (String) -> Boolean,
    modifier: Modifier = Modifier,
    setOption: (String) -> Unit
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
        Column(
            modifier = Modifier
                .padding(vertical = Padding.MEDIUM.dp, horizontal = Padding.SMALL.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            for (option in options) {
                RadioButtonWithTitle(
                    title = stringResource(id = option.labelRes),
                    selected = optionIsSelected(option.name)
                ) {
                    setOption(option.name)
                }
            }
        }
    }
}

@Composable
private fun RadioButtonWithTitle(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = Padding.MEDIUM.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RadioButton(
            modifier = Modifier
                .padding(end = Padding.SMALL.dp),
            selected = selected,
            onClick = { onClick() }
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

interface RadioComponentData {
    @get:StringRes
    val labelRes: Int
    val name: String
}
