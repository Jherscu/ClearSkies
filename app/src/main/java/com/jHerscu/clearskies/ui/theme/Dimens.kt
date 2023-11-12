package com.jHerscu.clearskies.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 2 **/
const val BASE_TONAL_ELEVATION_DP = 2

/** 3 **/
const val CARD_TONAL_ELEVATION_DP = 3

/** 4 **/
const val ON_CARD_TONAL_ELEVATION_DP = 4

const val CORNER_RADIUS_DP = 16

// Based on Material 3 Spacing Recommendations
private const val PADDING_EXTRA_LARGE = 24
private const val PADDING_LARGE = 20
private const val PADDING_STANDARD = 16
private const val PADDING_MEDIUM = 12
private const val PADDING_SMALL = 8
private const val PADDING_EXTRA_SMALL = 4

enum class Padding(val dp: Dp) {
    /** 4 **/
    EXTRA_SMALL(PADDING_EXTRA_SMALL.dp),

    /** 8 **/
    SMALL(PADDING_SMALL.dp),

    /** 12 **/
    MEDIUM(PADDING_MEDIUM.dp),

    /** 16 **/
    STANDARD(PADDING_STANDARD.dp),

    /** 20 **/
    LARGE(PADDING_LARGE.dp),

    /** 24 **/
    EXTRA_LARGE(PADDING_EXTRA_LARGE.dp)
}
