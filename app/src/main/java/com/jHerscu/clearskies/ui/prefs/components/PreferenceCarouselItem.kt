package com.jHerscu.clearskies.ui.prefs.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PreferenceCarouselItem(
    sortMethod: String,
    modifier: Modifier = Modifier,
    onItemTap: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onItemTap() }
        ) {
            Text(
                text = sortMethod,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
