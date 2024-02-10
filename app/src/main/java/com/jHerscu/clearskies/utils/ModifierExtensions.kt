package com.jHerscu.clearskies.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
fun Modifier.padding(
    horizontal: Dp? = null,
    vertical: Dp? = null,
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
) = this then
    Modifier.padding(
        start = horizontal ?: start,
        top = vertical ?: top,
        end = horizontal ?: end,
        bottom = vertical ?: bottom,
    )
